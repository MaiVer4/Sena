type EmailNotification = {
    type: "email";
    email: string;
    subject: string;
    message: string;
}

type SMSNotification = {
    type: "sms";
    phone: string;
    message: string;
}

type PushNotification = {
    type: "push";
    deviceId: string;
    message: string;
}

type Notification = EmailNotification | SMSNotification | PushNotification;

function enviarNotificacion(notification: Notification): void {

    switch (notification.type) {
        case "email":
            console.log(`Enviando EMAIL a ${notification.email}`);
            console.log(`Asunto: ${notification.subject}`);
            break;
        case "sms":
            console.log(`Enviando SMS a ${notification.phone}`);
            break;
        case "push":
            console.log(`Enviando PUSH a dispositivo ${notification.deviceId}`);
            break;
    }
}

// Pruebas
const email: Notification = {
    type: "email",
    email: "test@gmail.com",
    subject: "Hola",
    message: "Bienvenido"
};

const sms: Notification = {
    type: "sms",
    phone: "3001234567",
    message: "Código 1234"
};

const push: Notification = {
    type: "push",
    deviceId: "device123",
    message: "Nueva notificación"
};

enviarNotificacion(email);
enviarNotificacion(sms);
enviarNotificacion(push);