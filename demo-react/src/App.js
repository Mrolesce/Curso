import logo from "./logo.svg";
import "./App.css";
import { useState, useEffect } from "react";
// import { eventWrapper } from "@testing-library/user-event/dist/utils";

import React, { Component } from "react";
import { Card, Contador } from "./componentes";

export default class App extends Component {
  render() {
    return (
      <>
        {/*  <Home />
        <DemoJSX />
        <Timer /> */}
        <main className="container-fluid">
        <Card tittle='Ejemplo de componente'>
        <Contador init={10} delta={2}/>
        </Card>
          
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
