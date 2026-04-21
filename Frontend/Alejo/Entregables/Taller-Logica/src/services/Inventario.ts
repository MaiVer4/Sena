import { Producto } from "../models/Producto.js";

export class Inventario {
    private productos: Producto[] = [];

    agregar(nuevoProducto: Producto): void {
        this.productos.push(nuevoProducto);
    }

    listarTodos(): Producto[] {
        return this.productos;
    }

    buscarPorNombre(nombre: string): Producto | undefined{
       return this.productos.find(p => p.nombre.toLowerCase() === nombre.toLowerCase())  
    }

    calcularTotal(): number {
        return this.productos.reduce((acc, p) => {
            return acc + (p.precio * p.stock);
        }, 0)
    }
}

