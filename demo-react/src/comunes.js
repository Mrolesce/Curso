import React from "react";
import imgAjax from "./imagenes/loading.gif";

export function Esperando() {
  return <img src={imgAjax} alt="Eperando respuesta del servidor" />;
}

export function ErrorMessage({ msg }) {
  return <output style={{ color: "red" }}></output>;
}
