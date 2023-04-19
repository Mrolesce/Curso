import React, { Component } from "react";
import { ErrorMessage, Esperando } from "./comunes";

export default class Muro extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listado: null,
      loading: true,
      error: null,
    };
    this.estilo = "hidden";
  }
  render() {
    if (this.state.loading) return <Esperando />;
    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <h1>Muro</h1>
        <div className="container text-center">
        <div className="row">
        {this.state.listado &&
          this.state.listado.map((item) => (
            <div class="col">
            <div className="card" style={{ width: "18rem" }}>
              <img src={item.download_url} className="card-img-top" alt="..." />
              {/* <svg
                className="bd-placeholder-img card-img-top"
                width="100%"
                height={180}
                xmlns="http://www.w3.org/2000/svg"
                role="img"
                aria-label="Placeholder: Image cap"
                preserveAspectRatio="xMidYMid slice"
                focusable="false"
              >
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="#868e96" />
                <text x="50%" y="50%" fill="#dee2e6" dy=".3em">
                  Image cap
                </text>
              </svg> */}
              <div className="card-body">
                <h5 className="card-title">Foto número {+item.id+1}</h5>
                <p className="card-text">
                  Autor: {item.author}
                </p>
                <button type="button" className="btn btn-primary">
                  Mostrar foto
                </button>
              </div>
              <input type="button" defaultValue={1} onClick={() => this.load(1)}/>
              <input type="button" defaultValue={2} onClick={() => this.load(2)}/>
              <input type="button" defaultValue={3} onClick={() => this.load(3)}/>
              <input type="button" defaultValue={3} onClick={() => this.load(4)}/>
              <input type="button" defaultValue={3} onClick={() => this.load(5)}/>
            </div>
            </div>
          ))}
            </div>
        </div>
          
      </>
    );
  }

  setImagen(url) {}

  setError(msg) {
    this.setState({ error: msg });
  }
  load(num) {
    this.setState({ loading: true });
    fetch("https://picsum.photos/v2/list?page=" + num + "&limit=10")
      .then((resp) => {
        if (resp.ok) {
          resp.json().then((data) => this.setState({ listado: data }));
        } else {
          this.setError(resp.status);
        }
      })
      .catch((err) => this.setError(JSON.stringify(err)))
      .finally(() => this.setState({ loading: false }));
  }
  componentDidMount() {
    this.load(1);
  }
}
