import React from "react";
import imgAjax from "./imagenes/loading.gif";

export function Esperando() {
  return (
    <div>
      <div className="ajax-wait"></div>
      <div className="loader"></div>
    </div>
  );
}

export class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null, errorInfo: null };
  }
  static getDerivedStateFromError(error) {
    return { hasError: true };
  }
  componentDidCatch(error, info) {
    this.setState({ hasError: true, error: error, errorInfo: info });
  }
  render() {
    if (this.state.hasError) {
      return (
        <div>
          <h1>ERROR</h1>
          {this.state.error && <p>{this.state.error.toString()}</p>}
          {this.state.errorInfo && <p>{this.state.errorInfo.componentStack}</p>}
        </div>
      );
    }
    return this.props.children;
  }
}

export function ErrorMessage({ msg, onClear }) {
  if (msg) {
    return (
      <div
        className="alert alert-danger alert-dismissible fade show"
        role="alert"
      >
        {msg}
        <button
          type="button"
          className="btn-close"
          data-dismiss="alert"
          aria-label="Close"
          onClick={(e) => onClear && onClear()}
        />
      </div>
    );
  }
  return null;
}

export function PaginacionCmd({ actual, total, onChange }) {
  let click = (number, ev) => {
    ev.preventDefault();
    if (onChange) onChange(number);
  };
  let items = [];
  for (let number = 0; number < total; number++) {
    items.push(
      number === actual ? (
        <li key={number} className="page-item active" aria-current="page">
          <a href="." className="page-link" onClick={click.bind(this, number)}>
            {number + 1}
          </a>
        </li>
      ) : (
        <li key={number} className="page-item">
          <a href="." className="page-link" onClick={click.bind(this, number)}>
            {number + 1}
          </a>
        </li>
      )
    );
  }
  return (
    <nav aria-label="Page navigation">
      <ul className="pagination">{items}</ul>
    </nav>
  );
}
