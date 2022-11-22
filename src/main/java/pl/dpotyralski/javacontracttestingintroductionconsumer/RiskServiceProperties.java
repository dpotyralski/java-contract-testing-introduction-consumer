package pl.dpotyralski.javacontracttestingintroductionconsumer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "risk-service")
public class RiskServiceProperties {

    private String url;

}