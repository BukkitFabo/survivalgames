package de.debitorlp.server.survivalgames.database.tables.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.debitorlp.server.survivalgames.database.tables.User;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet rs, StatementContext statementctx) throws SQLException {
        return new User(rs.getString("PlayerName"),
            UUID.fromString(rs.getString("PlayerUUID")),
            rs.getInt("Kills"),
            rs.getInt("Deaths"),
            rs.getInt("Rounds"),
            rs.getInt("Wins"),
            rs.getInt("Points"));
    }

}
