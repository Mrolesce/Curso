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
  }
  render() {
    if(this.state.loading)
        <Esperando/>
    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error}/>}
        <div>muro</div>
        {JSON.stringify(this.state.listado)}
      </>
    );
  }
  setErr(msg){
    this.setState({error: msg})
  }
  load(num){
    fetch('http://localhost:8010/api/actores/v1')
        .then(resp => {
            if(resp.ok){
                resp.json().then(
                    data => this.setState({listado: data})
                )
            } else{
                this.setErr(resp.status)
            }
        })
        .catch(err => this.setErr(JSON.stringify(err)))
        .finally(() => this.setState({loading: false}))
  }
  componentDidMount(){
    this.load(1);
  }
}
