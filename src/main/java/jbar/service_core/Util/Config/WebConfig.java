package jbar.service_core.Util.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Permite cualquier origen
                .allowedMethods("*") // Permite todos los métodos
                .allowedHeaders("*") // Permite todos los headers
                .allowCredentials(true)
                .maxAge(3600); // Cache de opciones CORS por 1 hora
    }
}
