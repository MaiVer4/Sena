type CategoriaProductos = "alimentos" | "tecnologia" | "papeleria";

interface Producto {
    id: number;
    nombre: string;
    cantidad: number;
    categoria: CategoriaProductos;
}

const reglasStock: Record<CategoriaProductos, number> = {
    alimentos: 20,
    tecnologia: 5,
    papeleria: 50
};

function bajoStock(Producto: Producto) {
    const limite = reglasStock[Producto.categoria];
    return {
        bajoStock: Producto.cantidad < limite,
        limite,
        actual: Producto.cantidad
    };
}

// Pruebas
const productos: Producto[] = [
    { id: 1, nombre: "Arroz", cantidad: 10, categoria: "alimentos" },
    { id: 2, nombre: "Laptop", cantidad: 3, categoria: "tecnologia" },
    { id: 3, nombre: "Cuaderno", cantidad: 60, categoria: "papeleria" }
];

productos.forEach(p => {
    console.log(p.nombre, bajoStock(p));
});

