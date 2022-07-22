	package com.example.rest.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.rest.jpa.entities.Book;
import com.example.rest.jpa.service.BookPriceCalculator;

import net.bytebuddy.asm.Advice.Local;

class CalcularPecioTest {

	@Test
	void test() {
		//configurar prueba
		BookPriceCalculator calculator=new BookPriceCalculator();
		Book book=new Book(1L,"jeje","eheo",1000,12.25,LocalDate.now(),true);
		//ejecuto comportamiento
		double price=calculator.calcularPrecio(book);
		System.out.println(price);
		//comprobaciones aserciones
		assertTrue(price>0);
		assertEquals(21.240000000000002, price);
		
	
	}

}
