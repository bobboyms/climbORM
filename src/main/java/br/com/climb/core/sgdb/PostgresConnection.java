package br.com.climb.core.sgdb;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.PersistentEntity;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.jdbcconnection.FactoryJdbcConnection;
import br.com.climb.utils.SqlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static br.com.climb.utils.ReflectionUtil.generateModel;
import static br.com.climb.utils.ReflectionUtil.getTableName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresConnection implements ClimbConnection {

    private static final Logger logger = LogManager.getLogger(FactoryJdbcConnection.class);

    private Connection connection;

    public PostgresConnection(ConfigFile configFile) {
        this.connection = FactoryJdbcConnection.createJdbcConnection(configFile);
    }

    @Override
    public void save(Object object) {

//        PreparedStatement ps = SqlUtil.preparedStatementInsert(this.schema, this.connection, generateModel(object), getTableName(object));
//
//        try {
//            ps.executeUpdate();
//            ResultSet rsID = ps.getGeneratedKeys();
//            if (rsID.next()) {
//                Long id = rsID.getLong("id");
//
//                PersistentEntity pers = (PersistentEntity) object;
//                pers.setId(id);
//            }
//        } catch (SQLException e) {
//            logger.error("context", e);
//        }


    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

    }
}
