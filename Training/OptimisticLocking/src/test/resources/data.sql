INSERT INTO publisher (id, name, version) VALUES (1, 'Addison Wesley', 0);
INSERT INTO publisher (id, name, version) VALUES (2, 'Manning Publications', 0);
INSERT INTO publisher (id, name, version) VALUES (3, 'OReilly Media', 0);

INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (1, 'Joshua', 'Bloch', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (2, 'Gavin', 'King', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (3, 'Christian', 'Bauer', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (4, 'Gary', 'Gregory', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (5, 'Raoul-Gabriel', 'Urma', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (6, 'Mario', 'Fusco', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (7, 'Alan', 'Mycroft', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (8, 'Andrew Lee', 'Rubinger', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (9, 'Aslak', 'Knutsen', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (10, 'Bill', 'Burke', 0, '2016-01-1 12:00:00.000');
INSERT INTO author (id, firstname, lastname, version, moddate) VALUES (11, 'Scott', 'Oaks', 0, '2016-01-1 12:00:00.000');

INSERT INTO book (id, publishingdate, title, publisherid, version) VALUES (1, '2008-05-08', 'Effective Java', 1, 0);
INSERT INTO book (id, publishingdate, title, publisherid, version) VALUES (2, '2015-10-01', 'Java Persistence with Hibernate', 2, 0);
INSERT INTO book (id, publishingdate, title, publisherid, version) VALUES (3, '2014-08-28', 'Java 8 in Action', 1, 0);
INSERT INTO book (id, publishingdate, title, publisherid, version) VALUES (4, '2014-03-12', 'Continuous Enterprise Development in Java', 3, 0);
INSERT INTO book (id, publishingdate, title, publisherid, version) VALUES (5, '2010-09-08', 'Enterprise JavaBeans 3.1', 3, 0);
INSERT INTO book (id, publishingdate, title, publisherid, version) VALUES (6, '2014-04-29', 'Java Performance The Definitive Guide', 3, 0);

INSERT INTO bookauthor (bookid, authorid) VALUES (1, 1);
INSERT INTO bookauthor (bookid, authorid) VALUES (2, 2);
INSERT INTO bookauthor (bookid, authorid) VALUES (2, 3);
INSERT INTO bookauthor (bookid, authorid) VALUES (2, 4);
INSERT INTO bookauthor (bookid, authorid) VALUES (3, 5);
INSERT INTO bookauthor (bookid, authorid) VALUES (3, 6);
INSERT INTO bookauthor (bookid, authorid) VALUES (3, 7);
INSERT INTO bookauthor (bookid, authorid) VALUES (4, 8);
INSERT INTO bookauthor (bookid, authorid) VALUES (4, 9);
INSERT INTO bookauthor (bookid, authorid) VALUES (5, 8);
INSERT INTO bookauthor (bookid, authorid) VALUES (5, 10);
INSERT INTO bookauthor (bookid, authorid) VALUES (6, 11);

INSERT INTO review (id, comment, rating, version, book_id) VALUES (1, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 1);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (2, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 1);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (3, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 0, 0, 1);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (4, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 1);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (5, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 2);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (6, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 2);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (7, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 2);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (8, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 2);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (9, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 3);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (10, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 3);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (11, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 3);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (12, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 4);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (13, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 4);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (14, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 0, 0, 4);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (15, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 4);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (16, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 5);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (17, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 5);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (18, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 2, 0, 5);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (19, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 5);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (20, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 2, 0, 6);
INSERT INTO review (id, comment, rating, version, book_id) VALUES (21, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 6);
