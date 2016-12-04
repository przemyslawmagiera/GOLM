package pl.golm.gui.impl;

import pl.golm.UtilGUI;
import pl.golm.gui.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Przemek on 30.11.2016.
 */
public class PlayerPanelImpl extends JPanel implements PlayerPanel
{
    private JButton pass;
    private JButton surrender;

    public PlayerPanelImpl()
    {
        this.pass = new JButton("Pass");
        this.surrender = new JButton("Surrender");
        pass.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                //TODO send information to parser
            }
        });
        surrender.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                //TODO send information to parser
            }
        });
        setLayout(new FlowLayout());
        add(pass);
        add(surrender);
        setBackground(UtilGUI.APPLICATION_BACKGROUND);
        setPreferredSize(new Dimension(UtilGUI.APPLICATION_WIDTH, UtilGUI.PANEL_PLAYER_HEIGHT));
        setVisible(true);
    }

    public JButton getPass()
    {
        return pass;
    }

    public void setPass(JButton pass)
    {
        this.pass = pass;
    }

    public JButton getSurrender()
    {
        return surrender;
    }

    public void setSurrender(JButton surrender)
    {
        this.surrender = surrender;
    }
}
