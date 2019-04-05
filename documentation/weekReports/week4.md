Week 2 | Hours | Work done
------ | ------| ------
4.4.19|    5   |connecting rooms and mazes
5.4.19|    9   |deleting dead ends, documentation
sum | 14  |      

This week my focus was on connecting rooms with passageway. Core functionality is achived (needs a lot of optimization tho (
now for instance, door may appear at room corner)).

### Current rusult  
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/mapAfterWeek4.png" width="1000">  
+ -> door   

### Test coverage
<img src="https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/testCoverageAfterWeek4.png" width="1000">  

### My thoughts
During this week I have noticed that program can very easily run into java.lang.StackOverflowError, but I think I can recognize the source of the problem. This error occurs when program tries to create big map (starting from 200x200) that has a lot of open space (small number of rooms or bunch of very little rooms). In that case flood fill alghoritm has to go deep. too deep. One solution that I can think of is to force flood fill alghoritm to stop going deeper and shut it down. Then start new recursion from where last one ended.

This week I also went with a bit of a different strategy when connecting passageway with rooms compared to the method described in reference article [Rooms and Mazes: A Procedural Dungeon Generator](http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/).My solution is to connect room to maze right away after maze touches unconnected room for the first time. But this approach assumes that there will be only one maze covering all map. In article solution there may be plenty of different mazes (for example, if rooms create closed area, maze can't grow). I want to make only one. And right now I think it would be  possible if I leave open space (at least one unit) betwen rooms. So flood fill alghoritm will always have space to grow and there will be only one maze. If that will be the case then I will be able to implement my solution.

### Next week
At this moment program uses only arrays. No other data structures. I mean, I will have to make my very own Random, but that's about it. So if there won't be any big changes I will focus on testing, documentation and will work on my recursion.
