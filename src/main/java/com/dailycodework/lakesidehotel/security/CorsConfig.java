package com.dailycodework.lakesidehotel.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * Configuration for CORS setup.
 *
 * Author: Simpson Alfred
 */

@Configuration
@EnableWebMvc
public class CorsConfig {

    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

    // Define the UrlBasedCorsConfigurationSource
    private UrlBasedCorsConfigurationSource buildCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        config.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Define the CorsFilter bean directly for Spring Security
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(buildCorsConfigurationSource());
    }

    // Define the FilterRegistrationBean for filter registration
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(buildCorsConfigurationSource()));
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }
}
