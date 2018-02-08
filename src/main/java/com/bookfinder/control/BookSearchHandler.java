package com.bookfinder.control;

import com.bookfinder.control.extractors.OfferFinder;
import com.bookfinder.control.extractors.BookFinder;
import com.bookfinder.model.Book;
import com.bookfinder.model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by jdzido on 07.02.2018.
 */
public class BookSearchHandler {
    private static final Logger logger = LoggerFactory.getLogger(BookSearchHandler.class);

    private static final int SEARCH_LIMIT = 5;

    @Resource
    private ManagedExecutorService mes;

    @Inject
    private BookFinder bookFinder;

    @Inject
    @Any
    private Instance<OfferFinder> priceFinders;

    public void searchByTitle(Session session, String title) {

        List<Book> books = bookFinder.searchByTitle(title);

        if (!books.isEmpty()) {
            books.stream()
                    .limit(SEARCH_LIMIT)
                    .forEach(book -> {
                        sendMessage(session, ResponseMapper.positiveResponse(book));
                        searchByIsbn(session, book.getIsbn());
                    });
        } else {
            sendMessage(session, ResponseMapper.negativeResponse());
        }
    }

    private void searchByIsbn(Session session, String isbn) {

        StreamSupport.stream(priceFinders.spliterator(), false)
                .forEach(finder -> mes.submit(() -> {
                    Offer offer = finder.searchBookstore()
                            .andThen(document -> finder.findOffer().apply(document, isbn))
                            .apply(isbn);
                    if (offer != null) {
                        sendMessage(session, ResponseMapper.positiveResponse(offer));
                    }
                }));
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("Error sending message", e);
        }
    }
}
