DROP TABLE IF EXISTS book;

CREATE TABLE book (
  id int(11) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  category varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  pages int(11) NOT NULL,
  price int(11) NOT NULL,
  publication varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
       id int(11) NOT NULL AUTO_INCREMENT,
       username varchar(255) NOT NULL UNIQUE,
       password varchar(255) NOT NULL,
       PRIMARY KEY (id)
);

INSERT INTO users (username, password)
VALUES ('test', '$2a$10$7guDKmKNLCMi1gmivK/KwOnwxmts.s0ZahCQjMoleKuY0YNjvpWwS');



