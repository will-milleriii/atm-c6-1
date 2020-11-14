import com.sun.tools.javac.comp.Check;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class AtmProcessor {
    private HashMap<String,User> userInfo;
    private String signedIn;
    private String acctAccessed;
    private HashMap<Integer, Account> tempAcctMap;

    public AtmProcessor(){
    userInfo = new HashMap<String, User>();
    signedIn = "No one is signed in";
    tempAcctMap = new HashMap<Integer, Account>();
    Account[] accounts = {new Checking(100.00), new Savings(1000.00)}; //debugging
    userInfo.put("zekaihe", new User("zekai","password", accounts)); //debugging
    }

    public Integer readIntInput(){
        Scanner prompt = new Scanner(System.in);
        Integer output = null;
        try { //checks for integer input
            return prompt.nextInt();
        }
        catch(InputMismatchException e){
            System.out.println("Please enter valid input");
        }
        return output;
    }

    public String readStrInput(){
        Scanner prompt = new Scanner(System.in);
        return prompt.nextLine();
    }

    public void startScreen(){
        System.out.println("WELCOME TO C6 BANK!\n"+
                "1. Log In\n"+
                "2. Create a New Account");

        while (true){
            Integer userInput = readIntInput();
            try {
            promptLogin(userInput);
            }
            catch(NullPointerException e){
            }
        }
    }

    //used in startScreen()
    public boolean promptLogin(Integer userInput){
        boolean loop= true;
            switch (userInput) {
                case 1:
                    logInScreen();
                case 2:
                    registerScreen();
                default:
                    System.out.println("Please enter valid input");
                    break;
            }
        return loop;
    }

    public void registerScreen() {
        String username = "";
        String name = "";
        String password;
        System.out.println("NEW USER REGISTRATION");
        boolean loopName = true;
        boolean loopUserName = true;
        while (loopName) {
            name = promptName();
            loopName = newName(name);
        }
        while(loopUserName) {
            username = promptUsername();
            loopUserName = newUsername(username);
        }
        password = promptPassword();
        userInfo.put(username, new User(name,password,new Account[0])); //adds new user to list, no accounts have been created. 3rd arg, account is only there for debugging, remove after
    }

    //used in registerScreen()
    private String promptName() {
        System.out.println("Please Enter Full Name:");
        return readStrInput();
    }

    //used in registerScreen()
    private boolean newName(String name) {
        boolean noExist = true;
        for(User u: userInfo.values()){
            if(u.getName().equals(name)) {
                System.out.println("You are already an account with C6 Bank, please log in");
                logInScreen();
            }
            else{
                noExist = false;
            }
        }
        return noExist;
    }

    // used in registerScreen()
    public boolean newUsername(String username) {
        boolean noExist = true;
            if(!userInfo.containsKey(username)){
                noExist = false;
            }
            else{
                System.out.println("This username has been taken");
            }
        return noExist;
    }

    public void logInScreen() {
        String username;
        String password;
        System.out.println("LOG IN\n");
        boolean loop = true;
        while (loop){
            username = promptUsername();
            password = promptPassword();
            loop = validateUserPass(username, password);
        }
    }

    //used in logInScreen()
    public String promptUsername() {
        System.out.println("Please Enter Username:");
        return readStrInput();
    }

    //used in logInScreen()
    public String promptPassword() {
        System.out.println("Please Enter Password:");
        return readStrInput();
    }

    //used in logInScreen()
    public boolean validateUserPass(String username, String password) {
        boolean notValidated = true;

        if(userInfo.containsKey(username) && userInfo.get(username).getPassword().equals(password)){
                signedIn = username;
                accountScreen();
                notValidated = false;
        }
        else {
            System.out.println("Invalid combination of Username and Password");
        }

        return notValidated;
    }

    public void accountScreen() {
        ArrayList<Account> acctList = new ArrayList<Account>(userInfo.get(signedIn).getAccounts()); //retrieves account list from user that is signed in
        System.out.println("ACCOUNTS SCREEN\n");
        for(int i = 0; i< acctList.size(); i++){
            System.out.printf("%d. %s\n",i+1,acctList.get(i).getClass().getSimpleName());
            tempAcctMap.put(i+1,acctList.get(i)); //putting into hashmap to recall later in promptAcctScrn
            if(i == acctList.size()-1){ //prints "log out" to the end of menu
                System.out.printf("%d. Log out\n",i+2);
            }
            if(acctList.size()<3 && i == acctList.size()-1){ //prints "Create an account" to the end of menu
                System.out.printf("%d. Create an account!\n",i+3);
            }
        }

        boolean loop = true;
        while (loop){
            Integer userInput = readIntInput();
            try {
                loop = promptAcctScrn(userInput);
            }
            catch(NullPointerException e){
            }
        }
    }

    private boolean promptAcctScrn(Integer userInput) {
        boolean loop= true;
        switch (userInput) {
            case 1:
                acctAccessed = tempAcctMap.get(1).getClass().getSimpleName();
                //acctActionScrn(); need to do
            case 2:
                acctAccessed = tempAcctMap.get(1).getClass().getSimpleName();
            default:
                System.out.println("Please enter valid input");
                break;
        }
        return loop;
    }

    //For debugging
    public HashMap<String,User> returnUser(){
        return userInfo;
    }
}


