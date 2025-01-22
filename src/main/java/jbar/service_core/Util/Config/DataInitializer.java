package jbar.service_core.Util.Config;

import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Optional;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase( UserRepository userRepository,PasswordEncoder passwordEncoder) {
        return args -> {
            // Lista de usuarios de prueba
            User[] users = new User[]{
                    // Usuarios con rol ADMIN
                    new User("Isaac","lastname", "20233tn182@utez.edu.mx", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1001", new Date(2025,01,22)),
                    new User("Daniel","lastname", "admin2@example.com", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1002", new Date(2025,01,22)),
                    new User("Alexis","lastname", "admin4@example.com", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1004", new Date(2025,01,22)),
                    new User("Alicia","lastname", "admin3@example.com", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1003", new Date(2025,01,22)),

                    // Usuarios con rol LEADER
                    new User("Isaac","lastname", "leader1@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2001", new Date(2025,01,22)),
                    new User("Daniel","lastname", "leader2@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2002", new Date(2025,01,22)),
                    new User("Alexis","lastname", "leader3@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2003", new Date(2025,01,22)),
                    new User("Alicia","lastname", "leader4@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2004", new Date(2025,01,22)),

                    // Usuarios con rol WAITER
                    new User("Isaac","lastname", "waiter1@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3001", new Date(2025,01,22)),
                    new User("Daniel","lastname", "waiter2@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3002", new Date(2025,01,22)),
                    new User("Alexis","lastname", "waiter3@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3003", new Date(2025,01,22)),
                    new User("Alicia","lastname", "waiter4@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3004", new Date(2025,01,22))
            };

            // Guardar los usuarios si no existen
            for (User user : users) {
                Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
                if (!existingUser.isPresent()) {
                    userRepository.saveAndFlush(user);
                }
            }
        };
    }
}
