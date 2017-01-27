package pl.golm.controller.factory.impl;

import pl.golm.controller.GameController;
import pl.golm.controller.factory.DialogFactory;

import javax.swing.*;

/**
 * Created by Przemek on 20.12.2016.
 */
public class DialogFactoryImpl implements DialogFactory
{
    @Override
    public void showMessageDialog(JFrame parent, String message)
    {
        JOptionPane.showMessageDialog(parent, message);
    }
}
