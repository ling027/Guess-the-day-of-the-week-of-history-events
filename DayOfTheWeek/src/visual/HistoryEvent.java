package visual;

public class HistoryEvent {
	private String year;
    private String month;
    private String day;
    private String event;
    private String weekday;

    // Constructor
    public HistoryEvent(String name, String Year, String month, String day, String weekday) {
        this.event = name;
        this.year = Year;
        this.month = month;
        this.day =day;
        this.weekday=weekday;
    }

    // Getter for the date
    public String getYear() {
        return year;
    }
    public String getMonth() {
        return month;
    }
    public String getDay() {
        return day;
    }
    public String getWeekDay() {
        return weekday;
    }
    // Getter for the description
    public String getName() {
        return event;
    }

}
