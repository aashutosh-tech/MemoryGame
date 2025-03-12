package com.memorygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private JButton[] buttons = new JButton[8];
    private int[] numbers = new int[8]; // To store numbers for buttons
    private int openCount = 0; // Count of currently revealed cards
    private JButton firstOpened = null;
    private JButton secondOpened = null;
    private JLabel scoreLabel;
    private int score = 0;

    public GamePanel() {
        this.setLayout(new GridLayout(3, 3)); // 3x3 grid (one will be empty)
        initGame();
    }

    private void initGame() {
        scoreLabel = new JLabel("Score: 0");
        this.add(scoreLabel);
        generateNumbers();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 24));
            buttons[i].addActionListener(this);
            this.add(buttons[i]);
        }
    }

    private void generateNumbers() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int number = (int)(Math.random() * 100) + 1; // Random numbers between 1-100
            list.add(number);
            list.add(number);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            numbers[i] = list.get(i);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (openCount < 2) {
            JButton clicked = (JButton) e.getSource();
            if (clicked.getText().equals("")) {
                displayNumber(clicked);
            }
        }
    }

    private void displayNumber(JButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (button == buttons[i]) {
                button.setText(String.valueOf(numbers[i]));
                if (openCount == 0) {
                    firstOpened = button;
                    openCount++;
                } else if (openCount == 1) {
                    secondOpened = button;
                    openCount++;
                    checkMatch();
                }
                break;
            }
        }
    }

    private void checkMatch() {
        if (firstOpened.getText().equals(secondOpened.getText())) {
            score += 10; // Correct pair
            firstOpened.setEnabled(false);
            secondOpened.setEnabled(false);
        } else {
            score -= 5; // Wrong pair
            Timer timer = new Timer(1000, evt -> {
                firstOpened.setText("");
                secondOpened.setText("");
                firstOpened = null;
                secondOpened = null;
                openCount = 0;
            });
            timer.setRepeats(false);
            timer.start();
        }
        scoreLabel.setText("Score: " + score);
        openCount = 0; // Reset open count after checking
    }
}
