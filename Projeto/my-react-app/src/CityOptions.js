import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const CityOptions = () => {

    React.useEffect(() => {
        axios
            .get(`http://localhost:8080/api/get-cidades`)
            .then(response => {
              setCidades(response.data);
            })
            .catch(error => {
              console.error('Erro ao buscar cidades:', error);
            });
      }, []);

    const [cidades, setCidades] = useState([]);
    const [highlightedCity, setHighlightedCity] = useState(null);
    const [selectedCity, setSelectedCity] = useState(null);


    const handleCityClick = (city) => {
        setSelectedCity(city.nome);
        setHighlightedCity(city.nome); // Destaca a cidade ao ser clicada
        handleCitySubmit(city.nome);
      };
    
      const handleCitySubmit = (cityName) => {
        // Enviar o nome da cidade para o servidor
    
        const formData = new FormData();
        formData.append("cidade", cityName);
        console.log(cityName)
    
        fetch(`http://localhost:8080/api/upload-crime-city`, {
          method: 'POST',
          body: formData,
        })
            .then((response) => response.json())
            .then((data) => {
              console.log(data);
              // Lógica adicional após o envio bem-sucedido
            })
            .catch((error) => {
              console.error('Erro ao enviar cidade:', error);
              // Lógica adicional em caso de erro
            });
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
        padding: '1px',
        color: '#ffdc00', // Amarelo
      };
    
    
      const labelStyle = {
        marginBottom: '8px',
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
    
      const cityButtonStyle = {
        padding: '8px',
        marginBottom: '16px',
        fontSize: '16px',
        backgroundColor: '#ffdc00', // Amarelo
        color: '#001f3f', // Azul marinho
        borderRadius: '5px',
        cursor: 'pointer',
        transition: 'background-color 0.3s', // Adiciona uma transição para a propriedade background-color
      };
    
      const citiesContainerStyle = {
        display: 'flex',
        flexWrap: 'wrap',
      };

      const imageStyle = {
        width: '480px', // Ajuste o tamanho conforme necessário
        marginBottom: '200px',
      };
    
      const cityButtonContainerStyle = {
        flex: '0 0 25%', // Cada coluna ocupa 25% da largura
        boxSizing: 'border-box',
        padding: '5px',
        color:' red',
      };
    
      const transitionDuration = '0.3s';
    
      const highlightedButtonStyle = {
        ...cityButtonStyle,
        boxShadow: '0 0 5px 2px red', // Adiciona uma sombra vermelha ao botão destacado
        transition: `box-shadow ${transitionDuration}`,
      };

      const homeStyle = {
        textDecoration: 'none',
        color: '#ffdc00', // Amarelo
        marginLeft: '10px',
      };

      return (
        <div style={headerStyle}>
            
        </div>,
        <div style={containerStyle}>
            <div style={headerStyle}>
                <Link to="/" style={homeStyle}>
                <img
                    src="/home_logo.png"
                    alt="Home"
                    style={{ width: '50px', height: '50px', cursor: 'pointer' }}
                />
                </Link>
                <h1>Configurações de Cidade</h1>
            </div>
            <img src="/cidades-inteligentes.jpg" alt="Crime Watch" style={imageStyle} />
            <div style={citiesContainerStyle}>
            {cidades.map((city) => (
                <div key={city.nome} style={cityButtonContainerStyle}>
                  <button
                      onClick={() => handleCityClick(city)}
                      style={highlightedCity === city.nome ? highlightedButtonStyle : cityButtonStyle}>
                    {city.nome}
                  </button>
                </div>
            ))}
            <br />
            </div>
            <div style={buttonStyle}>
                <label style={labelStyle}>Caso a cidade desejada não esteja disponivel clique aqui <Link to= "/AdicionarCidade" >
                    Adicionar Cidade
                </Link>
                </label>
            </div>
        </div>

      )
};

export default CityOptions;