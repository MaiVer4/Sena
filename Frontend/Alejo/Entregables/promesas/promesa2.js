function obternerNumero() {
    return new Promise((resolve, rejec) => {
        setTimeout(() => {
             resolve(10)
        }, 2000);
    })
}

function multiplicarPorDos(numero) {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve(numero * 2)
        }, 2000)
    })
}

obternerNumero()
    .then((numero) => {
        return multiplicarPorDos(numero)
    })
    .then((resultado) => {
        console.log(resultado)
    })
    .catch((error) => {
        console.log(error)
    })