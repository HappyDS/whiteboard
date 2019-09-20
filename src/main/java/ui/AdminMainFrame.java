package ui;

import shape.IShape;
import util.FileUtil;
import util.MsgJsonFactory;
import util.StringUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AdminMainFrame extends BaseMainFrame {

    private final FileFilter loadSaveFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            } else {
                return f.getName().endsWith(".wb");
            }
        }

        @Override
        public String getDescription() {
            return ".wb (whiteboard)";
        }
    };
    private final FileFilter exportFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            } else {
                return f.getName().endsWith(".png");
            }
        }

        @Override
        public String getDescription() {
            return ".png";
        }
    };
    @Override
    public void initMenuBar() {
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
        saveFileChooser.setFileFilter(this.loadSaveFilter);
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
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
        saveFileChooser.setFileFilter(exportFilter);
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
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
        loadFileChooser.addChoosableFileFilter(this.loadSaveFilter);
        loadFileChooser.setFileFilter(this.loadSaveFilter);
        loadFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        loadFileChooser.showOpenDialog(null);
        File file = loadFileChooser.getSelectedFile();
        if (file != null) {
            List<String> info = FileUtil.read(file.getAbsolutePath());
            List<IShape> shapes = new ArrayList<>();
            for (String s : info) {
                if (!StringUtil.isEmpty(s)) {
                    try {
                        IShape shape = (IShape) MsgJsonFactory.fromJson(s);
                        shapes.add(shape);
//                        paintBoard.addShapeWithRepaint(shape);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                                "Error loading file", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            paintBoard.clearShapes();
            paintBoard.addShapesWithRepaint(shapes);
        }

    }
}
