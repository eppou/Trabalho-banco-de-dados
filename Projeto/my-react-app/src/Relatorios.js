import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Chart from 'chart.js/auto';
import axios from "axios";


const buttonStyle = {
    padding: '8px',
    marginBottom: '5px',
    width: '100%',
    fontSize: '16px',
    backgroundColor: '#ffdc00', // Amarelo
    color: '#001f3f', // Azul marinho
    borderRadius: '5px',
    cursor: 'pointer',
    alignItems: 'center',
    justifyContent: 'center',
};

const labelStyle = {
    marginBottom: '8px',
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

const linkStyle = {
    textDecoration: 'none',
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

const CrimeChart = () => {
    const [crimes, setCrimes] = useState([]);
    const [cidade, setCidade] = useState('');

    React.useEffect(() => {
        axios
        .get(`http://localhost:8080/api/ocorrencias_por_cidade`)
        .then(response => {
            setCrimes(response.data);
        }
        );
    }, []);

    const handleCidadeSubmit = () => {
        //redirecionar para a pagina de crimes da cidade passando a cidade como parametro
        window.location.href = `Relatorio_cidade?cidade=${encodeURIComponent(cidade)}`;
    }

  useEffect(() => {
    // Extrai as cidades e os números de crimes do seu banco de dados
    const cities = crimes.map((crime) => crime.nome_cidade);
    const crimeNumbers = crimes.map((crime) => crime.total_crimes);




    // Obtém o contexto do canvas
    const ctx = document.getElementById('crimeChart').getContext('2d');

    // Configuração do gráfico
    let myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: cities,
        datasets: [{
          label: 'Número de Crimes',
          data: crimeNumbers,
          backgroundColor: ['orange'], // Adicione mais cores conforme necessário
        }],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });


    return () => {
        myChart.destroy();
      };

  }, [crimes]);


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
        <h2>Filtro por cidade:</h2>
        <select style={labelStyle} onChange={(e) => setCidade(e.target.value)}>
        {crimes.map((crime) => (
          <option key={crime.nome_cidade} value={crime.nome_cidade}>
            {crime.nome_cidade}
          </option>
          
        ))}
      </select>
      <button onClick={handleCidadeSubmit} style={buttonStyle}>
            Redirecionar
      </button>
        <br />
        <Link to="/Relatorio_populacao">
        <button style={buttonStyle}>clique aqui para ver o relatorio por população</button>
        </Link>
        <br />
        <Link to="/Relatorio_rpc">
        <button style={buttonStyle}>clique aqui para ver o relatorio por renda per capita</button>
        </Link>
        <h2>Número de Crimes por Cidade</h2>
        <div style={{ width: '80%', height: '80%', padding: '10px' }}>
        <canvas id="crimeChart" width="40" height="20"></canvas>
        </div>
    </div>
  );
};

export default CrimeChart;