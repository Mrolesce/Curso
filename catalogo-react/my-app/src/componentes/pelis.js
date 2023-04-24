import React, { Component } from "react";
import Multiselect from "multiselect-react-dropdown";

import {
  ValidationMessage,
  ErrorMessage,
  Esperando,
  PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
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
  loadImgs(num) {
    let pagina = this.state.pagina;
    if (num || num === 0) pagina = num;
    this.setState({ loading: true });
    fetch(`https://picsum.photos/v2/list?page=${pagina}&limit=100`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "list",
                  imgs: data,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
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
        rating: "G",
        releaseYear: "",
        rentalDuration: 0,
        rentalRate: 0,
        replacementCost: 0,
        title: "",
        language: "1",
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
    this.loadImgs(0);
  }

  cancel() {
    this.list();
    this.loadImgs();
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
            onDelete={(key) => this.delete(key)}
          />
        );
        break;
      case "view":
        result.push(
          <PelisView
            key="main"
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
            onEdit={(key) => this.edit(key)}
          />
        );
        break;
      default:
        if (this.state.listado)
          result.push(
            <PelisList
              key="main"
              listado={this.state.listado}
              imgs={this.state.imgs}
              pagina={this.state.pagina}
              paginas={this.state.paginas}
              onAdd={(e) => this.add()}
              onView={(key) => this.view(key)}
              onEdit={(key) => this.edit(key)}
              onChangePage={(num) => {
                this.list(num);
              }}
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
      <div className="container-fluid">
        <input
          type="button"
          className="btn btn-primary mt-3"
          value="Añadir película"
          onClick={(e) => props.onAdd()}
        />
        <div className="row">
          <div className="row"></div>

          {props.imgs &&
            props.listado &&
            props.listado.map((item, index) => (
              <div className="col-sm mt-3">
                <div className="card" style={{ width: "18rem" }}>
                  <img
                    className="card-img-top"
                    src={
                      props.imgs[index].download_url == null
                        ? null
                        : props.imgs[index].download_url
                    }
                    alt="Peli"
                  />
                  <div className="card-body">
                    <h5 className="card-title">{item.title}</h5>
                    <p className="card-text">Película con id: {item.filmId}</p>
                    <input
                      type="button"
                      className="btn btn-primary"
                      value="Ver"
                      onClick={(e) => props.onView(item.filmId)}
                    />
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>
      <div className="container-fluid mt-3">
        <Paginacion
          actual={props.pagina}
          total={props.paginas}
          onChange={(num) => props.onChangePage(num)}
        />
      </div>
    </>
  );
}

function PelisView({ elemento, onCancel, onEdit }) {
  return (
    <div className="container-fluid">
      <div>
        <b>Código:</b> {elemento.filmId}
        <br />
        <b>Título: </b>
        {elemento.title}
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
        <b>Categorías:</b>
        <ul>
          {" "}
          {elemento.categories.map((item) => (
            <li>{item}</li>
          ))}
        </ul>
        <br />
      </div>
      <p>
        <input
          type="button"
          className="btn btn-primary"
          value="Editar"
          onClick={(e) => onEdit(elemento.filmId)}
        />
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
      categoriesState: [],
      selectedValueActor: {},
      selectedValueCategory: {},
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSend = () => {
      if (this.props.onSend) this.props.onSend(this.state.elemento);
    };
    this.onCancel = () => {
      if (this.props.onCancel) this.props.onCancel();
    };
    this.onDelete = () => {
      if (this.props.onDelete) this.props.onDelete(this.state.elemento.filmId);
    };
  }
  handleChange(event) {
    const cmp = event.target.name;
    const valor = event.target.value;

    if (valor === "No language" && cmp === "languageVO") {
      this.setState((prevState) => ({
        elemento: {
          ...prevState.elemento,
          languageVO: null,
        },
      }));
    } else if (cmp === "actors") {
      let valueAc = Array.from(
        event.target.selectedOptions,
        (option) => option.value
      );
      this.setState((prev) => {
        prev.elemento[cmp] = valueAc;
        return { elemento: prev.elemento };
      });
    } else if (cmp === "categories") {
      let valueCa = Array.from(
        event.target.selectedOptions,
        (option) => option.value
      );
      this.setState((prev) => {
        prev.elemento[cmp] = valueCa;
        return { elemento: prev.elemento };
      });
    } else {
      this.setState((prev) => {
        prev.elemento[cmp] = valor;
        return { elemento: prev.elemento };
      });
    }

    this.validar();
    console.log(this.state.elemento);
  }

  validar() {
    if (this.form) {
      const errors = {};
      let invalid = false;
      for (var cntr of this.form.elements) {
        if (cntr.name) {
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
    this.loadCategorias();
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
        let actoresNames = [];
        for (let ac of data) {
          if (
            this.state.elemento.actors.includes(ac.nombre) ||
            this.state.elemento.actors.includes(ac.actorId)
          ) {
            actoresNames.push({ actorId: ac.actorId, nombre: ac.nombre });
            actoresExistentes.push(ac.actorId);
          }
        }
        this.setState((prev) => {
          prev.elemento.actors = actoresExistentes;
          return {
            actoresState: data,
            elemento: prev.elemento,
            selectedValueActor: actoresNames,
          };
        });
      })
      .catch((error) => console.log("error", error));
  }
  loadCategorias() {
    var requestOptions = {
      method: "GET",
    };

    fetch(
      "http://localhost:8080/catalogo/api/categorias/v1?sort=name",
      requestOptions
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        let categoriasExistentes = [];
        let categoriasNombre = [];
        for (let ca of data) {
          if (
            this.state.elemento.categories.includes(ca.categoria) ||
            this.state.elemento.categories.includes(ca.id)
          ) {
            categoriasNombre.push({ id: ca.id, categoria: ca.categoria });
            categoriasExistentes.push(ca.id);
          }
        }
        this.setState((prev) => {
          prev.elemento.categories = categoriasExistentes;
          return {
            categoriesState: data,
            elemento: prev.elemento,
            selectedValueCategory: categoriasNombre
          };
        });
      })
      .catch((error) => console.log("error", error));
  }
  render() {
    let ratingsPeli = ["G", "PG", "PG-13", "R", "NC-17"];
    /* this.elemento.language.find(v => v) */
    return (
      <div className="container-fluid">
        <form
          ref={(tag) => {
            this.form = tag;
          }}
        >
          <div className="form-group">
            <label htmlFor="filmId">Código</label>
            <input
              type="number"
              className={
                "form-control" + (this.props.isAdd ? "" : "-plaintext")
              }
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
              value={
                this.state.elemento.rating === ""
                  ? "G"
                  : this.state.elemento.rating
              }
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
            <label htmlFor="language">Idioma: </label>
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
            <ValidationMessage msg={this.state.msgErr.language} />
          </div>
          <div className="form-group">
            <label htmlFor="languageVO">Idioma VO: </label>
            <select
              id="languageVO"
              name="languageVO"
              onChange={this.handleChange}
              value={
                this.state.elemento.languageVO == null ||
                this.state.elemento.languageVO === undefined ||
                this.state.elemento.languageVO === []
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
            <ValidationMessage msg={this.state.msgErr.languageVO} />
          </div>
          <div className="form-group">
            <label htmlFor="actors">Actores</label>
            <Multiselect
              options={this.state.actoresState} // Options to display in the dropdown
              selectedValues={this.state.selectedValueActor} // Preselected value to persist in dropdown
              onSelect={this.onSelect} // Function will trigger on select event
              onRemove={this.onRemove} // Function will trigger on remove event
              displayValue="nombre" // Property name to display in the dropdown options
            />
            <ValidationMessage msg={this.state.msgErr.actors} />
          </div>
          <div className="form-group">
            <label htmlFor="actors">Categories</label>

            <br />
            {/* {this.state.categoriesState &&
                this.state.categoriesState.map((item) => (
                  <option key={item.id} value={item.id}>
                    {item.categoria}
                  </option>
                ))} */}
            <Multiselect
              options={this.state.categoriesState} // Options to display in the dropdown
              selectedValues={this.state.selectedValueCategory} // Preselected value to persist in dropdown
              onSelect={this.onSelect} // Function will trigger on select event
              onRemove={this.onRemove} // Function will trigger on remove event
              displayValue="categoria" // Property name to display in the dropdown options
            />
            <ValidationMessage msg={this.state.msgErr.categories} />
          </div>
          <div className="form-group mt-3">
            <button
              className="btn btn-primary"
              type="button"
              disabled={this.state.invalid}
              onClick={this.onSend}
            >
              Enviar
            </button>
            <input
              type="button"
              className="btn btn-danger ml-2"
              value="Borrar"
              onClick={(e) => this.onDelete()}
            />
            <button
              className="btn btn-primary ml-2"
              type="button"
              onClick={this.onCancel}
            >
              Volver
            </button>
          </div>
        </form>
      </div>
    );
  }
  onSelect = (selectedList, selectedItem) => {
    const actorIds = selectedList.filter((item) => item.actorId).map((item) => item.actorId);
    const categoriaIds = selectedList.filter((item) => item.id).map((item) => item.id);
  
    if (actorIds.length > 0) {
      this.setState({
        elemento: { ...this.state.elemento, actors: actorIds },
        selectedValueActor: selectedList,
      });
    } else if (categoriaIds.length > 0) {
      this.setState({
        elemento: { ...this.state.elemento, categories: categoriaIds },
        selectedValueCategory: selectedList,
      });
    }
  };
  
  onRemove = (selectedList, selectedItem) => {
    const actorIds = selectedList.filter((item) => item.actorId).map((item) => item.actorId);
    const categoriaIds = selectedList.filter((item) => item.id).map((item) => item.id);
  
    if (actorIds.length > 0) {
      this.setState({
        elemento: { ...this.state.elemento, actors: actorIds },
        selectedValueActor: selectedList,
      });
    } else if (categoriaIds.length > 0) {
      this.setState({
        elemento: { ...this.state.elemento, categories: categoriaIds },
        selectedValueCategory: selectedList,
      });
    }
  };
  
}

export function MultiSelectActors({ elemento, actores }) {}
