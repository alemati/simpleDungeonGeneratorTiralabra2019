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
using randomized iterative flood fill algorithm. That's how it works: first, we take one square from MyStack. If it can be turned into passageway, program does so. At the same time program checks if this new passageway square is attached to unconnected room and, if that's the case, places door. After that we have 4 options to continue our passageway 
(continue to left/right/up/down). Program randomizes order of those options using Fisher–Yates shuffle algorithm and pushes them into MyStack and cycle starts from the beginning. Flood fill stops when MyStack becomes empty. At this point we have map with rooms and passageway. 

In the last phase program removes all dead ends from the map replacing them by walls. Deadend is a square that is surrounded by at least 3 walls. After turning deadend to wall program checks all neighbours and if one of them is passageway square, program turns it into wall too. That process continues until there is no deadends anymore. At the end map will be showen in the console.

#### Used alghoritms and data structures  
Program uses basic arrays and MyStask (selfmade Stack Data Structure). Also randomized iterative flood fill algorithm is used for creating a passageway and Fisher–Yates shuffle alghoritm is used for randomizing squares neighbours while making passageway.

#### Achived time and space complexity 
While testing I have noticed that the most time demanding phase is removing dead ends. Results below show the speed of program with and without removing dead ends

map side size | with removing (ms) | without removing (ms)
------ | ------| ------
50|29|13
100|73|25
200|448|43    
300|1948|68
400|5626|89
500|14418|139 

Clearly, phase of removing dead end needs heavy polishing. Right now it is obivious that time compelxyty is nowhere near O(n) that was suggested at the beggining of the project.

Space complexity on the other hand meets expectation of O(n) where n is total number of squares on map. Program creates only one map and doesn't make any duplicates of it. The most space heavy part of the program is flood fill alghoritm because it uses MyStack and in worst case scenario every square can be pushed into MyStack. But even then it cannot push more then n squares.

#### Flaws  
During program execution flood fill alghoritm is triggered only once and in treory, together with rooms, passageway can create closed area of maze and it can stop making any progress. I.e. program doesn't guarantee that every room will be connected to passageway. However, that never happened on practice.  

At this point phase of deliting dead ends is executed very poorly and it leads to bad performance.

#### Sources
Bob Nystrom's article [Rooms and Mazes: A Procedural Dungeon Generator](http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/)   
hackerearth.com [Flood-fill Algorithm](https://www.hackerearth.com/practice/algorithms/graphs/flood-fill-algorithm/tutorial/)





