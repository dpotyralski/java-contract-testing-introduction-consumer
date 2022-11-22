package pl.dpotyralski.javacontracttestingintroductionconsumer;

import lombok.Value;

@Value
public class BidRequest {

    Integer amount;
    String company;

}