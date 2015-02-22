package windroids.semifinal.logic.statistic;

import android.util.Pair;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class PointValueDiff<T>{

    private SingleValueDiff<T> first = new SingleValueDiff<>();
    private SingleValueDiff<T> second = new SingleValueDiff<>();

    public Pair<Double, Double> getAvg()
    {
        return new Pair<>(first.getAvg(), second.getAvg());
    }

    public void calculateStatistics()
    {
        first.calculateStatistics();
        second.calculateStatistics();
    }

    public void add(Pair<T, T> elem)
    {
        first.add(elem.first);
        second.add(elem.second);
    }

    public double distance(Pair<T,T> elem)
    {
        return sqrt(
                pow(first.distance(elem.first), 2) +
                pow(second.distance(elem.second), 2));
    }
}
