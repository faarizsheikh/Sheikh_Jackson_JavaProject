// GameDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import org.example.sheikh_jackson_javaproject.pojo.*;
import java.util.ArrayList;

public interface GameDAO {
    ArrayList<Game> getAllGames();
    Game getGame(int id);
    void deleteGame(int id);
}
