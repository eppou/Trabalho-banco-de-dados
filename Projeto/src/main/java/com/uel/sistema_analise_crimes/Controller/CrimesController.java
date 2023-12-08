package com.uel.sistema_analise_crimes.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uel.sistema_analise_crimes.DAO.CrimesDAO;
import com.uel.sistema_analise_crimes.DAO.DAO;
import com.uel.sistema_analise_crimes.DAO.DAOFactory;
import com.uel.sistema_analise_crimes.models.Cidade;
import com.uel.sistema_analise_crimes.models.Crimes;
import com.uel.sistema_analise_crimes.models.CrimesBrutais;
import com.uel.sistema_analise_crimes.models.OutrosCrimes;
import com.uel.sistema_analise_crimes.Controller.CidadeController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Map;

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
                crime.setNome_pais(jsonNode.get("nome_pais").asText());
                crime.setTipo(jsonNode.get("tipo").asText());
                crime.setDescricao(jsonNode.get("descricao").asText());
                crime.setNome_cidade(jsonNode.get("nome_cidade").asText());
                crime.setLatitude((float) jsonNode.get("latitude").asDouble());
                crime.setData_crime(formato.parse(jsonNode.get("data_string").asText()));
                crime.setLongitude((float) jsonNode.get("longitude").asDouble());
                crime.setNome_estado(jsonNode.get("nome_estado").asText());
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

    @PostMapping("/upload-crime-city")
    @ResponseBody
    public void processarUploadCity(@RequestParam("cidade") String cidade) throws Exception {
        System.out.println(cidade);
        String poly = "";
        int ano = 2020;
        int mes = 1;

        Cidade city = CidadeController.getCidade(cidade);

        if (cidade.equals("Londres")) {
            poly = "51.55,-0.20:51.50,-0.037:51.49,-0.11";
        } else if (cidade.equals("Leeds")) {
            poly = "53.86,-1.66:53.77,-1.40:53.76,-1.55";
        } else if (cidade.equals("Liverpool")) {
            poly = "53.40,-2.90:53.49,-2.82:53.35,-3.005";
        }else if (cidade.equals("Sheffield")) {
            poly = "53.35,-1.53:53.43,-1.47:53.347644,-1.388335";
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 13; j++){

                String data = Integer.toString(ano) + "-" + ((mes < 10) ? "0" + Integer.toString(mes) : Integer.toString(mes));
                System.out.println(data);
                String CrimeGeral = getAPIResponse("https://data.police.uk/api/crimes-street/all-crime?poly=" + poly + "&date=" + data);

                CrimesDAO crimesdao;
                DAO<CrimesBrutais> crimesbrutaisdao;
                DAO<OutrosCrimes> outroscrimesdao;
                String formatoString = "yyyy-MM";
                SimpleDateFormat formato = new SimpleDateFormat(formatoString);
                ObjectMapper objectMapper = new ObjectMapper();

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {

                    crimesdao = daoFactory.getCrimesDAO();
                    crimesbrutaisdao = daoFactory.getCrimesBrutaisDAO();
                    outroscrimesdao = daoFactory.getOutrosCrimesDAO();

                    try {
                        JsonNode jsonNode = objectMapper.readTree(CrimeGeral);

                        for (JsonNode crimeNode : jsonNode) {
                            Crimes crime = new Crimes();

                            crime.setNome_pais(city.getNome_pais());
                            crime.setNome_cidade(cidade);

                            crime.setTipo(tratamentoTipoCrime(crimeNode.get("category").asText()));

                            crime.setDescricao(crimeNode.get("category").asText() +" "+ crimeNode.get("location").get("street").get("name").asText());

                            crime.setLatitude((float) crimeNode.get("location").get("latitude").asDouble());

                            crime.setData_crime(formato.parse(data));

                            crime.setLongitude((float) crimeNode.get("location").get("longitude").asDouble());

                            crime.setNome_estado(city.getNome_estado());
                            crime.setSigla_estado(city.getSigla_estado());

                            crimesdao.create(crime);
                            if (crimesdao.getId(crime) != -1) {
                                if (crime.getTipo().equals("crime violento")) {
                                    CrimesBrutais crimesBrutais = new CrimesBrutais(crimesdao.getId(crime), -1);
                                    crimesbrutaisdao.create(crimesBrutais);

                                } else {
                                    OutrosCrimes outroscrimes = new OutrosCrimes(crimesdao.getId(crime));
                                    outroscrimesdao.create(outroscrimes);
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
                mes++;
            }

            mes =1;
            ano++;
        }
    }

    public String tratamentoTipoCrime(String tipo){
        String newTipo;

        if ("criminal-damage-arson".equals(tipo) || "violent-crime".equals(tipo)) {
            newTipo = "crime violento";

        } else if ("anti-social-behaviour".equals(tipo)) {
            newTipo = "danos morais";

        } else if ("bicycle-theft".equals(tipo) || "robbery".equals(tipo) || "theft-from-the-person".equals(tipo)) {
            newTipo = "roubo";

        } else if ("burglary".equals(tipo)|| "other-theft".equals(tipo) || "shoplifting".equals(tipo)) {
            newTipo = "furto";

        } else if ("drugs".equals(tipo)) {
            newTipo = "drogas";

        } else if ("possession-of-weapons".equals(tipo)) {
            newTipo = "posse de armas";

        } else if ("public-order".equals(tipo)) {
            newTipo = "violação da ordem publica";

        }  else if ("vehicle-crime".equals(tipo)) {
            newTipo = "roubo de veiculo";

        } else if ("other-crime".equals(tipo)) {
            newTipo = "outros";
        } else {
            newTipo = "desconhecido";
        }

        return newTipo;
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

    @GetMapping("/ocorrencias_por_cidade")
    @ResponseBody
    public List<Map<String, Object>> getOcorrenciasPorCidade() throws Exception {

        CrimesDAO crimesdao;

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            crimesdao = daoFactory.getCrimesDAO();
            List<Map<String, Object>> ocorrencias = crimesdao.get_todas_ocorrencias_crimes();
            return ocorrencias;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/relatorio_crime_por_populacao")
    @ResponseBody
    public List<Map<String, Object>> getRelatorioCrimePopulcao() throws Exception {

        CrimesDAO crimesdao;

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            crimesdao = daoFactory.getCrimesDAO();
            List<Map<String, Object>> ocorrencias = crimesdao.get_crimes_por_populacao();
            return ocorrencias;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/relatorio_crime_rpc_cidade")
    @ResponseBody
    public List<Map<String, Object>> getRelatorioCrimeRendaPerCapita() throws Exception {

        CrimesDAO crimesdao;

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            crimesdao = daoFactory.getCrimesDAO();
            List<Map<String, Object>> ocorrencias = crimesdao.get_crimes_rpc_cidade();
            return ocorrencias;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/relatorio_por_cidade")
    @ResponseBody
    public List<Map<String, Object>> getRelatorioPorCidade(@RequestParam("nome_cidade") String cidade) throws SQLException, IOException, ClassNotFoundException {

        CrimesDAO crimesdao;
        System.out.println(cidade);

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            crimesdao = daoFactory.getCrimesDAO();
            List<Map<String, Object>> ocorrencias = crimesdao.get_Ocorrencias_por_cidade(cidade);

            return ocorrencias;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





        public String getAPIResponse(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Retorna a resposta como uma string JSON
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



}
