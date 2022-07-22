package com.example.rest.jpa.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.rest.jpa.entities.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerTest {

	private TestRestTemplate testRestTemplate;
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@LocalServerPort
	private int port;

	@BeforeEach
	private void setup() {
		restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
		testRestTemplate = new TestRestTemplate(restTemplateBuilder);

	}

	@Test
	void testObtenerLibros() {
		ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books", Book[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());

		List<Book> books = Arrays.asList(response.getBody());
		System.out.println(books.size());
	}

//	@Test
//	void testObtenerLibro() {
//		fail("Not yet implemented");
//	}
//
	@DisplayName("agregando un nuevo libro para prueba")
	@Test
	void testCreateBook() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		String json = """
								{
				    "title":"primero",
				    "autor":"geo",
				    "pages":4546,
				    "price":15,
				    "releaseDate":"2022-02-05",
				    "online":false
				}
								""";
		
		HttpEntity<String> request= new HttpEntity<>(json,headers);
		ResponseEntity<Book> response =testRestTemplate.exchange("/api/books",HttpMethod.POST, request,Book.class);
		Book result=response.getBody();
		assertEquals("primero", result.getTitle());
		assertEquals(1L, result.getId());
	}

}
