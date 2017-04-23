package de.debitorlp.server.survivalgames.database.tables.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.debitorlp.server.survivalgames.database.tables.Map;
import de.debitorlp.server.survivalgames.enm.Type;

public class MapMapper implements ResultSetMapper<Map> {

    @Override
    public Map map(int index, ResultSet rs, StatementContext statementctx) throws SQLException {
        return new Map(
            Type.valueOf(rs.getString("Type")),
            rs.getString("MapName"),
            rs.getString("Author"),
            rs.getInt("Size"));
    }

}
