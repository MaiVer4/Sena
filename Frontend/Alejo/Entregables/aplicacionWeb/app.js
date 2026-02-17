const usuarios = [
    {
        "id": 1,
        "name": "Alejandro Gómez",
        "email": "alejandro.gomez@example.com"
    },
    {
        "id": 2,
        "name": "María Fernanda López",
        "email": "maria.lopez@example.com"
    },
    {
        "id": 3,
        "name": "Carlos Andrés Ruiz",
        "email": "carlos.ruiz@example.com"
    },
    {
        "id": 4,
        "name": "Laura Daniela Martínez",
        "email": "laura.martinez@example.com"
    },
    {
        "id": 5,
        "name": "Juan Sebastián Torres",
        "email": "juan.torres@example.com"
    }
];

//DOM
const btnBuscar = document.getElementById('btnBuscar');
const usuarioInput = document.getElementById('usuarioInput');
const resultado = document.getElementById('resultado');
const spinner = document.getElementById('spinner');

// PROMESA
function buscarUsuario(id) {
    return new Promise((resolve, reject) => {

        if (!id || id.trim() === '') {
            reject('Por favor ingresa un ID válido');
            return;
        }
        setTimeout(() => {
            const usuarioEncontrado = usuarios.find(u => u.id === parseInt(id));
            usuarioEncontrado ? resolve(usuarioEncontrado) : reject(`No se encontró el usuario con el ID: ${id}`);
        }, 2000);
    });
}

function mostrarSpinner() {
    spinner.style.display = 'flex';
    resultado.innerHTML = '';
}

function ocultarSpinner() {
    spinner.style.display = 'none';
}

function mostrarResultado(usuario) {
    resultado.innerHTML = `
        <div>
            <h2>Usuario Encontrado</h2>
            <p><strong>ID:</strong> ${usuario.id}</p>
            <p><strong>Nombre:</strong> ${usuario.name}</p>
            <p><strong>Email:</strong> ${usuario.email}</p>
        </div>
    `;
}

function mostrarError(error) {
    resultado.innerHTML = `<p style="color: red;">${error}</p>`;
}

btnBuscar.addEventListener('click', () => {
    const id = usuarioInput.value.trim();

    mostrarSpinner();

    buscarUsuario(id)
        .then((usuario) => {
            mostrarResultado(usuario);
        })
        .catch((error) => {
            mostrarError(error);
        })
        .finally(() => {
            usuarioInput.value = '';
            ocultarSpinner();
        });
});


usuarioInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        btnBuscar.click();
    }
});
