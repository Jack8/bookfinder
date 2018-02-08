package com.bookfinder.control.extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * Created by jdzido on 07.02.2018.
 */
public class AmazonOfferFinder implements OfferFinder {
    private static final Logger logger = LoggerFactory.getLogger(AmazonOfferFinder.class);

    public Function<String, Element> searchBookstore() {
        return (String isbn) -> {
            try {
                logger.info("start amazon search: {}", isbn);
                Document doc =  Jsoup.connect("https://www.amazon.de/s/")
                        .data("field-isbn", isbn)
                        .data("sort", "price").get();
                if(doc != null) {
                    return doc.getElementById("result_0");
                }
                return doc;

            } catch (IOException e) {
                logger.error("error searching amazon", e);
                return null;
            } finally {
                logger.info("stop amazon search");
            }
        };
    }

    @Override
    public Double extractPrice(Element element) {
        logger.info("extracting price");

        if(element != null) {
            List<Element> prices = element.getElementsByClass("a-offscreen");
            if(prices.isEmpty()) {
                prices = element.getElementsByClass("s-price");
            }
            String priceFull = prices.get(0).ownText();
            if (!(priceFull.contains("€") || priceFull.contains("EUR"))) {
                logger.info("no price detected");
                return null;
            }
            Double price = Double.parseDouble(priceFull.replace("€", "").replace("EUR", "").replace(",", "."));

            logger.info("amazon price: {}", price);
            return price;
        } else {
            logger.info("No price detected");
            return null;
        }

    }

    @Override
    public String extractLink(Element element, String isbn) {
        logger.info("extracting link");

        if(element != null) {
            String link = element.getElementsByClass("a-link-normal").first().attr("href");

            logger.info("amazon link: {}", link);
            return link;
        } else {
            logger.info("No link detected");
            return null;
        }

    }

    @Override
    public String extractCurrency(Element element) {
        return "€";
    }
}
