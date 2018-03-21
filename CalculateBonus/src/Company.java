public class Company {
	private int revenue = 20000000 - (int)(Math.random()*10000000);
	private int overall = 15000000 - (int)(Math.random()*5000000);
	public int income = revenue - overall;
	public float getProfit(int income) {
		if(income <= 0) return 0;
		else if (income < 2000000) return 0.05f;
		else if (income < 5000000) return 0.1f;
		else return 0.15f;
	}
	public int getRevenue() {
		return revenue;
	}
	public int getOverall() {
		return overall;
	}
}
