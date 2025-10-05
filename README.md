# Assignment 2 – Algorithmic Analysis and Peer Code Review
**Reviewer and Algorithm:** Baldauren Zaman (Student A — Boyer–Moore Majority Vote)  
**Partner:** Zhaniya Abdraiym (Student B — Kadane’s Algorithm)

---

## Overview

This repository contains the implementation and performance analysis of the **Boyer–Moore Majority Vote** algorithm — a linear-time algorithm used to find the element that appears more than ⌊n/2⌋ times in an array, if such an element exists.
This work is part of **Assignment 2: Algorithmic Analysis and Peer Code Review**, where each student implements one algorithm and reviews their partner’s implementation.

---

## Project Structure

```
assignment2-boyermoore/
├── src/
│   ├── main/java/
│   │   ├── algorithms/BoyerMooreMajority.java
│   │   ├── metrics/PerformanceTracker.java
│   │   └── cli/BenchmarkRunner.java
│   └── test/java/
│       └── algorithms/BoyerMooreMajorityTest.java
├── docs/
│   ├── analysis-report.pdf
│   └── performance-plots/
├── scripts/
│   └── aggregate_and_plot.py
├── README.md
└── pom.xml
```

## Implementation Details

- **Algorithm:** Boyer–Moore Majority Vote  
- **Time Complexity:**  
  - Best Case: Θ(n)  
  - Average Case: Θ(n)  
  - Worst Case: Θ(n)  
- **Space Complexity:** Θ(1) — constant extra memory  
- **Optimization:** Single-pass linear scan with candidate and counter tracking  
- **Metrics Collected:** comparisons, updates, array accesses  
- **Language and Tools:** Java 17, Maven, JUnit 5  

---

## How to Run

### 1. Build the Project

```
mvn clean compile
```

### 2. Run the Benchmark
````
mvn exec:java -Dexec.mainClass=cli.BenchmarkRunner
````

This command will generate the following CSV file:

`metrics_boyer_ns.csv`

### 3. Plot the Results (Optional)

To generate performance plots and summary tables, run:

python scripts/aggregate_and_plot.py


The generated files will appear in:

docs/performance-plots/boyer_summary_ns.csv
docs/performance-plots/time_vs_n_ns.png
docs/performance-plots/execution_time_table.md

## Results Summary
```
Input Size (n)	Time (ns)	Comparisons	Updates	Array Accesses
100	20,000	99	50	200
1,000	120,000	999	501	2,000
10,000	950,000	9,999	5,001	20,000
```

### Observation:
Execution time increases linearly with input size, confirming the Θ(n) theoretical complexity.
The algorithm maintains constant memory usage and completes in a single traversal, ensuring high efficiency for large datasets.

### Testing

The JUnit 5 test suite covers:
* Empty and single-element arrays
* Arrays without a majority element
* Arrays with a clear majority element
* Randomized validation against brute-force checks

To run all tests:
```
mvn test
```
### Key Takeaways

The Boyer–Moore Majority Vote algorithm is one of the most elegant linear-time algorithms.
It achieves optimal performance in both time and space complexity.
Ideal for streaming or real-time majority detection problems.
Experimental data aligns closely with theoretical predictions.

### Team Information
````
Role	Student	Algorithm
Student A	Zhaniya Abdraiym	Kadane’s Algorithm
Student B	Baldauren [You]	Boyer–Moore Majority Vote
````
---