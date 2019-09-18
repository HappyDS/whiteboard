package ui;

import shape.ShapeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

@SuppressWarnings("ALL")
public class ServerFrame extends JFrame {

    private JPanel boardPanel = new JPanel();
    private JPanel chatPanel = new JPanel();
    private JPanel optionPanel = new JPanel();
    private JPanel shapeOptionPanel = new JPanel();
    private JPanel eraserOptionPanel = new JPanel();
    private JPanel textOpentionPanel = new JPanel();


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
        String[] optionListData = new String[]{"Line", "Rectangle", "Circle", "Oval", "Free", "Text", "Eraser"};
        JComboBox<String> optionMenu = new JComboBox<>(optionListData);

        boardPanel.add(paintBoard, BorderLayout.CENTER);
        boardPanel.add(optionMenu, BorderLayout.NORTH);
        boardPanel.add(optionPanel, BorderLayout.SOUTH);

        CardLayout cardLayout = new CardLayout();
        optionPanel.setLayout(cardLayout);
        shapeOptionPanel.setLayout(new FlowLayout());

        Integer[] eraserSizeData = new Integer[]{10, 20, 30, 40, 50, 60};
        JComboBox<Integer> eraserSizeBox = new JComboBox<>(eraserSizeData);
        eraserSizeBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                paintBoard.setEraseSize(eraserSizeData[eraserSizeBox.getSelectedIndex()]);
            }
        });
        eraserOptionPanel.add(new JLabel("Eraser size: "));
        eraserOptionPanel.add(eraserSizeBox);

        Integer[] shapeSizeData = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox<Integer> shapeSizeBox = new JComboBox<>(shapeSizeData);
        shapeSizeBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                paintBoard.setPenSize(shapeSizeData[shapeSizeBox.getSelectedIndex()]);
            }
        });
        shapeOptionPanel.add(new JLabel("Pen size: "));
        shapeOptionPanel.add(shapeSizeBox);
        JButton buttonColorPicker = new JButton("Select color");
        buttonColorPicker.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose a color", paintBoard.getCurrentColor());
            paintBoard.setCurrentColor(color);
        });
        shapeOptionPanel.add(buttonColorPicker);

        Integer[] textSizeData = new Integer[]{14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        JComboBox<Integer> textSizeBox = new JComboBox<>(textSizeData);
        textSizeBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                paintBoard.setTextSize(textSizeData[textSizeBox.getSelectedIndex()]);
            }
        });
        textOpentionPanel.add(new JLabel("Text size: "));
        textOpentionPanel.add(textSizeBox);
        JButton buttonTextColorPicker = new JButton("Select color");
        buttonTextColorPicker.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose a color", paintBoard.getCurrentColor());
            paintBoard.setCurrentColor(color);
        });
        textOpentionPanel.add(buttonTextColorPicker);

        optionPanel.add("shapeOption", shapeOptionPanel);
        optionPanel.add("eraserOption", eraserOptionPanel);
        optionPanel.add("textOption", textOpentionPanel);

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
                    case 6:
                        paintBoard.setCurrentShape(ShapeType.ERASER);
                        break;
                }
                if (optionMenu.getSelectedIndex() == 6) {
                    cardLayout.show(optionPanel, "eraserOption");
                } else if (optionMenu.getSelectedIndex() == 5) {
                    cardLayout.show(optionPanel, "textOption");
                } else {
                    cardLayout.show(optionPanel, "shapeOption");
                }
            }
        });

    }
}
