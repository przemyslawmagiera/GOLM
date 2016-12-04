package pl.golm.gui.impl;

import pl.golm.communication.dto.GameDto;
import pl.golm.controller.GameController;
import pl.golm.gui.ConfigurationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Przemek on 04.12.2016.
 */
public class ConfigurationWindowImpl extends JFrame implements ConfigurationWindow
{
    GameController controller = GameController.getInstance();

    public static void main(String[] args)
    {
        ConfigurationWindow cf = new ConfigurationWindowImpl();
    }

    public ConfigurationWindowImpl()
    {
        setSize(100, 200);
        setLayout(new FlowLayout());
        DefaultListModel<Integer> sizeList = new DefaultListModel<Integer>();
        sizeList.addElement(9);
        sizeList.addElement(13);
        sizeList.addElement(19);
        DefaultListModel<String> typeList = new DefaultListModel<String>();
        typeList.addElement("Multi player");
        typeList.addElement("Single player");
        final JList<Integer> sizes = new JList<Integer>(sizeList);
        final JList<String> types = new JList<String>(typeList);
        JButton confirm = new JButton("CONFIRM");
        final JTextField nameField = new JTextField("name");
        nameField.setPreferredSize(new Dimension(80,30));
        confirm.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(sizes.getSelectedValue() != null && types.getSelectedValue() != null && nameField.getText() != null)
                {
                    GameDto gameDto = new GameDto();
                    gameDto.setPlayerName(nameField.getText());
                    gameDto.setSize(sizes.getSelectedValue());
                    gameDto.setType(types.getSelectedValue());
                    controller.startGame(gameDto);
                }
            }
        });

        add(sizes);
        add(types);
        add(nameField);
        add(confirm);
        setVisible(true);
    }
}
