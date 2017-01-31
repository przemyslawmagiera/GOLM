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
        return new GreeterService(message.getInfo());
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.POST)
    public String gameRequest(@RequestParam Map<String, String> params, Model model)
    {
        GameController newContorller = new GameController();
        GameDto newGameDto = new GameDto();
        String multi = params.get("multi");
        String size = params.get("size");
        String name = String.valueOf(Math.random()*100000);

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
        newContorller.run();
        model.addAttribute("size", Integer.parseInt(size));
        model.addAttribute("player", newGameDto.getPlayerName());
        model.addAttribute("occupied", newContorller.board.toString());
        clientControllers.put(name, newContorller);
        return "/board";
    }

    @RequestMapping(value = "/game/moveRequest", method = RequestMethod.POST)
    public String moveRequest(@RequestParam Map<String, String> params, Model model)
    {
        String size = params.get("size");
        String player = params.get("player");
        String x = params.get("x");
        String y = params.get("y");
        GameController gameController = clientControllers.get(player);

        if (clientControllers.get(gameController.gameDto.getOpponentName()) != null) if (clientControllers.get(gameController.gameDto.getOpponentName()).surrendered)
        {
            return opponentSurr(model);
        }
        if(x.equals("-1"))
        {
            gameController.passRequest();
            if(gameController.gameDto.getGameState().equals(GameState.COUNTING_DEAD_GROUPS))
            {
                model.addAttribute("occupied", gameController.board.toString());
                model.addAttribute("size", Integer.parseInt(size));
                model.addAttribute("player", player);
                return "/boardCount";
            }
            if(gameController.gameDto.getGameState().equals(GameState.ACCEPTING_DEAD_GROUPS))
            {
                model.addAttribute("occupied", gameController.opponentSelected);
                model.addAttribute("size", Integer.parseInt(size));
                model.addAttribute("player", player);
                return "/acceptRequested";
            }
        }
        else
        {
            gameController.moveRequest(Integer.parseInt(x), Integer.parseInt(y));
        }
        model.addAttribute("occupied", gameController.board.toString());
        model.addAttribute("size", Integer.parseInt(size));
        model.addAttribute("player", player);
        return "/board";
    }

    @RequestMapping(value = "/proceedPassRequest", method = RequestMethod.POST)
    public String proceedPassRequest(@RequestParam Map<String, String> params, Model model)
    {
        String selected = params.get("selected");
        String player = params.get("player");
        String size = params.get("size");
        GameController gameController = clientControllers.get(player);
        List<String> messages = BasicOperationParser.prepareCountedTerritoriesMessage(selected, gameController.gameDto.getSize());
        gameController.requestDeadGroups(messages);
        if(gameController.gameDto.getGameState().equals(GameState.RUNNING))
        {
            model.addAttribute("occupied", gameController.board.toString());
            model.addAttribute("size", Integer.parseInt(size));
            model.addAttribute("player", player);
            return "/board";
        }

        return "/mainPage";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String configuration(@RequestParam Map<String, String> params, Model model)
    {
        model.addAttribute("msg","");
        return "/mainPage";
    }

    @RequestMapping(value = "/surrender", method = RequestMethod.POST)
    public String surrender(@RequestParam Map<String, String> params, Model model)
    {
        String player = params.get("player");
        GameController gameController = clientControllers.get(player);
        gameController.surrender();
        model.addAttribute("msg","you surrendered");
        return "/mainPage";
    }

    @RequestMapping(value = "/declinedDeadGroups", method = RequestMethod.POST)
    public String declined(@RequestParam Map<String, String> params, Model model)
    {
        String player = params.get("player");
        GameController gameController = clientControllers.get(player);
        gameController.declineDeadGroups();
        String size = params.get("size");

        model.addAttribute("occupied", gameController.board.toString());
        model.addAttribute("size", Integer.parseInt(size));
        model.addAttribute("player", player);
        return "/board";
    }

    public String opponentSurr(Model model)
    {
        model.addAttribute("msg","you won, opponent surrendered");
        return "/mainPage";
    }
}


