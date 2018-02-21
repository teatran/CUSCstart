/**
 * Author:  gux
 * Created: Feb 9, 2018
 */

-- CREATE DATABASE loginman CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- 
-- USE loginman;
-- 
-- CREATE TABLE login (
--     id INTEGER PRIMARY KEY AUTO_INCREMENT,
--     username VARCHAR(30),
--     password VARCHAR(30)
-- );

-- INSERT INTO login (username, password) VALUES ('test', 'test');

CREATE TABLE class (
    classid INTEGER PRIMARY KEY AUTO_INCREMENT,
    classname VARCHAR(50)
);

CREATE TABLE student (
    studentid INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30),
    password VARCHAR(30), 
    fullname VARCHAR(50),
    sex VARCHAR(3),
    email VARCHAR(50),
    dayofbirth DATE,
    classid INTEGER REFERENCES class(classid)
);

INSERT INTO class (classname) VALUES ('Công nghệ thông tin');
INSERT INTO class (classname) VALUES ('Khoa học máy tính');
INSERT INTO class (classname) VALUES ('Kỹ thuật phần mềm');

INSERT INTO student (username, password, fullname, sex, email, dayofbirth, classid)
VALUES ('hello', 'world', 'Thế Anh', 'nam', 'tea@gmail.com', '1983-01-10', 1);

INSERT INTO student (username, password, fullname, sex, email, dayofbirth, classid)
VALUES ('paul', 'world', 'Paul Scholes', 'nam', 'tea@gmail.com', '1983-06-11', 2);

INSERT INTO student (username, password, fullname, sex, email, dayofbirth, classid)
VALUES ('ron', 'world', 'Ronaldo', 'nu', 'tea@gmail.com', '1983-06-11', 3);

