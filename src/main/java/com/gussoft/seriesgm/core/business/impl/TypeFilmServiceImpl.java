package com.gussoft.seriesgm.core.business.impl;

import static com.gussoft.seriesgm.core.utils.Constrains.NOT_FOUND;

import com.gussoft.seriesgm.core.business.TypeFilmService;
import com.gussoft.seriesgm.core.models.TypeFilm;
import com.gussoft.seriesgm.core.exception.ApiException;
import com.gussoft.seriesgm.core.exception.NotFoundException;
import com.gussoft.seriesgm.core.mappers.TypeFilmMapper;
import com.gussoft.seriesgm.core.repository.TypeFilmRepository;
import com.gussoft.seriesgm.integration.tranfer.request.TypeFilmRequest;
import com.gussoft.seriesgm.integration.tranfer.response.TypeFilmResponse;
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
public class TypeFilmServiceImpl implements TypeFilmService {

    private final TypeFilmRepository repository;
    private final TypeFilmMapper mapper;

    @Override
    public Flux<TypeFilmResponse> getAll() {
        return repository.findAll()
                .map(mapper::entityToResponse)
                .sort(Comparator.comparing(TypeFilmResponse::getIdType));
    }

    @Override
    public Mono<Page<TypeFilmResponse>> getAllToPage(Pageable pageable, String name) {
        return repository.findByNames(name, pageable.getPageSize(), pageable.getOffset())
                .collectList()
                .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NO_CONTENT)))
                .zipWith(repository.findCountByQ(name))
                .map(result -> {
                    PageImpl<TypeFilm> categories = new PageImpl<>(result.getT1(), pageable, result.getT2());
                    return categories.map(mapper::entityToResponse);
                });
    }

    @Override
    public Flux<TypeFilmResponse> getByName(String name) {
        return repository.findByName(name)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
                .map(mapper::entityToResponse);
    }

    @Override
    public Mono<TypeFilmResponse> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
                .map(mapper::entityToResponse);
    }

    @Override
    public Mono<TypeFilmResponse> create(TypeFilmRequest request) {
        return repository.save(mapper.requestToEntity(request))
                .map(mapper::entityToResponse);
    }

    @Override
    public Mono<TypeFilmResponse> update(TypeFilmRequest request, Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
                .flatMap(typeFilm ->{
                    TypeFilm data = this.mapper.requestToEntity(request);
                    data.setIdType(id);
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
*/
}
