DROP DATABASE IF EXISTS JAM;

CREATE DATABASE JAM;

USE JAM;

CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목',CEIL(RAND()*10)),
`body` = CONCAT('내용',CEIL(RAND()*10));

SELECT * FROM article ORDER BY id DESC;

UPDATE article 
SET title = '안녕1',
updateDate = NOW()
WHERE id = 1;