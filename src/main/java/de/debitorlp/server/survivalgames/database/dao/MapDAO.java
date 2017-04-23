package de.debitorlp.server.survivalgames.database.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import de.debitorlp.server.survivalgames.database.tables.Map;
import de.debitorlp.server.survivalgames.database.tables.mapper.MapMapper;

public interface MapDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS `Map`("
        + "`Type` VARCHAR(16) NOT NULL,"
        + " `MapName` VARCHAR(32) NOT NULL PRIMARY KEY,"
        + " `Author` VARCHAR(64) NOT NULL DEFAULT 'Unknown',"
        + " `Size` INT(3) NOT NULL DEFAULT 0)")
    void createTable();

    @SqlUpdate("INSERT IGNORE INTO `Map`(`Type`, `MapName`, `Author`) VALUES (:Type, :MapName, :Author, :Size)")
    void insertMap(@Bind("Type") String type, @Bind("MapName") String mapName, @Bind("Author") String author,
        @Bind("Size") int size);

    @SqlUpdate("UPDATE `Map` SET `MapName` = :MapName, `Author` = :Author, `Size` = :Size")
    void updateMap(@Bind("MapName") String mapName, @Bind("Author") String author, @Bind("Size") int size);

    @SqlQuery("SELECT * FROM `Map` WHERE (`Type` = :Type AND `MapName` = :MapName)")
    @RegisterMapper(MapMapper.class)
    Map getMap(@Bind("Type") String type, @Bind("MapName") String mapName);

    @SqlQuery("SELECT * FROM `Map` WHERE `Type` = :Type")
    @RegisterMapper(MapMapper.class)
    List<Map> getMaps(@Bind("Type") String type);

    /**
     * close with no args is used to close the connection
     */
    void close();
}
