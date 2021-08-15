ALTER TABLE IF EXISTS ONLY customer DROP CONSTRAINT IF EXISTS fk_billingId CASCADE;
ALTER TABLE IF EXISTS ONLY product DROP CONSTRAINT IF EXISTS fk_supplierId CASCADE;
ALTER TABLE IF EXISTS ONLY orderdetails DROP CONSTRAINT IF EXISTS fk_userId CASCADE;
ALTER TABLE IF EXISTS ONLY orderdetails DROP CONSTRAINT IF EXISTS fk_productAmountId CASCADE;
ALTER TABLE IF EXISTS ONLY productAmount DROP CONSTRAINT IF EXISTS fk_orderId CASCADE;
ALTER TABLE IF EXISTS ONLY productAmount DROP CONSTRAINT IF EXISTS fk_productId CASCADE;

DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS orderdetails;
DROP TABLE IF EXISTS productAmount;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS billingInfo;


CREATE TABLE customer (
        id SERIAL PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        email VARCHAR(20) NOT NULL,
        password VARCHAR(20) NOT NULL,
        billingId INTEGER NOT NULL
);

CREATE TABLE product (
        id serial PRIMARY KEY,
        name VARCHAR(40) NOT NULL,
        description VARCHAR,
        picturePath VARCHAR,
        defaultPrice DECIMAL,
        defaultCurrency VARCHAR,
        productCategory VARCHAR,
        supplierId INTEGER NOT NULL
);

CREATE TABLE orderdetails (
        id serial PRIMARY KEY,
        orderTime timestamp without time zone,
        userId INTEGER NOT NULL,
        productAmountId INTEGER NOT NULL
);

CREATE TABLE supplier (
        id serial PRIMARY KEY,
        name VARCHAR(40) NOT NULL,
        description VARCHAR(40) NOT NULL
);


CREATE TABLE billingInfo (
        id serial PRIMARY KEY,
        name VARCHAR(30) NOT NULL,
        city VARCHAR(30) NOT NULL,
        email VARCHAR(40) NOT NULL,
        phone VARCHAR(15) NOT NULL,
        country VARCHAR(30) NOT NULL,
        zipcode VARCHAR(10) NOT NULL,
        address VARCHAR(50) NOT NULL
);

CREATE TABLE productAmount(
        id serial PRIMARY KEY,
        orderId INTEGER NOT NULL,
        productId INTEGER NOT NULL,
        amount INTEGER NOT NULL
);

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_billingId FOREIGN KEY (billingId) REFERENCES billingInfo(id) ON DELETE CASCADE;

ALTER TABLE ONLY orderdetails
    ADD CONSTRAINT fk_userId FOREIGN KEY (userId) REFERENCES customer(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_productAmountId FOREIGN KEY (productAmountId) REFERENCES productAmount(id) ON DELETE CASCADE ;

ALTER TABLE ONLY productAmount
    ADD CONSTRAINT fk_orderId FOREIGN KEY (orderId) REFERENCES orderdetails(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_productId FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE;

ALTER TABLE ONLY product
    ADD CONSTRAINT fk_supplierId FOREIGN KEY (supplierId) REFERENCES supplier(id) ON DELETE CASCADE;

INSERT INTO supplier (name, description ) VALUES
('Amazon', 'Jeff Bezos''s corrupt company'),
('Libri', 'Book and ebook');

INSERT INTO product (name, description, picturePath, defaultPrice, defaultCurrency,productCategory, supplierId) VALUES
('Cracking the Coding Interview', 'I'' am not a recruiter.','src/main/webapp/static/img/product_1.jpg', 30, 'USD', 'programming', 1),
('Other very good book', 'Description.','src/main/webapp/static/img/product_1.jpg', 30, 'USD', 'programming', 2);



