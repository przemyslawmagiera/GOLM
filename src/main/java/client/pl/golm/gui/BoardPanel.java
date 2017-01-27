package pl.golm.gui;

import java.util.ArrayList;

/**
 * Created by Przemek on 30.11.2016.
 */
public interface BoardPanel
{
    int getOption();

    void setOption(int option);

    ArrayList<ArrayList<Circle>> getCircles();

    void setCircles(ArrayList<ArrayList<Circle>> circles);
}
