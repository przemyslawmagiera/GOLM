package client.pl.golm.controller.factory.impl;

import client.pl.golm.controller.factory.DialogFactory;

import javax.swing.*;

/**
 * Created by Przemek on 20.12.2016.
 */
public class ErrorDialogFactoryImpl implements DialogFactory
{
    @Override
    public void showMessageDialog(JFrame parent, String message)
    {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
