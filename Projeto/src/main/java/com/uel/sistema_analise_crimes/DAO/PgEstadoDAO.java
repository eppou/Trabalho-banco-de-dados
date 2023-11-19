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

    private static final String CREATE_ESTADO = "INSERT INTO crimes_db.estado(id_estado, nome_estado, sigla_estado, nome_pais, area_estado, populacao_estado, rpc_estado)" + "VALUES (?,?,?,?,?,?,?)";
    private static final String GET_ESTADO = "SELECT * FROM crimes_db.estado  WHERE id_estado = ?";
    private static final String GET_ALL_ESTADOS = "SELECT * FROM crimes_db.estado";
    private static final String UPDATE_ESTADO = "UPDATE * FROM crimes_db.estado SET nome_estado=?, sigla_estado=?, nome_pais=?, area_estado=?, populacao_estado=?, rpc_estado=? WHERE id_estado = ?";
    private static final String DELETE_ESTADO = "DELETE * FROM crimes_db.estado  WHERE id_estado = ?";

    @Override
    public void create(Estado object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ESTADO)) {

            statement.setInt(1, object.getId_estado());
            statement.setString(2, object.getNome_estado());
            statement.setString(3, object.getSigla_estado());
            statement.setString(4, object.getNome_pais());
            statement.setFloat(5, object.getArea_estado());
            statement.setInt(6, object.getPopulacao_estado());
            statement.setFloat(7, object.getRpc_estado());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar estado");
        }
    }

    @Override
    public Estado get(Object key) throws SQLException {
        Estado estado = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_ESTADO)) {
            statement.setInt(1, (int)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                estado = new Estado(statement.getResultSet().getInt("sigla"), statement.getResultSet().getString("nome_pais"));
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
                estados.add(new Estado(statement.getResultSet().getInt("sigla"), statement.getResultSet().getString("nome_pais")));
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
            statement.setString(1, object.getNome_estado());
            statement.setString(2, object.getSigla_estado());
            statement.setString(3, object.getNome_pais());
            statement.setFloat(4, object.getArea_estado());
            statement.setInt(5, object.getPopulacao_estado());
            statement.setFloat(6, object.getRpc_estado());
            statement.setInt(7, object.getId_estado());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao atualizar estado");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ESTADO)) {
            statement.setInt(1, (int)key);
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao deletar estado");
        }
    } 
}