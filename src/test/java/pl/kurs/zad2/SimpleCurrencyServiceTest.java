package pl.kurs.zad2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    public void shouldExchange100EurTo400Pln() {
        Mockito.when(currencyRatesHolder.getRate("EUR", "PLN")).thenReturn(4.0);

        double exchangeResult = simpleCurrencyService.exchange("EUR", "PLN", 100);

        assertEquals(400,exchangeResult,0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCurrencyFromIsNull(){
        simpleCurrencyService.exchange(null,"USD",100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCurrencyToIsNull(){
        simpleCurrencyService.exchange("EUR",null,100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAmountIsNegative() {
        simpleCurrencyService.exchange("EUR","USD", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfRateIsNotFound() {
        Mockito.when(currencyRatesHolder.getRate("NotFoundCurrency","PLN")).thenThrow(IllegalArgumentException.class);
        simpleCurrencyService.exchange("NotFoundCurrency","PLN",100);
    }
}