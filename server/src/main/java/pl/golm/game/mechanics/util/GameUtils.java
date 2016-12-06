package pl.golm.game.mechanics.util;

import pl.golm.game.model.*;
import java.util.*;

/**
 * Collection of static methods used by every game instance thread to determinate changes on board caused by any player's actions.
 * @author Dominik Lachowicz
 * @version  1.01
 */
public class GameUtils
{
    /**
     * bonus points given to player who starts the game as second
     */
    public static final double KOMI = 6.5;

    /**
     * method checking legitimacy of move before it can be properly created and accepted by server
     * @param board a board on which a game is taking place
     * @param field a field {@link Player} intends to put a stone on
     * @param player a player that requests checking legitimacy of his move
     * @return true if {@link Player} can place a stone on {@link Field} considering he is playing on certain {@link Board}, false otherwise
     */
    public static boolean moveIsLegal(Board board, Field field, Player player)
    {
        List<Move> history = board.getHistory();
        List<List<Field>> currentBoard = board.getBoard();
        if ((history.size() == 0 && player.getColor().equals(PlayerColor.WHITE)) || (history.size() > 0 && player.equals(history.get(history.size() - 1).getPlayer())))
            return false; // returns false when it is not this players move
        if (currentBoard.get(field.getRow()).get(field.getColumn()).getPlayer() != null)
            return false; // returns false when a field is already occupied
        if(moveIsSuicide(board, field, player))
            return false; // returns false when a move is suicide, GO rules forbids suicide moves
        if (history.size() > 0 && history.get(history.size() - 1).getKilled().size() == 1 && history.get(history.size() - 1).getKilled().get(0).equals(field) && moveKills(board, field, player).size() == 1 && moveKills(board, field, player).contains(history.get(history.size() - 1).getField()))
            return false; //returns false when player wants to create a situation on the board that has already occurred a turn ago, KO rule forbids this to happen
        return true; // returns true only if none of above took place
    }

    private static boolean moveIsSuicide(Board board, Field field, Player player) // private method, used by @GameUtils.moveIsLegal to determinate if an intended move is a suicide
    {
        List<Field> neighbours = getFieldNeighbors(board, field);
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer() == null)
                return false; // move is not a suicide if any of neighbours is empty
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer().equals(player) && getLiberties(board, neighbour).size() > 1) // more then one because we are taking away one with move tho check if not zero on tests
                return false; // move is not suicide if a placed stone belongs to a group with at least one liberty, it asks for more then one because one is already being taken away by the move
        for (Field neighbour : neighbours)
            if (neighbour.getPlayer() != null && !neighbour.getPlayer().equals(player) && getLiberties(board, neighbour).size() == 1)
                return false; //move is not a suicide if it kills some opponents (takes last liberty of enemy group of stones)
        return true; // if none of above took place a move is a suicide
    }

    /**
     * method used to suggest players their and their opponents territories on current board
     * @param board a board on which a game is taking place
     * @param player a player who's territories are being counted
     * @return set of fields which are predicted to belong to {@link Player}, the implementation of java Set is a HashSet
     */
    public static Set<Field> getTerritories(Board board, Player player)
    {
        Set<Field> territories = new HashSet<Field>();
        for (int i = 0; i < board.getSize(); i++)
            for(int j = 0; j < board.getSize(); j++) // for each field on the board
            {
                if (board.getBoard().get(i).get(j).getPlayer() == null) // a field can only be a territory if it is empty
                {
                    Set<Field> checked = new HashSet<Field>(); // this set helps not to check same field more then once
                    if(fieldIsTerritory(board, player, board.getBoard().get(i).get(j), checked))
                        territories.add(board.getBoard().get(i).get(j)); // if field is a part of a territory add to returned set
                }
            }
        return territories;
    }

    private static boolean fieldIsTerritory(Board board, Player player, Field field, Set<Field> checked) // private function used for empty fields checking if they are part of players territory, recursive, used by @GameUtils.getTerritories
    {
        if (field.getPlayer() == null || field.getPlayer().equals(player)) // using modified flood-fill algorithm adds checks if field is only surrounded by stones of Player
        {
            if (checked.contains(field))
                return true;
            else if (field.getPlayer() == null)
            {
                checked.add(field);
                boolean onlyPlayerOrNullBorders = true;
                for (Field neighbour : getFieldNeighbors(board, field))
                    if (!fieldIsTerritory(board, player, neighbour, checked))
                        onlyPlayerOrNullBorders = false;
                return onlyPlayerOrNullBorders;
            }
            else
            {
                checked.add(field);
                return true;
            }
        }
        else
            return false;
    }

    /**
     * method counting points of {@link Player}, including territories, prisoners and KOMI advantage
     * @param player a player who's points are being counted
     * @return points of player, as it is type double it may be a inaccurate, it's inaccuracy is not bigger then 0.1
     */
    public static double countPoints(Player player)
    {
        if (player.getColor().equals(PlayerColor.WHITE))
            return player.getPrisonerAmount() + player.getTerritoryAmount() + KOMI;
        return player.getPrisonerAmount() + player.getTerritoryAmount();
    }

    /**
     * searches {@link Board} for all the stones killed by a legal, not yet played move
     * @param board a board on which a game takes place
     * @param field a field on which a player intends to play
     * @param player a player who plays this move
     * @return Set of fields on the board which are going to be killed by this move
     */
    public static Set<Field> moveKills(Board board, Field field, Player player)
    {
        Set<Field> listOfKilled = new HashSet<Field>();
        for (Field neighbour : getFieldNeighbors(board, field)) // a move can only influence neighbours and their chain (neighbours of their neighbours)
            if (neighbour.getPlayer() != null && !neighbour.getPlayer().equals(player) && getLiberties(board, neighbour).size() == 1)
                addNeighboursWithSameColor(board, neighbour, neighbour.getPlayer(), listOfKilled); // if the move takes exactly the last liberty of a chain add whole chain to the set
        return listOfKilled;
    }

    private static void addNeighboursWithSameColor(Board board, Field field, Player player, Set<Field> set) // private method adding whole chain to the set, used by GameUtils.moveKills
    {
        set.add(field);
        for (Field neighbour : getFieldNeighbors(board, field))
            if (neighbour.getPlayer() != null && neighbour.getPlayer().equals(player) && !set.contains(neighbour))
                addNeighboursWithSameColor(board, neighbour, player, set);
    }

    /**
     * method that creates a list of all fields neighbours
     * @param board a board on which a game takes place
     * @param field a field who's neighbours function is looking for
     * @return List(ArrayList) of fields that are fields neighbours
     */
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

    /**
     * method checks all the fields that are liberties of the stone or the whole chain if the stone belongs to one
     * @param board a board that the game takes place on
     * @param field a field on which a stone is placed
     * @return Set(HashSet) of fields that are stones liberties
     */
    public static Set<Field> getLiberties(Board board, Field field)
    {
        Set<Field> liberties = new HashSet<Field>();
        Set<Field> checked = new HashSet<Field>();
        Player player = field.getPlayer();
        addLibertiesToSet(board, field, liberties, checked); // using modified flood-fill algorithm to determinate if field is a liberty
        return liberties;
    }

    private static void addLibertiesToSet(Board board, Field field, Set<Field> liberties, Set<Field> checked) // private, recursive function adding liberties to set
    {
        // for each neighbour check if has already been checked, if is empty add as a liberty, if is enemy stop checking, if belongs to player check it's neighbours too
        getFieldNeighbors(board, field).forEach((Field neighbour) -> {if(!checked.contains(neighbour)) {checked.add(neighbour); if (neighbour.getPlayer() == null)liberties.add(neighbour); else if(neighbour.getPlayer().equals(field.getPlayer()))addLibertiesToSet(board, neighbour, liberties, checked);}});
    }

    /**
     * method used to suggest players their and their opponents dead groups of stones on current board
     * @param board a board on which a game is taking place
     * @param player a player who's dead groups are being counted
     * @return set of fields which are predicted to be dead and belong to {@link Player}, the implementation of java Set is a HashSet
     */
    public static Set<Field> getDeadGroups(Board board, Player player)
    {
        Set<Field> deadGroups = new HashSet<Field>();
        for (int i = 0; i < board.getSize(); i++)
            for (int j = 0; j < board.getSize(); j++)
                if (board.getBoard().get(i).get(j).getPlayer() != null && board.getBoard().get(i).get(j).getPlayer().equals(player))
                {
                    boolean add = true;
                    for (Field neighbour : getFieldNeighbors(board, board.getBoard().get(i).get(j)))
                        if (neighbour.getPlayer() != null && neighbour.getPlayer().equals(player))
                            add = false;
                    if(add)
                        deadGroups.add(board.getBoard().get(i).get(j));
                }
        return deadGroups;
    }
}