package pl.golm.gui.impl;

import pl.golm.UtilGUI;
import pl.golm.gui.BoardPanel;
import pl.golm.gui.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.*;

/**
 * Created by Przemek on 30.11.2016.
 */
public class BoardPanelImpl extends JPanel implements BoardPanel, MouseListener
{
    private int option;
    private ArrayList<ArrayList<Circle>> circles;
    private ArrayList<Line2D.Double> grid;

    public BoardPanelImpl()
    {
        circles = new ArrayList<ArrayList<Circle>>();
        grid = new ArrayList<Line2D.Double>();
        option = 13;
        setBackground(UtilGUI.BOARD_BACKGROUND);
        setPreferredSize(new Dimension(UtilGUI.APPLICATION_WIDTH, UtilGUI.APPLICATION_WIDTH));
        addMouseListener(this);
        setVisible(true);
        initBoard();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        drawComps(g2);
        repaint();
    }

    private void initBoard()
    {
        double x1 = UtilGUI.APPLICATION_WIDTH/(option+1);
        double x2 = x1*(option);
        double y1 = x1;
        double y2 = y1*(option);
        for(int i = 1; i <= option; i++)
        {
            ArrayList<Circle> buffer = new ArrayList<Circle>();
            for (int j = 1; j <= option; j++)
            {
                buffer.add(new Circle(x1*j,y1*i,UtilGUI.DEFAULT_CIRCLE_SIZE));
            }
            circles.add(buffer);
            grid.add(new Line2D.Double(x1, y1*i, x2, y1*i));
            grid.add(new Line2D.Double(x1*i, y1, x1*i, y2));
        }
    }

    private void drawComps(Graphics2D g2d)
    {
        for(int i = 0; i < option; i++)
        {
            for (int j = 0; j < option; j++)
            {
                Circle actual = circles.get(j).get(i);
                g2d.setColor(actual.getColor());
                g2d.draw(actual);
            }
            g2d.draw(grid.get(2*i));
            g2d.draw(grid.get(2*i+1));
        }
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        repaint();
    }

    public void mousePressed(MouseEvent mouseEvent)
    {

    }

    public void mouseReleased(MouseEvent mouseEvent)
    {

    }

    public void mouseEntered(MouseEvent mouseEvent)
    {

    }

    public void mouseExited(MouseEvent mouseEvent)
    {

    }

    public int getOption()
    {
        return option;
    }

    public void setOption(int option)
    {
        this.option = option;
    }
}
