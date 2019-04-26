## Test document  

### Current test coverage
Program is tested using JUnit tests.
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/coverageAfterWeek6.png" width="1000">  
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
Program and it's performance was tested empirically. In tests both map height and width parameters where the same (_n_), min room size was about 5% of _n_ and max room size 10% of _n_. Room placing attemps was equal to _n_. 

While testing I have noticed that the most time demanding phase is removing dead ends. Results below show the speed of program with and without removing dead ends.

map side size | with removing (ms) | without removing (ms)
------ | ------| ------
50|29|13
100|73|25
200|448|43    
300|1948|68
400|5626|89
500|14418|139 
diagram|![](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/unnamed.png)|![](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/unnamed%20(1).png)   

Clearly, dead ends removing phase needs heavy polishing. Right now it is obivious that time compelxyty is nowhere near O(n) that was suggested at the beginning of the project.

Space complexity on the other hand meets expectation of O(n) where n is total number of squares on map. Program creates only one map and doesn't make any duplicates of it. Part that can demand more space then any other is flood fill alghoritm because it uses MyStack and in worst case scenario every square can be pushed into MyStack. But even then it cannot push more then n squares.
