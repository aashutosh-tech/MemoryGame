package com.memorygame;

import java.io.*;

public class ScoreManager {
    private static final String HIGH_SCORE_FILE = "com/memorygame/highscore.txt";

    public static int getHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0; // No high score yet or error reading file
        }
    }

    public static void saveHighScore(int score) {
        try (PrintWriter out = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
            out.println(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
