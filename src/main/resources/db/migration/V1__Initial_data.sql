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
    description TEXT,
    url_image TEXT,
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

INSERT INTO categories (name) VALUES ('Cats');
INSERT INTO categories (name) VALUES ('Dogs');

INSERT INTO pets (name, status, birth_date, category_id, description, url_image) VALUES
('Bella', 0, '2020-01-01 00:00:00', 2, 'Bella is a calm and loving dog who enjoys sunbathing.', 'https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg'),
('Charlie', 1, '2019-05-15 00:00:00', 1, 'Charlie is an adventurous cat who loves to explore.', 'https://www.argospetinsurance.co.uk/assets/uploads/2017/12/cat-pet-animal-domestic-104827.jpeg'),
('Daisy', 0, '2021-07-22 00:00:00', 2, 'Daisy is a playful dog who enjoys chasing toys.', 'https://pbs.twimg.com/profile_images/948761950363664385/Fpr2Oz35_400x400.jpg'),
('Max', 1, '2018-11-03 00:00:00', 1, 'Max is a loyal and protective cat, always on the lookout.', 'https://www.catster.com/wp-content/uploads/2017/08/A-fluffy-cat-looking-funny-surprised-or-concerned.jpg'),
('Luna', 0, '2020-02-14 00:00:00', 2, 'Luna is a mysterious dog with a majestic presence.', 'https://www.cats.org.uk/uploads/images/featurebox_sidebar_kids/grief-and-loss.jpg'),
('Rocky', 1, '2017-06-09 00:00:00', 1, 'Rocky is a strong and energetic cat who loves to play.', 'https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d'),
('Milo', 0, '2021-08-30 00:00:00', 2, 'Milo is a curious dog with a passion for adventure.', 'https://www.healthypawspetinsurance.com/Images/V3/DogAndPuppyInsurance/Dog_CTA_Desktop_HeroImage.jpg'),
('Ruby', 0, '2020-04-17 00:00:00', 2, 'Ruby is a gentle dog who loves cuddles and naps.', 'http://i.dailymail.co.uk/i/pix/2017/12/20/13/477ACA2400000578-0-image-a-61_1513778255742.jpg'),
('Lucy', 0, '2021-01-20 00:00:00', 2, 'Lucy is a shy dog who warms up quickly to kindness.', 'https://i.ytimg.com/vi/ZgE-ZRvlIlk/maxresdefault.jpg'),
('Molly', 0, '2020-03-07 00:00:00', 2, 'Molly is a playful dog who enjoys attention and affection.', 'https://d1o50x50snmhul.cloudfront.net/wp-content/uploads/2018/03/29155020/gettyimages-604576373-800x533.jpg'),
('Bailey', 1, '2019-08-15 00:00:00', 1, 'Bailey is an energetic cat who loves long walks.', 'https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg'),
('Stella', 0, '2021-05-29 00:00:00', 2, 'Stella is a serene dog with a loving demeanor.', 'https://hips.hearstapps.com/ghk.h-cdn.co/assets/16/08/3200x1600/landscape-gettyimages-530330473-1.jpg?resize=480:*');