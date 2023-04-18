let num = 0;
let acumulated = 0;
let op = '+';
let pantallaMsg = '';
let limpia = true;

let botonesNum = document.querySelectorAll('.btnNum');
let botonesOp = document.querySelectorAll('.btnOp');
let pantalla = document.querySelector('output');
let clear = document.querySelector('#clear');
let resultado = document.querySelector('#resultado');

clear.addEventListener('click', ev => {
    pantallaMsg = '';
    acumulated = 0;
    op = '+';
    pantalla.textContent = pantallaMsg;
})

for (b of botonesNum) {
    b.addEventListener('click', (ev) => {
      if (limpia) {
        pantallaMsg = "";
        limpia = false;
      }
      if (ev.target.value === "." && pantallaMsg.includes(".")) {
        return;
      }
      pantallaMsg = pantallaMsg + ev.target.value;
      pantalla.textContent = pantallaMsg;
    });
  }

for (let opBtn of botonesOp){
    opBtn.addEventListener('click', ev => {
        opera(ev.target.value);
    })
}

function opera(newOp){
    switch(op){
        case '+':
            acumulated+= +pantallaMsg;
            break;
        case '-':
            acumulated-= +pantallaMsg;
            break;
        case '*':
            acumulated*= +pantallaMsg;
            break;
        case '/':
            acumulated/= +pantallaMsg;
            break;
    }
    
    pantallaMsg='';
    pantalla.textContent = parseFloat(acumulated.toPrecision(15).toString());
    op = newOp;
    limpia = true;

    if(newOp == '='){
        limpia=false;
        pantallaMsg = '';
    }
}