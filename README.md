# AI Stack Game 🧱

A Java-based desktop application that recreates the classic "Stacke" arcade game, featuring a custom-built **Artificial Intelligence (AI)** agent capable of playing the game autonomously.

## 🎮 How It Works

The goal is simple: stack the moving blocks on top of each other. If you miss, the stack gets thinner. If you reach the top, you win.

**The Twist:** I implemented an AI algorithm that calculates the precise timing and position of the moving blocks to achieve a perfect score, demonstrating algorithmic problem-solving.

## 🧠 AI Implementation

Unlike standard games, this project focuses on the backend logic:
- **State Analysis:** The AI constantly monitors the coordinate state of the moving block relative to the stack.
- **Timing Algorithm:** Calculates the exact frame to trigger the "drop" action for perfect alignment.
- **Automated Gameplay:** You can toggle between "Player Mode" and "AI Mode" to watch the algorithm solve the game in real-time.

## 🛠️ Tech Stack

- **Language:** Java (JDK 12+)
- **Concepts:** Object-Oriented Programming (OOP), Game Loops, 2D Coordinate Systems.

## 🚀 How to Play

1. **Clone the Repo**
   ```bash
   git clone [https://github.com/hasan-devtech/stack-game-java.git](https://github.com/hasan-devtech/stack-game-java.git)
