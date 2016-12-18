package pl.golm.gui;

import pl.golm.UtilGUI;
import pl.golm.communication.dto.GameDto;
import pl.golm.controller.GameController;
import pl.golm.gui.impl.BoardPanelImpl;
import pl.golm.gui.impl.PlayerInfoPanelImpl;
import pl.golm.gui.impl.PlayerPanelImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Przemek on 18.12.2016.
 */
public class CountTerritoriesWindow extends JFrame
{
    private BoardPanel board;
    private GameDto gameDto;
    private JButton ok;
    private GameController controller = GameController.getInstance();

    public CountTerritoriesWindow(GameDto gameDto)
    {
        super(UtilGUI.APPLICATION_NAME);
        this.gameDto = gameDto;
        setLayout(new BorderLayout());
        setSize(UtilGUI.APPLICATION_WIDTH, UtilGUI.APPLICATION_HEIGHT);
        this.board = new BoardPanelImpl(gameDto);
        this.ok = new JButton("OK");
        ok.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
               //TODO handle
                setVisible(false);
            }
        });

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
