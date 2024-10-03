package com.gussoft.seriesgm.core.business;

import com.gussoft.seriesgm.integration.tranfer.request.CategoryRequest;
import com.gussoft.seriesgm.integration.tranfer.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<CategoryResponse> getAll();
    Mono<Page<CategoryResponse>> findAllToPage(Pageable pageable, String name);
    Flux<CategoryResponse> findByName(String name);
    Mono<CategoryResponse> findById(Long id);
    Mono<CategoryResponse> create(CategoryRequest request);
    Mono<CategoryResponse> update(CategoryRequest request, Long id);
    Mono<Boolean> delete(Long id);

}
