package pl.kurs.zad2.services;

import pl.kurs.zad2.exceptions.InvalidAmountException;
import pl.kurs.zad2.exceptions.InvalidCurrencyException;

public class SimpleCurrencyService implements ICurrencyService {

    private CurrencyRatesHolder currencyRatesHolder;

    public SimpleCurrencyService(CurrencyRatesHolder currencyRatesHolder) {
        this.currencyRatesHolder = currencyRatesHolder;
    }

    @Override
    public double exchange(String currencyFrom, String currencyTo, double amount) {
        if (currencyFrom == null || currencyTo == null) {
            throw new InvalidCurrencyException("Null currency");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Amount should be greater than 0");
        }
        double rate = currencyRatesHolder.getRate(currencyFrom, currencyTo);
        return amount * rate;
    }
}
