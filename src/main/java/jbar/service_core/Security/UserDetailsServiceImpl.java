package jbar.service_core.Security;

import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // Cambiado de username a email
        User user = usuarioRepository.findUserByEmail(email) // Busca por correo
                .orElseThrow(() -> new UsernameNotFoundException("User no encontrado: " + email)); // Mensaje de error actualizado

        // Convertimos el único rol a un SimpleGrantedAuthority
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRol().name()));
        logger.info("Rol asignado al usuario: ROLE_{}", user.getRol().name());

        // Retornamos el objeto UserDetails con los datos de usuario y el rol
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
