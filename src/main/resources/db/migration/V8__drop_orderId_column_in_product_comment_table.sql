ALTER TABLE product_comment DROP CONSTRAINT uq_comment_per_order;
ALTER TABLE product_comment DROP COLUMN order_id;