package client.pl.golm.gui;

import client.pl.golm.UtilGUI;
import client.pl.golm.communication.dto.GameDto;
import client.pl.golm.gui.impl.BoardPanelImpl;
import client.pl.golm.communication.dto.GameState;
import client.pl.golm.controller.WebController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dominik on 2016-12-19.
 */
public class TerritoriesWindow extends JFrame
{
    private BoardPanel board;
    private GameDto gameDto;
    private JButton ok;
    private JButton decline;
    private JPanel buttonPanel;
    private WebController controller = WebController.getInstance();

    public TerritoriesWindow(GameDto gameDto)
    {
        super("DEAL WITH TERRITORIES!");
        this.gameDto = gameDto;
        setSize(UtilGUI.APPLICATION_WIDTH, UtilGUI.APPLICATION_HEIGHT);
        setLayout(new BorderLayout());
        this.board = new BoardPanelImpl(gameDto);
        this.ok = new JButton("OK");
        this.buttonPanel = new JPanel(new FlowLayout());
        add((JPanel) board, BorderLayout.NORTH);
        ok.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(GameState.ACCEPTING_DEAD_GROUPS.equals(gameDto.getGameState()))
                {
                    controller.acceptTerritories();
                }
                else
                {
                    controller.requestTerritories();
                }
                setVisible(false);
            }
        });
        if(GameState.ACCEPTING_DEAD_GROUPS.equals(gameDto.getGameState()))
        {
            decline = new JButton("Decline");
            buttonPanel.add(decline);
            decline.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent actionEvent)
                {
                    controller.declineTerritories();
                    setVisible(false);
                }
            });
        }
        buttonPanel.add(ok);
        buttonPanel.setVisible(true);
        buttonPanel.setBackground(UtilGUI.APPLICATION_BACKGROUND);
        add(buttonPanel,BorderLayout.CENTER);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        repaint();
        setVisible(true);
    }

    public BoardPanel getBoard()
    {
        return board;
    }

    public void setBoard(BoardPanel board)
    {
        this.board = board;
    }
}
