package com.nano.demoware.hystrix.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@PropertySource("classpath:app.properties")
@ComponentScan("com.nano.demoware.hystrix")
/** This class enables all the Spring configuration magic */
public class AppConfiguration extends WebMvcConfigurerAdapter {

    // lame Spring artifact--can't inject properties without this
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
