import java.util.Scanner;
 
public class PersonalTax {
	public static void main(String[] arg) {	
		Scanner scanner = new Scanner(System.in);
		Controler controler = Controler.getInstance();
		double income = scanner.nextDouble();
		System.out.println(controler.run(income));
	}
}
