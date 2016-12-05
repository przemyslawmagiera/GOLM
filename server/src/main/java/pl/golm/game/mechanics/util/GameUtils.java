package pl.golm.game.mechanics.util;

import pl.golm.game.model.*;

import java.util.*;

/**
 * Created by Ktoś on 29.11.2016.
 */
//TODO check all implemented methods some are not trivial and may have bugs
public class GameUtils
{
    public static boolean moveIsLegal(Board board, Field field, Player player)
    {
        List<Move> history = board.getHistory();
        List<List<Field>> currentBoard = board.getBoard();
        if ((history.size() == 0 && player.getColor().equals(PlayerColor.WHITE)) || (history.size() > 0 && player.equals(history.get(history.size() - 1).getPlayer()))) // not your move
            return false;
        if (currentBoard.get(field.getRow()).get(field.getColumn()).getPlayer() != null) // field already occupied
            return false;
        if(moveIsSuicide(board, field, player)) // move is suicide
            return false;
        if (history.size() > 0 && history.get(history.size() - 1).getKilled().size() == 1 && history.get(history.size() - 1).getKilled().get(0).equals(field) && moveKills(board, field, player).size() == 1 && moveKills(board, field, player).contains(history.get(history.size() - 1).getField())) //Ko rule makes move illegal
            return false;
        return true;
    }

    private static boolean moveIsSuicide(Board board, Field field, Player player)
    {
        List<Field> neighbours = getFieldNeighbors(board, field);
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer() == null)
                return false; // move is not a suicide if any of neighbours is empty
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer().equals(player) && getLiberties(board, neighbour).size() > 1) // more then one because we are taking away one with move tho check if not zero on tests
                return false; // move is not suicide if placed stone belongs to a group with at least one liberty (ore two depends how to look at it (before / after move))
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer() != null && !neighbour.getPlayer().equals(player) && getLiberties(board, neighbour).size() == 1)
                return false; //move is not a suicide if kills some opponents (takes last liberty of enemy group of stones)
        return true;
    }

    public static Set<Field> getTerritories(Board board, Player player)
    {
        Set<Field> territories = new HashSet<Field>(); // zoptymalizowac
        for (int i = 0; i < board.getSize(); i++)
            for(int j = 0; j < board.getSize(); j++)
            {
                if (board.getBoard().get(i).get(j).getPlayer() == null) // if field is empty
                {
                    Set<Field> checked = new HashSet<Field>();
                    if(fieldIsTerritory(board, player, board.getBoard().get(i).get(j), checked))
                        territories.add(board.getBoard().get(i).get(j));
                }
            }
        return territories;
    }

    private static boolean fieldIsTerritory(Board board, Player player, Field field, Set<Field> checked) // function for empty fields checking if are terrs
    {
        if (field.getPlayer() == null || field.getPlayer().equals(player))
        {
            if (checked.contains(field))
                return true;
            else if (field.getPlayer().equals(player))
            {
                checked.add(field);
                return true;
            }
            else
            {
                boolean onlyPlayerOrNullBorders = true;
                for (Field neighbour : getFieldNeighbors(board, field))
                    if (!fieldIsTerritory(board, player, neighbour, checked))
                        onlyPlayerOrNullBorders = false;
                checked.add(field);
                return onlyPlayerOrNullBorders;
            }
        }
        else
            return false;
    }

    public static int countPoints(Player player)
    {
       return  player.getPrisonerAmount() + player.getTerritoryAmount();
    }

    public static Set<Field> moveKills(Board board, Field field, Player player) // returns set of stones killed by a legal yet unplayed move
    {
        Set<Field> listOfKilled = new HashSet<Field>();
        for (Field neighbour : getFieldNeighbors(board, field))
            if (neighbour.getPlayer() != null && !neighbour.getPlayer().equals(player) && getLiberties(board, neighbour).size() == 1)
                addNeighboursWithSameColor(board, neighbour, neighbour.getPlayer(), listOfKilled);
        return listOfKilled;
    }

    private static void addNeighboursWithSameColor(Board board, Field field, Player player, Set<Field> set)
    {
        set.add(field);
        for (Field neighbour : getFieldNeighbors(board, field))
            if (neighbour.getPlayer() != null && neighbour.getPlayer().equals(player) && !set.contains(neighbour))
                addNeighboursWithSameColor(board, neighbour, player, set);
    }

    public static List<Field> getFieldNeighbors(Board board, Field field)
    {
        List<Field> fieldList = new ArrayList<Field>();
        if (field.getRow() > 0)
            fieldList.add(board.getBoard().get(field.getColumn()).get(field.getRow() - 1));
        if (field.getRow() < board.getSize() - 1)
            fieldList.add(board.getBoard().get(field.getColumn()).get(field.getRow() + 1));
        if (field.getColumn() > 0)
            fieldList.add(board.getBoard().get(field.getColumn() - 1).get(field.getRow()));
        if (field.getColumn() < board.getSize() - 1)
            fieldList.add(board.getBoard().get(field.getColumn() + 1).get(field.getRow()));
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
        getFieldNeighbors(board, field).forEach((Field neighbour) -> {if(!checked.contains(neighbour)) {checked.add(neighbour); if (neighbour.getPlayer() == null)liberties.add(neighbour); else if(neighbour.getPlayer().equals(field.getPlayer()))addLibertiesToSet(board, neighbour, liberties, checked);}});
    }
    public static Set<Field> getDeadGroups(Board board, Player player) // check this
    {
        Set<Field> deadGroups = new HashSet<Field>();
        for (int i = 0; i < board.getSize(); i++)
            for (int j = 0; j < board.getSize(); j++)
                if (board.getBoard().get(i).get(j).getPlayer().equals(player))
                {
                    boolean add = true;
                    for (Field neighbour : getFieldNeighbors(board, board.getBoard().get(i).get(j)))
                        if (neighbour.getPlayer().equals(player))
                            add = false;
                    if(add)
                        deadGroups.add(board.getBoard().get(i).get(j));
                }
        return deadGroups;
    }
}