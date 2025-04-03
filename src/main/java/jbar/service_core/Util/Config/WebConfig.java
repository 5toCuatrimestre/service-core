  package jbar.service_core.Util.Config;

  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.CorsRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

  @Configuration
  public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
              .allowedOrigins(
                      "http://localhost:3000",
                      "http://localhost:8081",
                      "http://localhost:8080",
                      "http://localhost:5000" // Añade esto si es necesario
              )
              .allowedMethods("*") // Permite todos los métodos
              .allowedHeaders("*") // Permite todos los headers
              .allowCredentials(true)
              .maxAge(3600); // Cache de opciones CORS por 1 hora
    }
  }
