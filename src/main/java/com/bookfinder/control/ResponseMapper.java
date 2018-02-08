package com.bookfinder.control;

import com.bookfinder.model.Book;
import com.bookfinder.model.Offer;
import com.bookfinder.model.SearchStatus;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

/**
 * Created by jdzido on 07.02.2018.
 */
public class ResponseMapper {

    private static final String STATUS_FIELD = "status";

    private ResponseMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static String negativeResponse() {
        JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add(STATUS_FIELD, SearchStatus.NOT_FOUND.toString())
                .build();
        return message.toString();
    }

    public static String positiveResponse(Book book) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add(STATUS_FIELD, SearchStatus.FOUND_BOOK.toString())
                .add("title", book.getTitle())
                .add("publisher", book.getPublisher())
                .add("isbn", book.getIsbn())
                .add("cover", book.getCover())
                .build();
        return message.toString();
    }

    public static String positiveResponse(Offer offer) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add(STATUS_FIELD, SearchStatus.FOUND_OFFER.toString())
                .add("isbn", offer.getIsbn())
                .add("link", offer.getLink())
                .add("currency", offer.getCurrency())
                .add("price", offer.getPrice())
                .build();
        return message.toString();
    }
}
