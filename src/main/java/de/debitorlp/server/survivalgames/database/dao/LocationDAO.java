package de.debitorlp.server.survivalgames.database.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import de.debitorlp.server.survivalgames.database.tables.Location;
import de.debitorlp.server.survivalgames.database.tables.mapper.LocationMapper;

public interface LocationDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS `Location`("
        + "`Type` VARCHAR(16) NOT NULL,"
        + " `MapName` VARCHAR(32) NOT NULL,"
        + " `Number` INT NOT NULL DEFAULT 1,"
        + " `World` VARCHAR(64) NOT NULL,"
        + " `X` DOUBLE NOT NULL,"
        + " `Y` DOUBLE NOT NULL,"
        + " `Z` DOUBLE NOT NULL,"
        + " `Yaw` FLOAT NOT NULL,"
        + " `Pitch` FLOAT NOT NULL)")
    void createTable();

    @SqlUpdate("INSERT INTO `Location`(`Type`, `MapName`, `Number`, `World`, `X`, `Y`, `Z`, `Yaw`, `Pitch`) "
        + "VALUES (:Type, :MapName, :Number, :World, :X, :Y, :Z, :Yaw, :Pitch)")
    void insertLocation(@Bind("Type") String type, @Bind("MapName") String mapName, @Bind("Number") int number,
        @Bind("World") String world, @Bind("X") double xloc, @Bind("Y") double yloc, @Bind("Z") double zloc,
        @Bind("Yaw") float yaw, @Bind("Pitch") float pitch);

    @SqlUpdate("UPDATE `Location` SET `World` = :World, `X` = :X, `Y` = :Y, `Z` = :Z, `Yaw` = :Yaw, `Pitch` = :Pitch "
        + "WHERE (`Type` = :Type AND `MapName` = :MapName AND `Number` = :Number)")
    void updateLocation(@Bind("Type") String type, @Bind("MapName") String mapName, @Bind("Number") int number,
        @Bind("World") String world, @Bind("X") double xloc, @Bind("Y") double yloc, @Bind("Z") double zloc,
        @Bind("Yaw") float yaw, @Bind("Pitch") float pitch);

    @SqlUpdate("DELETE FROM `Location` WHERE (`Type` = :Type AND `MapName` = :MapName AND `Number` = :Number)")
    void deleteLocation(@Bind("Type") String type, @Bind("MapName") String mapName, @Bind("Number") int number);

    @SqlQuery("SELECT * FROM `Location` WHERE (`Type` = :Type AND `MapName` = :MapName AND `Number` = :Number)")
    @RegisterMapper(LocationMapper.class)
    Location getLocation(@Bind("Type") String type, @Bind("MapName") String mapName, @Bind("Number") int number);

    @SqlQuery("SELECT * FROM `Location` WHERE (`Type` = :Type AND `MapName` = :MapName)")
    @RegisterMapper(LocationMapper.class)
    List<Location> getLocations(@Bind("Type") String type, @Bind("MapName") String mapName);

    /**
     * close with no args is used to close the connection
     */
    void close();
}
