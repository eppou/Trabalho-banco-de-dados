package com.uel.sistema_analise_crimes.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uel.sistema_analise_crimes.DAO.CrimesDAO;
import com.uel.sistema_analise_crimes.DAO.DAO;
import com.uel.sistema_analise_crimes.DAO.DAOFactory;
import com.uel.sistema_analise_crimes.models.Crimes;
import com.uel.sistema_analise_crimes.models.CrimesBrutais;
import com.uel.sistema_analise_crimes.models.OutrosCrimes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CrimesController {

    @GetMapping("/crimes-list")
    @ResponseBody
    public List<Crimes> listarCrimes(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) throws Exception {
        List<Crimes> crimes = new ArrayList<>();
        DAO<Crimes> crimesdao;

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            crimesdao = daoFactory.getCrimesDAO();

            crimes = crimesdao.getAll();

            int startIndex = page * pageSize;
            int endIndex = Math.min(startIndex + pageSize, crimes.size());

            crimes = crimes.subList(startIndex, endIndex);

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

        return crimes;
    }

    @PostMapping("/upload-crimes")
    @ResponseBody
    public void criarNovoCrime(@RequestBody String jsonString) throws Exception {

        DAO<Crimes> crimesdao;
        String formatoString = "yyyy-MM";
        SimpleDateFormat formato = new SimpleDateFormat(formatoString);
        ObjectMapper objectMapper = new ObjectMapper();

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            crimesdao = daoFactory.getCrimesDAO();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonString);

                Crimes crime = new Crimes();

                // Atribuir valores manualmente
                crime.setNome_pais(jsonNode.get("pais").asText());
                crime.setTipo(jsonNode.get("tipo").asText());
                crime.setDescricao(jsonNode.get("descricao").asText());
                crime.setNome_cidade(jsonNode.get("cidade").asText());
                crime.setLatitude((float) jsonNode.get("latitude").asDouble());
                crime.setData_crime(formato.parse(jsonNode.get("data").asText()));
                crime.setLongitude((float) jsonNode.get("longitude").asDouble());
                crime.setNome_estado(jsonNode.get("estado").asText());
                crime.setSigla_estado(jsonNode.get("sigla_estado").asText());

                crimesdao.create(crime);

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

    @PostMapping("/upload-json")
    @ResponseBody
    public void processarUpload(@RequestParam("jsonFile") MultipartFile jsonFile) throws Exception {
        int id;
        CrimesDAO crimesdao;
        DAO< CrimesBrutais> crimesbrutaisdao;
        DAO< OutrosCrimes> outroscrimesdao;
        String formatoString = "yyyy-MM";
        SimpleDateFormat formato = new SimpleDateFormat(formatoString);
        ObjectMapper objectMapper = new ObjectMapper();

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {

            crimesdao = daoFactory.getCrimesDAO();
            crimesbrutaisdao = daoFactory.getCrimesBrutaisDAO();
            outroscrimesdao = daoFactory.getOutrosCrimesDAO();

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
                    if(crimesdao.getId(crime)!=-1) {
                        if (crimeNode.get("grave").asInt() == 0) {
                            OutrosCrimes outroscrimes = new OutrosCrimes(crimesdao.getId(crime));
                            outroscrimesdao.create(outroscrimes);

                        } else {
                            CrimesBrutais crimesBrutais = new CrimesBrutais(crimesdao.getId(crime), crimeNode.get("n_vitimas").asInt());
                            crimesbrutaisdao.create(crimesBrutais);
                        }
                    }
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