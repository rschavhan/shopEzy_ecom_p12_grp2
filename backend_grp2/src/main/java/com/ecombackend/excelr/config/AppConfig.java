package com.ecombackend.excelr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            
            .authorizeHttpRequests(authorize -> authorize
                // Admin endpoints
                .requestMatchers("/api/admin/**").permitAll()
                .requestMatchers("/api/addresses/**").permitAll()
                .requestMatchers("/api/orders/**").permitAll()
                // User management (admin only)
                .requestMatchers("/api/users/**").permitAll()
                
                // User endpoints
                .requestMatchers("/api/products/**").permitAll()
                
                // Public endpoints
                .requestMatchers("/api/home/**").permitAll()
                
                // Auth endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/cart/**").permitAll()
                .requestMatchers("/api/wishlist/**").permitAll()

                // Other requests
                .anyRequest().permitAll()
            )
            // If you're using JWT, add JWT filter here
            // .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .cors(Customizer.withDefaults());  // Enable CORS configuration

        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(
//            "http://localhost:3000", // React frontend
//            "http://localhost:3001",
//            "http://localhost:4000",
//            "http://localhost:4200",
//            "femsha-shop-website.vercel.app"
//        ));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.setExposedHeaders(Arrays.asList("Authorization"));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}