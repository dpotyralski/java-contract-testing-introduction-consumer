package pl.dpotyralski.javacontracttestingintroductionconsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTempateConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
