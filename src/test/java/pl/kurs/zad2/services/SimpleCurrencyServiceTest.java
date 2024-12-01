package pl.kurs.zad2.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kurs.zad2.exceptions.InvalidAmountException;
import pl.kurs.zad2.exceptions.InvalidCurrencyException;

import static org.junit.Assert.*;

public class SimpleCurrencyServiceTest {

    @Mock
    private CurrencyRatesHolder currencyRatesHolder;

    @InjectMocks
    private SimpleCurrencyService simpleCurrencyService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldExchange100PlnTo400Eur(){
        Mockito.when(currencyRatesHolder.getRate("PLN","EUR")).thenReturn(4.0);

        double exchangeResult = simpleCurrencyService.exchange("PLN", "EUR", 100);

        assertEquals(400,exchangeResult,0);
    }

    @Test(expected = InvalidCurrencyException.class)
    public void shouldThrowInvalidCurrencyExceptionWhenCurrencyFromIsNull() {
        simpleCurrencyService.exchange(null, "USD", 100);
    }

    @Test(expected = InvalidCurrencyException.class)
    public void shouldThrowInvalidCurrencyExceptionWhenCurrencyToIsNull() {
        simpleCurrencyService.exchange("USD", null, 100);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldThrowInvalidAmountExceptionWhenAmountIsLessThan0() {
        simpleCurrencyService.exchange("USD", "PLN", -2);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldThrowInvalidAmountExceptionWhenAmountIs0() {
        simpleCurrencyService.exchange("USD", "PLN", 0);
    }
}