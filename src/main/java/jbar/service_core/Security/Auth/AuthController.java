package jbar.service_core.Security.Auth;

import jbar.service_core.Security.Auth.Model.AuthRequest;
import jbar.service_core.Security.Auth.Model.AuthResponse;
import jbar.service_core.Security.Jwt.JwtUtil;

import jbar.service_core.Security.UserDetailsServiceImpl;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;

import jbar.service_core.Util.Enum.Status;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, UserRepository userRepository, AuthService authService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        User user = userRepository.findUserByEmail(authRequest.getEmail()).orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new Exception("El usuario está inactivo y no puede realizar préstamos");
        }

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new Exception("Correo o contraseña incorrectos");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails, user.getUserId());  // Aquí pasamos el ID del usuario

        if (authService.isTokenInvalid(jwt)) {
            throw new Exception("Token inválido");
        }

        long expirationTime = jwtUtil.getExpirationTime();

        return new AuthResponse(jwt, user.getUserId(), user.getEmail(), expirationTime);
    }


    @DeleteMapping("/logout")
    public ResponseEntity<Message> logout(@RequestHeader(value = "Authorization", required = false) String token) throws Exception {
        if (token == null || token.trim().isEmpty()) {
            return new ResponseEntity<>(new Message("Hubo un error", TypesResponse.ERROR), HttpStatus.UNAUTHORIZED);
        }
        if (!token.startsWith("Bearer ")) {
            return new ResponseEntity<>(new Message("Hubo un error", TypesResponse.ERROR), HttpStatus.UNAUTHORIZED);
        }
        if (authService.isTokenInvalid(token)) {
            throw new Exception("Token inválido");
        }

        String jwt = token.substring(7);
        authService.invalidateToken(jwt);

        return new ResponseEntity<>(new Message("Logout exitoso", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
