package com.gussoft.seriesgm.core.business.impl;

import static com.gussoft.seriesgm.core.utils.Constrains.NOT_FOUND;

import com.gussoft.seriesgm.core.business.CategoryService;
import com.gussoft.seriesgm.core.models.Category;
import com.gussoft.seriesgm.core.exception.ApiException;
import com.gussoft.seriesgm.core.exception.NotFoundException;
import com.gussoft.seriesgm.core.mappers.CategoryMapper;
import com.gussoft.seriesgm.core.repository.CategoryRepository;
import com.gussoft.seriesgm.integration.tranfer.request.CategoryRequest;
import com.gussoft.seriesgm.integration.tranfer.response.CategoryResponse;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Flux<CategoryResponse> getAll() {
        return repository.findAll()
                .map(mapper::entityToResponse)
                .sort(Comparator.comparing(CategoryResponse::getIdCategory));
    }

    @Override
    public Mono<Page<CategoryResponse>> findAllToPage(Pageable pageable, String name) {
        return repository.findByNames(name, pageable.getPageSize(), pageable.getOffset())
                .collectList()
                .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NO_CONTENT)))
                .zipWith(repository.findCountByQ(name))
                .map(result -> {
                    PageImpl<Category> categories = new PageImpl<>(result.getT1(), pageable, result.getT2());
                    return categories.map(mapper::entityToResponse);
                });
    }

    @Override
    public Flux<CategoryResponse> findByName(String name) {
        return repository.findByName(name)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
                .map(mapper::entityToResponse);
    }

    @Override
    public Mono<CategoryResponse> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
                .map(mapper::entityToResponse);
    }

    @Override
    public Mono<CategoryResponse> create(CategoryRequest request) {
        return repository.save(mapper.requestToEntity(request))
                .map(mapper::entityToResponse)
                .doOnError(error -> log.error("Error occurred while creating TypeFilm: {}", error.getMessage(), error));
    }

    @Override
    public Mono<CategoryResponse> update(CategoryRequest request, Long id) {
        return repository.findById(id)
                .flatMap(category -> {
                    Category data = this.mapper.requestToEntity(request);
                    data.setIdCategory(id);
                    return repository.save(data).map(mapper::entityToResponse);
                })
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)));
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return repository.findById(id)
                .hasElement()
                .flatMap(data -> {
                    if (data) {
                        return repository.deleteById(id).thenReturn(true);
                    }
                        return Mono.just(false);
                });
    }

        /*
    public Mono<MovieDTO> update(UUID uuid, Mono<MovieDTO> movieMono) {
        return this.movieRepository.findById(uuid)
                .flatMap(movieEntity -> movieMono.map(movie ->
                                this.movieMapper.toEntity(movie, movieEntity)))
                .log("Update", Level.INFO)
                .flatMap(movieRepository::save)
                .map(this.movieMapper::toDto);
    }

    .flatMap(category ->
                        repository.updateCategory(request.getName(), request.getCreatedAt(), category.getIdCategory())
                                .map(mapper::entityToResponse)
                )
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)));
*/
}
