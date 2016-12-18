package pl.golm.gui;

import pl.golm.UtilGUI;
import pl.golm.communication.dto.GameDto;
import pl.golm.communication.dto.GameState;
import pl.golm.controller.GameController;
import pl.golm.gui.impl.BoardPanelImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Przemek on 18.12.2016.
 */
public class DeadGroupsWindow extends JFrame
{
    private BoardPanel board;
    private GameDto gameDto;
    private JButton ok;
    private GameController controller = GameController.getInstance();
    private JPanel buttonPanel;

    public DeadGroupsWindow(GameDto gameDto)
    {
        super(UtilGUI.APPLICATION_NAME);
        this.gameDto = gameDto;
        setLayout(new BorderLayout());
        setSize(UtilGUI.APPLICATION_WIDTH, UtilGUI.APPLICATION_HEIGHT);
        this.board = new BoardPanelImpl(gameDto);
        this.ok = new JButton("OK");
        buttonPanel = new JPanel(new BorderLayout(2,1));
        ok.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(GameState.ACCEPTING_DEAD_GROUPS.equals(gameDto.getGameState()))
                {
                    controller.acceptDeadGroups();
                }
                else
                {
                    controller.requestDeadGroups();
                }
                setVisible(false);
            }
        });
        if(GameState.ACCEPTING_DEAD_GROUPS.equals(gameDto.getGameState()))
        {
            JButton decline = new JButton("Decline");
            buttonPanel.add(decline, BorderLayout.EAST);
            decline.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent actionEvent)
                {
                    controller.declineDeadGroups();
                    setVisible(false);
                }
            });
        }
        buttonPanel.add(ok, BorderLayout.WEST);
        add((JPanel) board, BorderLayout.CENTER);
        add(ok, BorderLayout.SOUTH);
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
