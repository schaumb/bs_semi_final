package windroids.semifinal.logic.statistic;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class SingleValueDiff<T> extends ArrayList<T> {

    protected double avg = 0;
    protected boolean hasCalculatedAvg = false;
    protected double allowedDifference = 0;

    private void calculateAvg()
    {
        if(hasCalculatedAvg)
            return;

        hasCalculatedAvg = true;

        for(T elem : this)
        {
            avg += Double.valueOf(elem.toString());
        }
        avg /= this.size();
    }

    private double calculateStandardDeviation()
    {
        double result = 0;
        for(T elem : this)
        {
            result += pow(avg - Double.valueOf(elem.toString()), 2);
        }
        return sqrt(result);
    }

    private double maximalDifference()
    {
        double result = 0;
        for(T elem : this)
        {
            double diff = abs(avg - Double.valueOf(elem.toString()));
            if(diff > result)
            {
                result = diff;
            }
        }
        return result;
    }

    public double getAvg()
    {
        calculateAvg();
        return avg;
    }

    public double getAllowedDifference()
    {
        return allowedDifference;
    }

    public void calculateStatistics()
    {
        calculateAvg();
        double stddev = calculateStandardDeviation();
        double maxdif = maximalDifference();
        allowedDifference = maxdif + stddev;
    }

    public double distance(T elem)
    {
        return abs(avg - Double.valueOf(elem.toString())) / allowedDifference;
    }
}
