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
                "https://ucore.cloud",       // Dominio de producción
                "http://localhost:5173",    // Desarrollo local
                "http://127.0.0.1:5173"     // Alternativa local
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type", "Accept", "X-Requested-With")
            .exposedHeaders("Authorization") // Headers visibles para el frontend
            .allowCredentials(true)          // Permite cookies/JWT
            .maxAge(3600);                  // Cache de preflight (1 hora)
    }
}