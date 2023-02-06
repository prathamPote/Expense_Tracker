package com.example.expensetracker;

import java.util.Date;

public class UserExpenseModel {
    private String expenseId;
    private String note;
    private long amount;
    private String date;

    public UserExpenseModel() {
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserExpenseModel(String expenseId, String note, long amount, String date) {
        this.expenseId = expenseId;
        this.note = note;
        this.amount = amount;
        this.date = date;
    }
}
