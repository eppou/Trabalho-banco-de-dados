package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.Cidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgCidadeDAO implements CidadeDAO{

    private final Connection connection;
    public PgCidadeDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_CIDADE = "INSERT INTO crime_db.cidade(nome, nome_estado, sigla_estado, nome_pais, area_cidade, populacao_cidade, rpc_cidade)" + "VALUES (?,?,?,?,?,?,?)";
    private static final String GET_CIDADE = "SELECT * FROM crime_db.cidade  WHERE nome = ?";
    private static final String GET_ALL_CIDADES = "SELECT * FROM crime_db.cidade";
    private static final String UPDATE_CIDADE = "UPDATE * FROM crime_db.cidade SET nome_cidade=?, estado=? WHERE id_cidade = ?";
    private static final String DELETE_CIDADE = "DELETE * FROM crime_db.cidade  WHERE id_cidade = ?";

    @Override
    public void create(Cidade object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CIDADE)) {

            statement.setString(1, object.getNome());
            statement.setString(2, object.getNome_estado());
            statement.setString(3, object.getSigla_estado());
            statement.setString(4, object.getNome_pais());
            statement.setFloat(5, object.getArea_cidade());
            statement.setInt(6, object.getPopulacao_cidade());
            statement.setFloat(7, object.getRpc_cidade());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar cidade");
        }
    }

    @Override
    public Cidade get(Object key) throws SQLException {
        Cidade cidade = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_CIDADE)) {
            statement.setString(1, key.toString());
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                cidade = new Cidade(statement.getResultSet().getString("nome"),statement.getResultSet().getString("nome_estado"), statement.getResultSet().getString("sigla_estado"), statement.getResultSet().getString("nome_pais"),statement.getResultSet().getFloat("area_cidade"),statement.getResultSet().getInt("populacao_cidade"),statement.getResultSet().getFloat("rpc_cidade"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de cidade");
        }

        return cidade;
    }

    @Override
    public List<Cidade> getAll() throws SQLException {
        List<Cidade> cidadesList= new ArrayList<Cidade>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CIDADES)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                Cidade cidade = new Cidade(statement.getResultSet().getString("nome"),statement.getResultSet().getString("nome_estado"), statement.getResultSet().getString("sigla_estado"), statement.getResultSet().getString("nome_pais"),statement.getResultSet().getFloat("area_cidade"),statement.getResultSet().getInt("populacao_cidade"),statement.getResultSet().getFloat("rpc_cidade"));
                cidadesList.add(cidade);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de todas as cidades");
        }

        return cidadesList;
    }

    @Override
    public void update(Cidade object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CIDADE)) {
            statement.setString(1, object.getNome());
            statement.setString(2, object.getSigla_estado());
            statement.setString(3, object.getNome_pais());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no update de cidade");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CIDADE)) {
            statement.setInt(1, (int)key);
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete de cidade");
        }
    }
}