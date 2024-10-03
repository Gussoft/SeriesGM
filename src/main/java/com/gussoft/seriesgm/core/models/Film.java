package com.gussoft.seriesgm.core.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "film")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Film implements Serializable {

    @Id
    private Long idFilm;

    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer chapter;
    private String imageBanner;
    private String imageDif;

    @Transient
    private TypeFilm typeFilm;
    private Long idTypeFilm;

    @Transient
    private Category category;
    private Long idCategory;

    private String status;
    private LocalDateTime createdAt;

}
