
public class Controler {
	
	static Controler controlerInstance;
	
	private Controler() {}
	
	public static Controler getInstance() {
		if(controlerInstance == null) {
			controlerInstance = new Controler();
		}
		return controlerInstance;
	}
	
	public void run() {
		RuleManager manager = RuleManager.getInstance();
		int c = 6;
		double a[] = {1, 2, 3, 4, 5, 6};
		double b[] = {1, 2, 3, 4, 5, 6};
		TaxRule taxRule = new TaxRule(c, a, b);
		manager.modify(taxRule);
	}
}
