package pl.golm.gui;

import pl.golm.Main;
import pl.golm.communication.dto.GameDto;
import pl.golm.gui.impl.BoardPanelImpl;
import pl.golm.gui.impl.PlayerInfoPanelImpl;
import pl.golm.gui.impl.PlayerPanelImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Przemek on 30.11.2016.
 */
public class MainWindow extends JFrame
{
    private BoardPanel board;
    private PlayerPanel playerPanel;
    private PlayerInfoPanel playersInfo;

    public MainWindow(GameDto gameDto)
    {
        super(Main.APPLICATION_NAME);
        setLayout(new BorderLayout());
        setSize(Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);
        setBoard(new BoardPanelImpl());
        setPlayerPanel(new PlayerPanelImpl());
        setPlayersInfo(new PlayerInfoPanelImpl("DUPA", PlayerColor.BLACK, "GOWNO"));
        add((JPanel) getPlayersInfo(), BorderLayout.NORTH);
        add((JPanel) getBoard(), BorderLayout.CENTER);
        add((JPanel) getPlayerPanel(), BorderLayout.SOUTH);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

    public PlayerPanel getPlayerPanel()
    {
        return playerPanel;
    }

    public void setPlayerPanel(PlayerPanel playerPanel)
    {
        this.playerPanel = playerPanel;
    }

    public PlayerInfoPanel getPlayersInfo()
    {
        return playersInfo;
    }

    public void setPlayersInfo(PlayerInfoPanel playersInfo)
    {
        this.playersInfo = playersInfo;
    }
}
