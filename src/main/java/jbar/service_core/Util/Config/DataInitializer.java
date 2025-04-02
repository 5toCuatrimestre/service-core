package jbar.service_core.Util.Config;

import jbar.service_core.Menu_Product.Model.MenuProduct;
import jbar.service_core.Menu_Product.Model.MenuProductRepository;
import jbar.service_core.Product_Category.Model.ProductCategory;
import jbar.service_core.Product_Category.Model.ProductCategoryRepository;
import jbar.service_core.Sell_Detail.Model.SellDetail;
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
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.Sell_Detail.Model.SellDetailRepository;
import jbar.service_core.RatingUserSell.Model.RatingUserSell;
import jbar.service_core.RatingUserSell.Model.RatingUserSellRepository;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class DataInitializer {

    private final RatingUserSellRepository ratingUserSellRepository;

    public DataInitializer(RatingUserSellRepository ratingUserSellRepository) {
        this.ratingUserSellRepository = ratingUserSellRepository;
    }

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
            MenuProductRepository menuProductRepository,
            SellRepository sellRepository,
            SellDetailRepository sellDetailRepository,
            RatingUserSellRepository ratingUserSellRepository
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
            initializeSellsWithDetailsAndRatings(sellRepository, userRepository, productRepository, sellDetailRepository, ratingUserSellRepository);
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
            // Lista de temas a guardar
            List<Map<String, String>> themes = Arrays.asList(
                    Map.of(
                            "H1", "#ffffff",
                            "H2", "#cccccc",
                            "H3", "#aaaaaa",
                            "P", "#000000",
                            "BgCard", "#222222",
                            "BgInterface", "#111111",
                            "BgButton", "#c7c7c7"
                    ),
                    Map.of(
                            "H1", "#000000",
                            "H2", "#333333",
                            "H3", "#000000",
                            "P", "#ffffff",
                            "BgCard", "#f0f0f0",
                            "BgInterface", "#ffffff",
                            "BgButton", "#000000"
                    ),
                    Map.of(
                            "H1", "#ffffff",
                            "H2", "#ffffff",
                            "H3", "#ffffff",
                            "P", "#000000",
                            "BgCard", "#05004a",
                            "BgInterface", "#1f24ab",
                            "BgButton", "#ffffff"
                    )
            );

            // Guardar cada tema como un nuevo objeto Style
            for (int i = 0; i < themes.size(); i++) {
                Map<String, String> theme = themes.get(i);
                Style style = new Style();

                // Establecer el estado (el primer tema será el activo)
                style.setStatus(i == 0);  // El primer tema tiene status = true, los demás false

                style.setH1(theme.get("H1"));
                style.setH2(theme.get("H2"));
                style.setH3(theme.get("H3"));
                style.setP(theme.get("P"));
                style.setBgCard(theme.get("BgCard"));
                style.setBgInterface(theme.get("BgInterface"));
                style.setBgButton(theme.get("BgButton"));

                styleRepository.save(style);
            }
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

    private void initializeSellsWithDetailsAndRatings(SellRepository sellRepository,
                                                      UserRepository userRepository,
                                                      ProductRepository productRepository,  // Usamos ProductRepository para obtener los productos
                                                      SellDetailRepository sellDetailRepository,
                                                      RatingUserSellRepository ratingUserSellRepository) {
        // Obtener productos disponibles
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            System.out.println("No products found in the system.");
            return;
        }
        // Obtener usuarios con rol WAITER
        List<User> waiters = userRepository.findByRol(Rol.WAITER);

        if (waiters.size() < 3) {  // Verificar que haya al menos 5 meseros
            System.out.println("Not enough waiters in the system. At least 3 are required.");
            return;
        }
        // Crear una instancia de Random
        Random random = new Random();
        // Crear 5 ventas (1 venta por cada mesero)
        for (int i = 0; i < 5; i++) {
            User user = waiters.get(random.nextInt(waiters.size()));  // Usamos solo meseros  // Asegurarse de que no se exceda del tamaño de la lista  // Asignar usuarios de forma cíclica

            // Crear una nueva venta
            Sell sell = new Sell();
            sell.setUser(user);
            sell.setTotalPrice(generateTotalPrice());  // Generamos un precio aleatorio para la venta
            sell.setSellDate(new Timestamp(System.currentTimeMillis()));  // Fecha y hora actuales
            sell.setSellTime(new java.sql.Time(System.currentTimeMillis()));  // Hora actual
            sell.setStatus(true);

            // Guardar la venta
            sellRepository.save(sell);

            // Crear los detalles de la venta, asociando productos con IDs 1, 2 y 3
            createSellDetails(sell, products, sellDetailRepository);

            // Crear la calificación de la venta (solo si el usuario es un mesero, por ejemplo)
            if (user.getRol() == Rol.WAITER) {
                createRatingForSell(sell, user, ratingUserSellRepository);
            }
        }

        System.out.println("Sample sells created with details and ratings.");
    }

    private void createSellDetails(Sell sell, List<Product> products, SellDetailRepository sellDetailRepository) {
        Random random = new Random();
        int[] productIds = {1, 2, 3};  // Los IDs de los productos predefinidos

        // Seleccionar entre 1 a 3 productos aleatorios para la venta
        int numProducts = random.nextInt(3) + 1;

        for (int i = 0; i < numProducts; i++) {
            int productId = productIds[random.nextInt(productIds.length)];  // Seleccionar producto aleatorio por ID
            Product product = products.stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);  // Obtener el producto por ID

            if (product == null) {
                System.out.println("Product with ID " + productId + " not found.");
                continue;  // Si el producto no existe, salimos y pasamos al siguiente
            }

            // Crear detalle de venta
            SellDetail sellDetail = new SellDetail();
            sellDetail.setSell(sell);
            sellDetail.setProduct(product);  // Asociar el producto completo
            sellDetail.setQuantity(random.nextInt(3) + 1);  // Cantidad aleatoria de productos
            sellDetail.setUnitPrice(product.getPrice());  // Obtener el precio del producto
            sellDetail.setTotalPrice(sellDetail.getQuantity() * sellDetail.getUnitPrice());

            // Guardar el detalle de la venta
            sellDetailRepository.save(sellDetail);
        }
    }

    private void createRatingForSell(Sell sell, User waiter, RatingUserSellRepository ratingUserSellRepository) {
        // Crear una calificación para la venta
        RatingUserSell rating = new RatingUserSell();
        rating.setSell(sell);
        rating.setUser(waiter);  // Asociar el mesero
        rating.setStars(new Random().nextInt(5) + 1);  // Asignar una calificación aleatoria (1-5)

        // Guardar la calificación
        ratingUserSellRepository.save(rating);
    }

    private double generateTotalPrice() {
        Random random = new Random();
        double totalPrice = 0;

        // Generamos un precio aleatorio sumando precios de productos aleatorios
        int[] productPrices = {80, 90, 100};  // Precios de los productos con IDs 1, 2, 3

        int numProducts = random.nextInt(3) + 1;
        for (int i = 0; i < numProducts; i++) {
            totalPrice += productPrices[random.nextInt(productPrices.length)];
        }

        return totalPrice;
    }


}