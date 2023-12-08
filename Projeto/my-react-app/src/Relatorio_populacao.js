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

const PopulationChart = () => {
    const [crimes, setCrimes] = useState([]);

    React.useEffect(() => {
        axios
          .get(`http://localhost:8080/api/relatorio_crime_por_populacao`)
          .then(response => {
            setCrimes(response.data);
          });
      }, []);

    useEffect(() => {
        if(crimes.length > 0) {
            const ctx = document.getElementById('myChart').getContext('2d');
            const cidade = crimes.map(crime => crime.nome_cidade);
            const populacao = crimes.map(crime => crime.populacao_cidade);
            const total_crimes = crimes.map(crime => crime.total_crimes);
            const taxa = crimes.map(crime => crime.taxa_crimes_por_pessoa);
            //console.log(taxa);
            console.log(populacao);
            //console.log(total_crimes);

            let myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: cidade,
                    datasets: [
                        {
                            label: 'População',
                            data: populacao,
                            backgroundColor: 'red',
                            borderColor: 'red',
                            borderWidth: 1,
                        },
                        {
                            label: 'Total de Crimes',
                            data: total_crimes,
                            backgroundColor: 'green',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1,
                        },
                        {
                            label: 'Taxa de Crimes por Pessoa',
                            data: taxa,
                            backgroundColor: 'yellow',
                            borderColor: 'rgba(255, 206, 86, 1)',
                            borderWidth: 1,
                        },
                    ],
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
        }
    }
    , [crimes]);

    return (
        <div style={containerStyle}>
            <header style={headerStyle}>
                <h1>Relatório de Crimes por População</h1>
            </header>
            <div style={{ width: '80%', height: '80%', padding: '10px' }}>
                <canvas id="myChart" />
            </div>
            <Link to="/Relatorios" style={linkStyle}>
                <button style={buttonStyle}>Voltar</button>
            </Link>
        </div>
    );

}

export default PopulationChart;
