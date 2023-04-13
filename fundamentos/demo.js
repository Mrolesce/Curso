// 'use strict' esta línea nos obliga a declarar todas las variables (buena práctica)
console.log('Hola desde demo.js');

// a diferencia de var, let para declarar una variable accesible solo en el bloque en el que se declara
// conveniente olvidarnos completamente de var y usar let solamente.
a=20;
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

t = [10, "20", 30];
t = { x: 10, y: 20}
for(v in t){ // este foreach nos devuelve el indice del objecto a diferencia de java que devuelve los contenidos
    console.log(t[v])
}

// for v of t devuelve los valores, se puede usar por ejemplo en un array de valores

t.x === t['x'] // esto es true, formas distintas de hacer lo mismo

console.log(t instanceof Object) // t es de tipo Object

let cond = 0; // cero es falso aunque sea un valor valido, ojo con los ifs
console.log(cond ? "Hola" : "adios")

// funciones, se les puede asignar atributo, se pueden sobreescribir (siempre que no se defina como constante)

// function f (x,y ...a) En esta function los dos primeros valores serian fijo y el resto serían añadidos a un array

// Ojo con los argumentos:



arr = [0,1,3,4,6,7]
function avg() { // aunque la function no tenga parámetros, los recoge igual
    var rslt= 0;
    for(var i=0; i < arguments.length; i++) {
        rslt += arguments[i];
    }

    return arguments.length ? (rslt / arguments.length) : 0;
    
}
    
// la variable "arguments" pasa a ser una LISTA de todos los argumentos enviados
console.log(avg(10,30,20)); 
console.log(avg(...arr)); // ... nos separa el array. Sirve para desestructurar.

punto = { x: 10, y: 20};

function coordenadas (){
    return x + y;
}
coordenadas = (x,y) => x + y;
// console.log(coordenadas(punto.x, punto.y))
console.log(coordenadas(punto.x, punto.y));
//console.log(coordenadas(...punto));

(function () {
    console.log('ola');
})

//sintaxis distintas , mismo resultado
function suma(a, b) { return a + b; }
suma = (a, b) => a + b;


t = [10, "20", 30];
// función de orden superior (IMPORTANTE)
t.filter(item => item % 2) ;//devuelve impares
// los dos siguientes filtros hacen lo mismo, son funciones anonimas
t.filter(item => item > 10);
t.filter(function(item) { return item > 10} );

for(let i = 0; i < 10; i++){
    t.filter(function(item) {return item > a;})// creación de funciones anonimas
}

filtrado = function(item) {return item > a;};// guardamos la función anónima

for(let i = 0; i < 10; i++){
    t.filter(filtrado); // usa la función 10 veces pero no la crea 10 veces.
}

console.log(t.filter(filtrado));
// --------- ARRAYS JAVA SCRIPT ---------
// declaraciones de array, lo mismo hecho de distintas formas
let tab = new Array(); 
tab = [10, 20, 30];
tab[10] = 'otro' // podemos meter un valor a un indice inexistente, se les crea de relleno de forma undefined el resto
tab[5] = (a, b) => a + b; // función anónima
tab.push(hola = (a, b) => a + (b / 7)); // push mete un tipo al final de la colección
tab.splice(2,1); // eliminamos el índice 2

console.log('--------------------');

console.log('Indices');
for(v in tab) {
    console.log(v);
}

console.log('--------------------');

console.log('Valores');
for(v of tab) {
    console.log(v);
}

console.log('--------------------');
// objeto JS
let o = new Object();
o = {};
o.nombre = 'Pepito' 
o['apellidos'] = 'Grillo'
o.nom = () => o.nombre + ' ' + o.apellidos;
p = { nombre: 'Carmelo', apellidos: 'Cotón', nom : () => p.nombre + ' ' + p.apellidos}

console.log(`${o.nombre} ${o.apellidos} Función --> ${o.nom()} `);
console.log(`${p.nombre} ${p.apellidos} Función --> ${p.nom()} `);

function MiClase(elId, nombre){
    this.id = elId;
    this.nombre = nombre;
    this.muestraId = () => alert('El ID del objeto es '+ this.id);
    this.ponNombre = (nom) => this.nombre = nom;
}

let persona = new MiClase('1', 'Marc');
let persona2 = new MiClase('1', 'Marc');
console.log(persona);
persona.ponNombre('Hola')
console.log(persona);
//prototype está un escalón por encima de MiClase
MiClase.prototype.cotilla = () => console.log('Estoy en el prototipo'); // creación de método a nivel de prototipo, que esta por encima de constructor

persona.cotilla();