INSERT INTO book (id, title, version) VALUES (1, 'Hibernate Tips - More than 70 solutions to common Hibernate problems', 0);

INSERT INTO author (id, firstname, lastname, version) VALUES (1, 'Thorben', 'Janssen', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (2, 'Steve', 'Ebersol', 0);
INSERT INTO author (id, firstname, lastname, version) VALUES (3, 'Unknown', 'Author', 0);

INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (1, 2);
INSERT INTO book_author (book_id, author_id) VALUES (1, 3);
-- INSERT INTO book_author (book_id, author_id, authors_order) VALUES (1, 1, 0);
-- INSERT INTO book_author (book_id, author_id, authors_order) VALUES (1, 2, 1);
-- INSERT INTO book_author (book_id, author_id, authors_order) VALUES (1, 3, 2);

INSERT INTO Manuscript(id) values(1);