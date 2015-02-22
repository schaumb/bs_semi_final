package windroids.semifinal.logic.statistic;


import android.util.Pair;
import static java.lang.Math.acos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class AngleValueDiff extends SingleValueDiff<Double>
{
    static double vectorToAngle(Pair<Double, Double> from, Pair<Double, Double> to)
    {
        Pair<Double, Double> vector = new Pair<>(to.first - from.first, to.second - from.second);
        double dist = sqrt(pow(vector.first, 2) + pow(vector.second, 2));
        return acos(vector.first / dist);
    }

    public void add(Pair<Double, Double> from, Pair<Double, Double> to)
    {
        this.add(vectorToAngle(from, to));
    }

    public double distance(Pair<Double, Double> from, Pair<Double, Double> to)
    {
        return this.distance(vectorToAngle(from, to));
    }
}