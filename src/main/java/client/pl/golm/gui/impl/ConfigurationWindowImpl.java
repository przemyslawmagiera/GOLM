package pl.golm.gui.impl;

import jdk.nashorn.internal.scripts.JO;
import pl.golm.communication.dto.GameDto;
import pl.golm.controller.GameController;
import pl.golm.controller.factory.DialogFactory;
import pl.golm.controller.factory.impl.DialogFactoryImpl;
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
    private GameController controller = GameController.getInstance();
    private DialogFactory dialogFactory;

    public ConfigurationWindowImpl()
    {
        super("GOLM 1.0 Launcher");
        dialogFactory = new DialogFactoryImpl();
        setSize(350, 150);
        setResizable(false);
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ConfigurationWindowImpl thisWindow = this;
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
                    dialogFactory.showMessageDialog(null,"Wait patiently for opponent!");
                    setVisible(false);
                    controller.setParentFrame(thisWindow);
                    controller.requestGame(gameDto);
                }
            }
        });
        add(sizes);
        add(types);
        add(nameField);
        add(confirm);
        setVisible(true);
    }

    public ConfigurationWindow getInstance()
    {
        return this;
    }
}
