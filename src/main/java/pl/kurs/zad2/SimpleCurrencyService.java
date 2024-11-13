package pl.kurs.zad2;

public class SimpleCurrencyService implements ICurrencyService {

    private CurrencyRatesHolder currencyRatesHolder;

    public SimpleCurrencyService(CurrencyRatesHolder currencyRatesHolder) {
        this.currencyRatesHolder = currencyRatesHolder;
    }

    @Override
    public double exchange(String currencyFrom, String currencyTo, double amount) {
        if (currencyFrom == null || currencyTo == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount should not be less than 0");
        }
        double rate = currencyRatesHolder.getRate(currencyFrom, currencyTo);
        return amount * rate;
    }
}
