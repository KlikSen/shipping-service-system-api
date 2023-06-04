package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.entity.Jwt;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.repository.JwtTokenRepository;
import com.example.shippingservicesystemapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${constants.secret-key}")
    private String secretKey;
    @Value("#{new Integer('${constants.interim}')}")
    private int interim;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public String generateJwt(final UserDTO userDTO) {
        LocalDateTime currentDate = LocalDateTime.now();  //current date
        Date expirationDate = Date.from(currentDate.plusDays(interim).atZone(ZoneId.systemDefault()).toInstant()); //current date + interim

        Claims additionalClaims = Jwts.claims();
        additionalClaims.setSubject(userDTO.getEmail());
        additionalClaims.put("userRole", userDTO.getUserRole());

        String token = Jwts.builder()
                .setHeaderParam("alg", "HS512")
                .setHeaderParam("type", "JWT")
                .setClaims(additionalClaims)
                .setIssuedAt(new Date()) //set current time according to timezone
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        Jwt jwt = new Jwt()
                .setToken(token)
                .setUser(userMapper.toEntity(userDTO));

        jwtTokenRepository.save(jwt);

        return jwt.getToken();
    }

    @Override
    public boolean isJwtValid(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (Objects.isNull(jwtTokenRepository.findByToken(token))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteJwt(String token) {
        jwtTokenRepository.deleteByToken(token);
    }

    @Override
    public void deleteJwtByUserId(Long userId) {
        jwtTokenRepository.deleteJwtByUserId(userId);
    }

    @Override
    public List<String> getJwtByUserId(Long userId) {
        return jwtTokenRepository.findByUser(userId).stream()
                .map(o -> o.getToken())
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(String token) {
        return userMapper.toDTO(jwtTokenRepository.findByToken(token).getUser());
    }

    @Override
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public String getJwtFromRequest(final HttpServletRequest httpServletRequest) {
        final String token = httpServletRequest.getHeader("Authorization");
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        } else {
            return null;
        }
    }

    @Override
    public String getPrincipalFromRequest(final HttpServletRequest httpServletRequest) {
        final String token = getJwtFromRequest(httpServletRequest);
        if (Objects.nonNull(token)) {
            return getUsername(token);
        }
        throw new IllegalStateException("Principal cannot be extracted from token");
    }
}
