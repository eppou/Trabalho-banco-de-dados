package com.uel.sistema_analise_crimes.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uel.sistema_analise_crimes.DAO.CrimesDAO;
import com.uel.sistema_analise_crimes.DAO.DAO;
import com.uel.sistema_analise_crimes.DAO.DAOFactory;
import com.uel.sistema_analise_crimes.models.Crimes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/api")
public class CrimesController {

    @PostMapping("/crimes")
    @ResponseBody
    public void criarNovoCrime(Crimes crime) {
        DAO<Crimes> crimesDAO;
        try (DAOFactory daoFactory = DAOFactory.getInstance()){

            crimesDAO = daoFactory.getCrimesDAO();
            crimesDAO.create(crime);

            System.out.println("Crime inserido com sucesso!");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.err.println("Erro ao inserir crime no banco de dados: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/upload-json")
    @ResponseBody
    public void processarUpload(@RequestParam("jsonFile") MultipartFile jsonFile) throws Exception {

        DAO<Crimes> crimesdao;
        String formatoString = "yyyy-MM";
        SimpleDateFormat formato = new SimpleDateFormat(formatoString);
        ObjectMapper objectMapper = new ObjectMapper();

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            crimesdao = daoFactory.getCrimesDAO();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonFile.getInputStream());

                for (JsonNode crimeNode : jsonNode) {
                    Crimes crime = new Crimes();

                    // Atribuir valores manualmente

                    crime.setNome_pais(crimeNode.get("pais").asText());

                    crime.setTipo(crimeNode.get("tipo").asText());

                    crime.setDescricao(crimeNode.get("descricao").asText());

                    crime.setNome_cidade(crimeNode.get("cidade").asText());

                    crime.setLatitude((float) crimeNode.get("latitude").asDouble());

                    crime.setData_crime(formato.parse(crimeNode.get("data").asText()));


                    crime.setLongitude((float) crimeNode.get("longitude").asDouble());

                    crime.setNome_estado(crimeNode.get("estado").asText());

                    crime.setSigla_estado(crimeNode.get("sigla_estado").asText());



                    crimesdao.create(crime);
                }



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
