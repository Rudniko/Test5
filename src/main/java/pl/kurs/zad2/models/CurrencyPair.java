package pl.kurs.zad2.models;

import java.util.Objects;

public class CurrencyPair {
    private final String currencyFrom;
    private final String currencyTo;


    public CurrencyPair(String currencyFrom, String currencyTo) {
        this.currencyFrom = currencyFrom.toUpperCase();
        this.currencyTo = currencyTo.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPair that = (CurrencyPair) o;
        return Objects.equals(currencyFrom, that.currencyFrom) && Objects.equals(currencyTo, that.currencyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, currencyTo);
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
                "currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                '}';
    }
}

