package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductTransformer {

    public PriceRange transform(Product product) {

        // 1) Uppercase name
        product.setName(product.getName().toUpperCase());

        BigDecimal price = product.getPrice();
        String category = product.getCategory();

        // 2) Apply 10% discount if Electronics
        if ("Electronics".equals(category)) {
            price = price.multiply(new BigDecimal("0.90"));
        }

        // Round to 2 decimal places
        price = price.setScale(2, RoundingMode.HALF_UP);
        product.setPrice(price);

        // 3) Upgrade to Premium Electronics if applicable
        if ("Electronics".equals(category)
                && price.compareTo(new BigDecimal("500.00")) > 0) {
            product.setCategory("Premium Electronics");
        }

        // 4) Determine price range
        return determinePriceRange(price);
    }

    private PriceRange determinePriceRange(BigDecimal price) {

        if (price.compareTo(new BigDecimal("10.00")) <= 0) {
            return PriceRange.Low;
        } else if (price.compareTo(new BigDecimal("100.00")) <= 0) {
            return PriceRange.Medium;
        } else if (price.compareTo(new BigDecimal("500.00")) <= 0) {
            return PriceRange.High;
        } else {
            return PriceRange.Premium;
        }
    }
}