package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Assignment 3 ETL pipeline.
 * Coordinates reading, transforming, and writing product data.
 */
public class OOETLPipeline {

    /**
     * Main method that executes the ETL process.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        String inputPath = "data/products.csv";
        String outputPath = "data/transformed_products.csv";

        PipelineReport report = new PipelineReport();
        ProductCSVReader reader = new ProductCSVReader();
        ProductTransformer transformer = new ProductTransformer();
        ProductCSVWriter writer = new ProductCSVWriter();

        try {
            List<Product> products = reader.read(inputPath, report);

            List<PriceRange> ranges = new ArrayList<>();

            for (Product product : products) {
                PriceRange range = transformer.transform(product);
                ranges.add(range);
                report.incrementRowsTransformed();
            }

            writer.write(outputPath, products, ranges);

        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        report.printSummary(outputPath);
    }
}