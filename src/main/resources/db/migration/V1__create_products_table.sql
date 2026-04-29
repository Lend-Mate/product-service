-- ENUM TİPLERİ
CREATE TYPE availability_reason AS ENUM ('RENTED', 'BLOCKED', 'MAINTENANCE');

-- CATEGORY
CREATE TABLE category (
    id          BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);


-- product
CREATE TABLE product (
    id               BIGSERIAL PRIMARY KEY,
    owner_id         BIGINT NOT NULL,
    category_id      BIGINT NOT NULL REFERENCES category(id),
    product_name     VARCHAR(255) NOT NULL,
    description      TEXT,
    currency         VARCHAR(10) NOT NULL DEFAULT 'TRY',
    price            NUMERIC(12, 2) NOT NULL,
    brand            VARCHAR(100),
    stock_quantity   INTEGER NOT NULL DEFAULT 1,
    min_rental_days  INTEGER NOT NULL DEFAULT 1,
    max_rental_days  INTEGER,
    deposit_amount   NUMERIC(12, 2) NOT NULL DEFAULT 0,
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP NOT NULL DEFAULT NOW()
);


-- LOCATIONS
CREATE TABLE location (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    city        VARCHAR(100) NOT NULL,
    district    VARCHAR(100),
    latitude    FLOAT,
    longitude   FLOAT
);


-- PRODUCT IMAGES
CREATE TABLE product_image (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    image_url   VARCHAR(1000) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);


-- PRODUCT AVAILABILITY
CREATE TABLE product_availability (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    start_date  TIMESTAMP NOT NULL,
    end_date    TIMESTAMP NOT NULL,
    reason      availability_reason NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_availability_dates CHECK (end_date >= start_date)
);

-- PRODUCT COMMENTS
CREATE TABLE product_comment (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    user_id     BIGINT NOT NULL,
    order_id    BIGINT NOT NULL,
    text        TEXT NOT NULL,
    rating      INTEGER NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_rating_range CHECK (rating BETWEEN 1 AND 5),
    CONSTRAINT uq_comment_per_order UNIQUE (order_id, user_id)
);


-- İNDEKSLER
CREATE INDEX idx_product_owner    ON product(owner_id);
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_availability_product_dates ON product_availability(product_id, start_date, end_date);
CREATE INDEX idx_comments_product  ON product_comment(product_id);