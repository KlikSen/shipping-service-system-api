package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.AuthResponseDTO;
import com.example.shippingservicesystemapi.dto.LoginDTO;
import com.example.shippingservicesystemapi.dto.RegisterDTO;
import com.example.shippingservicesystemapi.security.JwtService;
import com.example.shippingservicesystemapi.service.AuthService;
import com.example.shippingservicesystemapi.service.ConfirmationService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthResource {
    @Autowired
    private ConfirmationService confirmationService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated final LoginDTO loginDTO) {
        Object authResponse = authService.login(loginDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated final RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return ResponseEntity.ok("Registered successfully");
    }

    @GetMapping ("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") final String token){
        confirmationService.confirm(token);
        return ResponseEntity.ok("Confirmed");
    }
}
