DROP DATABASE IF EXISTS `JAM`;
CREATE DATABASE JAM;
USE JAM;

CREATE TABLE article(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT UNSIGNED NOT NULL,
    title VARCHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

SHOW TABLES;
DESC article;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = CONCAT('제목', RAND()),
`body` = CONCAT('내용', RAND());


SELECT * 
FROM article;


-- INSERT INTO article
-- SET regDate = NOW(),
-- updateDate = NOW(),
-- title = CONCAT('제목', ROUND(RAND() * 100)),
-- `body` = CONCAT('내용', ROUND(RAND() * 100));

CREATE TABLE members(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,    
    loginId VARCHAR(50) NOT NULL UNIQUE,
    loginPw VARCHAR(100) NOT NULL,    
    `name` VARCHAR(50) NOT NULL
);

INSERT INTO members
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`name` = '운영자';

SELECT * FROM members;

SELECT *
FROM members 
WHERE loginId = 'admin' 
AND loginPw = 'admin';