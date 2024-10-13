package uz.uzgps.productservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
//public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .cors().and()
                .oauth2ResourceServer()
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://192.168.1.104:8080");  // TODO: lock down before deploying
        config.addAllowedOrigin("http://192.168.1.104:9090");  // TODO: lock down before deploying
        config.addAllowedOrigin("http://192.168.0.108:8080");  // TODO: lock down before deploying
        config.addAllowedOrigin("http://192.168.0.108:9090");  // TODO: lock down before deploying
//        config.addAllowedOrigin("http://localhost:8080");  // TODO: lock down before deploying
//        config.addAllowedOrigin("http://localhost:9090");  // TODO: lock down before deploying
        config.addAllowedHeader("*");
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /** Reset prefix to be empty. */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
        return jwtConverter;
    }


//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
//    }

}