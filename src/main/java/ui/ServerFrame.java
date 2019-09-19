package ui;

import javafx.stage.FileChooser;
import shape.IShape;
import shape.ShapeType;
import util.FileUtil;
import util.MsgJsonFactory;
import util.StringUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;

@SuppressWarnings("ALL")
public class ServerFrame extends JFrame {

    private JPanel boardPanel = new JPanel();
    private JPanel optionPanel = new JPanel();
    private JPanel shapeOptionPanel = new JPanel();
    private JPanel eraserOptionPanel = new JPanel();
    private JPanel textOpentionPanel = new JPanel();
    private ChatBoard chatBoard = new ChatBoard();
    private PaintBoard paintBoard = new PaintBoard();

    public ServerFrame() {
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        initMenuBar();
        initComponents();

    }

    private void initComponents() {
        chatBoard.setPreferredSize(new Dimension(300, 800));
        add(boardPanel, BorderLayout.CENTER);
        add(chatBoard, BorderLayout.EAST);
        boardPanel.setLayout(new BorderLayout());
//        PaintBoard paintBoard = new PaintBoard();
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
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> paintBoard.clearShapes());
        eraserOptionPanel.add(new JLabel("Eraser size: "));
        eraserOptionPanel.add(eraserSizeBox);
        eraserOptionPanel.add(clearButton);

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
        textOpentionPanel.add(new JLabel("Double click the canvas to add text..."));

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

    private void initMenuBar() {
        /* Initialize menu bar */
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu aboutMenu = new JMenu("About");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        saveMenuItem.addActionListener(e -> saveData());
        openMenuItem.addActionListener(e -> loadData());
        exportMenuItem.addActionListener(e -> savePNG());
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exportMenuItem);

        JMenuItem clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(e -> paintBoard.clearShapes());
        JMenuItem redoMenuItem = new JMenuItem("Undo");
        redoMenuItem.addActionListener(e -> paintBoard.undo());
        editMenu.add(clearMenuItem);
        editMenu.add(redoMenuItem);
        setJMenuBar(menuBar);
    }

    private void saveData() {
        JFileChooser saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveFileChooser.setDialogTitle("Select path to save");
        saveFileChooser.showDialog(this, "OK");
        try {
            String path = saveFileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".wb")) {
                path = path + ".wb";
            }
            FileUtil.write(paintBoard.exportData(), path);
        } catch (Exception e) {

        }
    }

    private void savePNG() {
        JFileChooser saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveFileChooser.setDialogTitle("Select path to save");
        saveFileChooser.showDialog(this, "OK");
        try {
            String path = saveFileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".png")) {
                path = path + ".png";
            }
            ImageIO.write(paintBoard.exportImage(), "png", new File(path));
        } catch (Exception e) {

        }
    }

    private void loadData() {
        JFileChooser loadFileChooser = new JFileChooser();
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".wb");
            }

            @Override
            public String getDescription() {
                return ".wb (whiteboard)";
            }
        };
        loadFileChooser.addChoosableFileFilter(filter);
        loadFileChooser.setFileFilter(filter);
        loadFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        loadFileChooser.showOpenDialog(null);
        File file = loadFileChooser.getSelectedFile();
        if (file != null) {
            List<String> info = FileUtil.read(file.getAbsolutePath());
            for (String s: info) {
                if (!StringUtil.isEmpty(s)) {
                    try {
                        IShape shape = (IShape) MsgJsonFactory.fromJson(s);
                        paintBoard.addShapeWithRepaint(shape);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                                "Error loading file", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
