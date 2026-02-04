//Personaje X

let nombre = "Aurelio";
let anime = "No soy otaku";
let edad = 23;


let personaje = {
    nombre: "Auerelio",
    anime: "No soy otaku",
    edad: 22,
};
console.log(personaje);
console.log(personaje['edad']);


personaje.edad = 18;

let llave = edad;
personaje['llave'] = 19;

console.log(llave);

delete personaje.anime;

 console.log(personaje);