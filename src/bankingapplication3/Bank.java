
package bankingapplication3;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {
    private String bankName;
    
    public Bank(){
    }
    
    public Bank(String bankName){
        this.bankName = bankName;
    }
    
    public String getBankName(){
        return this.bankName;
    }
    
    public void listAccount(){
        Connection con = BankConnection.connect();
        try {
            Statement statement= con.createStatement();
            String sql = "select * from account";
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                System.out.println(results.getString(1)+" "+results.getString(2)+" "+results.getString(3)+" "+results.getString(4));
            }
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    public void openAccount(Account account){
        Connection con = BankConnection.connect();
        String sql = "insert into account(accId,accName,accBalance,accType)"
                    + "values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setInt(1,account.getAccountNumber() );
            preparedStatement.setString(2,account.getAccountName() );
            preparedStatement.setDouble(3,account.getBalance() );
            preparedStatement.setString(4,account.getAccountType() );
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeAccount(Account account){
        Connection con = BankConnection.connect();
        String sql = "delete from account where accID = ?";
        try {
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setInt(1,account.getAccountNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void depositMoney(Account account,double amonut){
        account.deposit(amonut);
        Connection con = BankConnection.connect();
        String sql = "update account set accBalance = ? where accID = ?";
        try {
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void withdrawMoney(Account account,double amonut){
        account.withdraw(amonut);
        Connection con = BankConnection.connect();
        String sql = "update account set accBalance = ? where accID = ?";
        try {
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Account getAccount(int number){
        Connection con = BankConnection.connect();
        Account account = null;
        String sql = "select * from account where accID = ?";
        try {
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setInt(1, number);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            String accountName = result.getString(2);
            double balance = result.getDouble(3);
            //account = new Account (number,accountName,balance);
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }
     
}
