import java.util.Scanner;
public class Atm{
	public static double amount;
	public static String cardNumber;
	public String pin;
	public String type;
	CashDispenser cashDispenser = new CashDispenser();
	ReceiptPrinter receiptPrinter = new ReceiptPrinter();
	DespositUnit despositUnit = new DespositUnit();
	CardReader cardReader = new CardReader();
	public void handleTransaction() throws Exception{
		BankServer bankServer = new BankServer();
		CardReader cardReader = new CardReader();
		IOUnit ioUnit = new IOUnit();
		cardNumber = cardReader.readCard();
		int countTimes = 1;
		while(true) {
			pin = ioUnit.obtainCustomerPin();
			if (bankServer.verifyPin(cardNumber, pin)&&countTimes<=3) break;
			else if(countTimes < 3) {
				countTimes += 1;
				continue;
			}else {
				cardReader.confiscateCard();
				break;
			}
		}
		Transaction transaction = ioUnit.obtainTransaction();
		type = transaction.getType();
		if(type.equals("a") || type.equals("b"))
			amount = ioUnit.obtainAmount();
	}
	public void takeMoney() {
		cashDispenser.dispenseCash((int)amount);
		despositUnit.takeDepositEnvelope();
	}
	public void needReceipt() {
		System.out.println("Transaction finished! Do you need a receipt? y for yes and others for no");
		Scanner imput = new Scanner(System.in);
		String justfy = imput.next();
		receiptPrinter.printReceipt(justfy);
	}
	public void takeCard() {
		cardReader.releaseCard();
	}
}

class CashDispenser{
	public void dispenseCash(int amount) {
		System.out.println("Your cash is ready, please take it! The amount is:"+amount);
	}
	
}

class DespositUnit{
	public void takeDepositEnvelope() {
		System.out.println("Please take your envelope!");
	}
}

class ReceiptPrinter{
	public void printReceipt(String str) {
		if(str.equals("Y")||str.equals("y")) {
			System.out.println("Please take your receipt");
		}
	}
}

class CardReader{
	public String readCard() {
		System.out.println("Please enter your cardNumber:");
		Scanner imput = new Scanner(System.in);
		String cardNumber = imput.next();
		return cardNumber;
	}
	public void confiscateCard() {
		System.out.println("Your card has been confiscated due to you can't provide correct pin!");
		System.exit(0);
	}
	public void releaseCard() {
		System.out.println("Your card has been released!");
	}
}

class IOUnit{
	public String obtainCustomerPin() {
		System.out.println("Please enter your pin:");
		Scanner imput = new Scanner(System.in);
		String customerPin = imput.next();
		return customerPin;
	}
	public Transaction obtainTransaction() {
		Transaction transaction = new Transaction();
		return transaction;
	}
	public double obtainAmount(){
		Scanner imput = new Scanner(System.in);
		double amount = imput.nextDouble();
		return amount;
	}
}

class Transaction{
	private String type;
	Transaction(){
		CardReader cardReader = new CardReader();
		System.out.println("Please enter:");
		System.out.println("a: deposit");
		System.out.println("b: withdraw");
		System.out.println("c: query");
		Scanner imput = new Scanner(System.in);
		String type = imput.next();
		if(type.equals("a")){
			System.out.println("Plese enter the amount you what to deposit:");
			this.type = type;
		}else if(type.equals("b")){
			System.out.println("Please enter the amount you what to withdraw(only integer is allowed):");
			this.type = type;
		}else if(type.equals("c")){
			this.type = type;
		}else {
			System.out.println("You have entered illegal String! Please take you card!");
			cardReader.releaseCard();
			System.exit(0);
		}
	}
	public String getType() {
		return this.type;
	}
}