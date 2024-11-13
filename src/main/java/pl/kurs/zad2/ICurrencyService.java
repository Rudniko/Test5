package pl.kurs.zad2;

public interface ICurrencyService {
    double exchange(String currencyFrom, String currencyTo, double amount);
}
