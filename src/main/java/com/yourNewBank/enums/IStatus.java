package com.yourNewBank.enums;

public enum IStatus {
    Pending("Pending"),Cancelled("Cancelled"),Completed("Completed"),Recurring("Recurring");

private String statusOf;

    IStatus(String s) {
    }

    public String getStatusOf() {
        return statusOf;
    }

    public void setStatusOf(String statusOf) {
        this.statusOf = statusOf;
    }
}
