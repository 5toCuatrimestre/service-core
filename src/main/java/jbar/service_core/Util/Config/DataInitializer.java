package jbar.service_core.Util.Config;

import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Style.Model.Style;
import jbar.service_core.Style.Model.StyleRepository;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Position.Model.PositionRepository;
import jbar.service_core.Company.Model.Company;
import jbar.service_core.Company.Model.CompanyRepository;
import jbar.service_core.Route.Model.Route;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Site.Model.SiteRepository;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            StyleRepository styleRepository,
            PositionRepository positionRepository,
            CompanyRepository companyRepository,
            RouteRepository routeRepository,
            SiteRepository siteRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // Inicialización de usuarios
            initializeUsers(userRepository, passwordEncoder);

            // Inicialización de Styles
            initializeStyles(styleRepository);

            // Inicialización de Positions
            initializePositions(positionRepository);

            // Inicialización de Companies
            initializeCompanies(companyRepository);

            // Inicialización de Routes
            initializeRoutes(routeRepository);

            // Inicialización de Sites
            // Inicialización de Sites
            initializeSites(siteRepository, companyRepository); // Pasamos el companyRepository aquí
            System.out.println("Data Initialization complete.");
        };
    }

    // Método para inicializar usuarios
    private void initializeUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        User[] users = new User[] {
                new User("Isaac", "lastname", "20233tn182@utez.edu.mx", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1001", new Date(2025, 01, 22)),
                new User("Daniel", "lastname", "admin2@example.com", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1002", new Date(2025, 01, 22)),
                new User("Alexis", "lastname", "admin4@example.com", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1004", new Date(2025, 01, 22)),
                new User("Alicia", "lastname", "admin3@example.com", passwordEncoder.encode("admin123"), Status.ACTIVE, Rol.ADMIN, "555-1003", new Date(2025, 01, 22)),
                new User("Isaac", "lastname", "leader1@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2001", new Date(2025, 01, 22)),
                new User("Daniel", "lastname", "leader2@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2002", new Date(2025, 01, 22)),
                new User("Alexis", "lastname", "leader3@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2003", new Date(2025, 01, 22)),
                new User("Alicia", "lastname", "leader4@example.com", passwordEncoder.encode("leader123"), Status.ACTIVE, Rol.LEADER, "555-2004", new Date(2025, 01, 22)),
                new User("Isaac", "lastname", "waiter1@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3001", new Date(2025, 01, 22)),
                new User("Daniel", "lastname", "waiter2@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3002", new Date(2025, 01, 22)),
                new User("Alexis", "lastname", "waiter3@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3003", new Date(2025, 01, 22)),
                new User("Alicia", "lastname", "waiter4@example.com", passwordEncoder.encode("waiter123"), Status.ACTIVE, Rol.WAITER, "555-3004", new Date(2025, 01, 22))
        };

        for (User user : users) {
            Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
            if (!existingUser.isPresent()) {
                userRepository.saveAndFlush(user);
            }
        }
    }



    // Método para inicializar Styles
    private void initializeStyles(StyleRepository styleRepository) {
        if (styleRepository.count() == 0) {
            Style style = new Style();
            style.setStatus(true);
            style.setH1("#000000");
            style.setH2("#222222");
            style.setH3("#444444");
            style.setP("#666666");
            style.setBgCard("#FFFFFF");
            style.setBgInterface("#EEEEEE");
            style.setBgButton("#FF0000");
            styleRepository.save(style);
        }
    }

    // Método para inicializar Positions
    private void initializePositions(PositionRepository positionRepository) {
        if (positionRepository.count() == 0) {
            Position position = new Position();
            position.setName("Manager");
            position.setDescription("Oversees operations");
            positionRepository.save(position);
        }
    }

    // Método para inicializar Companies
    private void initializeCompanies(CompanyRepository companyRepository) {
        if (companyRepository.count() == 0) {
            Company company = new Company();
            company.setName("Test Company");
            company.setStatus(true);
            company.setCreatedAt(LocalDateTime.now());
            companyRepository.save(company);
        }
    }

    // Método para inicializar Routes
    private void initializeRoutes(RouteRepository routeRepository) {
        if (routeRepository.count() == 0) {
            Route route = new Route();
            route.setName("Main Route");
            route.setStatus(Status.ACTIVE);  // Usamos el enum Status
            routeRepository.save(route);
        }
    }

    // Método para inicializar Sites
    private void initializeSites(SiteRepository siteRepository, CompanyRepository companyRepository) {
        if (siteRepository.count() == 0) {
            Company company = new Company(); // Aseguramos que el company esté asociado
            company.setName("Test Company");
            company.setStatus(true);
            company.setCreatedAt(LocalDateTime.now());
            companyRepository.save(company);  // Guardamos la compañía primero

            Site site = new Site();
            site.setName("Main Site");
            site.setLocation("Somewhere");
            site.setStatus(true);
            site.setCreatedAt(LocalDateTime.now());
            site.setCompany(company); // Asociamos la compañía al sitio
            siteRepository.save(site);  // Finalmente guardamos el sitio
        }
    }
}