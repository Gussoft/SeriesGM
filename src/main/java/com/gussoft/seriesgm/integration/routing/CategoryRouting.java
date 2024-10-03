package com.gussoft.seriesgm.integration.routing;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import com.gussoft.seriesgm.integration.handler.CategoryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CategoryRouting {

    @Bean
    public RouterFunction<ServerResponse> categoryRoutes(CategoryHandler handler) {
        return RouterFunctions
                .route(GET("/v2/categories/pages"), handler::findAllToPages)
                .andRoute(GET("/v2/categories/{id_category}"), handler::findById)
                .andRoute(POST("/v2/categories"), handler::save)
                .andRoute(PUT("/v2/categories/{id_category}"), handler::update)
                .andRoute(DELETE("/v2/categories/{id_category}"), handler::delete);
    }
}
