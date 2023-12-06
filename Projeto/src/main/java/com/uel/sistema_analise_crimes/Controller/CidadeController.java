package com.uel.sistema_analise_crimes.Controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uel.sistema_analise_crimes.DAO.CrimesDAO;
import com.uel.sistema_analise_crimes.DAO.DAO;
import com.uel.sistema_analise_crimes.DAO.DAOFactory;
import com.uel.sistema_analise_crimes.models.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CidadeController {

    public static Cidade getCidade(String cidade){

        Cidade cidade3 = null;
        DAO<Cidade> cidadedao;

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            cidadedao = daoFactory.getCidadeDAO();

            cidade3 = cidadedao.get(cidade);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("2");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("3");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("4");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("5");
        }

        return cidade3;
    }

    @GetMapping("/get-cidades")
    @ResponseBody
    public List<Cidade> listarCidades() throws Exception {

        List<Cidade> cidade = new ArrayList<>();
        DAO<Cidade> cidadedao;

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            cidadedao = daoFactory.getCidadeDAO();

            cidade = cidadedao.getAll();;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("2");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("3");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("4");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("5");
        }

        return cidade;
    }

    @PostMapping("/upload-cidade")
    @ResponseBody
    public void criarNovaCidade(@RequestBody String jsonString) throws Exception {

        DAO<Cidade> cidadedao;
        DAO<Estado> estadodao;

        ObjectMapper objectMapper = new ObjectMapper();

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            cidadedao = daoFactory.getCidadeDAO();
            estadodao = daoFactory.getEstadoDAO();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonString);

                Object[] keyArray = new Object[]{jsonNode.get("siglaEstado").asText(), jsonNode.get("nomePais").asText()};
                Estado estado = estadodao.get(keyArray);

                if(estado == null){
                    estado = new Estado(jsonNode.get("siglaEstado").asText(),
                                        jsonNode.get("nomeEstado").asText(),
                                        jsonNode.get("nomePais").asText(),
                                        (float)jsonNode.get("rendaPerCapitaEstado").asDouble(),
                                        (float)jsonNode.get("areaEstado").asDouble(),
                                        jsonNode.get("populacaoEstado").asInt());
                    estadodao.create(estado);
                }


                Cidade city = new Cidade();

                // Atribuir valores manualmente
                city.setNome(jsonNode.get("nome").asText());
                city.setNome_estado(jsonNode.get("nomeEstado").asText());
                city.setNome_pais(jsonNode.get("nomePais").asText());
                city.setSigla_estado(jsonNode.get("siglaEstado").asText());
                city.setRpc_cidade((float)jsonNode.get("rendaPerCapita").asDouble());
                city.setArea_cidade((float)jsonNode.get("areaCidade").asDouble());
                city.setPopulacao_cidade(jsonNode.get("populacaoCidade").asInt());

                cidadedao.create(city);

            } catch (IOException e) {
                e.printStackTrace();
                // Lidar com exceções de leitura do JSON
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("2");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("3");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("4");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("5");
        }
    }
}
