package org.deblock.exercise.api.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration(proxyBeanMethods = false)
public class FlightSearchRoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(FlightSearchHandler handler) {
        return RouterFunctions.route(GET("/search"), handler::searchFlight);
    }
}
