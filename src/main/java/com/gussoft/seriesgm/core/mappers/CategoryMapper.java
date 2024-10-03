package com.gussoft.seriesgm.core.mappers;

import com.gussoft.seriesgm.core.models.Category;
import com.gussoft.seriesgm.integration.tranfer.request.CategoryRequest;
import com.gussoft.seriesgm.integration.tranfer.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category requestToEntity(CategoryRequest request);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "dateToString")
    CategoryResponse entityToResponse(Category entity);

    @Named("dateToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return dateTime.format(formatter);
    }

}
