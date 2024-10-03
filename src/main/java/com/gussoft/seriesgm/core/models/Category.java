package com.gussoft.seriesgm.core.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Category implements Serializable {

    @Id
    private Long idCategory;

    private String name;
    private LocalDateTime createdAt;

}
