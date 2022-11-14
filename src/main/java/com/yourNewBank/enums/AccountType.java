package com.yourNewBank.enums;

public enum AccountType {
    CHECKING("Checking Account"), SAVINGS("Savings Account"), CREDIT("Credit Account");
private String typeOfAccount;

    AccountType(String s) {
    }


//    IType(String typeOfAccount) {
//        this.typeOfAccount = typeOfAccount;
//    }

//    @JsonCreator
//    public static  IType decode(final String typeOfAccount){
//        return Stream.of(IType.values())
//                .filter(targetEnum -> targetEnum.typeOfAccount.equals(typeOfAccount)).findFirst().orElse(null);
//    }
//
//    @JsonValue
    public String getTypeOfAccount() {

        return typeOfAccount;
         }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }
    //    public void setTypeOfAccount(String typeOfAccount) {
//        this.typeOfAccount = typeOfAccount;
//    }
}
