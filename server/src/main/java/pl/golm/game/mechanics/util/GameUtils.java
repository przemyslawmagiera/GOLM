package pl.golm.game.mechanics.util;

import pl.golm.game.model.Board;
import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import pl.golm.game.model.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ktoś on 29.11.2016.
 */
//TODO check all implemented methods some are not trivial and may have bugs
public class GameUtils
{
    public static boolean moveIsLegal(Board board, Move move)
    {
        List<Move> history = board.getHistory();
        List<List<Field>> currentBoard = board.getBoard();
        if (move.getPlayer().equals(history.get(history.size() - 1).getPlayer())) // not your move
            return false;
        if (!currentBoard.get(move.getField().getRow()).get(move.getField().getColumn()).equals(null)) // field already occupied
            return false;
        if (history.get(history.size() - 1).getKilled().size() == 1 && history.get(history.size() - 1).getKilled().get(0).equals(move.getField()) && moveKills(board, move).size() == 1 && moveKills(board, move).get(0).equals(history.get(history.size() - 1).getField())) //Ko rule makes move illegal
            return false; // moze wpakowac to (^) do osobnej metody, troche dlugi ten if...your call
        if(moveIsSuicide(board, move)) // move is suicide
            return false;
        return true;
    }

    private static boolean moveIsSuicide(Board board, Move move)
    {
        List<Field> neighbours = getFieldNeighbors(board, move.getField());
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer().equals(null))
                return false; // move is not a suicide if any of neighbours is empty
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer().equals(move.getPlayer()) && getLiberties(board, neighbour).size() > 1) // more then one because we are taking away one with move tho check if not zero on tests
                return false; // move is not suicide if placed stone belongs to a group with at least one liberty (ore two depends how to look at it (before / after move))
        for (Field neighbour : neighbours)
            if (!neighbour.getPlayer().equals(move.getPlayer()) && !neighbour.getPlayer().equals(null) && getLiberties(board, neighbour).size() == 1)
                return false; //move is not a suicide if kills some opponents (takes last liberty of enemy group of stones)
        return true;
    }

    public static int countTerritories(Board board, Player player)
    {
        return 0;//TODO
    }

    public static int countPoints(Board board, Player player)
    {
        return 0;//TODO
    }

    public static List<Field> moveKills(Board board, Move move)
    {
        List<Field> listOfKilled = new ArrayList<Field>();//TODO implement
        return listOfKilled;
    }

    public static List<Field> getFieldNeighbors(Board board, Field field)
    {
        List<Field> fieldList = new ArrayList<Field>();
        if (field.getRow() > 0)
            fieldList.add(board.getBoard().get(field.getRow() - 1).get(field.getColumn()));
        if (field.getRow() < board.getSize() - 1)
            fieldList.add(board.getBoard().get(field.getRow() + 1).get(field.getColumn()));
        if (field.getColumn() > 0)
            fieldList.add(board.getBoard().get(field.getRow()).get(field.getColumn() - 1));
        if (field.getColumn() < board.getSize() - 1)
            fieldList.add(board.getBoard().get(field.getRow()).get(field.getColumn() - 1));
        return fieldList;
    }

    public static Set<Field> getLiberties(Board board, Field field) // maybe should be optimalized in the future
    {
        Set<Field> liberties = new HashSet<Field>();
        Set<Field> checked = new HashSet<Field>();
        Player player = field.getPlayer();
        addLibertiesToSet(board, field, liberties, checked);
        return liberties;
    }
    private static void addLibertiesToSet(Board board, Field field, Set<Field> liberties, Set<Field> checked) // needs to be checked
    {
        getFieldNeighbors(board, field).forEach((Field neighbour) -> {if(!checked.contains(neighbour)) {if (neighbour.getPlayer().equals(null))liberties.add(neighbour); else if(neighbour.getPlayer().equals(field.getPlayer()))addLibertiesToSet(board, neighbour, liberties, checked);checked.add(neighbour);}});
    }

}
