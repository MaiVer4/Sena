import java.util.Scanner;

public class ejercisio4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa numero 1: ");
        int numero1 = sc.nextInt();

        System.out.print("Ingresa numero 1: ");
        int numero2 = sc.nextInt();

        if (numero1 > numero2) {
            System.out.println(numero1 + " Es mayor que " + numero2);
        } else if (numero2 > numero1) {
            System.out.println(numero2 + " Es mayor que " + numero1);
        } else {
            System.out.println(numero1 + " Es igual que " + numero2);
        }
    }
}
