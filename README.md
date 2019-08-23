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

#### Disjoint Sets (Lecture 20)

|Implementation                           |Runtime<br>(M operations on N nodes)|
|:---------------------------------------:|:-------------:|
|QuickFindDS							  |Θ(NM)    	  |
|QuickUnionDS                             |O(MN)		  |
|WeightedQuickUnionDS                     |O(N + M logN)  |
|WeightedQuickUnionDSWithPathCompression  |O(N + M α(N))  |


- Weighted Quick Union: Always link the root of **_smaller_** tree to **_larger_** tree.
- Path Compression:

#### Trees, BSTs (Lecture 21)

