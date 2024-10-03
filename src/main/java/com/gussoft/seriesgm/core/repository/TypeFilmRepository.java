package com.gussoft.seriesgm.core.repository;

import com.gussoft.seriesgm.core.models.TypeFilm;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TypeFilmRepository extends GenericRepository<TypeFilm, Long> {

    @Query("SELECT s FROM type_film s WHERE s.name LIKE %$1%")
    Flux<TypeFilm> findByName(String name);

    @Query("SELECT a.id_type, a.name, a.created_at "
            + "FROM type_film a WHERE a.name LIKE CONCAT('%',:name ,'%') "
            + "ORDER BY a.id_type ASC LIMIT :pageSize OFFSET :offSet")
    Flux<TypeFilm> findByNames(String name, int pageSize, long offSet);

    @Query("SELECT count(a.id_type) FROM type_film a WHERE a.name like '%' || :q || '%'")
    Mono<Integer> findCountByQ(String q);

    @Query("UPDATE type_film SET name=$1, created_at=$2 WHERE id_type=$3")
    Mono<TypeFilm> updateTypeFilm(String name, LocalDateTime createAt, Long id);

}
