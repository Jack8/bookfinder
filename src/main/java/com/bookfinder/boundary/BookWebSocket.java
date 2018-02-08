package com.bookfinder.boundary;

import com.bookfinder.control.BookSearchHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by jdzido on 07.02.2018.
 */
@ServerEndpoint("/books")
public class BookWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(BookWebSocket.class);

    @Inject
    private BookSearchHandler bookSearchHandler;

    @OnOpen
    public void open(Session session) {
        logger.info("Session opened");
    }

    @OnClose
    public void close(Session session) {
        logger.info("Session closed");
    }

    @OnError
    public void onError(Throwable error) {
        logger.error("WebSocket error", error);
    }

    @OnMessage
    public void handleMessage(Session session, String message) {
        logger.info("Received message: {}", message);

        bookSearchHandler.searchByTitle(session, message);

    }
}
