# ![alt-tag](./kenka.gif) 
# Kenka

### User Stories

From the camera to the key-bindings to the sprites & animations, this game applet was built from the ground up. By applying concepts from multivariable calculus and linear algebra, features like AI map navigation, projectile trajectory tracking, and collison detection become possible. As such, we have the essentials to build this Beat 'em up style game. 

### Minimum Viable Product

* A game with at least 3 levels  

### Approach Taken

* Set up a lookup table to perform optimized calculations on an AI entity's normal vector (basis for course plotting)
* Sorted entities via nested class encapsulation tree
* Constructed tilemaps with each 16x16 image representing a particular unicode
* Linked unicode values to collision constraints to create world boundaries


### Technologies used

* **Applet** To provide a foundation 
* **Java** To construct component trees and behaviors
* **AWT** To implement mouse/key events and graphics


### Installation Instructions
* Run `game.java` file as a java application
