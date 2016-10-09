package codescripters.news.borders.bean;

import android.graphics.drawable.Drawable;

public class TimeLineItem {
    private Drawable timeLineIcon;
    private String timeLineName;
    private String timeLineDate;
    private String timeLineTime;

    public TimeLineItem() {
    }

    public TimeLineItem(String timeLineDate, Drawable timeLineIcon,
                        String timeLineName, String timeLineTime) {
        this.timeLineDate = timeLineDate;
        this.timeLineIcon = timeLineIcon;
        this.timeLineName = timeLineName;
        this.timeLineTime = timeLineTime;
    }

    public String getTimeLineDate() {
        return timeLineDate;
    }

    public void setTimeLineDate(String timeLineDate) {
        this.timeLineDate = timeLineDate;
    }

    public Drawable getTimeLineIcon() {
        return timeLineIcon;
    }

    public void setTimeLineIcon(Drawable timeLineIcon) {
        this.timeLineIcon = timeLineIcon;
    }

    public String getTimeLineName() {
        return timeLineName;
    }

    public void setTimeLineName(String timeLineName) {
        this.timeLineName = timeLineName;
    }

    public String getTimeLineTime() {
        return timeLineTime;
    }

    public void setTimeLineTime(String timeLineTime) {
        this.timeLineTime = timeLineTime;
    }
}
