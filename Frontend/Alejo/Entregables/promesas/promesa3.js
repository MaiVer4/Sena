function obterEdad(){
    return new Promise((resolve, rejec) =>{
        setTimeout(() =>{
            resolve(17)
        }, 2000)
    })
}

function verificarMayorEdad(edad){
    return new Promise((resolve, rejec) =>{
        if(edad >= 18){
            resolve("Eres Mayor de edad!!")
        } else {
            rejec("Eres menor de edad!!")
        }
    })
}

obterEdad()
    .then((edad) => {
        return verificarMayorEdad(edad);
    })
    .then((mensaje) =>{
        console.log(mensaje)
    })
    .catch((error) =>{
        console.log(error)
    })