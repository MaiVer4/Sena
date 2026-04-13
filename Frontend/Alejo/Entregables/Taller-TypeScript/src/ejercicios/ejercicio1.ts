console.log("Inicio del programa");
type TanksStatus = "pendiente" | "en_progreso" | "finalizada";

interface Task {
    id: number;
    description: string;
    isComplete: boolean;
    status: TanksStatus;
}

function getPendingAndProgressTasks(tasks: Task[]): Task[] {
    return tasks.filter(task => task.status !== "finalizada");
}

// Datos de prueba
const tasks: Task[] = [
    { id: 1, description: "Estudiar TS", isComplete: false, status: "pendiente" },
    { id: 2, description: "Hacer ejercicio", isComplete: false, status: "en_progreso" },
    { id: 3, description: "Dormir", isComplete: true, status: "finalizada" }
];

const result = getPendingAndProgressTasks(tasks);
console.log(result);