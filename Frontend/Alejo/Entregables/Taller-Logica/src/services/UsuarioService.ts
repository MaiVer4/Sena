import type { Usuario } from "../models/Usuario.js";

export class UsuarioService {
    private usuarios: Usuario[] = [];

    crear(nuevoUsuario: Usuario): void {
         this.usuarios.push(nuevoUsuario);
         console.log(`Usuario ${nuevoUsuario.nombre} creado con éxito.`);
    }

    desactivar(id: number): void { 
        const usuarioEncontrado = this.usuarios.find( u => u.id === id);
         if (usuarioEncontrado) {
            usuarioEncontrado.activo = false;
            console.log(`Usuario ${id} desactivado!`)
         } else {
            console.warn(`No se encontro al ususario con ID: ${id}`)
         }
    }

    listarActivos(): Usuario[] {
        return this.usuarios.filter(u => u.activo);
    }


}