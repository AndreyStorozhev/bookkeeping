public interface ConnectionForBD {
    void insertToBDIncome(int value);
    void insertToBDExpenses(int value);
    void selectToBDIncome(String from, String to);
    void selectToBDExpenses(String from, String to);
}
