import java.util.Scanner;

public class ejercisio9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingresa tu edad: ");
        int edad = sc.nextInt();

        if (edad >= 120 || edad < 0) {
            System.out.println("Lo sentimos, Tu edad no es admitidad:(");
        } else {
            System.out.println("Bienvenido");
        }
    }
}
