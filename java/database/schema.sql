BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, pizzas, user_data, toppings, orders, menu_items, orders_to_menu_items CASCADE;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE sizes (
    size_id SERIAL,
    name varchar(40) NOT NULL UNIQUE,
    available boolean NOT NULL,
    price numeric(10,2) NOT NULL,

    CONSTRAINT PK_pizza_size PRIMARY KEY (size_id)
);

CREATE TABLE crusts (
    crust_id SERIAL,
    name varchar(40) NOT NULL UNIQUE,
    available boolean NOT NULL,
    price numeric(10,2) NOT NULL,

    CONSTRAINT PK_pizza_crust PRIMARY KEY (crust_id)
);

CREATE TABLE sauces (
    sauce_id SERIAL,
    name varchar(40) NOT NULL UNIQUE,
    available boolean NOT NULL,
    price numeric(10,2) NOT NULL,

    CONSTRAINT PK_pizza_sauce PRIMARY KEY (sauce_id)
);

CREATE TABLE toppings (
    topping_id SERIAL,
    name varchar(40) NOT NULL UNIQUE,
    available boolean NOT NULL,
    price numeric(10,2) NOT NULL,

    CONSTRAINT PK_topping_id PRIMARY KEY (topping_id)
);

CREATE TABLE pizzas (
    pizza_id int,
    size_id int NOT NULL,
    crust_id int NOT NULL,
    sauce_id int NOT NULL,

    CONSTRAINT PK_pizzas PRIMARY KEY (pizza_id),
    CONSTRAINT fk_size FOREIGN Key (size_id) REFERENCES sizes(size_id),
    CONSTRAINT fk_crust FOREIGN Key (crust_id) REFERENCES crusts(crust_id),
    CONSTRAINT fk_sauce FOREIGN Key (sauce_id) REFERENCES sauces(sauce_id)
);

CREATE TABLE pizza_toppings (
    pizza_id int NOT NULL,
    topping_id int NOT NULL,

    CONSTRAINT ck_pizza_topping_id PRIMARY KEY (pizza_id, topping_id),
    CONSTRAINT fk_pizza_id FOREIGN KEY (pizza_id) REFERENCES pizzas(pizza_id),
    CONSTRAINT fk_topping_id FOREIGN KEY (topping_id) REFERENCES toppings(topping_id)
);

CREATE TABLE orders (
    order_id SERIAL,
    status varchar(40) NOT NULL,
    user_id int NOT NULL,

    CONSTRAINT PK_order_id PRIMARY KEY (order_id)
);

CREATE TABLE menu_items (
    item_id SERIAL,
    name varchar(40) NOT NULL UNIQUE,
    available boolean NOT NULL,
    price numeric(10,2) NOT NULL,
    pizza_id int DEFAULT -1,

    CONSTRAINT PK_item_id PRIMARY KEY (item_id)
);

CREATE TABLE orders_to_menu_items (
    order_id int NOT NULL,
    item_id int NOT NULL,

    CONSTRAINT ck_order_item_id PRIMARY KEY (order_id, item_id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

CREATE TABLE user_data (
    user_id integer NOT NULL UNIQUE,
    email varchar(200) DEFAULT '',
    address varchar(200) DEFAULT '',
    phone varchar(15) DEFAULT '',
    credit_card varchar(16) DEFAULT '',
    CONSTRAINT pk_user_data PRIMARY KEY (user_id),
    CONSTRAINT fk_user_data_user FOREIGN Key (user_id) REFERENCES users(user_id)
);

COMMIT TRANSACTION;
