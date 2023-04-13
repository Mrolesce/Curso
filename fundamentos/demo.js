// 'use strict' esta línea nos obliga a declarar todas las variables (buena práctica)
console.log('Hola desde demo.js');

// a diferencia de var, let para declarar una variable accesible solo en el bloque en el que se declara
// conveniente olvidarnos completamente de var y usar let solamente.
a=5;
b=2;
c='2';
hola='hola';
//console.log(`Hola, digo ${hola} y sumo ${a} + ${b} = ${a+b}`);//ponemos + delante de una cadena para convertirlo a un valor aritmético

if(b===c){
    console.log(`true`);
}else{
    console.log('No coinciden los tipos');
}

// si le ponemos const delante de la función evitamos sobreescrituras accidentales
const kk = function() {
    var ayuda = "No puedo salir"; //variable local de la function (si es declarada)
    noMeAyudes = "Puedo salir";
    console.log(`Hola, digo ${hola} y sumo ${a} + ${b} = ${a+b}`);

    b = ayuda+noMeAyudes;
}

kk();

// console.log(ayuda); esto daría error ya que "ayuda" es una variable local en el método "kk" */

console.log(noMeAyudes);

t = [10, "20", 30] 
t = { x: 10, y: 20}
for(v in t){ // este foreach nos devuelve el indice del objecto a diferencia de java que devuelve los contenidos
    console.log(t[v])
}

// for v of t devuelve los valores, se puede usar por ejemplo en un array de valores

t.x === t['x'] // esto es true, formas distintas de hacer lo mismo

console.log(t instanceof Object) // t es de tipo Object

let cond = 0; // cero es falso aunque sea un valor valido, ojo con los ifs
console.log(cond ? "Hola" : "adios")