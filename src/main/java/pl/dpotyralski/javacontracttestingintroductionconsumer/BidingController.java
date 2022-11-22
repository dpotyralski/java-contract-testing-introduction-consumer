package pl.dpotyralski.javacontracttestingintroductionconsumer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class BidingController {

    private final RestTemplate restTemplate;
    private final RiskServiceProperties riskServiceProperties;

    @PostMapping(path = "/bids")
    public ResponseEntity<BidResponse> createBid(@RequestBody BidRequest request) {
        if (!isRiskAcceptable(request)) {
            return new ResponseEntity<>(new BidResponse(BidResponseStatus.BID_REJECTED), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new BidResponse(BidResponseStatus.BID_CREATED));
    }

    private boolean isRiskAcceptable(@RequestBody BidRequest request) {
        ResponseEntity<ValidateRiskResponse> exchange = restTemplate.exchange(getRequestEntity(request), ValidateRiskResponse.class);

        if (Objects.isNull(exchange.getBody())) {
            throw new RuntimeException("Could not get body from the response !");
        }

        return Objects.equals(exchange.getBody().getStatus(), RiskResponseStatus.OK);
    }

    private RequestEntity<ValidateRiskRequest> getRequestEntity(@RequestBody BidRequest request) {
        URI uri = UriComponentsBuilder.fromUri(URI.create(riskServiceProperties.getUrl())).build().encode().toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new RequestEntity<>(new ValidateRiskRequest(request.getAmount(), request.getCompany()), httpHeaders, HttpMethod.POST, uri);
    }

}