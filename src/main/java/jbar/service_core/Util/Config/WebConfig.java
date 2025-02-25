package jbar.service_core.Util.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:5173") // Sin la barra final
      .allowedMethods("GET", "POST", "PUT", "DELETE")
      .allowedHeaders("Authorization", "Content-Type", "Accept")
      .allowCredentials(true);
  }
}
