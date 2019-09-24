package ui;

import rmi.IServer;
import shape.Point;
import shape.Rectangle;
import shape.*;
import util.MsgJsonFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("ALL")
public class PaintBoard extends Canvas implements MouseListener, MouseMotionListener {

    private Stack<IShape> shapeStack;   /* A stack to store all shapes. */

    private Point startPoint;
    private Point currentPoint;

    private boolean mousePressed = false;
    private Color currentColor;
    private ShapeType currentShape;

    //TODO: Might not be thread safe
    private List<Point> freeDraw = new ArrayList<>();   /* A series of points on a free draw path */
    private List<Point> eraserPath = new ArrayList<>();

    private Image offScreenImage;
    private int eraseSize = 10;
    private int penSize = 1;
    private int textSize = 14;

    private IServer server;
    private String username = "default";

    public PaintBoard(String username) {
        this.username = username;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        currentColor = Color.BLACK;
        shapeStack = new Stack<IShape>();
        currentShape = ShapeType.LINE;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Do actually painting
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /* Initialize canvas */
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        for (IShape shape : shapeStack) {
            shape.draw(graphics2D);
        }

        /* If mouse haven't been released */
        if (mousePressed) {
            g.setColor(currentColor);
            IShape shape = getShape();
            if (shape != null) {
                shape.draw(graphics2D);
            }
        }
    }

    /**
     * Get a shape based on start point and current point
     *
     * @return A shape object
     */
    private IShape getShape() {
        IShape shape = null;
        switch (currentShape) {
            case LINE:
                Line line = new Line(startPoint, currentPoint, currentColor, penSize);
                shape = line;
                break;
            case RECTANGLE:
                int width = Math.abs(startPoint.getX() - currentPoint.getX());
                int height = Math.abs(startPoint.getY() - currentPoint.getY());
                Point leftTop;
                if (startPoint.getX() < currentPoint.getX()) {
                    if (startPoint.getY() < currentPoint.getY()) {
                        leftTop = startPoint;
                    } else {
                        leftTop = new Point(startPoint.getX(), currentPoint.getY());
                    }
                } else {
                    if (startPoint.getY() < currentPoint.getY()) {
                        leftTop = new Point(currentPoint.getX(), startPoint.getY());
                    } else {
                        leftTop = currentPoint;
                    }
                }

                Rectangle rectangle = new Rectangle(leftTop, width, height, currentColor, penSize);
                shape = rectangle;
                break;
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(startPoint.getX() - currentPoint.getX(), 2)
                        + Math.pow(startPoint.getY() - currentPoint.getY(), 2));
                int radiusX = startPoint.getX() - radius / 2;
                int radiusY = startPoint.getY() - radius / 2;
                Circle circle = new Circle(new Point(radiusX, radiusY), radius, currentColor, penSize);
                shape = circle;
                break;
            case OVAL:
                int oWidth = Math.abs(startPoint.getX() - currentPoint.getX());
                int oHeight = Math.abs(startPoint.getY() - currentPoint.getY());
                Point oLeftTop;
                if (startPoint.getX() < currentPoint.getX()) {
                    if (startPoint.getY() < currentPoint.getY()) {
                        oLeftTop = startPoint;
                    } else {
                        oLeftTop = new Point(startPoint.getX(), currentPoint.getY());
                    }
                } else {
                    if (startPoint.getY() < currentPoint.getY()) {
                        oLeftTop = new Point(currentPoint.getX(), startPoint.getY());
                    } else {
                        oLeftTop = currentPoint;
                    }
                }
                Oval oval = new Oval(oLeftTop, oWidth, oHeight, currentColor, penSize);
                shape = oval;
                break;
            case FREE:
                freeDraw.add(currentPoint);
                FreeDraw draw = new FreeDraw(freeDraw, currentColor, penSize);
                shape = draw;
                break;
            case ERASER:
                eraserPath.add(currentPoint);
                Eraser eraser = new Eraser(eraserPath, eraseSize);
                shape = eraser;
                break;
        }
        return shape;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && currentShape == ShapeType.TEXT) {
            String input = JOptionPane.showInputDialog(null, "Input text");
            Text text = new Text(new Point(e.getX(), e.getY()), input, textSize, currentColor);
            addShape(text);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = new Point(e.getX(), e.getY());
        mousePressed = true;
        if (currentShape == ShapeType.FREE) {
            freeDraw.add(startPoint);
        }
        if (currentShape == ShapeType.ERASER) {
            eraserPath.add(startPoint);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        currentPoint = new Point(e.getX(), e.getY());

        IShape shape = getShape();
        if (shape == null) {
            return;
        }
        addShape(shape);
        freeDraw = new ArrayList<>();
        eraserPath = new ArrayList<>();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentPoint = new Point(e.getX(), e.getY());
        repaint();
    }

    /**
     * A communication thread can call this method to add shapes to the board
     *
     * @param shape
     */
    public void addShape(IShape shape) {
        shapeStack.push(shape);
        /* Print all shape as JSON */
        System.out.println(MsgJsonFactory.toJson(shape));
        try {
            server.sendShape(shape, username);
        } catch (RemoteException e) {
            //TODO: handle the exception. the user is disconnected.
            e.printStackTrace();
        }
    }

    public synchronized void addShapeWithRepaint(IShape shape) {
        shapeStack.push(shape);
        repaint();
    }

    public synchronized void addShapesWithRepaint(List<IShape> shapes) {
        for (IShape shape : shapes) {
            shapeStack.push(shape);
        }
        repaint();
    }

    /**
     * Set shape
     *
     * @param shape
     */
    public void setCurrentShape(ShapeType shape) {
        this.currentShape = shape;
    }

    /**
     * Set eraser size
     *
     * @param eraseSize
     */
    public void setEraseSize(int eraseSize) {
        this.eraseSize = eraseSize;
    }

    /**
     * Set pen size
     *
     * @param penSize
     */
    public void setPenSize(int penSize) {
        this.penSize = penSize;
    }

    /**
     * Set text size
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * clear the canvas
     */
    public void clearShapes() {
        while (!shapeStack.empty()) {
            shapeStack.pop();
        }
        repaint();
        try {
            server.clear(username);
        } catch (RemoteException e) {
            //TODO: handle the exception. the user is disconnected.
            e.printStackTrace();
        }
    }

    public void remoteClear() {
        while (!shapeStack.empty()) {
            shapeStack.pop();
        }
        repaint();
    }

    public void remoteUndo() {
        shapeStack.pop();
        repaint();
    }

    /**
     * undo
     */
    public void undo() {
        shapeStack.pop();
        repaint();
        try {
            server.undo(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            //TODO: handle the exception. the user is disconnected.
        }
    }

    public String exportData() {
        StringBuilder sb = new StringBuilder();
        for (IShape shape : shapeStack) {
            sb.append(MsgJsonFactory.toJson(shape))
                    .append("\n");
        }
        return sb.toString();
    }

    public BufferedImage exportImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        printAll(image.getGraphics());
        return image;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //Noting to do
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Noting to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Noting to do
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(getWidth(), getHeight());
        }
        Graphics gImage = offScreenImage.getGraphics();
        paint(gImage);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Set color of pen
     *
     * @param color
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
