
public class Controler {
	
	static Controler controlerInstance;
	
	private Controler() {}
	
	public static Controler getInstance() {
		if(controlerInstance == null) {
			controlerInstance = new Controler();
		}
		return controlerInstance;
	}
	
	public double run(double income) {
		RuleManager manager = RuleManager.getInstance();
		return Calculator.calculate(income);
	}
}
