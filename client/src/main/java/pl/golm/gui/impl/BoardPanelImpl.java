package pl.golm.gui.impl;

import pl.golm.Main;
import pl.golm.gui.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

/**
 * Created by Przemek on 30.11.2016.
 */
public class BoardPanelImpl extends JPanel implements BoardPanel, MouseListener
{
    private int option;

    public BoardPanelImpl()
    {
        option = 19;
        setBackground(Main.BOARD_BACKGROUND);
        setPreferredSize(new Dimension(Main.APPLICATION_WIDTH, Main.APPLICATION_WIDTH));
        addMouseListener(this);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        double x1 = Main.APPLICATION_WIDTH/(option+2);
        double x2 = x1*(option+1);
        double y1 = x1;
        double y2 = y1*(option+1);
        for(int i = 1; i <= option+1; i++)
        {
            g2.draw(new Line2D.Double(x1, y1*i, x2, y1*i));
            g2.draw(new Line2D.Double(x1*i, y1, x1*i, y2));
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
