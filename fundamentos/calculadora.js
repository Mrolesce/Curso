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
    b.addEventListener('click', ev =>{
        if(pantallaMsg.indexOf('.') === -1){
            pantalla.textContent += '.';
        }
        if(limpia){
            pantallaMsg = '';
            limpia = false;
        }
        pantalla.textContent = pantallaMsg += ev.target.value;
    })
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
            num=0;
            break;
        case '-':
            acumulated-= +pantallaMsg;
            num=0;
            break;
        case '*':
            acumulated*= +pantallaMsg;
            num=0;
            break;
        case '/':
            acumulated/= +pantallaMsg;
            num=0;
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