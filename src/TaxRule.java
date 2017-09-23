
public class TaxRule {
	
	int numOfLevel;
	double threshold[];
	double taxRate[];
	
	public TaxRule(){
		this(0, null, null);
	}
	
	public TaxRule(int numOfLevel, double[] threshold, double[] taxRate) {
		this.numOfLevel = numOfLevel;
		this.threshold = threshold;
		this.taxRate = taxRate;
	}
	
	public int getNumOfLevel() { return this.numOfLevel;}
	public void setNumOfLevel(int numOfLevel) { this.numOfLevel = numOfLevel; }
	public double[] getThreshold() { return this.threshold;}
	public void setThreshold(double[] threshold) { this.threshold = threshold; }
	public double[] getTaxRate() { return this.taxRate;}
	public void setTaxRate(double[] taxRate) { this.taxRate = taxRate; }
}
