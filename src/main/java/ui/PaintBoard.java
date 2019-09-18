package ui;

import shape.Point;
import shape.Rectangle;
import shape.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

    private List<Point> freeDraw = new ArrayList<>();   /* A series of points on a free draw path */
    private List<Point> eraserPath = new ArrayList<>();

    private Image offScreenImage;
    private int eraseSize = 10;
    private int penSize = 1;
    private int textSize = 14;

    public PaintBoard() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        currentColor = Color.BLACK;
        shapeStack = new Stack<IShape>();

        /* This variable is for testing purpose */
        currentShape = ShapeType.LINE;
    }

    /**
     * Do actually painting
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
//            case TEXT:
//                Text text = new Text(currentPoint, "Test", 16, currentColor);
//                shape = text;
//                break;
            case ERASER:
                eraserPath.add(currentPoint);
                Eraser eraser = new Eraser(eraserPath, eraseSize);
                shape = eraser;
                break;
        }
        return shape;

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && currentShape == ShapeType.TEXT) {
            //TODO: Double click to add a dialog for text inputting
            String input = JOptionPane.showInputDialog(null, "Input text");
            Text text = new Text(new Point(e.getX(), e.getY()), input, textSize, currentColor);
//            text.draw(getGraphics());
            shapeStack.push(text);
//            text.draw(getGraphics());
            repaint();
        }
    }

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

    public void mouseDragged(MouseEvent e) {
        currentPoint = new Point(e.getX(), e.getY());
        repaint();
    }

    /**
     * A communication thread can call this method to add shapes to the board
     *
     * @param shape
     */
    public synchronized void addShape(IShape shape) {
        shapeStack.push(shape);
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

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

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

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
