package ui;

import shape.IShape;
import util.FileUtil;
import util.MsgJsonFactory;
import util.StringUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AdminMainFrame extends BaseMainFrame {

    private String currentFileName = null;

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

    public AdminMainFrame(String username) {
        super(username);
        userListBoard.initKickOutOption();
    }

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
        JMenuItem saveAsMenuItem = new JMenuItem("Save as");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        saveMenuItem.addActionListener(e -> saveData());
        saveAsMenuItem.addActionListener(e -> saveDataToNewFile());
        openMenuItem.addActionListener(e -> loadData());
        exportMenuItem.addActionListener(e -> savePNG());
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exportMenuItem);

        JMenuItem clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(e -> clear());
        JMenuItem redoMenuItem = new JMenuItem("Undo");
        redoMenuItem.addActionListener(e -> undo());
        newMenuItem.addActionListener(e -> clear());
        editMenu.add(clearMenuItem);
        editMenu.add(redoMenuItem);
        setJMenuBar(menuBar);

    }

    @Override
    protected void onWindowClosing() {
        try {
            server.closeServer();
            looper.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerDisconnected() {
        setVisible(false);
    }

    private void saveDataToNewFile() {
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
            currentFileName = path;
        } catch (Exception e) {

        }
    }

    private void saveData() {
        if (!StringUtil.isEmpty(currentFileName)) {
            try {
                FileUtil.write(paintBoard.exportData(), currentFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveDataToNewFile();
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
            currentFileName = file.getAbsolutePath();
            List<String> info = FileUtil.read(file.getAbsolutePath());
            List<IShape> shapes = new ArrayList<>();
            for (String s : info) {
                if (!StringUtil.isEmpty(s)) {
                    try {
                        IShape shape = (IShape) MsgJsonFactory.fromJson(s);
                        shapes.add(shape);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                                "Error loading file", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            looper.post(() -> {
                try {
                    server.reloadFromFile(shapes);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
