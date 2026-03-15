// GameDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.*;

public interface GameDAO {
    ArrayList<Game> getAllGames();
    Game getGame(int id);
    void deleteGame(int id);
    void updateGame(Game game);
}
