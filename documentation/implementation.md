### Following document contains general description of the program structure.  


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
using randomized iterative flood fill algorithm. That's how it works: first, we take one square from MyStack. If it can be turned into passageway, program does so. At the same time program checks if this new passageway square is attached to unconnected room and, if that's the case, places the door. After that we have 4 options to continue our passageway 
(continue to left/right/up/down). Program randomizes order of those options using Fisher–Yates shuffle algorithm and pushes them into MyStack and cycle starts from the beginning. Flood fill stops when MyStack becomes empty. At this point we have map with rooms and passageway. 

After that program removes all dead ends from the map replacing them by walls. Deadend is a square that is surrounded by at least 3 walls. After turning deadend to wall program checks all neighbours and if one of them is passageway square, program turns it into wall too. That process continues until there is no deadends anymore. After that program checks how many rooms on map are connected. At the end map will be showen in the console.

#### Used alghoritms and data structures  
Program uses basic arrays, MyStask (selfmade Stack Data Structure) and MyRandom. Also randomized iterative flood fill algorithm is used for creating a passageway and Fisher–Yates shuffle alghoritm is used for randomizing squares neighbours while making passageway.

#### Achived time and space complexity 
[Test document](https://github.com/alemati/simpleDungeonGeneratorTiralabra2019/blob/master/documentation/testDocument.md)   

Time that program requires to create a map depends on given parameters. Usually phase that is filling map with passageway uses more time then any other and it demands esspacily a lot of time when there is a lot of open space on the map. But if user will put absurd number of room placing attempts (in an attempt to make map as dense as possible) then it can negatively affect on performance even more.

Right now, with small amount of square on the map (less then 1700 on  one side, 1700*1700=3062500 squares) program seems to work in O(n) range. 

Space complexity meets expectation of O(n) where n is total number of squares on map. Program creates only one map and doesn't make any duplicates of it. The most space heavy part of the program is flood fill alghoritm because it uses MyStack and in worst case scenario every square can be pushed into MyStack making size of it equal to n. But even then it cannot push more then n squares into my stack.

#### Flaws  
During program execution flood fill alghoritm is triggered only once and in theory, together with rooms, passageway can create closed area and maze can stop making any progress. I.e. program doesn't guarantee that every room will be connected to passageway.

#### Sources
Bob Nystrom's article [Rooms and Mazes: A Procedural Dungeon Generator](http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/)   
hackerearth.com [Flood-fill Algorithm](https://www.hackerearth.com/practice/algorithms/graphs/flood-fill-algorithm/tutorial/)





