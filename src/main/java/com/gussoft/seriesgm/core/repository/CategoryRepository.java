package com.gussoft.seriesgm.core.repository;

import com.gussoft.seriesgm.core.models.Category;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends GenericRepository<Category, Long> {

    @Query("SELECT s FROM Category s WHERE s.name LIKE %$1%")
    Flux<Category> findByName(String name);

    @Query("SELECT a.id_category, a.name, a.created_at "
            + "FROM Category a WHERE a.name LIKE CONCAT('%',:name ,'%') "
            + "ORDER BY a.id_category ASC LIMIT :pageSize OFFSET :offSet")
    Flux<Category> findByNames(String name, int pageSize, long offSet);

    @Query("SELECT count(a.id_category) FROM Category a WHERE a.name like '%' || :q || '%'")
    Mono<Integer> findCountByQ(String q);

    @Query("UPDATE Category SET name=$1, created_at=$2 WHERE id_category=$3")
    Mono<Category> updateCategory(String name, LocalDateTime createAt, Long id);

}
