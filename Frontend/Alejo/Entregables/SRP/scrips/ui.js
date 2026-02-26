export const form = document.getElementById("task-form");
export const input = document.getElementById("task-input");
export const list = document.getElementById("task-list");
export const apiButton = document.getElementById("load-api");

/**
 * Responsabilidad: pintar la lista de tareas en el DOM.
 * Recibe los datos y callbacks, no maneja estado ni lógica de negocio.
 */
export function renderTasks(tasks, { onToggle, onDelete }) {
    list.innerHTML = "";

    tasks.forEach((task, index) => {
        const li = document.createElement("li");
        li.textContent = task.title;

        if (task.completed) {
            li.classList.add("completed");
        }

        li.addEventListener("click", () => onToggle(index));

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "X";
        deleteBtn.addEventListener("click", (e) => {
            e.stopPropagation();
            onDelete(index);
        });

        li.appendChild(deleteBtn);
        list.appendChild(li);
    });
}

