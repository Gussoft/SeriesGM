package com.gussoft.seriesgm.integration.handler.impl;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import com.gussoft.seriesgm.core.business.CategoryService;
import com.gussoft.seriesgm.core.validation.RequestValidator;
import com.gussoft.seriesgm.integration.handler.CategoryHandler;
import com.gussoft.seriesgm.integration.tranfer.request.CategoryRequest;
import java.net.URI;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryHandlerImpl implements CategoryHandler {

    private final RequestValidator validator;
    private final CategoryService service;

    @Override
    public Mono<ServerResponse> findAllToPages(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String sortBy = request.queryParam("sortBy").orElse("id");
        String sortDirection = request.queryParam("sortDirection").orElse("asc");

        String[] sortArray = sortBy.contains(",")
                ? Arrays.stream(sortBy.split(",")).map(String::trim).toArray(String[]::new)
                : new String[] { sortBy.trim() };

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortArray);
        Pageable pageable = PageRequest.of(page, size, sort);

        return service.findAllToPage(pageable, name)
                .flatMap(pageResult -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(pageResult))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id_category"));

        return service.findById(id)
                .flatMap(data -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(data))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<CategoryRequest> category = request.bodyToMono(CategoryRequest.class);

        return category.flatMap(validator::validate)
                .flatMap(service::create)
                .flatMap(response -> ServerResponse
                        .created(URI.create(request.uri().toString().concat("/")
                                .concat(String.valueOf(response.getIdCategory()))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response)));
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id_category"));
        Mono<CategoryRequest> category = request.bodyToMono(CategoryRequest.class);

        return category.flatMap(validator::validate)
                .flatMap(data -> service.update(data, id))
                .flatMap(e -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id_category"));

        return service.delete(id)
                .flatMap(result -> {
                    if (result) {
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
                    }
                });
    }
}
