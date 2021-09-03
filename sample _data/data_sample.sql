ALTER TABLE IF EXISTS ONLY customer DROP CONSTRAINT IF EXISTS fk_billingId CASCADE;
ALTER TABLE IF EXISTS ONLY product DROP CONSTRAINT IF EXISTS fk_supplierId CASCADE;
ALTER TABLE IF EXISTS ONLY product DROP CONSTRAINT IF EXISTS fk_categoryId CASCADE;
ALTER TABLE IF EXISTS ONLY orderdetails DROP CONSTRAINT IF EXISTS fk_userId CASCADE;
-- ALTER TABLE IF EXISTS ONLY orderdetails DROP CONSTRAINT IF EXISTS fk_productAmountId CASCADE;
ALTER TABLE IF EXISTS ONLY productAmount DROP CONSTRAINT IF EXISTS fk_orderId CASCADE;
ALTER TABLE IF EXISTS ONLY productAmount DROP CONSTRAINT IF EXISTS fk_productId CASCADE;



DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS orderdetails;
DROP TABLE IF EXISTS productAmount;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS billingInfo;
DROP TABLE IF EXISTS category;


CREATE TABLE customer (
        id SERIAL PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        email VARCHAR(20) NOT NULL,
        password VARCHAR(20) NOT NULL,
        billingId INTEGER
);

CREATE TABLE product (
        id serial PRIMARY KEY,
        name VARCHAR(40) NOT NULL,
        description VARCHAR,
        picturePath VARCHAR,
        defaultPrice DECIMAL,
        defaultCurrency VARCHAR,
        categoryId INTEGER,
        supplierId INTEGER NOT NULL
);

CREATE TABLE orderdetails (
        id serial PRIMARY KEY,
        orderTime timestamp default current_timestamp,
        userId INTEGER NOT NULL,
--         productAmountId INTEGER NOT NULL,
        isActiveOrder INTEGER,
        orderStatus VARCHAR(40) NOT NULL
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

CREATE TABLE category(
        id serial PRIMARY KEY,
        name VARCHAR(30) NOT NULL
);

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_billingId FOREIGN KEY (billingId) REFERENCES billingInfo(id) ON DELETE CASCADE;

ALTER TABLE ONLY orderdetails
    ADD CONSTRAINT fk_userId FOREIGN KEY (userId) REFERENCES customer(id) ON DELETE CASCADE;
--     ADD CONSTRAINT fk_productAmountId FOREIGN KEY (productAmountId) REFERENCES productAmount(id) ON DELETE CASCADE ;

ALTER TABLE ONLY productAmount
    ADD CONSTRAINT fk_orderId FOREIGN KEY (orderId) REFERENCES orderdetails(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_productId FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE;

ALTER TABLE ONLY product
    ADD CONSTRAINT fk_supplierId FOREIGN KEY (supplierId) REFERENCES supplier(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_categoryId FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE CASCADE;

INSERT INTO supplier (name, description ) VALUES
('Amazon', 'Jeff Bezos''s corrupt company'),
('Libri', 'Book and ebook');

INSERT INTO category (name) VALUES
('Programming'),
('Romance'),
('Thriller');

INSERT INTO product (name, description, picturePath, defaultPrice, defaultCurrency, categoryId, supplierId) VALUES
('Cracking the Coding Interview', 'I am not a recruiter. I am a software engineer. And as such, I know what it''s like to be asked to whip up brilliant algorithms on the spot and then write flawless code on a whiteboard. I''ve been through this as a candidate and as an interviewer.','src/main/webapp/static/img/Programming_1.jpg', 30, 'USD', 1, 1),
('Clean Code', 'Even bad code can function. But if code isn''t clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesnt have to be that way.','src/main/webapp/static/img/Programming_2.jpg', 32, 'USD', 1, 2),
('Interviews in Java', 'The core of EPI is a collection of over 250 problems with detailed solutions. The problems are representative of interview questions asked at leading software companies. The problems are illustrated with 200 figures, 300 tested programs, and 150 additional variants.','src/main/webapp/static/img/Programming_3.jpg', 45, 'USD', 1, 1),
('Billy Summers', 'From legendary storyteller and number-one best seller Stephen King, whose “restless imagination is a power that cannot be contained” (The New York Times Book Review), comes a thrilling new novel about a good guy in a bad job.','src/main/webapp/static/img/Thriller_4.jpg', 52, 'USD', 2, 2),
('The Therapist', 'The multimillion-copy New York Times bestselling author B.A. Paris returns to her heartland of gripping psychological suspense in The Therapist―a powerful tale of a house that holds a shocking secret.','src/main/webapp/static/img/Thriller_5.jpg', 25, 'USD', 2, 1),
('The Dark', 'From New York Times and number one Audible best-selling duo Jeremy Robinson and R.C. Bray comes a horrifying revelation about the centuries-old Three Days of Darkness prophecy, during which the legions of Hell will be unleashed','src/main/webapp/static/img/Thriller_6.jpg', 46, 'USD', 2, 1),
('Foolish Hearts', 'It''s been three years since Ashiya Waters walked away from Russell—and made the biggest mistake of her life. She knows she shouldn''t dwell on the past. Love isn''t meant to last…and nobody taught her that better than her own family.','src/main/webapp/static/img/Romance_7.jpg', 32, 'USD', 3, 2),
('The Wildest Ride', 'Filled with deep emotion and intense spark, Marcella Bell brings grit, spark and brilliance to western romance! Marcella Bell is one to watch!”—Maisey Yates, New York Times bestselling author','src/main/webapp/static/img/Romance_8.jpg', 27, 'USD', 3, 2),
('New Moon', 'From evil vampires to a mysterious pack of wolves, new threats of danger and vengeance test Bella and Edward''s romance in the second book of the irresistible Twilight saga.','src/main/webapp/static/img/Romance_9.jpg', 45, 'USD', 3, 1);

INSERT INTO customer (id,name, email, password) VALUES
(1,'Example Customer', 'customer@example.com','password');

INSERT INTO orderdetails (ordertime, userid, isactiveorder, orderstatus) VALUES
('2021-05-26 07:06:19.000000', 1,1,'in progress'),
('2021-03-26 07:06:19.000000', 1,0,'in progress'),
('2021-01-26 07:06:19.000000', 1,0,'in progress');

INSERT INTO productamount (orderid, productid, amount) VALUES
(1, 1, 2),
(1, 2, 3),
(2, 1, 1),
(3, 2, 4);