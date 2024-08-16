package com.ecombackend.excelr.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:3000","http://localhost:3001")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//
//
//    @Bean
//    BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
    
//	==============================================
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList(
	            "http://localhost:3000", // React frontend
	            "http://localhost:3001",
	            "http://localhost:4000",
	            "http://localhost:4200",
	            "femsha-shop-website.vercel.app"
	        ));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS" , "PATCH"));
	        configuration.setAllowedHeaders(Collections.singletonList("*"));
	        configuration.setExposedHeaders(Arrays.asList("Authorization"));
	        configuration.setAllowCredentials(true);
	        configuration.setMaxAge(3600L);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
