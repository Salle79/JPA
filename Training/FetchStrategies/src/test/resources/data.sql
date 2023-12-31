INSERT INTO publisher (id, name, version) VALUES (1, 'Addison Wesley', 0);
INSERT INTO publisher (id, name, version) VALUES (2, 'Manning Publications', 0);
INSERT INTO publisher (id, name, version) VALUES (3, 'OReilly Media', 0);

INSERT INTO author (id, firstname, lastname, version) VALUES (1, 'Joshua', 'Bloch', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (2, 'Gavin', 'King', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (3, 'Christian', 'Bauer', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (4, 'Gary', 'Gregory', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (5, 'Raoul-Gabriel', 'Urma', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (6, 'Mario', 'Fusco', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (7, 'Alan', 'Mycroft', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (8, 'Andrew Lee', 'Rubinger', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (9, 'Aslak', 'Knutsen', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (10, 'Bill', 'Burke', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (11, 'Scott', 'Oaks', 0);

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

INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (1, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 1, 0 );
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (2, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 1, 1);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (3, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 0, 0, 1, 2);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (4, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 1, 3);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (5, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 2, 0);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (6, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 2, 1);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (7, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 2, 2);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (8, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 2, 3);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (9, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 3, 0);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (10, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 3, 1);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (11, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 3, 2);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (12, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 4, 0);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (13, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 4, 1);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (14, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 0, 0, 4, 2);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (15, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 4, 3);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (16, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 5, 0);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (17, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 3, 0, 5, 1);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (18, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 2, 0, 5, 2);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (19, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 1, 0, 5, 3);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (20, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 2, 0, 6, 0);
INSERT INTO review (id, comment, rating, version, book_id, reviews_index) VALUES (21, 'comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment', 4, 0, 6, 1);
