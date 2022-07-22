package com.example.rest.jpa.controllers;

import com.example.rest.jpa.repository.BookRepository;
import com.example.rest.jpa.entities.Book;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

@RestController
@RequestMapping("/api")
public class BookController {
	@Autowired
	private BookRepository repository;

	private final Logger log = org.slf4j.LoggerFactory.getLogger(BookController.class);

	@GetMapping("/books")
	public List<Book> obtenerLibros() {
		return repository.findAll();
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<?> obtenerLibro(@PathVariable Long id) {
		Optional<Book> libro = repository.findById(id);
		if (!libro.isEmpty()) {
			return ResponseEntity.ok(libro.get());
		} else
			return ResponseEntity.notFound().build();
	}

	@PostMapping("/books")
	public ResponseEntity<?> createBook(@RequestBody Book libro, @RequestHeader HttpHeaders headers) {
		// Desde donde estás enviando
		System.out.println(headers.get("User-Agent"));
		if (libro.getId() != null) {
			log.warn("papi, deberias modificar, no crear");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(repository.save(libro));
	}

	@PutMapping("/books")
	public ResponseEntity<?> actualizarLibro(@RequestBody Book book) {
		Optional<Book> bookDB = repository.findById(book.getId());
		if (bookDB.get().getId() == null) {
			log.warn("papa  q haces, no existe ese libro");
			return ResponseEntity.badRequest().build();
		}
		if (!repository.existsById(book.getId())) {
			log.warn("papa  q haces, no existe ese libro");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(repository.save(book));
	}

	@DeleteMapping("/books")
	public ResponseEntity<?> deleteAllBook() {
		repository.deleteAll();
		return ResponseEntity.noContent().build();
	}
	@DeleteMapping("/books/{id}")
	public ResponseEntity<?> deleteBook2(@PathVariable Long id){
	if (!repository.existsById(id)) {
			log.warn("papa  q haces, no existe ese libro");
			return ResponseEntity.notFound().build();
		}
	repository.deleteById(id);
	return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
}
