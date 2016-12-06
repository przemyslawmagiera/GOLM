package pl.golm.gui.impl;

import pl.golm.gui.Circle;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Przemek on 02.12.2016.
 */
public class CircleImpl extends Ellipse2D.Double implements Circle
{
    private Color color;
    private boolean occupied;
    private double r,x,y;

    public CircleImpl(double x, double y, double r)
    {
        this.r = r;
        this.x = x;
        this.y = y;
        occupied = false;
        setFrame(x - r, y - r, 2 * r, 2 * r);
    }

    public void setR(double r)
    {
        this.r = r;
        setFrame(x - r, y - r, 2 * r, 2 * r);
    }

    public boolean isOccupied()
    {
        return occupied;
    }

    public void setOccupied(boolean occupied)
    {
        this.occupied = occupied;
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
