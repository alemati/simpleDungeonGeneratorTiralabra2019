### Following document contains general description of the program structure and all implementation steps that were taken during the realization of the program in chronological order.  

At this point program isn't yet ready and it's structure may change during next couple of weeks. 

#### General idea  

Inspiration for this program was gathered from article [Rooms and Mazes: A Procedural Dungeon Generator](http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/)
and it's principles were used in this project, but it is not 
straight copy of suggested solution. For instance, my program connects passageway to rooms in a different manner.  

Structure is very simple and it is concentrated on simple two-dimensional  array that contains squares. Each square represents 
one unit on the map and in the end it can be shown as a wall (#), open space (room or passageway) or door (+, unit which connects 
passageway with room). Also program uses Room class, but its role in my implementation isn't significant. It is used only in the very 
beginning of the program run when rooms are being placed on the empty map. In addition to that program has one more main class 
(SimpleDungeonGenerator) and one test class. 

#### Program run  

User gives map parameters (height and width), room parameters (min and max room sizes) and number of attempts (NoA, this give user 
an opportunity to control map density). After that program generates asked amount of valid sized rooms and tries to "place them 
on the map". In practice, it changes squares statuses in main two-dimensional array (map) in a way that combined shape of
certain group of neighbour squares looks like a room in console output. After adding room "on the map" program doesn't store 
room data in special data structure. It is just manipulates main "map" in a way that it turns one square into "parentRoomSquare"
which represents all room and contains "room data", and attach all others room squares to this "parent". 

After room addition phase is over, program fills all remaining "open" (non room space) with passageway. Passageway is generated 
using randomized flood fill algorithm and recursion. Randomization is executed in a next way: in recursion we have 4 options 
(continue passageway to left/right/up/down) -> to randomize option order in each recursion call program puts all four options in
array and shuffle it using Fisher–Yates shuffle algorithm and then calls all options in (shuffled) order.  

While passageway grows it creates connections with rooms it encounters. When new passageway square is created, program checks if 
any of its neighbors is attached to unconnected room. If it finds one, program connects new room to the passageway right away. 
When there is no empty squares anymore, flood fill algorithm shuts down. At this point we have map with rooms and passageway. 

In the last phase program removes all dead ends from the map replacing them by walls and prints map in the console.

## This document will be supplemented in the future.






