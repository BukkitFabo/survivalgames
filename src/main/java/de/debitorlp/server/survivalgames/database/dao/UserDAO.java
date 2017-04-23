package de.debitorlp.server.survivalgames.database.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import de.debitorlp.server.survivalgames.database.tables.User;
import de.debitorlp.server.survivalgames.database.tables.mapper.UserMapper;

public interface UserDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS `User` ("
        + "`PlayerName` VARCHAR(16) NOT NULL,"
        + " `PlayerUUID` VARCHAR(32) PRIMARY KEY NOT NULL,"
        + " `Kills` INT DEFAULT 0,"
        + " `Deaths` INT DEFAULT 0,"
        + " `Rounds` INT DEFAULT 0,"
        + " `Wins` INT DEFAULT 0,"
        + " `Points` INT DEFAULT 0)")
    void createTable();

    @SqlUpdate("INSERT IGNORE INTO `User`(`PlayerName`, `PlayerUUID`) VALUES (:PlayerName, :PlayerUUID)")
    void insertUser(@Bind("PlayerName") String playerName, @Bind("PlayerUUID") String playerUUID);

    @SqlUpdate("UPDATE `User` SET `PlayerName` = :PlayerName, `Kills` = :Kills,"
        + " `Deaths` = :Deaths, `Rounds` = :Rounds, `Wins` = :Wins,"
        + " `Points` = :Points WHERE `PlayerUUID` = :PlayerUUID")
    void updateUser(@Bind("PlayerName") String playerName, @Bind("Kills") int kills, @Bind("Deaths") int deaths,
        @Bind("Rounds") int rounds, @Bind("Wins") int wins, @Bind("Points") int points,
        @Bind("PlayerUUID") String plyerUUID);

    @SqlQuery("SELECT * FROM `User` WHERE `PlayerUUID` = :PlayerUUID")
    @RegisterMapper(UserMapper.class)
    User getUserByUUID(@Bind("PlayerUUID") String playerUUID);

    @SqlQuery("SELECT * FROM `User` WHERE `PlayerName` = :PlayerName")
    @RegisterMapper(UserMapper.class)
    User getUserByName(@Bind("PlayerName") String playerName);

    @SqlQuery("SELECT * FROM `User`")
    @RegisterMapper(UserMapper.class)
    List<User> getUsers();

    /**
     * close with no args is used to close the connection
     */
    void close();
}
