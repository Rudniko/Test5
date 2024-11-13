package pl.kurs.zad2;

import java.util.Map;

public interface IRateService {
    Map<CurrencyPair,Double> getCurrenciesWithRates();
}
