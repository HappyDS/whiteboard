package ui;

import shape.ShapeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ServerFrame extends JFrame {

    private JPanel boardPanel = new JPanel();
    private JPanel chatPanel = new JPanel();
    private JPanel optionPanel = new JPanel();

    public ServerFrame() {
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        chatPanel.setSize(300, 800);
        add(boardPanel, BorderLayout.CENTER);
        add(chatPanel, BorderLayout.EAST);

        /* Initialize menu bar */
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("About");
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        setJMenuBar(menuBar);

        boardPanel.setLayout(new BorderLayout());
        PaintBoard paintBoard = new PaintBoard();
        String[] optionListData = new String[]{"Line", "Rectangle", "Circle", "Oval", "Free", "Text"};
        JComboBox<String> optionMenu = new JComboBox<>(optionListData);
        optionMenu.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                switch (optionMenu.getSelectedIndex()) {
                    case 0:
                        paintBoard.setCurrentShape(ShapeType.LINE);
                        break;
                    case 1:
                        paintBoard.setCurrentShape(ShapeType.RECTANGLE);
                        break;
                    case 2:
                        paintBoard.setCurrentShape(ShapeType.CIRCLE);
                        break;
                    case 3:
                        paintBoard.setCurrentShape(ShapeType.OVAL);
                        break;
                    case 4:
                        paintBoard.setCurrentShape(ShapeType.FREE);
                        break;
                    case 5:
                        paintBoard.setCurrentShape(ShapeType.TEXT);
                        break;
                }
            }
        });
        boardPanel.add(paintBoard, BorderLayout.CENTER);
        boardPanel.add(optionMenu, BorderLayout.NORTH);
    }
}
