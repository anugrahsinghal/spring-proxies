package com.example.springproxies;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
public class YourShinyAppConfig {

    @Primary
    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public BeanThatNeedsObjectMapper sameInstances() {
        ObjectMapper mapper1 = mapper();
        ObjectMapper mapper2 = mapper();

        if(mapper1 == mapper2) {
            log.info("AppConfig.sameInstances have Same bean instances");
        }

        return new BeanThatNeedsObjectMapper(mapper1);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ObjectMapper prototypeMapper() {
        return new ObjectMapper();
    }

    @Bean
    public BeanThatNeedsObjectMapper diffInstances() {
        ObjectMapper mapper1 = prototypeMapper();
        ObjectMapper mapper2 = prototypeMapper();

        if(mapper1 != mapper2) {
            log.info("AppConfig.diffInstances have Different bean instances");
        }

        return new BeanThatNeedsObjectMapper(mapper1);
    }

}

@RequiredArgsConstructor
class BeanThatNeedsObjectMapper {
    private final ObjectMapper objectMapper;
}