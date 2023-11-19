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

    private static final String CREATE_CIDADE = "INSERT INTO crimes_db.cidade(id_cidade, nome_cidade, estado)" + "VALUES (?,?,?)";
    private static final String GET_CIDADE = "SELECT * FROM crimes_db.cidade  WHERE id_cidade = ?";
    private static final String GET_ALL_CIDADES = "SELECT * FROM crimes_db.cidade";
    private static final String UPDATE_CIDADE = "UPDATE * FROM crimes_db.cidade SET nome_cidade=?, estado=? WHERE id_cidade = ?";
    private static final String DELETE_CIDADE = "DELETE * FROM crimes_db.cidade  WHERE id_cidade = ?";

    @Override
    public void create(CrimesBrutais object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CIDADE)) {

            statement.setInt(1, object.getNome());
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
            statement.setInt(1, (int)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                cidade = new Cidade(statement.getResultSet().getInt("nome"), statement.getResultSet().getString("nome_cidade"), statement.getResultSet().getString("estado"));
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
                Cidade cidade = new Cidade(statement.getResultSet().getInt("nome"), statement.getResultSet().getString("sigla_estado"), statement.getResultSet().getString("nome_pais"));
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
            statement.setInt(1, object.getNome());
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
            statement.setInt(1, int(key));
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete de cidade");
        }
    }
}