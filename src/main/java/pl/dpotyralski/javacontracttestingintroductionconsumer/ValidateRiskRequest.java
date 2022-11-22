package pl.dpotyralski.javacontracttestingintroductionconsumer;

import lombok.Value;

@Value
class ValidateRiskRequest {

    int amount;
    String company;

}