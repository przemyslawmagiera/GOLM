package pl.golm.gui.impl;

import pl.golm.UtilGUI;
import pl.golm.communication.dto.GameDto;
import pl.golm.communication.dto.GameState;
import pl.golm.controller.GameController;
import pl.golm.gui.BoardPanel;
import pl.golm.gui.Circle;
import pl.golm.gui.GUIComponent;
import pl.golm.gui.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.*;

/**
 * Created by Przemek on 30.11.2016.
 */
public class BoardPanelImpl extends JPanel implements BoardPanel, MouseListener, GUIComponent
{
    private int option;
    private ArrayList<ArrayList<Circle>> circles;
    private ArrayList<Line2D.Double> grid;
    private GameController controller = GameController.getInstance();
    private PlayerColor playerColor;
    private GameDto gameDto;

    public BoardPanelImpl(GameDto gameDto)
    {
        this.gameDto = gameDto;
        circles = new ArrayList<ArrayList<Circle>>();
        grid = new ArrayList<Line2D.Double>();
        option = gameDto.getSize();
        setBackground(UtilGUI.BOARD_BACKGROUND);
        setPreferredSize(new Dimension(UtilGUI.APPLICATION_WIDTH, UtilGUI.APPLICATION_WIDTH));
        addMouseListener(this);
        setVisible(true);
        initBoard();
    }

    @Override
    public void paintComponent(Graphics g)
    {
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
        double x1 = UtilGUI.APPLICATION_WIDTH / (option + 1);
        double x2 = x1 * (option);
        double y1 = x1;
        double y2 = y1 * (option);
        for (int i = 1; i <= option; i++)
        {
            ArrayList<Circle> buffer = new ArrayList<Circle>();
            for (int j = 1; j <= option; j++)
            {
                buffer.add(new Circle(x1 * j, y1 * i, UtilGUI.DEFAULT_CIRCLE_SIZE));
            }
            circles.add(buffer);
            grid.add(new Line2D.Double(x1, y1 * i, x2, y1 * i));
            grid.add(new Line2D.Double(x1 * i, y1, x1 * i, y2));
        }
    }

    private void drawComps(Graphics2D g2d)
    {
        for (int i = 0; i < option; i++)
        {
            for (int j = 0; j < option; j++)
            {
                g2d.draw(grid.get(2 * i));
                g2d.draw(grid.get(2 * i + 1));
            }
        }

        for (int i = 0; i < option; i++)
        {
            for (int j = 0; j < option; j++)
            {
                Circle actual = circles.get(j).get(i);
                if (actual.isOccupied())
                {
                    g2d.setColor(actual.getColor());
                    g2d.fill(actual);
                    g2d.setColor(Color.BLACK);
                } else
                {
                    g2d.draw(actual);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        for (int i = 0; i < option; i++)
        {
            for (int j = 0; j < option; j++)
            {//get y, get x
                if (circles.get(j).get(i).contains(mouseEvent.getPoint().getX(), mouseEvent.getPoint().getY()))
                {
                    if(gameDto.getGameState().equals(GameState.RUNNING))
                    {
                        controller.moveRequest(i, j);
                    }
                    else
                    {
                        if(circles.get(j).get(i).isOccupied())
                        {
                            circles.get(j).get(i).setColor(Color.MAGENTA);//just not to be green
                            circles.get(j).get(i).setOccupied(false);
                        }
                        else
                        {
                            circles.get(j).get(i).setColor(Color.GREEN);
                            circles.get(j).get(i).setOccupied(true);
                        }
                    }
                }
            }
        }
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

    public ArrayList<ArrayList<Circle>> getCircles()
    {
        return circles;
    }

    public void setCircles(ArrayList<ArrayList<Circle>> circles)
    {
        this.circles = circles;
    }
}
