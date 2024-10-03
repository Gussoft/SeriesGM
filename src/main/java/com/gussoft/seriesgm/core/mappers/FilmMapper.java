package com.gussoft.seriesgm.core.mappers;

import com.gussoft.seriesgm.core.models.Film;
import com.gussoft.seriesgm.integration.tranfer.request.FilmRequest;
import com.gussoft.seriesgm.integration.tranfer.response.FilmResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmMapper {

    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    Film requestToEntity(FilmRequest request);

    FilmResponse entityToResponse(Film entity);

}
