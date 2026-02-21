package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductCSVReader {

    public List<Product> read(String inputPath, PipelineReport report) throws IOException {

        List<Product> products = new ArrayList<>();
        File inputFile = new File(inputPath);

        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IOException("Input file not found: " + inputPath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

            br.readLine(); // Skip header
            String line;

            while ((line = br.readLine()) != null) {

                report.incrementRowsRead();

                if (line.trim().isEmpty()) {
                    report.incrementRowsSkipped();
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length != 4) {
                    report.incrementRowsSkipped();
                    continue;
                }

                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    BigDecimal price = new BigDecimal(fields[2].trim());
                    String category = fields[3].trim();

                    products.add(new Product(id, name, price, category));

                } catch (NumberFormatException e) {
                    report.incrementRowsSkipped();
                }
            }
        }

        return products;
    }
}