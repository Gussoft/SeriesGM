package com.gussoft.seriesgm.integration.tranfer.request;

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
public class FilmRequest implements Serializable {

    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer chapter;
    private String imageBanner;
    private String imageDif;

    private TypeFilmRequest typeFilm;
    private Long idTypeFilm;

    private CategoryRequest category;
    private Long idCategory;

    private String status;
    private LocalDateTime createdAt;

}
