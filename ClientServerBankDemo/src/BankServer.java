import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Scanner;
public class BankServer {
	BankServer() throws Exception{
		ObtainLine obtainLine = new ObtainLine();
		savingAccount = new SavingAccount[lineNum];
		checkingAccount = new CheckingAccount[lineNum];
		loadAccounts("Account.csv");
	}
	public static int now;
	public static int lineNum;
	public static SavingAccount[] savingAccount;
	public static CheckingAccount[] checkingAccount;
	Atm atm = new Atm();
	public void setNow() throws Exception{
		now = ObtainLine.ObtainNow();
	}
	public void releaseTemp() {
		AccountToTemp accountToTemp = new AccountToTemp();
	}
	public void createTemp() {
		TempToAccount tempToAccount = new TempToAccount();
	}
	public boolean verifyPin(String cardNumber, String pin) {
        try {  
            BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
            String line = null;  
            while((line=br.readLine())!=null){  
                String item[] = line.split(",");
                if(cardNumber.equals(item[1])&&pin.equals(item[3])){
                		br.close();
                		return true;
                	}
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace(); 
        } 
        return false;
	}
	public boolean verifyFunds(String cardNumber, double amount) throws Exception{
        try {  
            BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
            String line = null;  
            while((line=br.readLine())!=null){  
                String item[] = line.split(",");
                if(cardNumber.equals(item[1])&&amount<=Double.parseDouble(item[4])){ 
                		br.close();
                		return true;
                	}
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace(); 
        } 
        return false;
	}
	public void doWithdraw(String custNumber, int amount) throws Exception{
		if(ObtainLine.isSavingAccount()){
			savingAccount[now-1].setBalance(savingAccount[now-1].getBalance()-amount);
		}else {
			checkingAccount[now-1].setBalance(checkingAccount[now-1].getBalance()-amount);
		}
		atm.takeMoney();
	}
	public void doDeposit(String custNumber, double amount) throws Exception{
		if(ObtainLine.isSavingAccount()){
			savingAccount[now-1].setBalance(amount+savingAccount[now-1].getBalance());
			System.out.println("the total balance is: " + savingAccount[now-1].getBalance());   
		}else {
			checkingAccount[now-1].deposit(Atm.amount);
		}
	}
	public double doQuery(String custNumber) throws Exception{
		Double balance;
		if(ObtainLine.isSavingAccount()) balance = savingAccount[now-1].getBalance();
		else balance = checkingAccount[now-1].getBalance();
		System.out.println("Your balance is:" + balance);
		return balance;		
	}
	private void loadAccounts(String filename) throws Exception{
		int i = 0;
            BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
            String line = null;  
            while((line=br.readLine())!=null){  
                String item[] = line.split(",");
            	if(item[0].equals("S")) {
            		savingAccount[i] = new SavingAccount();
            		savingAccount[i].setAccountId(item[1]);
            		savingAccount[i].setCustomerId(item[2]);
            		savingAccount[i].setPin(item[3]);
            		savingAccount[i].setBalance(Double.parseDouble(item[4]));
            		savingAccount[i].setInterestRate(Double.parseDouble(item[5]));
            	}else{
            		checkingAccount[i] = new CheckingAccount();
            		checkingAccount[i].setAccountId(item[1]);
            		checkingAccount[i].setCustomerId(item[2]);
            		checkingAccount[i].setPin(item[3]);
            		checkingAccount[i].setBalance(Double.parseDouble(item[4]));
            		checkingAccount[i].setLastDepositAmount(Double.parseDouble(item[5]));
            		checkingAccount[i].setYear(item[6]);
            		checkingAccount[i].setMonth(item[7]);
            		checkingAccount[i].setDay(item[8]);
            		checkingAccount[i].setHour(item[9]);
            		checkingAccount[i].setMinute(item[10]);
            	}
            	i += 1;
            }
	}
}
class Account{
	private String accountId;
	private double balance;
	private String customerId;
	private String pin;
	public void deposit(double amount) {
		this.setBalance(this.getBalance() + amount);
	}
	public void withdraw(double amount) {
		this.setBalance(this.getBalance() - amount);
	}
	public boolean equals(Object obj) {
		if (obj.equals(getPin())) return true;
		else return false;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}

class SavingAccount extends Account{
	private double interestRate;
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public void applyMonthlyInterest() {
		this.setBalance(this.getBalance()*(1 + this.getInterestRate()));
	}
}

class CheckingAccount extends Account{
    Calendar cal = Calendar.getInstance();
	private double lastDepositAmount;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	public void deposit(double amount) {
		System.out.println("the last deposit date is: "+this.month+"/"+this.day+"/"+this.year+"  "+this.hour+":"+this.minute);
		System.out.println("the last deposit amount is: " + getLastDepositAmount());
        this.year = ""+cal.get(Calendar.YEAR);//获取年份
        this.month=""+(1+cal.get(Calendar.MONTH));//获取月份
        this.day=""+cal.get(Calendar.DATE);//获取日
        this.hour=""+cal.get(Calendar.HOUR);//小时
        this.minute=""+cal.get(Calendar.MINUTE);//分
        this.setBalance(this.getBalance()+amount);
        this.setLastDepositAmount(amount);
		System.out.println("the total balance is: " + this.getBalance());   
	}
	public double getLastDepositAmount() {
		return lastDepositAmount;
	}
	public void setLastDepositAmount(double lastDepositAmount) {
		this.lastDepositAmount = lastDepositAmount;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
}
class AccountToTemp{
	AccountToTemp(){
		try {  
            BufferedReader br = new BufferedReader(new FileReader("Temp.csv"));
            String line = null;  
            FileWriter fw = new FileWriter("Account.csv");
            BufferedWriter bufw = new BufferedWriter(fw);
            while((line=br.readLine())!=null) {
                	bufw.write(line);    
                    bufw.newLine();
            }
            bufw.flush();   
        }
        catch (Exception e) {
            e.printStackTrace(); 
        } 
	}
}
class TempToAccount{
	TempToAccount(){
		try {  
            BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
            String line = null;  
            FileWriter fw = new FileWriter("Temp.csv");
            BufferedWriter bufw = new BufferedWriter(fw);
            int i = 1;
            while((line=br.readLine())!=null) {
            	if(i == BankServer.now) {
            		if(ObtainLine.isSavingAccount()) {
            			bufw.write("S"+",");
            			bufw.write(BankServer.savingAccount[i-1].getAccountId()+",");
            			bufw.write(BankServer.savingAccount[i-1].getCustomerId()+",");
            			bufw.write(BankServer.savingAccount[i-1].getPin()+",");
            			bufw.write(BankServer.savingAccount[i-1].getBalance()+",");
            			bufw.write(BankServer.savingAccount[i-1].getInterestRate()+",");
                        bufw.newLine();
            		}else {
            			bufw.write("C"+",");
            			bufw.write(BankServer.checkingAccount[i-1].getAccountId()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getCustomerId()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getPin()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getBalance()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getLastDepositAmount()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getYear()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getMonth()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getDay()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getHour()+",");
            			bufw.write(BankServer.checkingAccount[i-1].getMinute()+",");
                        bufw.newLine();
            		}
            	}else {
                	bufw.write(line);    
                    bufw.newLine();
            	}
                i += 1;
            }
            bufw.flush();   
            bufw.close();
        }
        catch (Exception e) {
            e.printStackTrace(); 
        } 
	}
}
class ObtainLine {
	ObtainLine(){
		try {  
            BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
            String line = null;  
            int sum = 0;
            while((line=br.readLine())!=null){  
            	sum = sum + 1;
            	BankServer.lineNum = sum;
            }
        }
        catch (Exception e) {
            e.printStackTrace(); 
        } 
	}
	public static int ObtainNow() throws Exception{
		  BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
          String line = null;  
          int sum = 0;
          while((line=br.readLine())!=null){  
            String item[] = line.split(",");
          	sum = sum + 1;
          	if(item[1].equals(Atm.cardNumber)) return sum;
          }
          return 0;
	}
	public static boolean isSavingAccount() throws Exception{
		  BufferedReader br = new BufferedReader(new FileReader("Account.csv"));
          String line = null;  
          int i = 1;
          while((line=br.readLine())!=null){ 
              String item[] = line.split(",");
        	  if(i == BankServer.now && item[0].equals("S"))return true;
        	  i += 1;
          }
          return false;
	}
}