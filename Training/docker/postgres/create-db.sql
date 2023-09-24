--
-- Create database for the Hibernate Performance Tuning course
--
CREATE DATABASE "HPT";
\c "HPT"

CREATE OR REPLACE FUNCTION calculate(
    IN x double precision,
    IN y double precision,
    OUT sum double precision)
  RETURNS double precision AS
$BODY$
BEGIN
    sum = x + y;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION calculate(double precision, double precision)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION get_books()
  RETURNS refcursor AS
$BODY$
    DECLARE
      books refcursor;           -- Declare cursor variables
    BEGIN
      OPEN books FOR SELECT id, publishingdate, title, version, publisherid FROM book;   -- Open a cursor
      RETURN books;                                                       -- Return the cursor to the caller
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION get_books()
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION get_reviews(bookid bigint)
  RETURNS refcursor AS
$BODY$
    DECLARE
      reviews refcursor;           -- Declare cursor variables                         
    BEGIN
      OPEN reviews FOR SELECT id, comment, rating, version, book_id FROM review WHERE book_id = bookId;
      RETURN reviews;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION get_reviews(bigint)
  OWNER TO postgres;