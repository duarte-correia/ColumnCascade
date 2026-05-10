# Column Game Engine

Java implementation of a console-based column puzzle game engine. The project models a grid where randomly generated pieces are dropped into columns, matching symbol sequences are eliminated, the grid is reorganized, and the score is updated according to the selected game mode.

## What It Does

- Generates vertical pieces with configurable symbols.
- Places pieces into a rectangular play area by column.
- Detects and removes contiguous symbol sequences.
- Applies accommodation strategies to reorganize the grid after removals.
- Supports different scoring policies:
  - `SingleScoreGame`: one elimination reward based on the total number of symbols removed in a play.
  - `MultipleRewardGame`: separate rewards for each elimination step, with later chain reactions weighted more heavily.

## Architecture

The code is organized around small interfaces and interchangeable strategies:

- `Game` defines the public game operations.
- `AbstractGame` centralizes shared game state, piece generation, placement, end-game detection, and score bookkeeping.
- `Eliminator` defines how symbol sequences are removed.
- `SimpleEliminator` removes all contiguous groups with at least the configured block size.
- `Accomodator` defines how the grid reacts after eliminations.
- `SimpleAccomodator` simulates gravity by moving non-empty symbols down while preserving their relative order.
- `SingleScoreGame` and `MultipleRewardGame` implement alternative scoring rules.

The `doc/` folder contains generated Javadoc for the wider project API, including supporting classes such as `PlayArea`, `Piece`, `Symbol`, and the console entry point.

## Tech Stack

- Java
- Object-oriented design
- Strategy pattern
- Generated Javadoc documentation

## How To Read The Project

Start with `Game.java` to understand the public contract, then move to `AbstractGame.java` for the common game flow. The strategy interfaces, `Eliminator` and `Accomodator`, show how the behavior can be extended without changing the game modes.

## Running

This repository contains the core implementation files and generated documentation. To run the full console game, compile these classes together with the supporting classes documented in `doc/` (`PCOMain`, `PlayArea`, `Piece`, `Symbol`, and symbol enums).

```bash
javac *.java
java PCOMain
```

## Documentation

Open `doc/index.html` in a browser to browse the generated API documentation.
