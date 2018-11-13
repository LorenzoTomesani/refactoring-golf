package payroll.refactoring07;

/**
 * The check for null is a bit clumsy. Is there a better way?
 */

public class BandedTaxCalculator implements TaxCalculator {
	private final double minimumGross;
	private final double taxRate;
	private final TaxCalculator lowerBandCalculator;

	public BandedTaxCalculator(double minimumGross, double taxRate, TaxCalculator lowerBandCalculator) {
		this.minimumGross = minimumGross;
		this.taxRate = taxRate;
		this.lowerBandCalculator = lowerBandCalculator;
	}
	
	@Override
	public double taxFor(double grossSalary) {
		return getTaxForBand(grossSalary) + getTaxForLowerBands(grossSalary);
	}

	private double getTaxForLowerBands(double grossSalary) {
		return lowerBandCalculator == null ? 0 : lowerBandCalculator.taxFor(getGrossToTaxAtLowerBand(grossSalary));
	}

	private double getGrossToTaxAtLowerBand(double grossSalary) {
		return Math.min(minimumGross, grossSalary);
	}

	private double getTaxForBand(double grossSalary) {
		return getGrossInBand(grossSalary) * taxRate;
	}

	private double getGrossInBand(double grossSalary) {
		return Math.max(0, grossSalary - minimumGross);
	}
}