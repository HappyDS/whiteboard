package demo;

import ui.PaintBoard;

import javax.swing.*;

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
public class TestDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PaintBoard paintBoard = new PaintBoard();
        paintBoard.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.add(paintBoard);
        frame.setVisible(true);
    }
}
