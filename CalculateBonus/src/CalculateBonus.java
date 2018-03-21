public class CalculateBonus {

	public static void main(String[] args) {
		Employee[] employee = new Employee[10];
		Company company = new Company();
		System.out.println("The revenue of the company is: "+company.getRevenue());
		System.out.println("The overall cost of the company is: "+company.getOverall());
		System.out.println("The profit of the company is: "+(int)(100*company.getProfit(company.income))+"%");
	    for(int i=0; i<10; i++) {
	    	employee[i] = new Employee();
	    }
		for(int i = 0; i <= 9; i++) {
			if((i+1)==1) System.out.println("The following information is about the 1st emolyee:");
			else if((i+1)==2) System.out.println("The following information is about the 2nd emolyee:");
			else if((i+1)==3) System.out.println("The following information is about the 3rd emolyee:");
			else System.out.println("The following information is about the "+(i+1)+"th emolyee:");
			System.out.println("salary: "+employee[i].getSalary());
			System.out.println("bonus: "+(int)Math.rint(employee[i].getSalary() * company.getProfit(company.income)));
		}
	}
}
