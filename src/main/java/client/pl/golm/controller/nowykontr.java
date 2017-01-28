package client.pl.golm.controller;

import client.pl.golm.akka.MainClient;
import client.pl.golm.communication.Client;
import client.pl.golm.communication.Player;
import client.pl.golm.communication.dto.GameDto;
import client.pl.golm.communication.dto.GameState;
import client.pl.golm.communication.parser.BasicOperationParser;
import client.pl.golm.controller.factory.DialogFactory;
import client.pl.golm.controller.factory.impl.DialogFactoryImpl;
import client.pl.golm.controller.factory.impl.ErrorDialogFactoryImpl;
import client.pl.golm.gui.*;
import client.pl.golm.gui.impl.ConfigurationWindowImpl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Przemek on 28.01.2017.
 */
@Controller
@RequestMapping("/")
public class nowykontr
{
    Map<String,GameController> clientControllers = new HashMap<>();

    @MessageMapping("/golm")
    @SendTo("/topic/greetings")
    public GreeterService greeting(GameInfo message) throws Exception
    {
        Thread.sleep(3000); // simulated delay

        //tutaj dostaniemy wiadomość
        return new GreeterService(message.getInfo());
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.POST)
    public String gameRequest(@RequestParam Map<String, String> params, Model model)
    {
        GameController newContorller = new GameController();
        GameDto newGameDto = new GameDto();
        String multi = params.get("multi");
        String size = params.get("size");
        String name = params.get("name");

        if (multi.equals("multiplayer"))
        {
            newGameDto.setType("Multi player");
        } else
        {
            newGameDto.setType("Single player");
        }
        size.replace(" ", "");
        newGameDto.setSize(Integer.parseInt(size));
        newGameDto.setPlayerName(name);
        newContorller.gameDto = newGameDto;
        new Thread(newContorller).start();
        model.addAttribute("size", Integer.parseInt(size));
        model.addAttribute("player", newGameDto.getPlayerName());
        if (!newContorller.isYourTurn())
            newContorller.waitForOpponent();
        //model.addAtribute(list of white,black)
        clientControllers.put(name, newContorller);
        return "/board";
    }

    @RequestMapping(value = "/game/moveRequest", method = RequestMethod.POST)
    public String moveRequest(@RequestParam Map<String, String> params, Model model)
    {
        //String x = params.get("x");
        //String y = params.get("y");
        //String name = params.get("name");
        //GameController gameController = clientControllers.get(name);
        //make request and read the list set model
        String size = params.get("size");
        String name = params.get("name");

        model.addAttribute("size", Integer.parseInt(size));
        model.addAttribute("player", name);
        return "/board";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String configuration(@RequestParam Map<String, String> params, Model model)
    {

        return "/mainPage";
    }
}


