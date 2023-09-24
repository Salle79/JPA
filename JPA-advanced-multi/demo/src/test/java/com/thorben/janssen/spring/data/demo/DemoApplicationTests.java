package com.thorben.janssen.spring.data.demo;

import com.thorben.janssen.spring.data.demo.controller.AuthorController;
import com.thorben.janssen.spring.data.demo.model.Author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    AuthorController authorController;

	@Test
	void persistAndGetAuthor() {
        Author a = new Author();
        a.setFirstName("Thorben");
        a.setLastName("Janssen");
        authorController.persistAuthor(a);

        authorController.getAuthorById(a.getId());
	}

}
