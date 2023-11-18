package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.OutrosCrimes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PgOutrosCrimesDAO implements OutrosCrimesDAO{

    private final Connection connection;
    public PgOutrosCrimesDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_OUTRO_CRIME = "INSERT INTO crimes_db.outros_crimes(id_crime)" + "VALUES (?)";
    private static final String GET_OUTRO_CRIME = "SELECT * FROM crimes_db.outros_crimes  WHERE id_crime = ?";
    private static final String GET_ALL_OUTRO_CRIME = "SELECT * FROM crimes_db.outros_crimes";
    private static final String UPDATE_OUTRO_CRIME = "UPDATE * FROM crimes_db.outros_crimes WHERE id_crime = ?";
    private static final String DELETE_OUTRO_CRIME = "DELETE * FROM crimes_db.outros_crimes  WHERE id_crime = ?";


    @Override
    public void create(OutrosCrimes object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_OUTRO_CRIME)) {

            statement.setInt(1, object.getId_crime());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no create de outro crime");
        }

    }

    @Override
    public OutrosCrimes get(Object key) throws SQLException {
        OutrosCrimes crime = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_OUTRO_CRIME)) {
            statement.setInt(1, (int)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                crime = new OutrosCrimes(statement.getResultSet().getInt("id_crime"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de outro crime");
        }

        return crime;
    }

    @Override
    public List<OutrosCrimes> getAll() throws SQLException {
        List<OutrosCrimes> outroscrimesList= new ArrayList<OutrosCrimes>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_OUTRO_CRIME)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                OutrosCrimes crime = new OutrosCrimes(statement.getResultSet().getInt("id_crime"));
                outroscrimesList.add(crime);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de outros crimes");
        }

        return outroscrimesList;
    }

    @Override
    public void update(OutrosCrimes object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_OUTRO_CRIME)) {
            statement.setInt(1, object.getId_crime());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no update de outro crime");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_OUTRO_CRIME)) {
            statement.setInt(1, (int)key);
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete de outro crime");
        }
    }
}
