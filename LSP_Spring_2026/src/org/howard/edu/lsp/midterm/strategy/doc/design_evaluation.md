
The original `PriceCalculator` implementation presents several issues:

1. **Violation of the Open/Closed Principle :** The class is not closed for modification. Every time a new customer type (e.g., "STUDENT" or "SENIOR") is added, the `calculatePrice` method must be edited. This increases the risk of introducing bugs into existing, working logic.

2. **Poor Scalability:** As the number of customer types grows, the `if-else` or `if` block chain becomes massive and difficult to read, maintain, or test in isolation.

3. **Hardcoded Logic:** The discount percentages are hardcoded within a single method. This prevents the system from dynamically changing strategies or reusing specific discount logic in other parts of the application.

4. **Testing Difficulty:** You cannot test the "VIP" logic without involving the entire `PriceCalculator` class. In a refactored Strategy approach, each strategy can be unit-tested independently.