package com.websystique.springmvc.configuration;

import org.springframework.web.servlet.config.annotation.*;


/**
 * Adding @EnableWebMvc annotation to an @Configuration class imports the Spring MVC configuration from
 *To customize the imported configuration, implement the interface WebMvcConfigurer and override individual methods
 *  addFormatters, addResourceHandlers
 */
public class AppConfig implements WebMvcConfigurer {

}

