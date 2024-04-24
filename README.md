# Java Pixel Physics

## [Download Latest Release](https://github.com/aidenx11/Java-Pixel-Physics/releases/tag/v0.1.0-beta.1) 

## Overview
  Inspired by the various falling sand simulators from when I was a kid, such as [The Sandbox](https://apps.apple.com/us/app/the-sandbox-building-craft/id520777858), and more modern games such as [Noita](https://en.wikipedia.org/wiki/Noita_(video_game)), I aimed to create a simulation of my own in Java using the [LibGDX](https://libgdx.com/) framework.

https://github.com/aidenx11/Java-Pixel-Physics/assets/42153616/08be3ccc-5660-4040-a638-f70316f4df4b

  As of now, the simulation has basic gasses, water and lava, and a small variety of solids that interact with gravity. There is logic for objects burning, getting wet, melting, and settling. There is also logic for gravity, acceleration and basic concepts of momentum/intertia. 

  Most of the logic is performed by manipulating a 2D array of "element" objects, changing their positions in the array and their individual fields as needed for their behavior. Each frame, every element checks other elements surrounding itself and decides what it needs to do. For more information on this design pattern, see the concept of [Cellular Automata](https://en.wikipedia.org/wiki/Cellular_automaton).
