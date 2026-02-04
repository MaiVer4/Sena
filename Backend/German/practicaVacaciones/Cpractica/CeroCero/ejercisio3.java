import java.util.Scanner;

public class ejercisio3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresa un numero para determinar si es par o impar: ");
        int numero = sc.nextInt();

        if (numero % 2 == 0) {
            System.out.println(numero + " Es par");
        } else {
            System.out.println(numero + " Es impar");
        }
    }
}
