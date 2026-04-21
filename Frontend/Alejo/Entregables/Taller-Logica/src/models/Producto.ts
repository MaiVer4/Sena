export class Producto {
   id: number;
   nombre: string;
   precio: number;
   stock: number;
   

    constructor(id: number, nombre: string, precio: number, stock: number) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
}