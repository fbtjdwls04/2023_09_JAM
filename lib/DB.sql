DROP DATABASE IF EXISTS `JAM`;
CREATE DATABASE JAM;
USE JAM;

CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

SHOW TABLES;
DESC article;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', RAND()),
`body` = CONCAT('내용', RAND());


SELECT * 
FROM article;


-- INSERT INTO article
-- SET regDate = NOW(),
-- updateDate = NOW(),
-- title = CONCAT('제목', ROUND(RAND() * 100)),
-- `body` = CONCAT('내용', ROUND(RAND() * 100));
