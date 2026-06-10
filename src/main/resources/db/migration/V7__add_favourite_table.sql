CREATE TABLE favourite
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);