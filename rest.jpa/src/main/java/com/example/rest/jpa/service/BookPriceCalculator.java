package com.example.rest.jpa.service;

import com.example.rest.jpa.entities.Book;

public class BookPriceCalculator {

	public double calcularPrecio(Book book) {
		double price = book.getPrice();
		if (book.getPages() > 300) {
			price += 5;
		}
		// envio
		price += 3.99;
		return price;
	}

}
