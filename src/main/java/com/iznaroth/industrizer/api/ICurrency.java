package com.iznaroth.industrizer.api;

public interface ICurrency {

    double getCurrentBalance();

    double setBalance(final double mdd);

    double addCurrency(final double mddToAdd);

    double removeCurrency(final double mddToRemove);

}