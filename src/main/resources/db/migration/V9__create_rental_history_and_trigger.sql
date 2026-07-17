CREATE TABLE rental_history (
                                id           BIGSERIAL PRIMARY KEY,
                                product_id   BIGINT NOT NULL REFERENCES product(id),
                                start_date   TIMESTAMP NOT NULL,
                                end_date     TIMESTAMP NOT NULL,
                                reason       VARCHAR(50) NOT NULL,
                                created_at   TIMESTAMP NOT NULL,
                                returned_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_rental_history_product ON rental_history(product_id);

CREATE OR REPLACE FUNCTION move_to_rental_history()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO rental_history (product_id, start_date, end_date, reason, created_at, returned_at)
VALUES (OLD.product_id, OLD.start_date, OLD.end_date, OLD.reason, OLD.created_at, NOW());
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_move_to_rental_history
    BEFORE DELETE ON product_availability
    FOR EACH ROW
    EXECUTE FUNCTION move_to_rental_history();