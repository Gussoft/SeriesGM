package com.gussoft.seriesgm.core.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "type_film")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TypeFilm implements Serializable {

    @Id
    private Long idType;

    private String name;
    private LocalDateTime createdAt;

}
