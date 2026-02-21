package org.howard.edu.lsp.assignment3;

public class PipelineReport {

    private int rowsRead = 0;
    private int rowsTransformed = 0;
    private int rowsSkipped = 0;

    public void incrementRowsRead() {
        rowsRead++;
    }

    public void incrementRowsTransformed() {
        rowsTransformed++;
    }

    public void incrementRowsSkipped() {
        rowsSkipped++;
    }

    public void printSummary(String outputPath) {
        System.out.println("Run Summary");
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output written to: " + outputPath);
    }
}