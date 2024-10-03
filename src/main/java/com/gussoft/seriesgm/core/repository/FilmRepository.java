package com.gussoft.seriesgm.core.repository;

import com.gussoft.seriesgm.core.models.Film;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends R2dbcRepository<Film, Long> {

}
