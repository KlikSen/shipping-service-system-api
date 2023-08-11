package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.JwtDTO;
import com.example.shippingservicesystemapi.dto.LoginDTO;
import com.example.shippingservicesystemapi.service.AuthService;
import com.example.shippingservicesystemapi.service.ConfirmationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthResource {
    @Autowired
    private ConfirmationService confirmationService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@RequestBody @Validated final LoginDTO loginDTO) {
        JwtDTO authResponse = authService.login(loginDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping( "/do_logout")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<String> logout(final HttpServletRequest httpServletRequest) {
        authService.logout(httpServletRequest);
        return ResponseEntity.ok("Successful logout");
    }

    @GetMapping ("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") final String token){
        confirmationService.confirm(token);
        return ResponseEntity.ok("Confirmed");
    }

}
