package pl.golm.gui;

import java.awt.*;

/**
 * Created by Przemek on 06.12.2016.
 */
public interface Circle
{

    void setR(double r);

    boolean isOccupied();

    void setOccupied(boolean occupied);

    Color getColor();

    void setColor(Color color);
}
