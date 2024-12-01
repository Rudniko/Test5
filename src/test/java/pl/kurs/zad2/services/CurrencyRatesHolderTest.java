package pl.kurs.zad2.services;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.kurs.zad2.exceptions.InvalidCurrencyException;

import static org.junit.Assert.*;

public class CurrencyRatesHolderTest {

    @Mock
    private IRateService rateProvider;

    @Before
    public void init() {
        rateProvider = Mockito.mock(IRateService.class);
    }

    @Test
    public void shouldReturnDifferentRateAfterRateLastingTimeEnds() throws InterruptedException {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 1);
        Mockito.when(rateProvider.getExchangeRate("USD", "PLN")).thenReturn(4.0).thenReturn(2.0);

        double firstRate = ratesHolder.getRate("USD", "PLN");
        Thread.sleep(2000);
        double secondRate = ratesHolder.getRate("USD", "PLN");

        assertNotEquals(firstRate, secondRate);
        assertEquals(4.0, firstRate, 0);
        assertEquals(2.0, secondRate, 0);
    }

    @Test
    public void shouldReturnSameRateBeforeRateLastingTimeEnds() {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 5);
        Mockito.when(rateProvider.getExchangeRate("USD", "PLN")).thenReturn(4.0).thenReturn(2.0);

        double firstRate = ratesHolder.getRate("USD", "PLN");
        double secondRate = ratesHolder.getRate("USD", "PLN");

        assertEquals(firstRate, secondRate, 0);
        assertEquals(4.0, firstRate, 0);
        assertEquals(4.0, secondRate, 0);
    }

    @Test
    public void shouldCallGetExchangeRateMethodOnlyOnce() throws InterruptedException {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 5);
        Mockito.when(rateProvider.getExchangeRate("USD", "PLN")).thenReturn(4.0);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100 ; i++) {
                ratesHolder.getRate("USD", "PLN");
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100 ; i++) {
                ratesHolder.getRate("USD", "PLN");
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        Mockito.verify(rateProvider, Mockito.times(1)).getExchangeRate("USD", "PLN");
    }

    @Test
    public void shouldCallGetExchangeRateMethodTwice() throws InterruptedException {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 1);
        Mockito.when(rateProvider.getExchangeRate("USD", "PLN")).thenReturn(4.0).thenReturn(2.0);

        Thread t1 = new Thread(() -> ratesHolder.getRate("USD", "PLN"));
        Thread t2 = new Thread(() -> ratesHolder.getRate("USD", "PLN"));
        t1.start();
        Thread.sleep(2000);
        t2.start();

        t1.join();
        t2.join();

        Mockito.verify(rateProvider, Mockito.times(2)).getExchangeRate("USD", "PLN");
    }

    @Test
    public void shouldReturnUSDToPLNRate() {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 10);

        Mockito.when(rateProvider.getExchangeRate("USD", "PLN")).thenReturn(4.0);
        double rate = ratesHolder.getRate("USD", "PLN");

        assertEquals(4.0, rate, 0);
    }

    @Test
    public void shouldReturnJPYToPLNRate() {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 10);

        Mockito.when(rateProvider.getExchangeRate("JPY", "PLN")).thenReturn(0.3);
        double rate = ratesHolder.getRate("JPY", "PLN");

        assertEquals(0.3, rate, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenRateLastingTimeIs0(){
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 0);
        ratesHolder.getRate("USD","PLN");
    }
    @Test(expected = InvalidCurrencyException.class)
    public void shouldThrowInvalidCurrencyExceptionWhenCurrencyFromIsNull() {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 10);
        ratesHolder.getRate(null, "USD");
    }

    @Test(expected = InvalidCurrencyException.class)
    public void shouldThrowInvalidCurrencyExceptionWhenCurrencyToIsNull() {
        CurrencyRatesHolder ratesHolder = new CurrencyRatesHolder(rateProvider, 10);
        ratesHolder.getRate("USD", null);
    }
}