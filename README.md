# Simple Tetris

A classic Tetris clone built with **Java** and **JavaFX**. Stack the falling
tetrominoes, clear full lines, and rack up your score.

## Features

- All seven classic tetrominoes (I, J, L, O, S, T, Z)
- Rotation and collision detection
- Line clearing with scoring
- "Game Over" detection when the stack reaches the top

## Requirements

- **JDK 21** or newer (the build is configured for a Java 21 toolchain)

JavaFX and Gradle are resolved automatically — JavaFX comes through the
[OpenJFX Gradle plugin](https://github.com/openjfx/javafx-gradle-plugin) and
Gradle is provided by the wrapper, so no separate installation is needed.

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

## Project structure

```
src/main/java/
├── module-info.java
└── com/encom/simpletetris/
    ├── Tetris.java       # Application entry point and game loop
    ├── Controller.java   # Piece spawning and horizontal movement
    └── Form.java         # Tetromino model (four blocks + color)
```

## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

## License

This project is released under the [MIT License](LICENSE).
