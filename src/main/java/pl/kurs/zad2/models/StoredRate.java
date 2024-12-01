package pl.kurs.zad2.models;

import java.util.Objects;

public class StoredRate {

    private final double rate;
    private final long timeOfCacheInjection;

    public StoredRate(double rate, long timeOfCacheInjection) {
        this.rate = rate;
        this.timeOfCacheInjection = timeOfCacheInjection;
    }

    public double getRate() {
        return rate;
    }

    public long getTimeOfCacheInjection() {
        return timeOfCacheInjection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoredRate that = (StoredRate) o;
        return Double.compare(that.rate, rate) == 0 && timeOfCacheInjection == that.timeOfCacheInjection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, timeOfCacheInjection);
    }


    @Override
    public String toString() {
        return "CachedRate{" +
                "rate=" + rate +
                ", timeOfCacheInjection=" + timeOfCacheInjection +
                '}';
    }
}
