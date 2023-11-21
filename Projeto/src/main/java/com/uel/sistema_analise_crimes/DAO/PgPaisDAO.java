package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.Estado;
import com.uel.sistema_analise_crimes.models.Pais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgPaisDAO implements PaisDAO{

    private final Connection connection;
    public PgPaisDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_PAIS = "INSERT INTO crimes_db.pais(nome, area, populacao, idh, rpc_pais)" + "VALUES (?,?,?,?,?)";
    private static final String GET_PAIS = "SELECT * FROM crimes_db.pais  WHERE nome = ?";
    private static final String GET_ALL_PAISES = "SELECT * FROM crimes_db.pais";
    private static final String UPDATE_PAIS = "UPDATE * FROM crimes_db.pais SET area=?, populacao=?, idh=?, rpc_pais=? WHERE nome = ?";
    private static final String DELETE_PAIS = "DELETE * FROM crimes_db.pais  WHERE nome = ?";

    @Override
    public void create(Pais object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_PAIS)) {

            statement.setString(1, object.getPais());
            statement.setFloat(2, object.getArea());
            statement.setInt(3, object.getPopulacao());
            statement.setFloat(4, object.getIdh());
            statement.setFloat(5, object.getRpc_pais());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar pais");
        }
    }

    @Override
    public Pais get(Object key) throws SQLException {
        Pais pais = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_PAIS)) {
            statement.setString(1, (String)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                pais = new Pais(statement.getResultSet().getString("nome"), statement.getResultSet().getFloat("area"), statement.getResultSet().getInt("populacao"), statement.getResultSet().getFloat("idh"), statement.getResultSet().getFloat("rpc_pais"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de pais");
        }

        return pais;
    }

    @Override
    public List<Pais> getAll() throws SQLException {
        List<Pais> paises = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_PAISES)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                paises.add(new Pais(statement.getResultSet().getString("nome"), statement.getResultSet().getFloat("area"), statement.getResultSet().getInt("populacao"), statement.getResultSet().getFloat("idh"), statement.getResultSet().getFloat("rpc_pais")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de paises");
        }

        return paises;
    }

    @Override
    public void update(Pais object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PAIS)) {

            statement.setString(1, object.getPais());
            statement.setFloat(2, object.getArea());
            statement.setInt(3, object.getPopulacao());
            statement.setFloat(4, object.getIdh());
            statement.setFloat(5, object.getRpc_pais());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao atualizar pais");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PAIS)) {
            statement.setString(1, (String)key);

            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao deletar estado");
        }
    }
}