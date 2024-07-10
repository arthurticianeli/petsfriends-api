CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status smallint NOT NULL,
    birth_date TIMESTAMP NOT NULL,
    category_id INTEGER,
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

INSERT INTO categories (name) VALUES ('Cats');
INSERT INTO categories (name) VALUES ('Dogs');

INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Bella', 0, '2020-01-01 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Charlie', 1, '2019-05-15 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Daisy', 0, '2021-07-22 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Max', 1, '2018-11-03 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Luna', 0, '2020-02-14 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Rocky', 1, '2017-06-09 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Milo', 0, '2021-08-30 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Coco', 1, '2019-12-25 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Ruby', 0, '2020-04-17 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Oscar', 1, '2018-09-05 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Lucy', 0, '2021-01-20 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Teddy', 1, '2017-10-31 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Molly', 0, '2020-03-07 00:00:00', 2);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Bailey', 1, '2019-08-15 00:00:00', 1);
INSERT INTO pets (name, status, birth_date, category_id) VALUES ('Stella', 0, '2021-05-29 00:00:00', 2);