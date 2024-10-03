CREATE TABLE IF NOT EXISTS type_film (
    id_type BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS category (
    id_category BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS film (
    id_film BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(300),
    release_date TIMESTAMP,
    chapter INTEGER,
    status VARCHAR(15),
    image_banner VARCHAR(300),
    image_dif VARCHAR(300),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_type BIGINT REFERENCES type_film (id_type) REFERENCES type_film(id_type),
    id_category BIGINT REFERENCES category (id_category) REFERENCES category(id_category)
);