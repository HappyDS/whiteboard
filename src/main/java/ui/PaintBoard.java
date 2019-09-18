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

/**
 * @author Yangzhe Xie
 * @date 18/9/19
 */
@SuppressWarnings("ALL")
public class PaintBoard extends Canvas implements MouseListener, MouseMotionListener {

    private Stack<IShape> shapeStack;

    private Point startPoint;
    private Point currentPoint;
    private boolean mousePressed = false;
    private Color currentColor;
    private int currentShape;

    private List<Point> freeDraw = new ArrayList<>();

    public PaintBoard() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        currentColor = Color.BLACK;
        shapeStack = new Stack<IShape>();

        //TEST
        currentShape = 5;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getSize().width, getSize().height);
        for (IShape shape: shapeStack) {
            shape.draw(g);
        }

        if (mousePressed) {
            g.setColor(currentColor);
            IShape shape = null;

            shape = getShape();
            if (shape != null) {
                shape.draw(g);
            }
        }
    }

    private IShape getShape() {
        IShape shape = null;
        switch (currentShape) {
            case 1:
                Line line = new Line(startPoint, currentPoint, currentColor);
                shape = line;
                break;
            case 2:
                int width = Math.abs(startPoint.getX() - currentPoint.getX());
                int height = Math.abs(startPoint.getY() - currentPoint.getY());
                Point leftTop;
                if (startPoint.getX() < currentPoint.getX()
                        && startPoint.getY() < currentPoint.getY()) {
                    leftTop = startPoint;
                } else if (startPoint.getX() < currentPoint.getX()
                        && startPoint.getY() > currentPoint.getY()) {
                    leftTop = new Point(startPoint.getX(), currentPoint.getY());
                } else if (startPoint.getX() > currentPoint.getX()
                        && startPoint.getY() > currentPoint.getY()) {
                    leftTop = currentPoint;
                } else {
                    leftTop = new Point(currentPoint.getX(), startPoint.getY());
                }
                Rectangle rectangle = new Rectangle(leftTop, width, height, currentColor);
                shape = rectangle;
                break;
            case 3:
                int radius = (int) Math.sqrt(Math.pow(startPoint.getX() - currentPoint.getX(), 2)
                        + Math.pow(startPoint.getY() - currentPoint.getY(), 2));
                int radiusX = startPoint.getX() - radius / 2;
                int radiusY = startPoint.getY() - radius / 2;
                Circle circle = new Circle(new Point(radiusX, radiusY), radius, currentColor);
                shape = circle;
                break;
            case 4:
                int oWidth = Math.abs(startPoint.getX() - currentPoint.getX());
                int oHeight = Math.abs(startPoint.getY() - currentPoint.getY());
                Point oLeftTop;
                if (startPoint.getX() < currentPoint.getX()
                        && startPoint.getY() < currentPoint.getY()) {
                    oLeftTop = startPoint;
                } else if (startPoint.getX() < currentPoint.getX()
                        && startPoint.getY() > currentPoint.getY()) {
                    oLeftTop = new Point(startPoint.getX(), currentPoint.getY());
                } else if (startPoint.getX() > currentPoint.getX()
                        && startPoint.getY() > currentPoint.getY()) {
                    oLeftTop = currentPoint;
                } else {
                    oLeftTop = new Point(currentPoint.getX(), startPoint.getY());
                }
                Oval oval = new Oval(oLeftTop, oWidth, oHeight, currentColor);
                shape = oval;
                break;
            case 5:
                freeDraw.add(currentPoint);
                FreeDraw draw = new FreeDraw(freeDraw, currentColor);
                shape = draw;
                break;
            case 6:
                Text text = new Text(currentPoint, "Test", getFont());
                shape = text;
                break;
        }
        return shape;

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

        }
    }

    public void mousePressed(MouseEvent e) {
        startPoint = new Point(e.getX(), e.getY());
        mousePressed = true;
        if (currentShape == 5) {
            freeDraw.add(startPoint);
        }
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        currentPoint = new Point(e.getX(), e.getY());

        IShape shape = getShape();
        shapeStack.push(shape);
        freeDraw = new ArrayList<>();
        repaint();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        currentPoint = new Point(e.getX(), e.getY());
        repaint();
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void addShape(IShape shape) {
        shapeStack.push(shape);
    }
}
