package com.gussoft.seriesgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableR2dbcRepositories
@SpringBootApplication
@EnableWebFlux
public class SeriesgmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeriesgmApplication.class, args);
	}

}
