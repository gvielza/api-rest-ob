package com.example.rest.jpa.controllers;

import com.example.rest.jpa.repository.BookRepository;
import com.example.rest.jpa.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookRepository repository;

@GetMapping("/books")
    public List<Book> obtenerLibros(){
        return repository.findAll();
    }
    @GetMapping("/books/{id}")
    public ResponseEntity<?> obtenerLibro(@PathVariable Long id){
        Optional<Book> libro=repository.findById(id);
      if (!libro.isEmpty()){
          return ResponseEntity.ok(libro.get());
      }else
          return ResponseEntity.notFound().build();
    }
    @PostMapping("/books")
    public Book createBook(@RequestBody Book libro){
    return repository.save(libro);
    }

}
