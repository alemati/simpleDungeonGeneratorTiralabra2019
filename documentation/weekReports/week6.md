Week | Hours | Work done
------ | ------| ------
24.3.19|3|peer review
26.3.19|7|MyRandom, testing, refactoring, documentation
sum|10|

All the way I was thinking that flood fill algorithm is the "hardest" part of the program and I assumed that it is the most
time demanding one, but today I faced reality =). I was quite surprised how slooooow the way I am removing dead ends is. 

with removing dead ends             |  without removing dead ends
:-------------------------:|:-------------------------:
![](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/unnamed.png)|![](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/unnamed%20(1).png) 
  
Program doesn't keep track of dead ends during maze generation and when removing phase begins program loops throug all array from 
very first square (0,0) in an attempt to find dead end. After one such is found it is turned into a wall and then maze square 
previous to the current one is checked too, and so on. But when one "dead end branch" is removed, program once again starts looping
whole map hoping to find another one, and looping starts once again from (0,0). So there may be (and actually, more often then not, is) 
a lot of looping... I will try to fix that. Dunno how, but I will try to figure it out. Should have started testing earlier

### Current rusult  
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/resultAfterWeek6.png" width="1000">   

### Test coverage
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/coverageAfterWeek6.png" width="1000">  

### Plans  
I will try to increase performance by improving dead ends removing process.
