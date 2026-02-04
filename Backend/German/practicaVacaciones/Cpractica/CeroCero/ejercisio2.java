import java.util.Scanner;

public class ejercisio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa un numero: ");
        int numero = sc.nextInt();

        System.out.print("Ingresa otro numero:");
        int numero2 = sc.nextInt();

        int suma = numero + numero2;
        int resta = numero - numero2;
        int multiplicacion = numero * numero2;
        int divicion = numero / numero2;

        System.out.println("Suma: " + suma);
        System.out.println("Resta:" + resta);
        System.out.println("Multiplicacion:" + multiplicacion);
        System.out.println("Divicion:" + divicion);

    }
}
