package com.gussoft.seriesgm.integration.tranfer.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse implements Serializable {

    private Long idFilm;

    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer chapter;
    private String imageBanner;
    private String imageDif;

    private TypeFilmResponse typeFilm;

    private CategoryResponse category;

    private String status;
    private LocalDateTime createdAt;

}
