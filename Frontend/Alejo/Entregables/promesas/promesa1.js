
const nombre = "Mai";

function saludar(nombre) {
    return new Promise((resolve, rejec) => {
        setTimeout(() => {
            if (nombre === "Maicol") {
                resolve("Hola " + nombre + " Bienvenido!")
            } else {
                rejec("No te conozco bro!!")
            }
        }, 2000)
    });
}

saludar("Mai")
    .then((mensaje) => {
            console.log(mensaje)
        })
    .catch((error) => {
        console.log(error)
    });

 
