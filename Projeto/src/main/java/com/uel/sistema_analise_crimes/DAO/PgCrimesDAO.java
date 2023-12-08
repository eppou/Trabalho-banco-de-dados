package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.Crimes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PgCrimesDAO implements CrimesDAO {

    private final Connection connection;

    public PgCrimesDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_CRIMES = "INSERT INTO crime_db.crimes(tipo,descricao,data_crime,latitude, longitude,nome_cidade,nome_estado, sigla_estado, nome_pais)" +
            "VALUES (?,?,?,?,?,?,?,?,?)";

    private static final String GET_CRIMES = "SELECT * FROM crime_db.crimes  WHERE id_crime = ?";
    private static final String GET_ALL_CRIMES = "SELECT * FROM crime_db.crimes";
    private static final String UPDATE_CRIMES = "UPDATE FROM crime_db.crimes SET descricao=?, WHERE id_crime = ?";
    private static final String DELETE_CRIMES = "DELETE FROM crime_db.crimes  WHERE id_crime = ?";
    private static final String GET_CURRENT_ID = "SELECT currval('crime_db.id_crime_seq')";

    private static final String GET_ALL_CRIMES_BY_CITIES = "SELECT nome_cidade, COUNT(*) AS total_crimes FROM crime_db.crimes GROUP BY nome_cidade;";

    private static final String GET_CRIMES_POR_TIPO_E_CIDADES = "SELECT nome_cidade, tipo,  COUNT(*) as total_crimes FROM crime_db.crimes where nome_cidade = ? GROUP BY nome_cidade, tipo;";

    private static final String GET_CRIMES_POR_POPULACAO = "SELECT c.nome_cidade, " +
            "COUNT(*) AS total_crimes, cidade.populacao_cidade, " +
            "(COUNT(*) / CAST(cidade.populacao_cidade AS FLOAT)) AS taxa_crimes_por_pessoa " +
            "FROM crime_db.crimes c " +
            "JOIN crime_db.cidade ON c.nome_cidade = cidade.nome G" +
            "ROUP BY c.nome_cidade, cidade.populacao_cidade;";

    private static final String GET_CRIMES_RPC_CIDADE = "SELECT c.nome_cidade, COUNT(*) AS total_crimes, cidade.rpc_cidade FROM crime_db.crimes c JOIN crime_db.cidade ON c.nome_cidade = cidade.nome GROUP BY c.nome_cidade, cidade.rpc_cidade;";


    @Override
    public void create(Crimes crimes) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(CREATE_CRIMES)) {

            //statement.setInt(1, crimes.getId_crime());
            statement.setString(1, crimes.getTipo());
            statement.setString(2, crimes.getDescricao());
            statement.setDate(3, new java.sql.Date(crimes.getData_crime().getTime()));
            statement.setFloat(4, crimes.getLatitude());
            statement.setFloat(5, crimes.getLongitude());
            statement.setString(6, crimes.getNome_cidade());
            statement.setString(7, crimes.getNome_estado());
            statement.setString(8, crimes.getSigla_estado());
            statement.setString(9, crimes.getNome_pais());

            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar crime");
        }
    }

    @Override
    public Crimes get(Object key) throws SQLException {
        Crimes crimes = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_CRIMES)) {
            statement.setInt(1, (int) key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                crimes = new Crimes();
                //crimes.setId_crime(statement.getResultSet().getInt("id_crime"));
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
    public List<Crimes> getAll() throws SQLException {
        List<Crimes> crimesList = new ArrayList<Crimes>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CRIMES)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                Crimes crimes = new Crimes();
                //crimes.setId_crime(statement.getResultSet().getInt("id_crime"));
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
    public void update(Crimes object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CRIMES)) {
            statement.setString(1, object.getDescricao());
            //statement.setInt(2, object.getId_crime());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no update crime");
        }
    }


    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CRIMES)) {
            statement.setInt(1, (int) key);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete crime");
        }
    }

    @Override
    public int getId(Crimes crimes) {

        try (PreparedStatement statement = connection.prepareStatement(GET_CURRENT_ID)) {

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return -1;
    }

    @Override
    public List<Map<String, Object>> get_todas_ocorrencias_crimes() throws SQLException {
        List<Map<String, Object>> ocorrencias = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CRIMES_BY_CITIES)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> ocorrencia = new HashMap<>();
                ocorrencia.put("nome_cidade", resultSet.getString("nome_cidade"));
                ocorrencia.put("total_crimes", resultSet.getInt("total_crimes"));
                ocorrencias.add(ocorrencia);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return ocorrencias;
        }

    @Override
    public List<Map<String, Object>> get_Ocorrencias_por_cidade(String cidade) {
        List<Map<String, Object>> ocorrenciasList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_CRIMES_POR_TIPO_E_CIDADES)) {
            statement.setString(1, cidade);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> ocorrencia = new HashMap<>();
                ocorrencia.put("nome_cidade", resultSet.getString("nome_cidade"));
                ocorrencia.put("tipo", resultSet.getString("tipo"));
                ocorrencia.put("total_crimes", resultSet.getInt("total_crimes"));
                ocorrenciasList.add(ocorrencia);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return ocorrenciasList;
    }

    @Override
    public List<Map<String, Object>> get_crimes_por_populacao() throws SQLException {
        List<Map<String, Object>> ocorrencias = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_CRIMES_POR_POPULACAO)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> ocorrencia = new HashMap<>();
                ocorrencia.put("nome_cidade", resultSet.getString("nome_cidade"));
                ocorrencia.put("total_crimes", resultSet.getInt("total_crimes"));
                ocorrencia.put("populacao_cidade", resultSet.getInt("populacao_cidade"));
                ocorrencia.put("taxa_crimes_por_pessoa", resultSet.getFloat("taxa_crimes_por_pessoa"));
                ocorrencias.add(ocorrencia);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return ocorrencias;
    }

    @Override
    public List<Map<String, Object>> get_crimes_rpc_cidade() throws SQLException {
        List<Map<String, Object>> ocorrencias = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_CRIMES_RPC_CIDADE)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> ocorrencia = new HashMap<>();
                ocorrencia.put("nome_cidade", resultSet.getString("nome_cidade"));
                ocorrencia.put("total_crimes", resultSet.getInt("total_crimes"));
                ocorrencia.put("rpc_cidade", resultSet.getFloat("rpc_cidade"));
                ocorrencias.add(ocorrencia);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return ocorrencias;
    }
}
