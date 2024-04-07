# ShoppingCartDistribution
### Mateusz_Kochelski_Java_Krakow
### Problem
The task is to write an algorithm solving the "set cover problem" which is np-hard problem.
The additional requirement was that if it is possible to select a pair of combinations with the smallest number of sets, we choose the combination where the largest set is the most numerous.

### Algorithm
I use exponential algorithm to check every possibility.
I create bitmask array sorted by number of ones in bitmask. Then I check for every number of ones in bitmask, whether set union of these sets is superset of our expected set. Then I check which combination of sets is fulfills the additional condition.
I stop checking bitmasks of size i+1 when I found a solution that satisfies the bitmask of size i.

### Solution for larger Data
Set cover problem can be reduced to ILP problem. For larger Data I would use ILP solver.

### Prerequisites
- Java 17
- Gradle

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
   gradlew tests --rerun-tasks
5. You can create fat-jar using
   ```bash
   gradlew shadowJar
6. In build/libs directory you will have
   ```
   ShoppingCartDistribution-1.0-SNAPSHOT-all.jar
   ```
fat-jar file our library.


### How to link library as dependency
1. Put jar file in lib folder in your gradle project
2. Add in your gradle.settings file this code
   ```
   dependencies
   {
       implementation files('lib/ShoppingCartDistribution-1.0-SNAPSHOT-all.jar')
   }
   ```
