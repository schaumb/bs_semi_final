package windroids.semifinal.logic;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

import windroids.semifinal.logic.pattern.KeyEvent;
import windroids.semifinal.logic.pattern.Pattern;
import windroids.semifinal.logic.statistic.AngleValueDiff;
import windroids.semifinal.logic.statistic.PointValueDiff;
import windroids.semifinal.logic.statistic.SingleValueDiff;

public class Logic implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private List<Pattern> training;
	private String password;

    private List<SingleValueDiff<Long>> timeLimitations = new ArrayList<>();
    private SingleValueDiff<Long> sumTimeLimit = new SingleValueDiff<>();
    private List<PointValueDiff<Double>> pointLimitations = new ArrayList<>();
    private List<AngleValueDiff> angleLimitations = new ArrayList<>();

	public Logic(String password, List<Pattern> training)
	{
		this.password = password;
		this.training = training;
	}

	public void init()
	{
		for(Pattern tr : training)
        {
            String typedCode = tr.getTypedCode();

            if(typedCode.isEmpty()) continue;
            System.out.println("pw is:" + password + ", typed: " + typedCode + " are equals:" + typedCode.equals(password));
            if( typedCode.equals(password) ||
                    (typedCode.substring(0, typedCode.length() - 1).equals(password) &&
                    typedCode.charAt(typedCode.length() - 1) == '\n'))
            {
                tr.eraseBackSpaceEvents();

                int index = 0;
                ListIterator<KeyEvent> iterator = tr.getEvents().listIterator();

                KeyEvent prevKey = iterator.next();

                if(pointLimitations.size() == index)
                {
                    pointLimitations.add(new PointValueDiff<Double>());
                }

                pointLimitations.get(index).add(prevKey.getPos());

                while(iterator.hasNext())
                {
                    KeyEvent nextKey = iterator.next();

                    if(timeLimitations.size() == index)
                    {
                        timeLimitations.add(new SingleValueDiff<Long>());
                        angleLimitations.add(new AngleValueDiff());
                    }

                    timeLimitations.get(index).add(nextKey.getTime()-prevKey.getTime());
                    angleLimitations.get(index).add(prevKey.getPos(), nextKey.getPos());

                    ++index;

                    if(pointLimitations.size() == index)
                    {
                        pointLimitations.add(new PointValueDiff<Double>());
                    }

                    pointLimitations.get(index).add(prevKey.getPos());

                    prevKey = nextKey;
                }

                sumTimeLimit.add(
                        tr.getEvents().get(tr.getEvents().size()-1).getTime() -
                        tr.getEvents().get(0).getTime());
            }
        }

        // calculating the limits
        System.out.println("Time limitations: ");
        for(SingleValueDiff<Long> stat : timeLimitations)
        {
            stat.calculateStatistics();
            System.out.print("[" + (stat.getAvg() - stat.getAllowedDifference()) + " - " + (stat.getAvg() + stat.getAllowedDifference()) + "] ");
        }
        System.out.println();

        System.out.println("Point limitations: ");
        for(PointValueDiff<Double> stat : pointLimitations)
        {
            stat.calculateStatistics();
            System.out.print("[ x: " + (stat.getAvg().first - stat.getAllowedDifference().first) + " - " + (stat.getAvg().first + stat.getAllowedDifference().first) +
                    " , y: " + (stat.getAvg().second - stat.getAllowedDifference().second) + " - " + (stat.getAvg().second + stat.getAllowedDifference().second) + " ] ");

        }
        System.out.println();

        System.out.println("Angle limitations: ");
        for(AngleValueDiff stat : angleLimitations)
        {
            stat.calculateStatistics();
            System.out.print("[" + (stat.getAvg() - stat.getAllowedDifference()) + " - " + (stat.getAvg() + stat.getAllowedDifference()) + "] ");
			System.out.print("[" + (stat.getAvg() ) + " " + ( stat.getAllowedDifference()) + " - " + (stat.getAvg() + stat.getAllowedDifference()) + "] ");
        }
        System.out.println();

        System.out.println("Sum limitations: ");
        sumTimeLimit.calculateStatistics();
        System.out.println("[" + (sumTimeLimit.getAvg() - sumTimeLimit.getAllowedDifference()) + " - " + (sumTimeLimit.getAvg() + sumTimeLimit.getAllowedDifference()) + "] ");

    }

	public boolean isMatching(Pattern pattern)
	{
        boolean result = false;

        String typedCode = pattern.getTypedCode();

        if(typedCode.isEmpty()) return false;

		System.out.println("pw is:" + password + ", typed: " + typedCode + " are equals:" + typedCode.equals(password));
        if(typedCode.equals(password) ||
                (typedCode.substring(0, typedCode.length() - 1).equals(password) &&
                        typedCode.charAt(typedCode.length() - 1) == '\n'))
        {

            SingleValueDiff<Double> timePercents = new SingleValueDiff<>();
            SingleValueDiff<Double> pointPercents = new SingleValueDiff<>();
            SingleValueDiff<Double> anglePercents = new SingleValueDiff<>();

            pattern.eraseBackSpaceEvents();

            int index = 0;

            ListIterator<KeyEvent> iterator = pattern.getEvents().listIterator();

            KeyEvent prevKey = iterator.next();

            double tmp = pointLimitations.get(index).distance(prevKey.getPos());
            System.out.println(index + " character distance : " + tmp);
            if(tmp > 1) return false;
            pointPercents.add(tmp);

            while(iterator.hasNext())
            {
                KeyEvent nextKey = iterator.next();

                tmp = timeLimitations.get(index).distance(nextKey.getTime()-prevKey.getTime());
                System.out.println(index + " time distance : " + tmp);
                if(tmp > 1) return false;
                timePercents.add(tmp);

                tmp = angleLimitations.get(index).distance(prevKey.getPos(), nextKey.getPos());
                System.out.println(index + " angle distance : " + tmp);
                if(tmp > 1) return false;
                anglePercents.add(tmp);

                ++index;

                tmp = pointLimitations.get(index).distance(prevKey.getPos());
                System.out.println(index + " point distance : " + tmp);
                if(tmp > 1) return false;
                pointPercents.add(tmp);

                prevKey = nextKey;
            }

            tmp = sumTimeLimit.distance(
                    pattern.getEvents().get(pattern.getEvents().size()-1).getTime() -
                            pattern.getEvents().get(0).getTime());
            System.out.println(index + " sumTime distance : " + tmp);
            if(tmp > 1) return false;

            timePercents.add(tmp);
            System.out.println("Time AVG:" + timePercents.getAvg() + ", place AVG: " + (anglePercents.getAvg() + pointPercents.getAvg()));

            result = timePercents.getAvg() < 0.5 && (anglePercents.getAvg() + pointPercents.getAvg()) < 1.0;
        }

        return result;
	}
}
