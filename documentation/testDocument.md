## Test document  

### Current test coverage
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/testCoverageAfterWeek4.png" width="1000">  
At this point project is tested only using JUnit tests. Project idea is to create and show a map in console. To test console 
output I use ByteArrayOutputStream and PrintStream classes.    

   

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


I have noticed that program can very easily run into java.lang.StackOverflowError, but I think I can recognize the source of the problem. 
This error occurs when program tries to create big map (starting from 200x200) that has a lot of open space (small number of
rooms or bunch of very little rooms). In that case flood fill alghoritm has to go deep and sometimes it can be too deep.
One solution that I can think of is to force flood fill alghoritm to stop going deeper and shut it down. Then start new 
recursion from where last one ended.

## This document will be supplemented in the future.
