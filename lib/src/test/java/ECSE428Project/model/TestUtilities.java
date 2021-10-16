package ECSE428Project.model;

public class TestUtilities {

    public static Account createAccount(int seed) {
        return new Account("accountName"+seed, "accountEmail"+seed+"@a.ca", "password"+seed, false, false, 0, 0);
    }
}
