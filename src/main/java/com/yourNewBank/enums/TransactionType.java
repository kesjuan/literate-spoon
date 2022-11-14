package com.yourNewBank.enums;

public enum TransactionType {
    P2P("P2p"),Deposit("Deposit"),Withdrawal("Withdrawal");

    private String typeOfDeposit;

    TransactionType(String typeOfDeposit) {
    }

    public String getTypeOfDeposit() {
        return typeOfDeposit;
    }

    public void setTypeOfDeposit(String typeOfDeposit) {
        this.typeOfDeposit = typeOfDeposit;
    }
}
