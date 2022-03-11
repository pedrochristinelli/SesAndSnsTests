package com.SesAndSnsTests.domain.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IcsEvent {

    private String summary;

    private String description;

    private int timeBoxInHours;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    public IcsEvent() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeBoxInHours() {
        return timeBoxInHours;
    }

    public void setTimeBoxInHours(int timeBoxInHours) {
        this.timeBoxInHours = timeBoxInHours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
