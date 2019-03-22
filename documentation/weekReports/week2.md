Week 2 | Hours | Work done
------ | ------| ------
22.3.19|    9   |Map initialization, placing rooms, maven and tests, javadoc and comments
sum | 9  |  

This week I got reminded how uncomfortable it might be if you leave all the work till the deadline day. I am not writing this 
at the very last possible moment, but still... I clearly have to do a better job with my schedule.

### Current result
After second week (day), program is able to create a emty map and then add rooms to it. That's how results are looking right now:  
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/map.png" width="600">  
░ = grass, # = wall  
One thing that I was thinking about a lot are walls. I wasn't and I am still not sure if they are necessary, but they do make
sense in my opinion. I've noticed that if I don't surround every room by walls then rooms can possibly create 
together very big one and in some cases I don't want that. Also I think I will do it the same way with maze. I mean, 
every passageway square will be between two walls. Otherwise, managing the maze would be harder I think (a lot of open space,
hard to decide which square is deadend and which one should stay).  

### Tests
There is test coverage:  
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/testCoverage22.3.png" width="1000">  
SimpleDungeonGenerator is not yet tested at all, but it is main class and I hope that at this point is is not crutial. 
I didn't quite realize which way is the best to test Map class and its __console output__. I used ByteArrayOutputStream outContent 
and got positive results, but I am still not sure if that's the way to do it. Gotta do more research on that.

### Plans  
Next week I want to fill remaining space with maze and connect rooms to it. Gonna do it with flood fill algorithm. After received
feedback I thought how I want my dungeons to look like and at this point I just want every square of dungeon to be reachable 
from any other. And maybe I want loops between rooms too.
