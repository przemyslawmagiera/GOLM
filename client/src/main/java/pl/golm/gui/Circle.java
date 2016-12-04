package pl.golm.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

/**
 * Created by Przemek on 02.12.2016.
 */
public class Circle extends Ellipse2D.Double
{
    private Color color;
    private boolean active;
    private double r,x,y;

    public Circle(double x, double y, double r)
    {
        this.r = r;
        this.x = x;
        this.y = y;
        active = false;
        setFrame(x - r, y - r, 2 * r, 2 * r);
    }

    public void setR(double r)
    {
        this.r = r;
        setFrame(x - r, y - r, 2 * r, 2 * r);
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }
}
