package com.bookfinder.control.extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Function;

/**
 * Created by jdzido on 07.02.2018.
 */
public class ApressOfferFinder implements OfferFinder {
    private static final Logger logger = LoggerFactory.getLogger(ApressOfferFinder.class);

    public Function<String, Element> searchBookstore() {
        return (String isbn) -> {
            try {
                logger.info("start apress search: {}", isbn);
                return Jsoup.connect("https://www.apress.com/gp/book/" + isbn).get();

            } catch (IOException e) {
                logger.error("error searching apress: {}", e.getLocalizedMessage());
                return null;
            } finally {
                logger.info("stop apress search");
            }
        };
    }

    @Override
    public Double extractPrice(Element element) {
        logger.info("extracting price");
        if(element != null) {
            Element el = element.getElementsByAttributeValue("name", "price").first();

            String priceFull = el.val();
            if (!priceFull.contains("€")) {
                logger.info("no price detected");
                return null;
            }

            Double price = Double.parseDouble(priceFull.replace("€", "").replace(",", "."));

            logger.info("apress price: {}", price);
            return price;
        } else {
            logger.info("No document");
            return null;
        }

    }

    @Override
    public String extractLink(Element element, String isbn) {
        logger.info("extracting link");
        return "https://www.apress.com/gp/book/" + isbn;
    }

    @Override
    public String extractCurrency(Element element) {
        return "€";
    }
}
