
public class PersonalTax {
	public static double main(String[] arg) {
		Controler controler = Controler.getInstance();
		return controler.run(Double.parseDouble(arg[0]));
	}
}
