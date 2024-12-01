package pl.kurs.zad2.services;

import pl.kurs.zad2.exceptions.InvalidCurrencyException;
import pl.kurs.zad2.models.CurrencyPair;
import pl.kurs.zad2.models.StoredRate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyRatesHolder {

    private final Map<CurrencyPair, StoredRate> RATES = new ConcurrentHashMap<>();
    private final long ratesLastingTimeInSeconds;
    private IRateService rateProvider;

    public CurrencyRatesHolder(IRateService rateProvider, long ratesLastingTimeInSeconds) {
        this.ratesLastingTimeInSeconds = ratesLastingTimeInSeconds;
        this.rateProvider = rateProvider;
    }

    public double getRate(String currencyFrom, String currencyTo) {

        if (ratesLastingTimeInSeconds <= 0) {
            throw new IllegalStateException("Rates lasting time should be greater than 0");
        }
        if (currencyFrom == null || currencyTo == null) {
            throw new InvalidCurrencyException("Null currency");
        }

        CurrencyPair currencyPair = new CurrencyPair(currencyFrom, currencyTo);

        return RATES.compute(currencyPair, (key, storedRate) -> {

            long currentTimeInSeconds = System.currentTimeMillis() / 1000;
            if (storedRate == null || currentTimeInSeconds - storedRate.getTimeOfCacheInjection() > ratesLastingTimeInSeconds) {
                double rate = rateProvider.getExchangeRate(currencyFrom, currencyTo);
                return new StoredRate(rate, currentTimeInSeconds);
            }

            return storedRate;

        }).getRate();
    }
}

