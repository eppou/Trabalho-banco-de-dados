package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.CrimesBrutais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgCrimesBrutaisDAO implements CrimesBrutaisDAO{

    private final Connection connection;
    public PgCrimesBrutaisDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_CRIME_BRUTAL = "INSERT INTO crime_db.crimes_brutais(id_crime, num_vitimas)" + "VALUES (?,?)";
    private static final String GET_CRIME_BRUTAL = "SELECT * FROM crime_db.crimes_brutais  WHERE id_crime = ?";
    private static final String GET_ALL_CRIMES_BRUTAIS = "SELECT * FROM crime_db.crimes_brutais";
    private static final String UPDATE_CRIME_BRUTAL = "UPDATE * FROM crime_db.crimes_brutais SET num_vitimas=?, WHERE id_crime = ?";
    private static final String DELETE_CRIME_BRUTAL = "DELETE * FROM crime_db.crimes_brutais  WHERE id_crime = ?";

    @Override
    public void create(CrimesBrutais object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CRIME_BRUTAL)) {

            statement.setInt(1, object.getId_crime());
            statement.setInt(2, object.getNum_vitimas());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no create crime brutal");
        }
    }

    @Override
    public CrimesBrutais get(Object key) throws SQLException {

        CrimesBrutais crime = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_CRIME_BRUTAL)) {
            statement.setInt(1, (int)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                crime = new CrimesBrutais(statement.getResultSet().getInt("id_crime"),
                                        statement.getResultSet().getInt("num_vitimas"));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca do crime brutal");
        }

        return crime;
    }

    @Override
    public List<CrimesBrutais> getAll() throws SQLException {
        List<CrimesBrutais> crimesBrutaisList= new ArrayList<CrimesBrutais>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CRIMES_BRUTAIS)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                CrimesBrutais crime = new CrimesBrutais(statement.getResultSet().getInt("id_crime"),
                        statement.getResultSet().getInt("num_vitimas"));
                crimesBrutaisList.add(crime);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de crimes brutais");
        }

        return crimesBrutaisList;
    }

    @Override
    public void update(CrimesBrutais object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CRIME_BRUTAL)) {
            statement.setInt(1, object.getNum_vitimas());
            statement.setInt(2, object.getId_crime());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no update crime brutal");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CRIME_BRUTAL)) {
            statement.setInt(1, (int)key);
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete crime brutal");
        }
    }
}
