package iqTask.moneyConverter.objects;

public class ConvertHistory {
    private String date;
    private String firstName;
    private String secondName;
    private String amount;
    private String result;

    public ConvertHistory(String date, String firstName, String secondName, String amount, String result) {
        this(firstName, secondName, amount);
        this.date = date;
        this.result = result;
    }

    public ConvertHistory( String firstName, String secondName, String amount){
        this.firstName = firstName;
        this.secondName = secondName;
        this.amount = amount;
    }

    public ConvertHistory() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
