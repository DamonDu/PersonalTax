
public class Calculator {
	
	public static double calculate(double income) {
		TaxRule taxRule = RuleManager.getInstance().getTaxRule();
		int numOfLevel = taxRule.getNumOfLevel();
		double threshold[] = taxRule.getThreshold();
		double taxRate[] = taxRule.getTaxRate();
		int level = 0;
		double tax = 0;
		
		for(int i = 0; i < numOfLevel; i++) {
			if(income > threshold[i]) {
				level++;
			}
		}
		
		for(int i = 0; i < level; i++) {
			if(level - i > 1) {
				tax = tax + (threshold[i + 1] - threshold[i]) * taxRate[i];
			}
			else {
				tax = tax + (income - threshold[i]) * taxRate[i];
			}
		}
		return tax;
	}
}
