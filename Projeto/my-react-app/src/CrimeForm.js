import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";


const CrimeForm = () => {
  const [descricao, setDescricao] = useState("");
  const [tipoCrime, setTipoCrime] = useState("");
  const [dataCrime, setDataCrime] = useState("");
  const [cidade, setCidade] = useState("");
  const [estado, setEstado] = useState("");
  const [pais, setPais] = useState("");
  const [jsonFile, setJsonFile] = useState(null);
  const [sigla, setSigla] = useState("");

  const sendDataToServer = () => {
    // Criar um objeto com a estrutura desejada
    const crimeData = {
      tipo: tipoCrime,
      descricao: descricao,
      data_string:  dataCrime ? new Date(dataCrime) : null, // Certifique-se de converter a data para o formato desejado
      latitude: 0, // Preencha com os valores apropriados
      longitude: 0, // Preencha com os valores apropriados
      nome_cidade: cidade,
      nome_estado: estado,
      sigla_estado: sigla,
      nome_pais: pais,
    };

    // Enviar os dados para o servidor
    axios.post('http://localhost:8080/api/upload-crimes', JSON.stringify(crimeData), {
      headers: {
        'Content-Type': 'application/json',
      },
    })
        .then(response => {
          console.log('Resposta do servidor:', response.data);
        })
        .catch(error => {
          console.error('Erro na requisição:', error);
          console.error('Dados que não foram enviados:', JSON.stringify(crimeData));
          // Lógica para lidar com erros
        });
  };


  const handleSubmit = () => {
    // Enviar os dados do formulário para um servidor
    const data = {
      descricao,
      tipoCrime,
      dataCrime,
      cidade,
      estado,
      sigla,
      pais,
    };

    sendDataToServer(data);
  };


  const handleJsonSubmit = () => {
    // Enviar o arquivo JSON para o servidor
    if (jsonFile) {
      const formData = new FormData();
      formData.append("jsonFile", jsonFile);

      fetch("http://localhost:8080/api/upload-json", {
        method: "POST",
        body: formData,
      })
          .then((response) => response.json())
          .then((data) => {
            console.log(data);
            // Lógica adicional após o envio bem-sucedido
          })
          .catch((error) => {
            console.error("Erro ao enviar arquivo JSON:", error);
            // Lógica adicional em caso de erro
          });
    }
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setJsonFile(file);
  };

  const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '130vh',
    backgroundColor: '#001f3f', // Azul marinho
    color: '#ffdc00', // Amarelo
  };

  const headerStyle = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: '100%',
    padding: '10px',
    color: '#ffdc00', // Amarelo
  };

  const formStyle = {
    width: '300px', // Ajuste o tamanho conforme necessário
    margin: '20px',
  };

  const labelStyle = {
    marginBottom: '8px',
  };

  const inputStyle = {
    padding: '10px 20px',
    fontSize: '16px',
    backgroundColor: '#ffdc00', // Amarelo
    color: '#001f3f', // Azul marinho
    borderRadius: '5px',
    cursor: 'pointer',
  };

  const buttonStyle = {
    padding: '8px',
    marginBottom: '16px',
    width: '100%',
    fontSize: '16px',
    backgroundColor: '#ffdc00', // Amarelo
    color: '#001f3f', // Azul marinho
    borderRadius: '5px',
    cursor: 'pointer',
  };

  const selectStyle = {
    padding: '8px',
    marginBottom: '16px',
    width: '100%',
    fontSize: '16px',
    backgroundColor: '#ffdc00', // Amarelo
    color: '#001f3f', // Azul marinho
    borderRadius: '5px',
    cursor: 'pointer',
  };

  const linkStyle = {
    textDecoration: 'none',
    color: '#ffdc00', // Amarelo
  };




  return (
      <div style={containerStyle}>
        <div style={headerStyle}>
          <Link to="/" style={linkStyle}>
            <img
                src="/home_logo.png"
                alt="Home"
                style={{ width: '50px', height: '50px', cursor: 'pointer' }}
            />
          </Link>
          <h1>Formulário de Crimes</h1>
        </div>
        <div style={formStyle}>
          <label style={labelStyle}>Descrição</label>
          <input
              type="text"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
              style={inputStyle}
          />
          <br />
          <label style={labelStyle}>Tipo de Crime</label>
          <select
              value={tipoCrime}
              onChange={(e) => setTipoCrime(e.target.value)}
              style={selectStyle}
          >
            <option value="crime com assasinato">Crime com assasinato</option>
            <option value="crime sexual">Crime sexual</option>
            <option value="assedio sexual">Assédio sexual</option>
            <option value="furto">Furto</option>
            <option value="roubo">Roubo</option>
            <option value="roubo de veiculo">Roubo de veiculo</option>
            <option value="crimes de transito">Crimes de transito</option>
            <option value="danos morais">Danos morais</option>
            <option value="violacao da ordem publica">Violação da ordem publica</option>
            <option value="roubo a loja ou instituicao">Roubo a loja ou instituicao</option>
            <option value="posse de arma">Posse de arma</option>
            <option value="drogas">Drogas</option>
            <option value="crimes violento">Crimes violento</option>
            <option value="outros">Outros</option>
          </select>
          <br />
          <label style={labelStyle}>Data do Crime</label>
          <br />
          <input
              type="date"
              value={dataCrime}
              onChange={(e) => setDataCrime(e.target.value)}
              style={inputStyle}
          />
          <br />
          <label style={labelStyle}>Cidade</label>
          <br />
          <input
              type="text"
              value={cidade}
              onChange={(e) => setCidade(e.target.value)}
              style={inputStyle}
          />
          <br />
          <label style={labelStyle}>Estado</label>
          <br />
          <input
              type="text"
              value={estado}
              onChange={(e) => setEstado(e.target.value)}
              style={inputStyle}
          />
          <br />
          <br />
          <label style={labelStyle}>Sigla Estado</label>
          <br />
          <input
              type="text"
              value={sigla}
              onChange={(e) => setSigla(e.target.value)}
              style={inputStyle}
          />
          <br />
          <label style={labelStyle}>País</label>
          <br />
          <input
              type="text"
              value={pais}
              onChange={(e) => setPais(e.target.value)}
              style={inputStyle}
          />
          <br />
          <br />
          <button onClick={handleSubmit} style={buttonStyle}>
            Enviar
          </button>
          <br />
          <br />
          <label style={labelStyle}>Ou envie Arquivo JSON com os relatos</label>
          <input
              type="file"
              accept=".json"
              onChange={handleFileChange}
              style={inputStyle}
          />
          <br />
          <br />
          <button onClick={handleJsonSubmit} style={buttonStyle}>
            Enviar JSON
          </button>

        </div>
      </div>
  );
};

export default CrimeForm;
