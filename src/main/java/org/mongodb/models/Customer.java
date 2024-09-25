package org.mongodb.models;

public class Customer {
    private String email;
    private int trialPeriod;
    private int billingPeriod;
    private int maxBillingPeriods;
    private double initialBillingPeriodCharge;
    private String id;

    // Constructor
    public Customer(String email, int trialPeriod, int billingPeriod, int maxBillingPeriods, double initialBillingPeriodCharge, String id) {
        this.email = email;
        this.trialPeriod = trialPeriod;
        this.billingPeriod = billingPeriod;
        this.maxBillingPeriods = maxBillingPeriods;
        this.initialBillingPeriodCharge = initialBillingPeriodCharge;
        this.id = id;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(int trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public int getMaxBillingPeriods() {
        return maxBillingPeriods;
    }

    public void setMaxBillingPeriods(int maxBillingPeriods) {
        this.maxBillingPeriods = maxBillingPeriods;
    }

    public double getInitialBillingPeriodCharge() {
        return initialBillingPeriodCharge;
    }

    public void setInitialBillingPeriodCharge(double initialBillingPeriodCharge) {
        this.initialBillingPeriodCharge = initialBillingPeriodCharge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

