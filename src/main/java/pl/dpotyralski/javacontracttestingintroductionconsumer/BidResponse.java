package pl.dpotyralski.javacontracttestingintroductionconsumer;

import lombok.Value;

@Value
class BidResponse {
    BidResponseStatus status;
}