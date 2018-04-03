public class ManageBankAccounts {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Atm atm = new Atm();
		BankServer bankServer = new BankServer();
		atm.handleTransaction();
		bankServer.setNow();
		if(atm.type.equals("a")) {
			bankServer.doDeposit(Atm.cardNumber, Atm.amount);
		}else if(atm.type.equals("b")){
			if(bankServer.verifyFunds(Atm.cardNumber, Atm.amount)){
				bankServer.doWithdraw(Atm.cardNumber,(int)Atm.amount);
			}else {
				System.out.println("You don't have enough money, please take your card!");
				bankServer.releaseTemp();
				System.exit(1);
			}
		}else {
			bankServer.doQuery(Atm.cardNumber);
		}
		atm.needReceipt();
		atm.takeCard();
		bankServer.createTemp();
		bankServer.releaseTemp();

	}
}
