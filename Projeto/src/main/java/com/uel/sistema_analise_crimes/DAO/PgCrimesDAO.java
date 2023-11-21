package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.Crimes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgCrimesDAO implements CrimesDAO {

    private final Connection connection;
    public PgCrimesDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_CRIMES = "INSERT INTO crime_db.crimes(id_crime,tipo,descricao,data_crime,latitude, longitude,nome_cidade,nome_estado, sigla_estado, nome_pais)" +
                                                "VALUES (?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_CRIMES = "SELECT * FROM crime_db.crimes  WHERE id_crime = ?";
    private static final String GET_ALL_CRIMES = "SELECT * FROM crime_db.crimes";
    private static final String UPDATE_CRIMES = "UPDATE FROM crime_db.crimes SET descricao=?, WHERE id_crime = ?";
    private static final String DELETE_CRIMES = "DELETE FROM crime_db.crimes  WHERE id_crime = ?";

    @Override
    public void create(Crimes crimes) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(CREATE_CRIMES)) {

            statement.setInt(1, crimes.getId_crime());
            statement.setString(2, crimes.getTipo());
            statement.setString(3, crimes.getDescricao());
            statement.setDate(4, new java.sql.Date(crimes.getData_crime().getTime()));
            statement.setFloat(5, crimes.getLatitude());
            statement.setFloat(6, crimes.getLongitude());
            statement.setString(7, crimes.getNome_cidade());
            statement.setString(8, crimes.getNome_estado());
            statement.setString(9, crimes.getSigla_estado());
            statement.setString(10, crimes.getNome_pais());

            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar crime");
        }
    }

    @Override
    public Crimes get(Object key) throws SQLException{
        Crimes crimes = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_CRIMES)) {
            statement.setInt(1, (int)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                crimes = new Crimes();
                crimes.setId_crime(statement.getResultSet().getInt("id_crime"));
                crimes.setTipo(statement.getResultSet().getString("tipo"));
                crimes.setDescricao(statement.getResultSet().getString("descricao"));
                crimes.setData_crime(statement.getResultSet().getDate("data_crime"));
                crimes.setLatitude(statement.getResultSet().getFloat("latitude"));
                crimes.setLongitude(statement.getResultSet().getFloat("longitude"));
                crimes.setNome_cidade(statement.getResultSet().getString("nome_cidade"));
                crimes.setNome_estado(statement.getResultSet().getString("nome_estado"));
                crimes.setSigla_estado(statement.getResultSet().getString("sigla_estado"));
                crimes.setNome_pais(statement.getResultSet().getString("nome_pais"));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca do crime");
        }

        return crimes;
    }

    @Override
    public List<Crimes> getAll() throws SQLException{
        List<Crimes> crimesList= new ArrayList<Crimes>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CRIMES)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                Crimes crimes = new Crimes();
                crimes.setId_crime(statement.getResultSet().getInt("id_crime"));
                crimes.setTipo(statement.getResultSet().getString("tipo"));
                crimes.setDescricao(statement.getResultSet().getString("descricao"));
                crimes.setData_crime(statement.getResultSet().getDate("data_crime"));
                crimes.setLatitude(statement.getResultSet().getFloat("latitude"));
                crimes.setLongitude(statement.getResultSet().getFloat("longitude"));
                crimes.setNome_cidade(statement.getResultSet().getString("nome_cidade"));
                crimes.setNome_estado(statement.getResultSet().getString("nome_estado"));
                crimes.setSigla_estado(statement.getResultSet().getString("sigla_estado"));
                crimes.setNome_pais(statement.getResultSet().getString("nome_pais"));
                crimesList.add(crimes);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de crimes");
        }

        return crimesList;

    }

    @Override
    public void update(Crimes object) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CRIMES)) {
            statement.setString(1, object.getDescricao());
            statement.setInt(2, object.getId_crime());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no update crime");
        }
    }


    @Override
    public void delete(Object key) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CRIMES)) {
            statement.setInt(1, (int)key);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete crime");
        }
    }
}
