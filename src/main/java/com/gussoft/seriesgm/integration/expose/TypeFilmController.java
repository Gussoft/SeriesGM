package com.gussoft.seriesgm.integration.expose;

import com.gussoft.seriesgm.core.business.TypeFilmService;
import com.gussoft.seriesgm.core.exception.ApiException;
import com.gussoft.seriesgm.integration.tranfer.request.TypeFilmRequest;
import com.gussoft.seriesgm.integration.tranfer.response.GenericResponse;
import com.gussoft.seriesgm.integration.tranfer.response.TypeFilmResponse;
import java.net.URI;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TypeFilmController {

    private final TypeFilmService service;

    @GetMapping("/type-films/pages")
    public Mono<ResponseEntity<Page<TypeFilmResponse>>> findAllToPages(
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) throws ApiException {
        String[] sortArray = sortBy.contains(",")
                ? Arrays.stream(sortBy.split(",")).map(String::trim).toArray(String[]::new)
                : new String[] { sortBy.trim() };

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortArray);
        Pageable pageable = PageRequest.of(page, size, sort);

        return service.getAllToPage(pageable, name)
                .flatMap(play -> Mono.just(ResponseEntity.ok(play)));
    }

    @GetMapping("/type-films/{id_type}")
    public Mono<ResponseEntity<GenericResponse>> getPlayerById(@PathVariable("id_type") Long id) {
        return service.getById(id)
                .map(data -> ResponseEntity.ok(new GenericResponse("OK", data)))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new GenericResponse(throwable.getMessage()))));
    }

    @PostMapping("/type-films")
    public Mono<ResponseEntity<GenericResponse>> create(@RequestBody TypeFilmRequest request, ServerHttpRequest http) {
        return service.create(request)
                .map(data -> ResponseEntity.created(
                        URI.create(http.getURI().toString() + "/" + data.getIdType()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new GenericResponse("OK", data)))
                .onErrorResume(throwable ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new GenericResponse(throwable.getMessage()))));
    }

    @PutMapping("/type-films/{id_type}")
    public Mono<ResponseEntity<GenericResponse>> update(
            @RequestBody TypeFilmRequest request, @PathVariable("id_type") Long id) throws ApiException {
        return service.update(request, id)
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new GenericResponse("OK", data)))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new GenericResponse(throwable.getMessage()))));
    }

    @DeleteMapping("/type-films/{id_type}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id_type") Long id) {
        return service.delete(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<GenericResponse>> handleValidationExceptions(WebExchangeBindException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getFieldErrors().forEach(error ->
                errorMessage.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; "));
        return Mono.just(ResponseEntity.badRequest()
                .body(new GenericResponse(errorMessage.toString())));
    }

}
