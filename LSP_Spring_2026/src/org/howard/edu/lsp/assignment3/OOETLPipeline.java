package org.howard.edu.lsp.assignment3;
/*
 * Name: Malik John
 */
// Import classes for reading and writing files
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Import BigDecimal for precise decimal math 
import java.math.BigDecimal;
import java.math.RoundingMode;

public class OOETLPipeline {

    // Program entry point
    public static void main(String[] args) {

        // Relative path to the input CSV file
        String inputPath = "data/products.csv";

        // Relative path to the output CSV file
        String outputPath = "data/transformed_products.csv";

        // Counts all non-header rows encountered (including invalid ones)
        int rowsRead = 0;

        // Counts rows that are skipped due to errors
        int rowsSkipped = 0;

        // Counts rows successfully transformed and written
        int rowsTransformed = 0;

        // Create a File object for the input file
        File inputFile = new File(inputPath);

        // =========================
        // Handle missing input file
        // =========================
        if (!inputFile.exists() || !inputFile.isFile()) {
            // Print clear error message (no stack trace)
            System.out.println("ERROR: Input file not found: " + inputPath);
            return; // Exit cleanly
        }

        // Ensure the output directory (data/) exists
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create directory if needed
        }

        // Use try-with-resources so files close automatically
        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ) {

            // Read the first line (header row)
            String header = br.readLine();

            // Always write the output header row
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();

            // If the file had no content at all, exit after writing header
            if (header == null) {
                printSummary(rowsRead, rowsTransformed, rowsSkipped, outputPath);
                return;
            }

            String line;

            // Read each remaining line in the CSV
            while ((line = br.readLine()) != null) {

                // Skip blank or whitespace-only lines
                if (line.trim().isEmpty()) {
                    rowsRead++;
                    rowsSkipped++;
                    continue;
                }

                // Count this as a row read
                rowsRead++;

                // Split the line by commas
                // -1 keeps empty fields if they exist
                String[] fields = line.split(",", -1);

                // Skip if not exactly 4 fields
                if (fields.length != 4) {
                    rowsSkipped++;
                    continue;
                }

                // Trim whitespace from each field
                String productIdStr = fields[0].trim();
                String name = fields[1].trim();
                String priceStr = fields[2].trim();
                String category = fields[3].trim();

                int productId;
                BigDecimal price;

                // Try parsing ProductID
                try {
                    productId = Integer.parseInt(productIdStr);
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                    continue;
                }

                // Try parsing Price
                try {
                    price = new BigDecimal(priceStr);
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                    continue;
                }

                // =========================
                // TRANSFORM STEP (IN ORDER)
                // =========================

                // 1) Convert product name to uppercase
                String transformedName = name.toUpperCase();

                // Store original category for later checks
                String originalCategory = category;

                // 2) Apply 10% discount if category is Electronics
                BigDecimal transformedPrice = price;
                if ("Electronics".equals(originalCategory)) {
                    transformedPrice = transformedPrice.multiply(new BigDecimal("0.90"));
                }

                // Round price to exactly 2 decimal places (HALF_UP)
                BigDecimal finalRoundedPrice =
                        transformedPrice.setScale(2, RoundingMode.HALF_UP);

                // 3) Upgrade category to Premium Electronics if rules apply
                String transformedCategory = originalCategory;
                if ("Electronics".equals(originalCategory)
                        && finalRoundedPrice.compareTo(new BigDecimal("500.00")) > 0) {
                    transformedCategory = "Premium Electronics";
                }

                // 4) Determine PriceRange based on final rounded price
                String priceRange;
                if (finalRoundedPrice.compareTo(new BigDecimal("10.00")) <= 0) {
                    priceRange = "Low";
                } else if (finalRoundedPrice.compareTo(new BigDecimal("100.00")) <= 0) {
                    priceRange = "Medium";
                } else if (finalRoundedPrice.compareTo(new BigDecimal("500.00")) <= 0) {
                    priceRange = "High";
                } else {
                    priceRange = "Premium";
                }

                // =========================
                // LOAD STEP
                // =========================

                // Convert price to string (always 2 decimals)
                String priceOutput = finalRoundedPrice.toPlainString();

                // Write the transformed row to output CSV
                bw.write(productId + "," +
                         transformedName + "," +
                         priceOutput + "," +
                         transformedCategory + "," +
                         priceRange);
                bw.newLine();

                // Count successful transformation
                rowsTransformed++;
            }

        } catch (IOException e) {
            // Graceful I/O error handling
            System.out.println("ERROR: I/O error occurred: " + e.getMessage());
            return;
        }

        // Print final execution summary
        printSummary(rowsRead, rowsTransformed, rowsSkipped, outputPath);
    }

    // Prints the required run summary
    private static void printSummary(int rowsRead,
                                     int rowsTransformed,
                                     int rowsSkipped,
                                     String outputPath) {

        System.out.println("Run Summary");
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output written to: " + outputPath);
    }
}