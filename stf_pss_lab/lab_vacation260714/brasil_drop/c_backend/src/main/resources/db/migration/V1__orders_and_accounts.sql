CREATE TABLE user_accounts (
    email VARCHAR(254) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    password_hash VARCHAR(256) NOT NULL
);
CREATE TABLE purchase_orders (
    code VARCHAR(40) PRIMARY KEY,
    owner_email VARCHAR(254) NOT NULL REFERENCES user_accounts(email),
    buyer_name VARCHAR(200) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    installments INTEGER NOT NULL CHECK (installments BETWEEN 1 AND 10),
    total DECIMAL(19, 2) NOT NULL CHECK (total >= 0),
    cep VARCHAR(9) NOT NULL,
    street VARCHAR(250) NOT NULL,
    neighborhood VARCHAR(200) NOT NULL,
    city VARCHAR(200) NOT NULL,
    state VARCHAR(2) NOT NULL,
    address_number VARCHAR(30) NOT NULL,
    complement VARCHAR(250) NOT NULL
);
CREATE INDEX orders_owner_date ON purchase_orders(owner_email, created_at);
CREATE TABLE purchase_order_items (
    order_code VARCHAR(40) NOT NULL REFERENCES purchase_orders(code),
    position INTEGER NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(250) NOT NULL,
    unit_price DECIMAL(19, 2) NOT NULL CHECK (unit_price >= 0),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    subtotal DECIMAL(19, 2) NOT NULL CHECK (subtotal >= 0),
    PRIMARY KEY (order_code, position)
);
