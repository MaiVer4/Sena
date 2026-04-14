type Rol = "admin" | "editor" | "visitante";

interface Usuario {
  nombre: string;
  edad: number;
  activo: boolean;
  rol: Rol;
}

function filtrarUsuarios(usuarios: Usuario[]): Usuario[] {
  return usuarios.filter(usuario =>
    usuario.edad >= 18 &&
    usuario.activo &&
    usuario.rol !== "visitante"
  );
}

// Pruebas
const usuarios: Usuario[] = [
  { nombre: "Juan", edad: 20, activo: true, rol: "admin" },
  { nombre: "Ana", edad: 17, activo: true, rol: "editor" },
  { nombre: "Luis", edad: 25, activo: false, rol: "admin" },
  { nombre: "Sofia", edad: 22, activo: true, rol: "visitante" },
  { nombre: "Pedro", edad: 30, activo: true, rol: "editor" }
];

const resultado = filtrarUsuarios(usuarios);

console.log(resultado);