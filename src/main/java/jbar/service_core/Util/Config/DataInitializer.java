package jbar.service_core.Util.Config;

import jbar.service_core.Menu_Product.Model.MenuProduct;
import jbar.service_core.Menu_Product.Model.MenuProductRepository;
import jbar.service_core.Product_Category.Model.ProductCategory;
import jbar.service_core.Product_Category.Model.ProductCategoryRepository;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Style.Model.Style;
import jbar.service_core.Style.Model.StyleRepository;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Position.Model.PositionRepository;
import jbar.service_core.Category.Model.Category;
import jbar.service_core.Category.Model.CategoryRepository;
import jbar.service_core.Company.Model.Company;
import jbar.service_core.Company.Model.CompanyRepository;
import jbar.service_core.Route.Model.Route;
import jbar.service_core.Route.Model.RouteRepository;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.Site.Model.SiteRepository;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Model.MultimediaRepository;
import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductRepository;
import jbar.service_core.Menu.Model.Menu;
import jbar.service_core.Menu.Model.MenuRepository;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
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
            CategoryRepository categoryRepository,
            MultimediaRepository multimediaRepository,
            ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository, // 🔹 Agregado
            PasswordEncoder passwordEncoder,
            MenuRepository menuRepository,
            MenuProductRepository menuProductRepository
    ) {
        return args -> {
            initializeUsers(userRepository, passwordEncoder);
            initializeStyles(styleRepository);
            initializePositions(positionRepository);
            initializeCompanies(companyRepository);
            initializeRoutes(routeRepository);
            initializeSites(siteRepository, companyRepository);
            initializeCategories(categoryRepository);
            initializeMultimedia(multimediaRepository);
            initializeProducts(productRepository, categoryRepository, productCategoryRepository);
            initializeMenu(menuRepository,productRepository,menuProductRepository);

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
            route.setStatus(Status.ACTIVE);
            routeRepository.save(route);
        }
    }

    // Método para inicializar Sites
    private void initializeSites(SiteRepository siteRepository, CompanyRepository companyRepository) {
        if (siteRepository.count() == 0) {
            Company company = new Company();
            company.setName("Test Company");
            company.setStatus(true);
            company.setCreatedAt(LocalDateTime.now());
            companyRepository.save(company);

            Site site = new Site();
            site.setName("Main Site");
            site.setLocation("Somewhere");
            site.setStatus(true);
            site.setCreatedAt(LocalDateTime.now());
            site.setCompany(company);
            siteRepository.save(site);
        }
    }
// Métodos de inicialización


    private void initializeCategories(CategoryRepository categoryRepository) {
        if (categoryRepository.count() == 0) {
            Category desayuno = new Category("Desayuno", Status.ACTIVE);
            categoryRepository.save(desayuno);

            Category comida = new Category("Comida", Status.ACTIVE);
            categoryRepository.save(comida);

            Category cena = new Category("Cena", Status.ACTIVE);
            categoryRepository.save(cena);
        }
    }

    private void initializeMultimedia(MultimediaRepository multimediaRepository) {
        if (multimediaRepository.count() == 0) {
            Multimedia multimedia = new Multimedia();
            multimedia.setUrl("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/restaurant-logo-design-template-b281aeadaa832c28badd72c1f6c5caad_screen.jpg?ts=1595421543");
            multimediaRepository.save(multimedia);
        }
    }

    private void initializeProducts(ProductRepository productRepository,
                                    CategoryRepository categoryRepository,
                                    ProductCategoryRepository productCategoryRepository) {
        if (productRepository.count() == 0) {
            Optional<Category> desayuno = categoryRepository.findByNameIgnoreCase("Desayuno");
            Optional<Category> comida = categoryRepository.findByNameIgnoreCase("Comida");
            Optional<Category> cena = categoryRepository.findByNameIgnoreCase("Cena");

            desayuno.ifPresent(category -> {
                Product huevos = new Product("Huevos Rancheros", "Huevos fritos con salsa de jitomate y tortillas.", 80.0);
                Product hotCakes = new Product("Hot Cakes", "Panqueques con miel de maple y mantequilla.", 90.0);
                Product chilaquiles = new Product("Chilaquiles Verdes", "Totopos bañados en salsa verde con crema y queso.", 100.0);

                productRepository.saveAll(List.of(huevos, hotCakes, chilaquiles));

                productCategoryRepository.saveAll(List.of(
                        new ProductCategory(huevos, category),
                        new ProductCategory(hotCakes, category),
                        new ProductCategory(chilaquiles, category)
                ));
            });

            comida.ifPresent(category -> {
                Product carne = new Product("Carne Asada", "Corte de res a la parrilla con guarniciones.", 200.0);
                Product tacos = new Product("Tacos al Pastor", "Tortillas con carne de cerdo marinada y piña.", 90.0);
                Product sopa = new Product("Sopa Azteca", "Sopa de tortilla con aguacate y crema.", 110.0);
                Product enchiladas = new Product("Enchiladas Suizas", "Tortillas rellenas de pollo con salsa verde y queso gratinado.", 130.0);

                productRepository.saveAll(List.of(carne, tacos, sopa, enchiladas));

                productCategoryRepository.saveAll(List.of(
                        new ProductCategory(carne, category),
                        new ProductCategory(tacos, category),
                        new ProductCategory(sopa, category),
                        new ProductCategory(enchiladas, category)
                ));
            });

            cena.ifPresent(category -> {
                Product ensalada = new Product("Ensalada César", "Lechuga, aderezo césar, crutones y queso parmesano.", 120.0);
                Product sandwich = new Product("Sándwich de Pollo", "Pan integral con pollo a la plancha y verduras.", 110.0);
                Product pizza = new Product("Pizza Margarita", "Masa delgada con tomate, albahaca y mozzarella.", 150.0);

                productRepository.saveAll(List.of(ensalada, sandwich, pizza));

                productCategoryRepository.saveAll(List.of(
                        new ProductCategory(ensalada, category),
                        new ProductCategory(sandwich, category),
                        new ProductCategory(pizza, category)
                ));
            });
        }
    }


    private void initializeMenu(MenuRepository menuRepository, ProductRepository productRepository, MenuProductRepository menuProductRepository) {
        if (menuRepository.count() == 0) {
            // Crear un nuevo menú
            Menu menu = new Menu("Menú Principal", "Menú con los productos más populares", true);
            menuRepository.save(menu); // Guardamos el menú

            // Obtenemos los productos (en este caso, los primeros dos)
            Optional<Product> producto1 = productRepository.findById(1); // Suponiendo que el primer producto es Huevos Rancheros
            Optional<Product> producto2 = productRepository.findById(2); // Suponiendo que el segundo producto es Carne Asada

            // Relacionamos los productos con el menú
            if (producto1.isPresent() && producto2.isPresent()) {
                MenuProduct menuProduct1 = new MenuProduct(menu, producto1.get());
                MenuProduct menuProduct2 = new MenuProduct(menu, producto2.get());
                menuProductRepository.save(menuProduct1); // Guardamos la relación
                menuProductRepository.save(menuProduct2); // Guardamos la relación
            }
        }
    }

}