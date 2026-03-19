The tool used was Gemini. 



Prompt: 
Below is the code for the assignemnt. Help me develop the code to refactor using a design pattern. And then help me develop the driver to test said code.


package org.howard.edu.lsp.midterm.strategy;





public class PriceCalculator {



    public double calculatePrice(String

customerType, double price) {



        double finalPrice = price;



        if

(customerType.equals("REGULAR")) {

            finalPrice = price;

        }



        if

(customerType.equals("MEMBER")) {

            finalPrice = price * 0.90;

        }



        if

(customerType.equals("VIP")) {

            finalPrice = price * 0.80;

        }



        if

(customerType.equals("HOLIDAY")) {

            finalPrice = price * 0.85;

        }



        return finalPrice;

    }



}




Part 2 - Refactoring Using a Design Pattern (10 points)

Refactor the provided design to use the Strategy Pattern

discussed during lecture.

Your refactored implementation must be placed in the

package:

org.howard.edu.lsp.midterm.strategy

Each class must be placed in its own .java file. Your

implementation should demonstrate the use of the Strategy design pattern to

support different discount behaviors.

All classes and public methods must include Javadoc

comments.



Part 3 - Driver Program (5 points)

Create a Driver class that demonstrates that your Strategy

Pattern implementation works correctly.

Your Driver class must be placed in the package:

org.howard.edu.lsp.midterm.strategy

Your driver must demonstrate the following cases using a

purchase price of 100.0:

• REGULAR customer

• MEMBER customer

• VIP customer

• HOLIDAY customer

Your driver must produce output in the following format:

REGULAR: 100.0



MEMBER: 90.0



VIP: 80.0



HOLIDAY: 85.0

The output must be produced by executing your Strategy-based

implementation, not by hardcoding the results.

Output: 
Refactored Strategy Pattern Implementation
Package: org.howard.edu.lsp.midterm.strategy

1. PricingStrategy.java (The Interface)
Java
package org.howard.edu.lsp.midterm.strategy;

/**
 * Interface for defining discount strategies for different customer types.
 */
public interface PricingStrategy {
    /**
     * Calculates the price based on a specific strategy.
     * @param price the original price
     * @return the calculated price after discount
     */
    double calculate(double price);
}
2. RegularStrategy.java, MemberStrategy.java, VIPStrategy.java, HolidayStrategy.java
(Create these 4 files individually)

Java
// RegularStrategy.java
package org.howard.edu.lsp.midterm.strategy;
public class RegularStrategy implements PricingStrategy {
    @Override
    public double calculate(double price) { return price; }
}

// MemberStrategy.java
package org.howard.edu.lsp.midterm.strategy;
public class MemberStrategy implements PricingStrategy {
    @Override
    public double calculate(double price) { return price * 0.90; }
}

// VIPStrategy.java
package org.howard.edu.lsp.midterm.strategy;
public class VIPStrategy implements PricingStrategy {
    @Override
    public double calculate(double price) { return price * 0.80; }
}

// HolidayStrategy.java
package org.howard.edu.lsp.midterm.strategy;
public class HolidayStrategy implements PricingStrategy {
    @Override
    public double calculate(double price) { return price * 0.85; }
}
3. PriceCalculator.java (The Context)
Java
package org.howard.edu.lsp.midterm.strategy;

/**
 * Context class that uses a PricingStrategy to calculate the final price.
 */
public class PriceCalculator {
    private PricingStrategy strategy;

    /**
     * Sets the current pricing strategy.
     * @param strategy the strategy to use
     */
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calculates the price using the currently set strategy.
     * @param price the original price
     * @return the discounted price
     */
    public double calculatePrice(double price) {
        if (strategy == null) {
            return price;
        }
        return strategy.calculate(price);
    }
}