import { storage, api } from "./Services.js";
import { form, input, apiButton, renderTasks } from "./ui.js";

/**
 * Responsabilidad: coordinar el estado, los eventos y la inicialización.
 * No manipula el DOM directamente ni conoce detalles de almacenamiento o red.
 */
let tasks = storage.load();

function render() {
    renderTasks(tasks, {
        onToggle: (index) => {
            tasks[index].completed = !tasks[index].completed;
            storage.save(tasks);
            render();
        },
        onDelete: (index) => {
            tasks.splice(index, 1);
            storage.save(tasks);
            render();
        },
    });
}

/*********************
 * EVENTOS
 *********************/
form.addEventListener("submit", (e) => {
    e.preventDefault();
    const value = input.value.trim();
    if (!value) return;

    tasks.push({ title: value, completed: false });
    input.value = "";
    storage.save(tasks);
    render();
});

apiButton.addEventListener("click", async () => {
    try {
        const apiTasks = await api.fetchTasks();
        apiTasks.forEach((t) => {
            tasks.push({ title: t.title, completed: t.completed });
        });
        storage.save(tasks);
        render();
    } catch (error) {
        alert("Error cargando tareas");
    }
});

/*********************
 * INICIALIZACIÓN
 *********************/
render();
