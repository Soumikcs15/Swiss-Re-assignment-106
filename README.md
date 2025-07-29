# Swiss-Re-assignment-106
Swiss Re assignment 106 for Soumik
This code comprises two main services: 

1. **HCMAnalyzer Service**: This service reads and analyzes the employee file to prepare report data. 
2. **Report Service**: This service is responsible for generating the reports, which can either be printed to the console or saved as CSV files.

**Important Notes**:
1. Instead of creating a hierarchical tree, employee levels are calculated recursively. Once the hierarchy of a manager is determined, recursive calls are avoided.
2. The minimum average salary, maximum average salary, and highest hierarchical level are configurable parameters that can be set in the `com.swissre.constants.ConfigConstants` class.
