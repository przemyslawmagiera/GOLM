package pl.golm.gui.impl;

import pl.golm.UtilGUI;
import pl.golm.gui.PlayerColor;
import pl.golm.gui.PlayerInfoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Przemek on 30.11.2016.
 */
public class PlayerInfoPanelImpl extends JPanel implements PlayerInfoPanel
{
    private JLabel you;
    private JLabel opponent;

    public PlayerInfoPanelImpl(String opponentName, PlayerColor opponentColor, String playerName)
    {
        setOpponent(new JLabel("Opponent: " + opponentName));
        setYou(new JLabel("You: " + playerName));
        if(opponentColor.equals(PlayerColor.BLACK))
        {
            getOpponent().setForeground(Color.BLACK);
            getYou().setForeground(Color.WHITE);
        }
        else
        {
            getOpponent().setForeground(Color.WHITE);
            getYou().setForeground(Color.BLACK);
        }
        setBackground(UtilGUI.APPLICATION_BACKGROUND);
        setLayout(new GridLayout(1,2));
        add(getYou());
        add(getOpponent());
        setPreferredSize(new Dimension(UtilGUI.APPLICATION_WIDTH, UtilGUI.PANEL_PLAYERS_INFO_HEIGHT));
        setVisible(true);
    }

    public JLabel getYou()
    {
        return you;
    }

    public void setYou(JLabel you)
    {
        this.you = you;
    }

    public JLabel getOpponent()
    {
        return opponent;
    }

    public void setOpponent(JLabel opponent)
    {
        this.opponent = opponent;
    }
}
