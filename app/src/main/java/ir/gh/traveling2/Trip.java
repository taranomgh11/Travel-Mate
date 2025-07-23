package ir.gh.traveling2;

public class Trip {
    public String destination;
    public String startDate;
    public String endDate;
    public String budget;
    public String notes;
    public boolean reminder;

    public Trip() {

    }

    public Trip(String destination, String startDate, String endDate, String budget, String notes, boolean reminder) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.notes = notes;
        this.reminder = reminder;
    }
}
