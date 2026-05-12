package com.unir.gateway.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unir.gateway.decorator.DeleteRequestDecorator;
import com.unir.gateway.decorator.GetRequestDecorator;
import com.unir.gateway.decorator.PatchRequestDecorator;
import com.unir.gateway.decorator.PostRequestDecorator;
import com.unir.gateway.decorator.PutRequestDecorator;
import com.unir.gateway.model.GatewayRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestDecoratorFactory {

    private final ObjectMapper objectMapper;

    public ServerHttpRequestDecorator getDecorator(GatewayRequest request) {
        return switch (request.getTargetMethod().name().toUpperCase()) {
            case "GET"    -> new GetRequestDecorator(request);
            case "POST"   -> new PostRequestDecorator(request, objectMapper);
            case "PUT"    -> new PutRequestDecorator(request, objectMapper);
            case "PATCH"  -> new PatchRequestDecorator(request, objectMapper);
            case "DELETE" -> new DeleteRequestDecorator(request);
            default -> throw new IllegalArgumentException(
                    "Método HTTP no soportado por el Gateway ACL: " + request.getTargetMethod());
        };
    }
}
