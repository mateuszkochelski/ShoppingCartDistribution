# ShoppingCartDistribution
### Problem
The task is to write an algorithm solving the "set cover problem" which is np-hard problem.
The additional requirement was that if it is possible to select a pair of combinations with the smallest number of sets, we choose the combination where the largest set is the most numerous.

### Algorithm
I use brute-force algorithm to check every possibility, 
### Solution for larger Data
Set cover problem can be reduced to ILP problem. For larger Data I would use ILP solver.

### Prerequisites
- Java 17
- Gradlew

### Usage

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/mateuszkochelski/ShoppingCartDistribution.git
2. Navigate to the project directory:
   ```bash
   cd ShoppingCartDistribution/ShoppingCartDistribution
3. Build project:
   ```bash
   gradlew build
4. You can run tests using
   ```bash
   gradlew tests --rerun-tests
5. You can create fat-jar using
   ```bash
   gradlew shadowJar
6. In build/libs directory you will have
ShoppingCartDistribution-1.0-SNAPSHOT-all.jar fat-jar file our library.


### How to link library as dependency
1. Put jar file in lib folder in your gradle project
2. Add in your gradle.settings file this code
```
dependencies
{
    implementation files('lib/ShoppingCartDistribution-1.0-SNAPSHOT-all.jar')
}
```
