## Test document  

### Current test coverage
Program is tested using JUnit tests.
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/testsAfterWeek7.png" width="1000">  
To test console output I use ByteArrayOutputStream and PrintStream classes.

Tests can be performed for instance in a console:
* download project
```
git@github.com:alemati/simpleDungeonGeneratorTiralabra2019.git
```
* move to downloaded directory
```
cd simpleDungeonGeneratorTiralabra2019/
```
* move to source code containing directory
```
cd mavenSimpleDungeonGenerator
```
* run tests
```
mvn test jacoco:report
```
* open results
```
open target/site/jacoco/index.html
```

## Empirical testing  
Program and its performance was tested empirically. In tests both map height and width parameters where the same (_n_), min room size was about 2% of _n_ and max room size 5% of _n_. Room placing attemps was equal to _n/2_. 

Results:  

map side size | time (ms) 
------ | ------
250|106
500|189
750|204
1000|535
1250|1040
1500|1060
1750|1174
2000|2385
graph|![](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/graph.png)
