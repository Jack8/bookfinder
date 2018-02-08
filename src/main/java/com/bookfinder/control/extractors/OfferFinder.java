package com.bookfinder.control.extractors;

import com.bookfinder.model.Offer;
import org.jsoup.nodes.Element;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by jdzido on 07.02.2018.
 */
public interface OfferFinder {

    Function<String, Element> searchBookstore();

    default BiFunction<Element, String, Offer> findOffer() {
        return (Element element, String isbn) -> {
            Double price = extractPrice(element);
            String link = extractLink(element, isbn);
            String currency = extractCurrency(element);

            if (price != null && link != null) {
                return new Offer(isbn, link, currency, price);
            }
            return null;
        };
    }

    Double extractPrice(Element element);

    String extractLink(Element element, String isbn);

    String extractCurrency(Element element);

}
