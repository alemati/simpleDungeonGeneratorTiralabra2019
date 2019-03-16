## The projects goal is to create a program that generates a random 2D dungeon that can possible be used later on in roguelike-games.  

### Inspiration and sources  
Inspiration for this project was gathered from Bob Nystrom's article [Rooms and Mazes: A Procedural Dungeon Generator](http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/)  
and practically this project is an attempt to implement Bob Nystrom's approach on practise using Java and selfmade data structures and flood fill algorithm.   

### User input, program flow and result  
As an input program will take a size of desirable map (height and width), range of allowed room sizes and number of attemps 
to place room on the map. Last one gives user a control on how dense map is going to be. At the beggining program will try to place 
wanted amount of rooms on the map. If new room is overlapping with already existing one, new is discarded.
Then remaining space will be filled with maze using flood fill algorithm. 
After that all rooms will be connected to maze making every room reachable through the maze (passageway). In the end all deadends will be revomed.
As a result program should give user a map of dungeon that matches input data and print it in console.  

### Data structures
Program will be using only selfmade data structures. Right now, at the beginning, it seems like the program will be using at least
some sort of selfmade HashMap and List.  

### Time and space complexity  
_h_ = user input map height  
_w_ = user input map width  
_n_ = total number of squares = h * w  
_a_ = amount of atteps to place new room  

Creating a new, empty map will take O(_n_) in both time and space.  
Attempting to place _a_ rooms will take O(_a_) in time and O(1) in space, because it will just change state of already existing squares 
and will not create new ones.  
In worst case scenario, filling all remaining empty space with waze using flood fill algorithm will take O(_w_ _h_) in both time and space.  
In worst case scenario, removing all deadends will take O(_n_) in time and O(1) in space.

All in all, if none big miscalculations was made, then overall time complexity should be O(_n_) + O(_a_) + O(_w_ _h_) + O(_n_) = O(_n_) and
overall space complexity should be O(_n_) + O(1) + O(_w_ _h_) + O(1) = O(_n_)



