package ui;

import shape.*;
import shape.Point;
import shape.Rectangle;

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
        /* Initialize canvas */
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getSize().width, getSize().height);
        for (IShape shape : shapeStack) {
            shape.draw(g);
        }

        /* If mouse haven't been released */
        if (mousePressed) {
            g.setColor(currentColor);
            IShape shape = getShape();
            if (shape != null) {
                shape.draw(g);
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
                Line line = new Line(startPoint, currentPoint, currentColor);
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

                Rectangle rectangle = new Rectangle(leftTop, width, height, currentColor);
                shape = rectangle;
                break;
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(startPoint.getX() - currentPoint.getX(), 2)
                        + Math.pow(startPoint.getY() - currentPoint.getY(), 2));
                int radiusX = startPoint.getX() - radius / 2;
                int radiusY = startPoint.getY() - radius / 2;
                Circle circle = new Circle(new Point(radiusX, radiusY), radius, currentColor);
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
                Oval oval = new Oval(oLeftTop, oWidth, oHeight, currentColor);
                shape = oval;
                break;
            case FREE:
                freeDraw.add(currentPoint);
                FreeDraw draw = new FreeDraw(freeDraw, currentColor);
                shape = draw;
                break;
            case TEXT:
                Text text = new Text(currentPoint, "Test", getFont());
                shape = text;
                break;
        }
        return shape;

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            //TODO: Double click to add a dialog for text inputting
        }
    }

    public void mousePressed(MouseEvent e) {
        startPoint = new Point(e.getX(), e.getY());
        mousePressed = true;
        if (currentShape == ShapeType.FREE) {
            freeDraw.add(startPoint);
        }
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        currentPoint = new Point(e.getX(), e.getY());

        IShape shape = getShape();
        addShape(shape);
        freeDraw = new ArrayList<>();
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

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setCurrentShape(ShapeType shape) {
        this.currentShape = shape;
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
