package com.example.buensaboruno.config;

import com.example.buensaboruno.validations.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfig {

    @Bean
    public UserValidation userValidation(){
        return new UserValidation();
    }
}
