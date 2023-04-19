import React, { Component, useState } from "react";
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
    this.ultimoPulsado = 0;
  }
  render() {
    if (this.state.loading) return <Esperando />;
    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <div className="container text-center">
          <div className="row">
            {this.state.listado &&
              this.state.listado.map((item) => (
                <div class="col">
                  <Card
                    ruta={item.download_url}
                    id={item.id}
                    author={item.author}
                  />
                </div>
              ))}
          </div>
          <nav aria-label="Page navigation example">
            <ul className="pagination">
              <li className="page-item">
                <button type="button" className="page-link" onClick={() => this.load(1)}>
                  Previous
                </button>
              </li>
              <li className="page-item">
                <button type="button" className="page-link" onClick={() => this.load(1)}>
                  1
                </button>
              </li>
              <li className="page-item">
                <button type="button" className="page-link" onClick={() => this.load(2)}>
                  2
                </button>
              </li>
              <li className="page-item">
                <button type="button" className="page-link" onClick={() => this.load(3)}>
                  3
                </button>
              </li>
              <li className="page-item">
                <button type="button" className="page-link" onClick={() => this.load(3)}>
                  Next
                </button>
              </li>
            </ul>
          </nav>
        </div>
      </>
    );
  }

  setError(msg) {
    this.setState({ error: msg });
  }
  load(num) {
    this.setState({ loading: true });
    fetch("https://picsum.photos/v2/list?page=" + num + "&limit=24")
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

export function Card({ ruta, id, author }) {
  const [show, setShow] = useState(true);
  return (
    <div className="card mt-4" style={{ width: "18rem" }}>
      {!show ? <img src={ruta} className="card-img-top" alt="..." /> : null}
      {show ? (
        <svg
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
          <text x="42.7%" y="50%" fill="#dee2e6" dy=".3em">
            Foto {+id + 1}
          </text>
        </svg>
      ) : null}
      <div className="card-body">
        <h5 className="card-title">Foto número {+id + 1}</h5>
        <p className="card-text">Autor: {author}</p>
        <button
          type="button"
          className="btn btn-primary"
          onClick={() => setShow(!show)}
        >
          Mostrar foto
        </button>
      </div>
    </div>
  );
}
