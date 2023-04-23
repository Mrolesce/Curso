import React, { Component } from "react";
import {
  ValidationMessage,
  ErrorMessage,
  Esperando,
  PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
import { titleCase } from "../biblioteca/formateadores";
export class PelisMnt extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modo: "list",
      listado: null,
      elemento: undefined,
      error: null,
      loading: true,
      pagina: 0,
      paginas: 0,
    };
    this.idOriginal = null;
    this.url =
      (process.env.REACT_APP_API_URL || "http://localhost:8080/catalogo") +
      "/api/films/v1";
  }

  setError(msg) {
    this.setState({ error: msg, loading: false });
  }

  list(num) {
    let pagina = this.state.pagina;
    if (num || num === 0) pagina = num;
    this.setState({ loading: true });
    fetch(`${this.url}?sort=title&page=${pagina}&size=100`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "list",
                  listado: data.content,
                  loading: false,
                  pagina: data.number,
                  paginas: data.totalPages,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }

  add() {
    this.setState({
      modo: "add",
      elemento: {
        filmId: 0,
        description: "",
        length: 0,
        rating: "",
        releaseYear: "",
        rentalDuration: 0,
        rentalRate: 0,
        replacementCost: 0,
        title: "",
        language: "",
        languageVO: "",
        actors: [],
        categories: [],
      },
    });
  }
  edit(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "edit",
                  elemento: data,
                  loading: false,
                });
                this.idOriginal = key;
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }
  view(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "view",
                  elemento: data,
                  loading: false,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }
  delete(key) {
    if (!window.confirm("¿Seguro?")) return;
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`, { method: "DELETE" })
      .then((response) => {
        if (response.ok) this.list();
        else
          response.json().then((error) =>
            this.setError(`${error.status}:
    ${error.error}`)
          );
        this.setState({ loading: false });
      })
      .catch((error) => this.setError(error));
  }
  componentDidMount() {
    this.list(0);
  }

  cancel() {
    this.list();
  }
  send(elemento) {
    this.setState({ loading: true });
    // eslint-disable-next-line default-case
    switch (this.state.modo) {
      case "add":
        fetch(`${this.url}`, {
          method: "POST",
          body: JSON.stringify(elemento),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (response.ok) this.cancel();
            else
              response.json().then((error) =>
                this.setError(`${error.status}:
    ${error.detail}`)
              );
            this.setState({ loading: false });
          })
          .catch((error) => this.setError(error));
        break;
      case "edit":
        fetch(`${this.url}/${this.idOriginal}`, {
          method: "PUT",
          body: JSON.stringify(elemento),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (response.ok) this.cancel();
            else
              response.json().then((error) =>
                this.setError(`${error.status}:
    ${error.detail}`)
              );
            this.setState({ loading: false });
          })
          .catch((error) => this.setError(error));
        break;
    }
  }

  render() {
    if (this.state.loading) return <Esperando />;
    let result = [
      <ErrorMessage
        key="error"
        msg={this.state.error}
        onClear={() => this.setState({ error: null })}
      />,
    ];
    switch (this.state.modo) {
      case "add":
      case "edit":
        result.push(
          <PelisForm
            key="main"
            isAdd={this.state.modo === "add"}
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
            onSend={(e) => this.send(e)}
          />
        );
        break;
      case "view":
        result.push(
          <PelisView
            key="main"
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
          />
        );
        break;
      default:
        if (this.state.listado)
          result.push(
            <PelisList
              key="main"
              listado={this.state.listado}
              pagina={this.state.pagina}
              paginas={this.state.paginas}
              onAdd={(e) => this.add()}
              onView={(key) => this.view(key)}
              onEdit={(key) => this.edit(key)}
              onDelete={(key) => this.delete(key)}
              onChangePage={(num) => this.list(num)}
            />
          );
        break;
    }
    return result;
  }
}

function PelisList(props) {
  return (
    <>
      <table className="table table-hover table-striped">
        <thead className="table-info">
          <tr>
            <th>Lista de películas</th>
            <th className="text-end">
              <input
                type="button"
                className="btn btn-primary"
                value="Añadir"
                onClick={(e) => props.onAdd()}
              />
            </th>
          </tr>
        </thead>
        <tbody className="table-group-divider">
          {props.listado.map((item) => (
            <tr key={item.filmId}>
              <td>{titleCase(item.title)}</td>
              <td className="text-end">
                <div className="btn-group text-end" role="group">
                  <input
                    type="button"
                    className="btn btn-primary"
                    value="Ver"
                    onClick={(e) => props.onView(item.filmId)}
                  />
                  <input
                    type="button"
                    className="btn btn-primary"
                    value="Editar"
                    onClick={(e) => props.onEdit(item.filmId)}
                  />
                  <input
                    type="button"
                    className="btn btn-danger"
                    value="Borrar"
                    onClick={(e) => props.onDelete(item.filmId)}
                  />
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Paginacion
        actual={props.pagina}
        total={props.paginas}
        onChange={(num) => props.onChangePage(num)}
      />
    </>
  );
}

function PelisView({ elemento, onCancel }) {
  return (
    <div className="container-fluid">
      <p>
        <b>Código:</b> {elemento.filmId}
        <br />
        <b>Título:</b> {elemento.title}
        <br />
        <b>Descripción:</b> {elemento.description}
        <br />
        <b>Longitud:</b> {elemento.length}
        <br />
        <b>Rating:</b> {elemento.rating}
        <br />
        <b>Año de estreno:</b> {elemento.releaseYear}
        <br />
        <b>Tiempo alquilado:</b> {elemento.rentalDuration}
        <br />
        <b>Ratio alquiler:</b> {elemento.rentalRate}
        <br />
        <b>Idioma:</b> {elemento.language}
        <br />
        <b>Doblaje:</b>{" "}
        {elemento.languageVO === null || elemento.languageVO === undefined
          ? "No tiene doblaje"
          : elemento.languageVO}
        <br />
        <b>Actores:</b>{" "}
        <ul>
          {" "}
          {elemento.actors.map((item) => (
            <li>{item}</li>
          ))}
        </ul>
        <br />
        <b>Categorías:</b> {elemento.categories}
        <br />
      </p>
      <p>
        <button
          className="btn btn-primary"
          type="button"
          onClick={(e) => onCancel()}
        >
          Volver
        </button>
      </p>
    </div>
  );
}

class PelisForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      elemento: props.elemento,
      msgErr: [],
      invalid: false,
      idiomas: [],
      actoresState: [],
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSend = () => {
      if (this.props.onSend) this.props.onSend(this.state.elemento);
    };
    this.onCancel = () => {
      if (this.props.onCancel) this.props.onCancel();
    };
  }
  handleChange(event) {
    const cmp = event.target.name;
    const valor = event.target.value;

    if (valor === "No language") {
      this.setState((prevState) => ({
        elemento: {
          ...prevState.elemento,
          languageVO: undefined,
        },
      }));
    }
    this.setState((prev) => {
      prev.elemento[cmp] = valor;
      return { elemento: prev.elemento };
    });
    this.validar();
    console.log(event.target.value);
  }
  validarCntr(cntr) {
    if (cntr.name) {
      // eslint-disable-next-line default-case
      switch (cntr.name) {
        case "apellidos":
          cntr.setCustomValidity(
            cntr.value !== cntr.value.toUpperCase()
              ? "Debe estar en mayúsculas"
              : ""
          );
          break;
      }
    }
  }
  validar() {
    if (this.form) {
      const errors = {};
      let invalid = false;
      for (var cntr of this.form.elements) {
        if (cntr.name) {
          this.validarCntr(cntr);
          errors[cntr.name] = cntr.validationMessage;
          invalid = invalid || !cntr.validity.valid;
        }
      }
      this.setState({ msgErr: errors, invalid: invalid });
    }
  }
  componentDidMount() {
    this.validar();
    this.loadIdiomas();
    this.loadActores();
  }
  loadIdiomas() {
    var requestOptions = {
      method: "GET",
    };

    fetch("http://localhost:8080/catalogo/api/lenguajes", requestOptions)
      .then((response) => response.json())
      .then((data) => {
        this.setState({ idiomas: data });
        let idiomaFind = data.find(
          (a) => a.idioma === this.state.elemento.language
        );
        if (idiomaFind) {
          this.setState((prev) => {
            return { elemento: { ...prev.elemento, language: +idiomaFind.id } };
          });
        }
        console.log(this.state.elemento);
      })
      .catch((error) => console.log("error", error));
  }
  loadActores() {
    var requestOptions = {
      method: "GET",
    };

    fetch("http://localhost:8080/catalogo/api/actores/v1", requestOptions)
      .then((response) => response.json())
      .then((data) => {
        let actoresExistentes = [];

        for (let ac of data) {
          if (this.state.actoresState.includes(ac.nombre)) {
            actoresExistentes.push(ac.id);
          }
        }
        this.setState((prev) => {
          prev.elemento.actors = actoresExistentes;
          return { actoresState: data, elemento: prev.elemento };
        });
      })
      .catch((error) => console.log("error", error));
  }
  render() {
    let ratingsPeli = ["G", "PG", "PG-13", "R", "NC-17"];
    /* this.elemento.language.find(v => v) */
    return (
      <form
        ref={(tag) => {
          this.form = tag;
        }}
      >
        <div className="form-group">
          <label htmlFor="filmId">Código</label>
          <input
            type="number"
            className={"form-control" + (this.props.isAdd ? "" : "-plaintext")}
            id="filmId"
            name="filmId"
            value={this.state.elemento.filmId}
            onChange={this.handleChange}
            required
            readOnly={!this.props.isAdd}
          />
          <ValidationMessage msg={this.state.msgErr.id} />
        </div>
        <div className="form-group">
          <label htmlFor="title">Título</label>
          <input
            type="text"
            className="form-control"
            id="title"
            name="title"
            value={this.state.elemento.title}
            onChange={this.handleChange}
            required
            minLength={2}
            maxLength={128}
          />
          <ValidationMessage msg={this.state.msgErr.title} />
        </div>
        <div className="form-group">
          <label htmlFor="description">Descripción</label>
          <input
            type="text"
            className="form-control"
            id="description"
            name="description"
            value={this.state.elemento.description}
            onChange={this.handleChange}
          />
          <ValidationMessage msg={this.state.msgErr.description} />
        </div>
        <div className="form-group">
          <label htmlFor="description">Longitud</label>
          <input
            type="number"
            className="form-control"
            id="length"
            name="length"
            value={this.state.elemento.length}
            onChange={this.handleChange}
            maxLength="5"
          />
          <ValidationMessage msg={this.state.msgErr.length} />
        </div>
        <div className="form-group">
          <label htmlFor="description">Rating</label>
          <select
            id="rating"
            name="rating"
            onChange={this.handleChange}
            value={this.state.elemento.rating}
          >
            {ratingsPeli.map((item) => (
              <option key={item}>{item}</option>
            ))}
          </select>
          {this.state.elemento.rating}
          <ValidationMessage msg={this.state.msgErr.rating} />
        </div>
        <div className="form-group">
          <label htmlFor="releaseYear">Año de estreno</label>
          <input
            type="number"
            className="form-control"
            id="releaseYear"
            name="releaseYear"
            value={this.state.elemento.releaseYear}
            onChange={this.handleChange}
            min={1895}
          />
          <ValidationMessage msg={this.state.msgErr.releaseYear} />
        </div>
        <div className="form-group">
          <label htmlFor="rentalDuration">Duración de alquilado</label>
          <input
            type="number"
            className="form-control"
            id="rentalDuration"
            name="rentalDuration"
            value={this.state.elemento.rentalDuration}
            onChange={this.handleChange}
            min={0}
          />
          <ValidationMessage msg={this.state.msgErr.rentalDuration} />
        </div>
        <div className="form-group">
          <label htmlFor="rentalRate">Ratio alquiler</label>
          <input
            type="number"
            className="form-control"
            id="rentalRate"
            name="rentalRate"
            value={this.state.elemento.rentalRate}
            onChange={this.handleChange}
          />
          <ValidationMessage msg={this.state.msgErr.rentalRate} />
        </div>
        <div className="form-group">
          <label htmlFor="replacementCost">Coste de reemplazo</label>
          <input
            type="number"
            className="form-control"
            id="replacementCost"
            name="replacementCost"
            value={this.state.elemento.replacementCost}
            onChange={this.handleChange}
          />
          <ValidationMessage msg={this.state.msgErr.rentalRate} />
        </div>
        <div className="form-group">
          <label htmlFor="language">Idioma</label>
          <select
            id="language"
            name="language"
            onChange={this.handleChange}
            value={this.state.elemento.language}
          >
            {this.state.idiomas &&
              this.state.idiomas.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.idioma}
                </option>
              ))}
          </select>
          {JSON.stringify(typeof this.state.elemento.language)}
          <ValidationMessage msg={this.state.msgErr.language} />
        </div>
        <div className="form-group">
          <label htmlFor="languageVO">Idioma VO</label>
          <select
            id="languageVO"
            name="languageVO"
            onChange={this.handleChange}
            value={
              this.state.elemento.languageVO == null
                ? "No language"
                : this.state.elemento.languageVO
            }
          >
            <option value={"No language"}>No language</option>
            {this.state.idiomas &&
              this.state.idiomas.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.idioma}
                </option>
              ))}
          </select>
          {JSON.stringify(this.state.elemento.languageVO)}
          <ValidationMessage msg={this.state.msgErr.languageVO} />
        </div>
        <div className="form-group">
          <label htmlFor="actors">Actores</label>
          <select
            id="actors"
            style={{height: '200px'}}
            name="actors"
            onChange={this.handleChange}
            value={this.state.elemento.actors}
            multiple={true}
          >
            <option value='Option 1'>
              Option 1
            </option>
            <option value='Option 2'>
              Option 2
            </option>
            <option value='Option 3'>
              Option 3
            </option>
            {/* {this.state.actoresState &&
              this.state.actoresState.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.nombre}
                </option>
              ))} */}
          </select>
          {JSON.stringify(this.state.elemento.actors)}
          <ValidationMessage msg={this.state.msgErr.actors} />
        </div>
        <div className="form-group">
          <button
            className="btn btn-primary"
            type="button"
            disabled={this.state.invalid}
            onClick={this.onSend}
          >
            Enviar
          </button>
          <button
            className="btn btn-primary"
            type="button"
            onClick={this.onCancel}
          >
            Volver
          </button>
        </div>
      </form>
    );
  }

  /*  loadActores(){

  } */
}
