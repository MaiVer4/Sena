import java.util.Scanner;

public class ejercisio8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("===Bienvenido===\n");

        System.out.println("---Menu---");
        System.out.println("1: Saludar");
        System.out.println("2: Despedirse");
        System.out.println("3: Salir");


        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("Hola brou!, Esto es un saludo");
                break;
        
            case 2:
                System.out.println("Adios brou!, Esto es una despedida pero no para siempre");
                break;
            case 3: 
                System.out.println("Estas Saliendo!!");
                break;
            default: 
            System.out.println("Ingresa una opcion correcta!");
            break;
        }
    }

}
