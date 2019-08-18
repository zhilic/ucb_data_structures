# ubc_data_structures
This repository includes my codes when learning the open course *Data Structures (CS 61B)* instructed by Prof. JOsh Hug at UC Berkeley in Spring 2018.

Website for this course: https://sp18.datastructur.es/


## Java Syntax (Lectures 1 - 16)
Big project: [Project 2](./proj2)

A large design project to create an engine for generating explorable worlds, and players can play the simple "game".
> The goal of this project is to teach you how to handle a larger piece of code with little starter code in the hopes of emulating something like a product development cycle.


## Data Structures (Lectures 17 - 40)

### Asymptotics (Lectures 17 - 19)
### Disjoint Sets (Lecture 20)

|Implementation                           |Runtime             |
|:---------------------------------------:|:------------------:|
|QuickFindDS							  | \Theta (MN)        |
|QuickUnionDS                             |O(MN)			   |
|WeightedQuickUnionDS                     |O(N + M logN)       |
|WeightedQuickUnionDSWithPathCompression  |O(N + M  \alpha (N))|