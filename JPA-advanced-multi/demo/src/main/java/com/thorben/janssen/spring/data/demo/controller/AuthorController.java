package com.thorben.janssen.spring.data.demo.controller;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.thorben.janssen.spring.data.demo.model.Author;
import com.thorben.janssen.spring.data.demo.repositories.AuthorRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "author")
@Transactional
public class AuthorController {
    
    private AuthorRepository authorRepo;

    public AuthorController(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @GetMapping(path = "/{id}")
    public Author getAuthorById(@PathVariable("id") Long id) {
        Optional<Author> a = authorRepo.findById(id);
        if (!a.isPresent()) {
            throw new NoResultException();
        }
        return a.get();
    }

    @PostMapping
    public void persistAuthor(Author a) {
        authorRepo.save(a);
    }
}
