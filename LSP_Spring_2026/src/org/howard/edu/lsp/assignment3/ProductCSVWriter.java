package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.util.List;

public class ProductCSVWriter {

    public void write(String outputPath,
                      List<Product> products,
                      List<PriceRange> ranges) throws IOException {

        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();

            for (int i = 0; i < products.size(); i++) {

                Product p = products.get(i);
                PriceRange range = ranges.get(i);

                bw.write(p.getProductId() + "," +
                        p.getName() + "," +
                        p.getPrice().toPlainString() + "," +
                        p.getCategory() + "," +
                        range);
                bw.newLine();
            }
        }
    }
}