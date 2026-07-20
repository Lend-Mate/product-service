ALTER TABLE product DROP COLUMN min_rental_days;
ALTER TABLE product DROP COLUMN max_rental_days;

CREATE TABLE product_rental_periods (
                                        product_id BIGINT NOT NULL,
                                        period VARCHAR(50) NOT NULL,
                                        CONSTRAINT fk_product_rental_periods_product
                                            FOREIGN KEY (product_id) REFERENCES product(id)
                                                ON DELETE CASCADE,
                                        CONSTRAINT uq_product_period UNIQUE (product_id, period)
);