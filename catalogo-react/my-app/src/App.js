import "./App.css";
// import { eventWrapper } from '@testing-library/user-event/dist/utils';

import React, { Component } from "react";
import { ActoresMnt } from "./componentes/actores";
import CategoriasMnt from "./componentes/categorias";
import { IdiomasMnt } from "./componentes/idiomas";
import { PelisMnt } from "./componentes/pelis";

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cont: 0,
      main: 0,
    };
    this.menu = [
      { text: "Películas", url: "/", componente: <PelisMnt /> },
      { text: "Actores", url: "/", componente: <ActoresMnt /> },
      { text: "Categorias", url: "/", componente: <CategoriasMnt /> },
      { text: "Idiomas", url: "/", componente: <IdiomasMnt /> },
      
    ];
  }
  render() {
    return (
      <>
        <Cabecera
          menu={this.menu}
          onSelectMenu={(indice) => this.setState({ main: indice })}
        />
          {this.menu[this.state.main].componente}

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
              <li key={index} className="nav-item">
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
        </div>
      </div>
    </nav>
  );
}

function Pie() {
  return (
  <footer className="py-3 my-4">
    <ul className="nav justify-content-center border-bottom pb-3 mb-3">
      <li className="nav-item">
        <a href="/#" className="nav-link px-2 text-muted">
          Home
        </a>
      </li>
      <li className="nav-item">
        <a href="/#" className="nav-link px-2 text-muted">
          Muro
        </a>
      </li>
      <li className="nav-item">
        <a href="/#" className="nav-link px-2 text-muted">
          Pricing
        </a>
      </li>
      <li className="nav-item">
        <a href="/#" className="nav-link px-2 text-muted">
          FAQs
        </a>
      </li>
      <li className="nav-item">
        <a href="/#" className="nav-link px-2 text-muted">
          About
        </a>
      </li>
    </ul>
    <p className="text-center text-muted">© 2023 Marc Roles</p>
  </footer>
  );
}