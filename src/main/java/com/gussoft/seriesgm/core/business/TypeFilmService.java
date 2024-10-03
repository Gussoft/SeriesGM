package com.gussoft.seriesgm.core.business;

import com.gussoft.seriesgm.integration.tranfer.request.TypeFilmRequest;
import com.gussoft.seriesgm.integration.tranfer.response.TypeFilmResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeFilmService {

    Flux<TypeFilmResponse> getAll();
    Mono<Page<TypeFilmResponse>> getAllToPage(Pageable pageable, String name);
    Flux<TypeFilmResponse> getByName(String city);
    Mono<TypeFilmResponse> getById(Long id);
    Mono<TypeFilmResponse> create(TypeFilmRequest request);
    Mono<TypeFilmResponse> update(TypeFilmRequest request, Long id);
    Mono<Boolean> delete(Long id);

}
