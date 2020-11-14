import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    ArrayList<Account> acctList;

    public User(String name, String password, Account account[]) {
        this.name = name;
        this.password = password;
        acctList = new ArrayList<Account>(Arrays.asList(account)); //this line is for debugging purposes, delete later
        //acctList = new ArrayList<Account>(); commented out for debugging purposes, uncomment later
    }

    public void closeAccount(Account accountType){
        if(acctList.contains(accountType)){
            if(accountType.checkIfEmpty()){
                acctList.remove(accountType);
            }
            else {
                System.out.println("Please empty your account before closing");
            }
        }

    }

    public void openAccount(Account accountType){

    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public ArrayList<Account> getAccounts(){
        return acctList;
    }


}
