import React, { Component } from "react";
import './calculadora.css'
export default class Calculadora extends Component {
    constructor(props){
        super(props);
        this.limpia = true;
        this.acumalated = 0;
        this.operador = '+';
        this.state = {
            pantalla: ''
        }
        this.delta = +(this.props.delta ?? 1);
        this.baja = () => {
            this.changeContador(-this.delta);
        }
        this.acumulador = (ev) => {
            if (this.limpia) {
                this.state.pantalla='';
                this.limpia = false;
              }
            if (ev.target.value === "." && this.state.pantalla.includes(".")) {
                return;
              }
            this.setState(prev => ({pantalla:  prev.pantalla+ev.target.value}));//para no depender de momentos
        }
        this.opera = (ev) => {
            switch(this.operador){
                case '+':
                    this.acumalated+= +this.state.pantalla;
                    break;
                case '-':
                    this.acumalated-= +this.state.pantalla;
                    break;
                case '*':
                    this.acumalated*= +this.state.pantalla;
                    break;
                case '/':
                    this.acumalated/= +this.state.pantalla;
                    break;
                default:
                    break;
            }
            this.setState({pantalla: parseFloat(this.acumalated.toPrecision(15).toString())});
            this.limpia = true;
            this.operador = ev.target.value;

            if(ev.target.value === '='){
                this.limpia=false;
            }
        }

        this.limpiar = () => {
            this.setState({pantalla: ''});
            this.acumalated = 0;
            this.operador = '+';
        }

    }

    render() {
    return (
      <div className="container" id="calculadora">
        <div className="row">
          <div className="colz">
            <output className="outout">{this.state.pantalla}</output>
          </div>
        </div>
        <div className="row">
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num1"
              defaultValue={1}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num2"
              defaultValue={2}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num3"
              defaultValue={3}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnOp boton"
              id="suma"
              defaultValue="+"
              onClick={this.opera}
            />
          </div>
        </div>
        <div className="row">
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num4"
              defaultValue={4}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num5"
              defaultValue={5}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num6"
              defaultValue={6}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnOp boton"
              id="resta"
              defaultValue="-"
              onClick={this.opera}
            />
          </div>
        </div>
        <div className="row">
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num7"
              defaultValue={7}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num8"
              defaultValue={8}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num9"
              defaultValue={9}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnOp boton"
              id="multiplica"
              defaultValue="*"
              onClick={this.opera}
            />
          </div>
        </div>
        <div className="row">
          <div className="col-sm">
            <input
              type="button"
              className="boton"
              id="clear"
              defaultValue="C"
              onClick={this.limpiar}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="num0"
              defaultValue={0}
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnNum boton"
              id="coma"
              defaultValue="."
              onClick={this.acumulador}
            />
          </div>
          <div className="col-sm">
            <input
              type="button"
              className="btnOp boton"
              id="divide"
              defaultValue="/"
              onClick={this.opera}
            />
          </div>
        </div>
        <div className="row">
          <div className="col-sm"></div>
          <div className="col-sm"></div>
          <div className="col-sm"></div>
          <div className="col-sm">
            <input
              type="button"
              className="btnOp boton"
              id="resultado"
              defaultValue="="
              onClick={this.opera}
            />
          </div>
        </div>
      </div>
    );
  }
}
