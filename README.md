Advances Data Structures (COP 5536) 
Fall-2016
Programming Project Report



Project Description

In this Project, I have implemented a Max Fibonacci heap data structure which helps in determining the n most popular hashtags given in an input file. The input file here is provided by the user and it can have million hashtags. Each hashtag appears with a key which we must store in our data structure.   If a hashtag appears again then we must increase its key.  Besides that, there are multiple queries in input file which represents a number n and whenever such query appears we must print top n hashtags with maximum value of key. After doing this we must again insert the nodes which we have removed. Our program should stop when “STOP” is encountered in the input file. I have also implemented a Hash table which is storing hashtags as keys and values as addresses to nodes in Fibonacci heap. 
 
 In this project the main operations we should perform are removing maximum frequency hashtag and increasing the frequency of hashtag. For this reason we have chosen Fibonacci heap in our implementation since the amortized cost for these operations for Fibonacci heap are   O(log n) and O(1).
 
During consolidate operation there is a degree array through which I should traverse to perform merging of tree. I have chosen its size to be 45 because if we have n nodes then 
Degree(N) ≤ logφ N, where φ = (1 + √5) / 2. Here Degree(N) represents the maximum degree of node when Fibonacci heap has N nodes. If N is equal to Integer.MAX_VALUE then it evaluates to 45.

Working Environment:
Language: java version "1.8.0_101”; Java Hotspot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
Runtime Environment: Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
IDE: IntelliJ IDEA edition 2016.2.2

Compilation procedure:
 I have successfully tested my project on thunder.cise.ufl.edu server.
The folder vijayvargia_harshit.zip contains java files and makefile. To run the program, follow these steps:
1)	Extract the files from vijayvargia_harshit folder.
2)	use command ‘make’ to compile the java files.
3)	After compiling run the main class file by using: java hashtagcounter file_name

Here file_name is the name of input file. The execution will generate an output file named as “output_file.txt”. 
Structure of the program and function descriptions: 

Program Structure: There are three classes in my program:
1)	Fnode:
This class describes the structure of nodes. Each hashtag in input is represented as 
“#hashtag key” - I have stored hashtag and key as two class variables of Fnode. There are other class variables also which are used during construction and modification of Fibonacci heap structure.
Class variables of Fnode:
a. degree: The data type of this variable is Integer. For a node x, it defines the number of children of x or the number of nodes which originate from x.
b. childcut: This is a Boolean type variable which tells whether the node has lost its child since it became a child during merging. If it is false, then node has not lost its child. If it’s true, then node has lost it’s one child. In Fibonacci heap a node can have at most loose two Childs.
c. left: This is a Fnode type object which points to the left sibling of a node in doubly circular linked list.
d. right: This is a Fnode type object which points to the right sibling of a node in doubly circular linked list.
e. child: This is a Fnode type object which points to the leftmost child of a node.
f. parent: This is a Fnode type object which points to the parent of a node.
g. hashtag: This is a String type variable which stores the hashtag value. In input file on any line there can be three things: #hashtag key, query(Integer-n), “STOP”.
h. frequency: It is an Integer type variable. It stores the key values encountered along with hashtags. When hashtag repeats, it keeps on adding key values of hashtags in nodes.

Methods in Fnode (getter and setters):
These are the getter and setter methods used for setting instance variables as well as retrieving them.
public int getDegree()
public void setDegree(int degree) 
public boolean isChildCut()
public void setChildCut(boolean childCut) 
public Fnode getLeft()
public void setLeft(Fnode left) 
public Fnode getRight()
public void setRight(Fnode right) 
public Fnode getChild()
public void setChild(Fnode child) 
public Fnode getParent()
public void setParent(Fnode parent) 
public String getHashtag()
public void setHashtag(String hashtag) 
public int getFrequency()
public void setFrequency(int frequency
2)	FibonacciHeap: This is the class where all operations which we will be doing on Fibonacci heap data structure will be implemented. 

Instance Variables in FibonacciHeap:

a.	private Fnode nodeMax: nodeMax points to the node with maximum frequency in the Fibonacci heap    
         
b. private int nodeNumber: nodeNumber tells how many nodes are there in Fibonacci heap at any instant.

Methods in FibonacciHeap:
a.	public void insert(Fnode node)
Access modifier: public – to make it accessible to hashtagcounter class
Parameter: node to be inserted
Returns: null.
Running time (Amortized cost) : O(1)
Description: Inserts node into the doubly circular linked list at root level and make nodeMax points to current node inserted if current node frequency is greater than nodeMax frequency. If heap is empty then it makes nodeMax points to inserted node.


b.	public void increaseKey(Fnode node, int f)

Access modifier: public – to make it accessible to hashtagcounter class
Parameters: 1) node whose frequency is to be increased 2) new frequency
Returns: null.
Running time(Amortized cost): O(1)
Description: Increases the frequency of node by f, performs cut operation
if parent frequency less then child node. It keeps doing it recursively unless parent childCut is false. Once it encounters a parent node whose childCut value is false, it sets it to true.

c.	public Fnode removeMax()

Access modifier: public – to make it accessible to hashtagcounter class
Parameters: No parameters.
Returns: returns maximum frequency node which nodeMax is pointing to.
Running time(Amortized cost): O(log n).
Description: Removes maximum frequency node from heap. After removing the node,
it does merging based on degree of nodes and again aligns the nodeMax to maximum frequency node.

d.	protected void cascadingCut(Fnode node)

Access modifier: protected – to make it accessible to inner classes only.
Parameters: node which is to be separated from tree based on its childCut value
Returns: null
Description: The reason for this operation is to keep Degree(N) low. If we allow any number of cuts in Fibonacci heap then removeMax operation will have linear running time. This method Recursively cuts node from its parent till the childCut of parent encountered is false.

e.	protected void cut(Fnode node, Fnode nodeParent)
Access modifier: protected – to make it accessible to inner classes only.
Parameters: 1)node which is to be separated from tree based on it's childCut value, 2)parent of node from which it is to be separated.
Returns: null.
Running time(Amortized cost): O(1)
Description: Separates a node from its parent nodeParent if increased frequency of node is greater than nodeParent frequency. It adds the separated node to root list. 

f.	protected void consolidate ()

Access modifier: protected – to make it accessible to inner classes only.
Parameters: null
Returns: null.
Running time(Amortized cost): O(1).
Description: Only called when removemax operation is performed. It merges trees in fibonacci heap based on their degrees. Equal degrees are merged together in such a way that they satisfy heap property. Merging happens from left to right.

g. protected void pair (Fnode nodeChild, Fnode nodeParent)

Access modifier: protected – to make it accessible to inner classes only.
Parameters: 1) node which is to be made child, 2)node which is to be made parent of nodeChild
Returns: null.
Running time(Amortized cost):O(1)
Description: makes nodeChild child of nodeParent




3)	hashtagcounter:

This is the class with main method in my program. Here I am reading input from the input file which is passed as an argument from user. I used regular expressions in java to perform this operation. Besides that, I am also generating output file “output_file.txt” through this class.
This class creates an instance of FibonacciHeap and performs operations defined for fibonacciHeap data structure. Simultaneously during insertion and removal of nodes it is modifying hastable structure too.
           Methods in hashtagcounter:
a.	public static void main(String args[]) throws Exception
Parameters: Name of the input file given by user while execution




References : Introduction to Algorithms  by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein.
