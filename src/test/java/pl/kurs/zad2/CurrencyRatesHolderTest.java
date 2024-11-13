package pl.kurs.zad2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class CurrencyRatesHolderTest {

    @Mock
    private IRateService rateService;

    @InjectMocks
    private CurrencyRatesHolder currencyRatesHolder;

    private Map<CurrencyPair, Double> firstRates;
    private Map<CurrencyPair, Double> updatedRates;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        firstRates = new ConcurrentHashMap<>();
        firstRates.put(new CurrencyPair("USD","EUR"), 0.1);
        updatedRates = new ConcurrentHashMap<>();
        updatedRates.put(new CurrencyPair("USD","EUR"), 0.5);
    }

    @After
    public void clean() {
        currencyRatesHolder.stopRefreshing();
    }

    @Test
    public void shouldUpdateCacheAfter5s() throws InterruptedException {

        Mockito.when(rateService.getCurrenciesWithRates())
                .thenReturn(firstRates)
                .thenReturn(updatedRates);

        currencyRatesHolder.refreshRates(5);
        Thread.sleep(6000);

        assertEquals(updatedRates, currencyRatesHolder.getCurrentRates());
    }

    @Test
    public void shouldUpdateCacheAfter2s() throws InterruptedException {

        Mockito.when(rateService.getCurrenciesWithRates())
                .thenReturn(firstRates)
                .thenReturn(updatedRates);

        currencyRatesHolder.refreshRates(2);
        Thread.sleep(3000);

        assertEquals(updatedRates, currencyRatesHolder.getCurrentRates());
    }

    @Test
    public void shouldNotUpdateCacheBefore5s() throws InterruptedException {

        Mockito.when(rateService.getCurrenciesWithRates())
                .thenReturn(firstRates)
                .thenReturn(updatedRates);

        currencyRatesHolder.refreshRates(5);
        Thread.sleep(2000);

        assertNotEquals(updatedRates, currencyRatesHolder.getCurrentRates());
        assertEquals(firstRates, currencyRatesHolder.getCurrentRates());
    }

    @Test
    public void shouldStopRefreshingAfterOneRefresh() throws InterruptedException {

        Mockito.when(rateService.getCurrenciesWithRates()).thenReturn(firstRates);

        currencyRatesHolder.refreshRates(2);
        Thread.sleep(1000);
        currencyRatesHolder.stopRefreshing();
        Thread.sleep(3000);

        Mockito.verify(rateService, Mockito.times(1)).getCurrenciesWithRates();
    }

    @Test
    public void shouldReturnRate05ForUsdEurExchange() throws InterruptedException{

        Mockito.when(rateService.getCurrenciesWithRates()).thenReturn(updatedRates);

        currencyRatesHolder.refreshRates(1);
        Thread.sleep(100);

        double rate = currencyRatesHolder.getRate("USD", "EUR");
        assertEquals(0.5, rate, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCurrencyIsNotPresent() {
        currencyRatesHolder.getRate("UnknownRate","PLN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRefreshSecondsIs0(){
        currencyRatesHolder.refreshRates(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnmodifiableMap() throws InterruptedException {
        currencyRatesHolder.refreshRates(1);
        Thread.sleep(100);
        currencyRatesHolder.getCurrentRates().put(new CurrencyPair("EUR", "USD"), 1.2);
    }

}