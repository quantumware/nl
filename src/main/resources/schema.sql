DROP TABLE IF EXISTS Person;
CREATE TABLE Person(ID INT PRIMARY KEY,
   NAME VARCHAR(255),
   PHONE VARCHAR(15),
   EMAIL VARCHAR(255));

DROP TABLE IF EXISTS Books;
CREATE TABLE Person(ID INT PRIMARY KEY,
   TITLE VARCHAR(255),
   AUTHOR INT,
   ISBN INT(13));
