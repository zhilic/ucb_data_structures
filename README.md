# CS61B Data Structures
This repository includes my codes when learning the open course *Data Structures (CS 61B)* instructed by Prof. JOsh Hug at UC Berkeley in Spring 2018.

Website for this course: https://sp18.datastructur.es/


## Java Syntax (Lectures 1 - 16)
Big project: [Project 2](./proj2)

A large design project to create an engine for generating explorable worlds, and players can play the simple "game".
> The goal of this project is to teach you how to handle a larger piece of code with little starter code in the hopes of emulating something like a product development cycle.


## Data Structures (Lectures 17 - 40)

#### Asymptotics (Lectures 17 - 19)
- Intuitive Simplification to Yield the **Order of Growth**
	1. Consider only the worst case
	2. Restrict attention to one operation
	3. Eliminate low order terms
	4. Eliminate multiplicative constants
- Analysis Methods:
	- Big Theta (satisfy both upper bound and lower bound)
	- Big O (satisfy upper bound)
	- Big Omega (satisfy lower bound)
	- Amortized Analysis

#### [Disjoint Sets](./Data_Structure_Implementation/Disjoint_Sets) (Lecture 20)

|Implementation      |Runtime<br>(M operations on N nodes)|
|:---------------------------------------:|:-------------:|
|QuickFindDS							  |Θ(NM)    	  |
|QuickUnionDS                             |O(MN)		  |
|WeightedQuickUnionDS                     |O(N + M logN)  |
|WeightedQuickUnionDSWithPathCompression  |O(N + M α(N))  |


- Weighted Quick Union: Always link the root of **_smaller_** tree to **_larger_** tree.
- Path Compression: When we try to find the root of a specific node, tie all nodes seen to the root.

#### Trees, BSTs (Lecture 21)

(Definition) A **tree** consists of:
- A set of nodes.
- A set of edges that connect those nodes.
	- Constraint: There is exactly one path between any two nodes.

A **BST (Binary Search Tree)** is a rooted binary tree (every node has either 0, 1, or 2 children) with the BST property.

**BST property**:

For every node X in the tree:
- Every key in the **left** subtree is **less** than X's key.
- Every key in the **right** subtree is **greater** than X's key.

#### Balanced BSTs (Lecture 22) Θ(log N)

**Rotations** (BSTs use rotations to keep balanced)
- RotateLeft(Node r)
	- The root of r.right becomes the new root.
	- The old r.right.left becomes the new r.right.
- RotateRight(Node r)
	- The root of r.left becomes the new root.
	- The old r.left.right becomes the new r.left.

**B-Trees** (always balanced, also called 2-3 trees or 2-3-4 trees)
- Order M: The maximum number of non-null children per node <--> Max M-1 items per node.
- Most popular in two specific contexts:
	- B-Tree with small M: used as a conceptually (not practically useful) simple BST. 
	- (More often) B-Tree with large M ('000): Used in pratice for databases and filesystems.

**Red Black Tree**
- Red-Black Invariants:
	- Each node is red or black
	- Root is black
	- No 2 red nodes in a row (Red nodes can only have black children)
	- Every root-NULL path has the same number of black nodes
- Left-Leaning Red Black Tree<br>
	For any 2-3 tree (which is balanced), there exists a corresponding red-black tree that has depth no more than 2 times the depth of the 2-3 tree.