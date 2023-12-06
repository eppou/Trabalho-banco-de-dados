package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgEstadoDAO implements EstadoDAO{

    private final Connection connection;
    public PgEstadoDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_ESTADO = "INSERT INTO crime_db.estado(nome, sigla, nome_pais, rpc_estado,area_estado, populacao_estado)" + "VALUES (?,?,?,?,?,?)";
    private static final String GET_ESTADO = "SELECT * FROM crime_db.estado  WHERE sigla = ? AND nome_pais = ?";
    private static final String GET_ALL_ESTADOS = "SELECT * FROM crime_db.estado";
    private static final String UPDATE_ESTADO = "UPDATE * FROM crime_db.estado SET nome=?, sigla=?, nome_pais=?, area_estado=?, populacao_estado=?, rpc_estado=? WHERE sigla_estado = ? AND nome_pais = ?";
    private static final String DELETE_ESTADO = "DELETE * FROM crime_db.estado  WHERE sigla = ? AND nome_pais = ?";

    @Override
    public void create(Estado object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ESTADO)) {

            statement.setString(2, object.getSigla());
            statement.setString(1, object.getNome());
            statement.setString(3, object.getNome_pais());
            statement.setFloat(5, object.getArea_estado());
            statement.setInt(6, object.getPopulacao_estado());
            statement.setFloat(4, object.getRpc_estado());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar estado");
        }
    }

    @Override
    public Estado get(Object key) throws SQLException {

        if (!(key instanceof Object[])) {
            // Trate o erro de tipo aqui, se necessário
            throw new IllegalArgumentException("A chave deve ser um array com dois elementos");
        }

        Object[] keyArray = (Object[]) key;

        if (keyArray.length != 2) {
            throw new IllegalArgumentException("O array de chave deve conter exatamente dois elementos");
        }

        String sigla = (String) keyArray[0];
        String nomePais = (String) keyArray[1];


        Estado estado = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_ESTADO)) {
            statement.setString(1, sigla);
            statement.setString(2, nomePais);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                estado = new Estado(statement.getResultSet().getString("sigla"),statement.getResultSet().getString("nome"), statement.getResultSet().getString("nome_pais"), statement.getResultSet().getFloat("rpc_estado"),statement.getResultSet().getFloat("area_estado"),statement.getResultSet().getInt("populacao_estado"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de estado");
        }

        return estado;
    }

    @Override
    public List<Estado> getAll() throws SQLException {
        List<Estado> estados = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ESTADOS)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                estados.add(new Estado(statement.getResultSet().getString("sigla"),statement.getResultSet().getString("nome"), statement.getResultSet().getString("nome_pais"), statement.getResultSet().getFloat("rpc_estado"),statement.getResultSet().getFloat("area_estado"),statement.getResultSet().getInt("populacao_estado")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao listar estados");
        }

        return estados;
    }

    @Override
    public void update(Estado object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ESTADO)) {
            statement.setString(1, object.getNome());
            statement.setString(2, object.getSigla());
            statement.setString(3, object.getNome_pais());
            statement.setFloat(4, object.getArea_estado());
            statement.setInt(5, object.getPopulacao_estado());
            statement.setFloat(6, object.getRpc_estado());
            statement.setString(7, object.getSigla());
            statement.setString(8, object.getNome_pais());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao atualizar estado");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ESTADO)) {
            statement.setString(1, ((Estado) key).getSigla());
            statement.setString(1, ((Estado) key).getNome_pais());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao deletar estado");
        }
    }
}