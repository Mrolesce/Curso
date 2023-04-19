import logo from "./logo.svg";
import "./App.css";
import { useState, useEffect } from "react";
// import { eventWrapper } from '@testing-library/user-event/dist/utils';

import React, { Component } from "react";
import { Card, Contador } from "./componentes";
import Calculadora from "./calculadoraReact";
import Muro from "./muro";

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cont: 0,
      main: 0,
    };
    this.menu = [
      { text: "Inicio", url: "/", componente: <Home /> },
      { text: "Muro", url: "/muro", componente: <Muro /> },
      { text: "Demos", url: "/demos", componente: <DemoJSX /> },
      {
        text: "Contador",
        url: "/contador",
        componente: (
          <Contador
            init={10}
            delta={2}
            onChange={(num) => this.setState({ cont: num })}
          />
        ),
      },
      { text: "Ejemplos", url: "/ejemplos", componente: <Ejemplos /> },
      { text: "Calculadora", url: "/calculadora", componente: <Calculadora /> },
      
    ];
  }
  render() {
    return (
      <>
        <Cabecera
          menu={this.menu}
          onSelectMenu={(indice) => this.setState({ main: indice })}
        />
        <main className="container-fluid">
          {this.menu[this.state.main].componente}
        </main>
        <Pie />
      </>
    );
  }
}

function Cabecera(props) {
  return (
    <header>
      <Menu {...props} />
    </header>
  );
}

function Menu({ menu, onSelectMenu }) {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container-fluid">
        <a className="navbar-brand" href="blank">
          Catálogo
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon" />
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
            {menu.map((item, index) => (
              <li className="nav-item">
                <a
                  href="/#"
                  className="nav-link"
                  key={index}
                  type="button"
                  onClick={() => onSelectMenu && onSelectMenu(index)}
                >
                  {item.text}
                </a>
              </li>
            ))}
          </ul>
          <form className="d-flex" role="search">
            <input
              className="form-control me-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
            />
            <button className="btn btn-outline-success" type="submit">
              Search
            </button>
          </form>
        </div>
      </div>
    </nav>
  );
}

function Pie() {
  return null;
}

export class Ejemplos extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cont: 0,
      main: 0,
    };
  }
  render() {
    return (
      <>
        {/*  <Home />
        <DemoJSX />
        */}
        <main className="container-fluid">
          <Card tittle="Ejemplo de componente">
            <Contador
              init={10}
              delta={2}
              onChange={(num) => this.setState({ cont: num })}
            />
          </Card>
          <p>El contador: {this.state.cont}</p>
          <input
            className="btn btn-bg-danger"
            type="button"
            value="no tocar"
            onClick={() => {
              throw new Error("No tocar");
            }}
          />
          <Timer />
        </main>
      </>
    );
  }
}
class DemoJSX extends Component {
  render() {
    let nombre = "<b>Mundo</b>";
    let estilo = "App-link";
    let saluda = <span>Hola {nombre}!</span>;
    let dim = { width: 100, height: 50 };
    let errorStyle = { color: "white", backgroundColor: "red" };
    let list = [
      { id: 1, nombre: "Madrid" },
      { id: 2, nombre: "Barcelona" },
      { id: 3, nombre: "Valencia" },
      { id: 4, nombre: "Sevilla" },
    ];
    let cond = true; //  ?? si llega con valor, lo printa. && es el if sin el else '? : ' -> '? && '
    return (
      <>
        {/*<h2 className={estilo} dangerouslySetInnerHTML={{__html: nombre}}></h2>*/}
        <h2 className={estilo}>{saluda}</h2>
        {cond ? (
          <h1 style={{ color: "green" }}>Es true</h1>
        ) : (
          <h1 style={{ color: "red" }}>Es false</h1>
        )}
        {!cond && <h1>Hola</h1>}
        {errorStyle?.color?.backgroundColor ? <h1>Null</h1> : <h1>No null</h1>}
        <div style={{ color: "white", backgroundColor: "#61dafb" }}>
          DemoJSX
        </div>
        <div style={errorStyle}>DemoJSXERROR</div>
        <ul>
          {[1, 2, 3, 4, 5, 4, 3, 2, 1].map((item, index) => (
            <li key={index}>Element -&gt; {item}</li>
          ))}
        </ul>
        <select>
          {list.map((item) => (
            <option key={item.id} value={item.value}>
              {item.nombre}
            </option>
          ))}
        </select>
        <img src={logo} className="App-logo" alt="logo" {...dim} />
      </>
    );
  }
}

function Home() {
  return (
    // eslint-disable-next-line jsx-quotes
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Hello World!</h1>
        <h2>{process.env.REACT_APP_API_URL}</h2>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

function Timer() {
  const [time, setTime] = useState(new Date());

  useEffect(() => {
    const intervalId = setInterval(() => {
      setTime(new Date());
    }, 1000);
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div>
      <p>{time.toLocaleTimeString()}</p>
    </div>
  );
}

//export default App;
