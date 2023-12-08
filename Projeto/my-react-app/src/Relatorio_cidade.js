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

const CidadeChart = () => {
  const [crimes, setCrimes] = useState([]);
  const cidadeParams = new URLSearchParams(window.location.search);
  const cidade = cidadeParams.get('cidade');

  React.useEffect(() => {
    axios
      .get(`http://localhost:8080/api/relatorio_por_cidade?nome_cidade=${cidade}`)
      .then(response => {
        setCrimes(response.data);
      });
  }, []);

  useEffect(() => {
    if (crimes.length > 0) {
      const crimeTypes = crimes.map(crime => crime.tipo);
      const crimeNumbers = crimes.map(crime => crime.total_crimes);

      // Create a pie chart
      const ctx = document.getElementById('crimeChart').getContext('2d');
      let myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: crimeTypes,
          datasets: [{
            label: 'Crimes',
            data: crimeNumbers,
            backgroundColor: [
              '#ffdc00', // Amarelo
              '#ff851b', // Laranja
              '#ff4136', // Vermelho
              '#85144b', // Roxo
              '#7fdbff', // Azul claro
              '#0074d9', // Azul
              '#2ecc40', // Verde claro
              '#3d9970', // Verde
              '#01ff70', // Verde limão
              '#b10dc9', // Roxo claro
              '#ffdc00', // Amarelo
              '#ff851b', // Laranja
              '#ff4136', // Vermelho
              '#85144b', // Roxo
              '#7fdbff', // Azul claro
              '#0074d9', // Azul
              '#2ecc40', // Verde claro
              '#3d9970', // Verde
              '#01ff70', // Verde limão
              '#b10dc9', // Roxo claro
            ],
            borderColor: [
              '#ffdc00', // Amarelo
              '#ff851b', // Laranja
              '#ff4136', // Vermelho
              '#85144b', // Roxo
              '#7fdbff', // Azul claro
              '#0074d9', // Azul
              '#2ecc40', // Verde claro
              '#3d9970', // Verde
              '#01ff70', // Verde limão
              '#b10dc9', // Roxo claro
              '#ffdc00', // Amarelo
              '#ff851b', // Laranja
              '#ff4136', // Vermelho
              '#85144b', // Roxo
              '#7fdbff', // Azul claro
              '#0074d9', // Azul
              '#2ecc40', // Verde claro
              '#3d9970', // Verde
              '#01ff70', // Verde limão
              '#b10dc9', // Roxo claro
            ],
            borderWidth: 1
          }]
        },        
      });
  
  
      return () => {
          myChart.destroy();
        };
    };

  }, [crimes]);

  return (
    <div style={containerStyle}>
      <h1 style={headerStyle}>Relatório de Crimes de {cidade}</h1>
      <canvas id="crimeChart" width="400" height="400"></canvas>
      <br />
      <Link to="/Relatorios">
        <button style={buttonStyle}>Voltar</button>
      </Link>
    </div>
  );
}

export default CidadeChart;
