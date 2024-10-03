package com.gussoft.seriesgm.integration.expose;

import com.gussoft.seriesgm.core.business.CategoryService;
import com.gussoft.seriesgm.core.exception.ApiException;
import com.gussoft.seriesgm.integration.tranfer.request.CategoryRequest;
import com.gussoft.seriesgm.integration.tranfer.response.CategoryResponse;
import java.net.URI;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/categories/pages")
    public Mono<ResponseEntity<Page<CategoryResponse>>> findAllToPages(
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) throws ApiException {
        String[] sortArray = sortBy.contains(",")
                ? Arrays.stream(sortBy.split(",")).map(String::trim).toArray(String[]::new)
                : new String[]{sortBy.trim()};

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortArray);
        Pageable pageable = PageRequest.of(page, size, sort);

        return service.findAllToPage(pageable, name)
                .flatMap(play -> Mono.just(ResponseEntity.ok(play)));
    }

    @GetMapping("/categories/{id_category}")
    public Mono<ResponseEntity<CategoryResponse>> getPlayerById(@PathVariable("id_category") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/categories")
    public Mono<ResponseEntity<CategoryResponse>> create(@RequestBody CategoryRequest request, ServerHttpRequest http) {
        return service.create(request)
                .map(data -> ResponseEntity.created(
                        URI.create(http.getURI().toString() + "/" + data.getIdCategory()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/categories/{id_category}")
    public Mono<ResponseEntity<CategoryResponse>> update(
            @RequestBody CategoryRequest request, @PathVariable("id_category") Long id) throws ApiException {
        return service.update(request, id)
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/categories/{id_category}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id_category") Long id) {
        return service.delete(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

}
