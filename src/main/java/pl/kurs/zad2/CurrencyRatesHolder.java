package pl.kurs.zad2;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CurrencyRatesHolder {

    private final Map<CurrencyPair, Double> RATES = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    private IRateService rateService;

    public CurrencyRatesHolder(IRateService rateService) {
        this.rateService = rateService;
    }

    public Map<CurrencyPair, Double> getCurrentRates() {
        return Collections.unmodifiableMap(RATES);
    }

    public double getRate(String currencyFrom, String currencyTo) {
        CurrencyPair currencyPair = new CurrencyPair(currencyFrom, currencyTo);
        Double rate = RATES.get(currencyPair);
        if (rate == null) {
            throw new IllegalArgumentException("Rate not found for currency exchange: " + currencyFrom + "->" + currencyTo);
        }
        return rate;
    }

    private void updateCache() {
        Map<CurrencyPair, Double> currenciesWithRates = rateService.getCurrenciesWithRates();
        RATES.putAll(currenciesWithRates);
    }

    public void refreshRates(int secondsBeforeRefresh) {
        if (secondsBeforeRefresh <= 0) {
            throw new IllegalArgumentException("Refresh value should be greater than 0");
        }
        scheduledExecutorService.scheduleWithFixedDelay(this::updateCache, 0, secondsBeforeRefresh, TimeUnit.SECONDS);
    }

    public void stopRefreshing() {
        scheduledExecutorService.shutdown();
    }

}

