ALTER TABLE product_availability
ALTER COLUMN reason TYPE VARCHAR(50)
USING reason::text;
DROP TYPE availability_reason;