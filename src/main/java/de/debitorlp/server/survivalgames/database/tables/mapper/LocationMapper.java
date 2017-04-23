package de.debitorlp.server.survivalgames.database.tables.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.debitorlp.server.survivalgames.database.tables.Location;
import de.debitorlp.server.survivalgames.enm.Type;

public class LocationMapper implements ResultSetMapper<Location> {

    @Override
    public Location map(int index, ResultSet rs, StatementContext statementctx) throws SQLException {
        return new Location(Type.valueOf(rs.getString("Type")),
            rs.getString("MapName"),
            rs.getInt("Number"),
            Bukkit.getWorld(rs.getString("World")),
            rs.getDouble("X"),
            rs.getDouble("Y"),
            rs.getDouble("Z"),
            rs.getFloat("Yaw"),
            rs.getFloat("Pitch"));
    }

}
