# Simple Tetris

A classic Tetris clone built with **Java** and **JavaFX**. Stack the falling
tetrominoes, clear full lines, and rack up your score.

## Features

- All seven classic tetrominoes (I, J, L, O, S, T, Z)
- Four-state rotation with wall/stack collision detection
- Line clearing with scoring
- "Game Over" detection when the stack reaches the top

## Tech stack

| Area | Technology |
| --- | --- |
| Language | Java 21 (configured via a Gradle Java toolchain) |
| UI | JavaFX 21 (`javafx.controls`) — rendered with `Pane`, `Rectangle`, `Line`, `Text` |
| Build | Gradle 8.10.2 (via the included wrapper) |
| JavaFX wiring | [OpenJFX Gradle plugin](https://github.com/openjfx/javafx-gradle-plugin) `0.1.0` |
| Modules | Java Platform Module System (`module-info.java`) |
| Tests | JUnit 5 (Jupiter) |

The game has **no third-party runtime dependencies** beyond JavaFX itself —
everything is plain Java + JavaFX scene-graph nodes.

## Requirements

- **JDK 21** or newer (the build is configured for a Java 21 toolchain)

JavaFX and Gradle are resolved automatically — JavaFX comes through the OpenJFX
Gradle plugin and Gradle is provided by the wrapper, so no separate installation
is needed.

## Running the game

Use the Gradle wrapper bundled with the project.

On macOS / Linux:

```bash
./gradlew run
```

On Windows:

```bat
gradlew.bat run
```

## Building

To produce the compiled artifacts:

```bash
./gradlew build
```

## Controls

| Key | Action |
| --- | --- |
| ← | Move left |
| → | Move right |
| ↓ | Soft drop (move down) |
| ↑ | Rotate |

## Scoring

- **+1** for each soft drop (↓)
- **+50** for every cleared line

## How it works

### The playfield and coordinate system

The board is a JavaFX `Pane` drawn on a `Scene`. Every block is a `25 × 25`
pixel `Rectangle`. With the window measuring `300 × 600` px, the playfield is a
**12 columns × 24 rows** grid. A vertical `Line` separates the board from the
side panel that shows the **Score** and **Lines** counters.

### The collision grid (`MESH`)

State is tracked in a single integer matrix:

```java
int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE]; // [12][24], indexed [x][y]
```

- `0` = the cell is empty
- `1` = the cell is occupied by a locked block

Pixel positions are converted to grid indices by dividing by `SIZE` (`25`).
Before any move or rotation the code peeks at the target `MESH` cells; the move
is only applied if those cells are within bounds **and** still `0`. This is what
prevents pieces from overlapping the stack or leaving the board.

### The game loop

A `java.util.Timer` schedules a `TimerTask` every **300 ms**. Because Timer runs
on its own thread, each tick wraps its work in `Platform.runLater(...)` so the
scene graph is only ever touched on the JavaFX Application Thread. On each tick
the active piece is moved down one cell, and the Score/Lines labels are
refreshed.

### Pieces (`Form`)

Each tetromino is a `Form` — a small model holding its four `Rectangle` blocks
(`a`, `b`, `c`, `d`), a `Color`, a single-letter `name` identifying the shape,
and a rotation state (`form`, cycling `1 → 2 → 3 → 4 → 1`). `Controller.makeRect()`
spawns the next piece by picking a shape at random and positioning its four
blocks near the top-center of the board. The game always keeps a "next piece"
queued so spawning is instant when the current piece locks.

### Movement and rotation

- **Horizontal movement** lives in `Controller.MoveRight` / `MoveLeft`: all four
  blocks must clear the wall and the next column in `MESH` must be free.
- **Falling / soft drop** lives in `Tetris.MoveDown(Form)`: it checks whether any
  block has hit the floor or a block below it. If so, the piece is **locked**
  (its cells in `MESH` are set to `1`), full rows are cleared, and the next piece
  is spawned.
- **Rotation** lives in `Tetris.MoveTurn`: each shape has hand-tuned offsets for
  each of its four rotation states, validated by the `cB(...)` (can-be) helper
  that performs the same bounds + `MESH` checks before committing the turn. The
  `O` piece is intentionally a no-op since a square looks identical when rotated.

### Clearing lines

After a piece locks, `RemoveRows` scans every row. A row whose 12 cells are all
`1` is full: its rectangles are removed from the `Pane`, every block above it is
shifted down one cell, and `MESH` is rebuilt to match. Each cleared line adds
**50** points and increments the **Lines** counter.

### Game over

A `top` counter increments whenever a block sits in the very top row across
consecutive ticks. Once the stack reaches the top, a red **"GAME OVER"** banner
is drawn and the loop stops updating the board.

## Project structure

```
src/main/java/
├── module-info.java          # JPMS descriptor (requires javafx.controls)
└── com/encom/simpletetris/
    ├── Tetris.java           # Application entry point, game loop, falling,
    │                         #   rotation, line clearing, game-over
    ├── Controller.java       # Piece spawning + horizontal movement
    └── Form.java             # Tetromino model (four blocks, color, rotation)
```

## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

## License

This project is released under the [MIT License](LICENSE).
