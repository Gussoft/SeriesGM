package com.gussoft.seriesgm.core.mappers;

import com.gussoft.seriesgm.core.models.TypeFilm;
import com.gussoft.seriesgm.integration.tranfer.request.TypeFilmRequest;
import com.gussoft.seriesgm.integration.tranfer.response.TypeFilmResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TypeFilmMapper {

    TypeFilmMapper INSTANCE = Mappers.getMapper(TypeFilmMapper.class);

    TypeFilm requestToEntity(TypeFilmRequest request);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "dateToString")
    TypeFilmResponse entityToResponse(TypeFilm entity);

    @Named("dateToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return dateTime.format(formatter);
    }
}
