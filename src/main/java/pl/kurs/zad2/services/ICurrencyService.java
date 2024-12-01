package pl.kurs.zad2.services;

public interface ICurrencyService {
    double exchange(String currencyFrom, String currencyTo, double amount);
}
