# User guide  

## User input  

Dungeon generator creates a map based on given parameters. Parameters are: map width, map height, min room size, max room size and 
room placing attempts. All parameters should be integers. Program doesn't validate in any way users input and if given 
parameters were invalid (string for instance) program will crush.

## How to start   

You can run dungeon generator in NetBeans or in console using jar file. 

#### NetBeans

Download project to your computer (for instance command: git clone git@github.com:alemati/simpleDungeonGeneratorTiralabra2019.git) 
and open it in NetBeans. While being in NetBeans trigger program by cliking on "Run project" button. After that program 
will ask you parameters and will print map in the console. If map doesn't look right in console make sure to zoom out a little.

#### Console

[Download jar file](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/releases/tag/v1.2). After downloading user may run program in two different ways. User can either run jar file without parameters 
(then program will ask you to give them) 
```
java -jar mavenSimpleDungeonGenerator-1.0-SNAPSHOT.jar
```   
or run jar file with parameters (that way program won't ask you to give them second time)  
```
java -jar mavenSimpleDungeonGenerator-1.0-SNAPSHOT.jar width height minRoomSize mazRoomSize attempts
```
If map doesn't look right in console make sure to zoom out a little bit.




