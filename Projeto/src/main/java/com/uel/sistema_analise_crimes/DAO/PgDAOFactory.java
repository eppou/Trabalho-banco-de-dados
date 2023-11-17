package com.uel.sistema_analise_crimes.DAO;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CrimesDAO getCrimesDAO() {
        return new PgCrimesDAO(connection);
    }
}