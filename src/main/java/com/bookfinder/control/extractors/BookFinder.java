package com.bookfinder.control.extractors;

import com.bookfinder.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jdzido on 07.02.2018.
 */
public class BookFinder {
    private static final Logger logger = LoggerFactory.getLogger(BookFinder.class);

    public List<Book> searchByTitle(String title) {

        try {
            Document doc = Jsoup.connect("http://www.galeritus.com/api/search.aspx")
                    .data("title", title)
                    .get();

            Elements books = doc.select("document");

            return books.stream()
                    .map(this::mapToBook)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            logger.error("Error finding book", e);
        }
        return Collections.emptyList();

    }

    private Book mapToBook(Element element) {
        String bookTitle = element.select("title").text();
        String bookIsbn = element.select("isbn13").text();
        String bookPublisher = element.select("publisher").text();
        String bookCover = element.select("cover").text();
        return new Book(bookTitle, bookIsbn, bookPublisher, bookCover);
    }
}
