// 1. Servicio de Almacenamiento (LocalStorage)
export const storage = {
    save: (data) => {
        localStorage.setItem("tasks", JSON.stringify(data));
    },
    load: () => {
        const data = localStorage.getItem("tasks");
        return data ? JSON.parse(data) : [];
    }
};

// 2. Servicio de API (Peticiones externas)
export const api = {
    async fetchTasks() {
        const response = await fetch("https://jsonplaceholder.typicode.com/todos?_limit=5");
        if (!response.ok) throw new Error("Error en la red");
        return await response.json();
    }
};

