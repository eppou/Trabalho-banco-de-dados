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

    @Override
    public CrimesBrutaisDAO getCrimesBrutaisDAO() {
        return new PgCrimesBrutaisDAO(connection);
    }

    @Override
    public OutrosCrimesDAO getOutrosCrimesDAO() {
        return new PgOutrosCrimesDAO(connection);
    }

    @Override
    public CidadeDAO getCidadeDAO() {
        return new PgCidadeDAO(connection);
    }

    @Override
    public EstadoDAO getEstadoDAO() {
        return new PgEstadoDAO(connection);
    }

    @Override
    public PaisDAO getPaisDAO() {
        return new PgPaisDAO(connection);
    }
}