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

	public String patternStudied = "";
	public String patternMatch = "";
	public void init()
	{
		for(Pattern tr : training)
        {
            String typedCode = tr.getTypedCode();

            if(typedCode.isEmpty()) continue;
			System.out.println(patternStudied += "pw is:" + password + ", typed: " + typedCode + " are equals:" + typedCode.equals(password) +
					"areFuzzyEquals: " + (typedCode.substring(0, typedCode.length() - 1).equals(password) &&
					typedCode.charAt(typedCode.length() - 1) == '\n') + '\n');
            if( typedCode.equals(password) ||
                    (typedCode.substring(0, typedCode.length() - 1).equals(password) &&
                    typedCode.charAt(typedCode.length() - 1) == '\n'))
            {
                if(typedCode.length() != password.length())
                    tr.eraseLastEnter();
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
                    if(index % 2 == 0)
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
        System.out.print(patternStudied += "Time limitations: \n");
        for(SingleValueDiff<Long> stat : timeLimitations)
        {
            stat.calculateStatistics();
            System.out.print(patternStudied += "[" + (stat.getAvg() - stat.getAllowedDifference()) + " - " + (stat.getAvg() + stat.getAllowedDifference()) + "] ");
        }
        System.out.print(patternStudied += '\n');

        System.out.print(patternStudied += "Point limitations: \n");
        for(PointValueDiff<Double> stat : pointLimitations)
        {
            stat.calculateStatistics();
            System.out.print(patternStudied += "[ x: " + (stat.getAvg().first - stat.getAllowedDifference().first) + " - " + (stat.getAvg().first + stat.getAllowedDifference().first) +
                    " , y: " + (stat.getAvg().second - stat.getAllowedDifference().second) + " - " + (stat.getAvg().second + stat.getAllowedDifference().second) + " ] ");

        }
        System.out.print(patternStudied += '\n');

        System.out.print(patternStudied += "Angle limitations: \n");
        for(AngleValueDiff stat : angleLimitations)
        {
            stat.calculateStatistics();
            System.out.print(patternStudied += "[" + (stat.getAvg() - stat.getAllowedDifference()) + " - " + (stat.getAvg() + stat.getAllowedDifference()) + "] ");
        }
        System.out.print(patternStudied += '\n');

        System.out.print(patternStudied += "Sum limitations: \n");
        sumTimeLimit.calculateStatistics();
        System.out.println(patternStudied += "[" + (sumTimeLimit.getAvg() - sumTimeLimit.getAllowedDifference()) + " - " + (sumTimeLimit.getAvg() + sumTimeLimit.getAllowedDifference()) + "] ");

    }

	public boolean isMatching(Pattern pattern)
	{
        boolean result = false;

        String typedCode = pattern.getTypedCode();

        if(typedCode.isEmpty()) return false;

		System.out.print(patternMatch = "pw is:" + password + ", typed: " + typedCode + " are equals:" + typedCode.equals(password) +
		"areFuzzyEquals: " + (typedCode.substring(0, typedCode.length() - 1).equals(password) &&
				typedCode.charAt(typedCode.length() - 1) == '\n')+ '\n');
        if(typedCode.equals(password) ||
                (typedCode.substring(0, typedCode.length() - 1).equals(password) &&
                        typedCode.charAt(typedCode.length() - 1) == '\n'))
        {
            if(typedCode.length() != password.length())
                pattern.eraseLastEnter();

            SingleValueDiff<Double> timePercents = new SingleValueDiff<>();
            SingleValueDiff<Double> pointPercents = new SingleValueDiff<>();
            SingleValueDiff<Double> anglePercents = new SingleValueDiff<>();

            pattern.eraseBackSpaceEvents();

            int index = 0;

            ListIterator<KeyEvent> iterator = pattern.getEvents().listIterator();

            KeyEvent prevKey = iterator.next();

            double tmp = pointLimitations.get(index).distance(prevKey.getPos());
            System.out.print(patternMatch += index + " character distance : " + tmp + '\n');
            if(tmp > 1) return false;
            pointPercents.add(tmp);

            while(iterator.hasNext())
            {
                KeyEvent nextKey = iterator.next();

                tmp = timeLimitations.get(index).distance(nextKey.getTime()-prevKey.getTime());
                System.out.print(patternMatch += index + " time distance : " + tmp + '\n');
                if(tmp > 1) return false;
                timePercents.add(tmp);

				if(index % 2 == 0) {
					tmp = angleLimitations.get(index).distance(prevKey.getPos(), nextKey.getPos());
					System.out.print(patternMatch += index + " angle distance : " + tmp + '\n');
					if (tmp > 1)
						return false;
					anglePercents.add(tmp);
				}
                ++index;

                tmp = pointLimitations.get(index).distance(prevKey.getPos());
                System.out.print(patternMatch += index + " point distance : " + tmp + '\n');
                if(tmp > 1) return false;
                pointPercents.add(tmp);

                prevKey = nextKey;
            }

            tmp = sumTimeLimit.distance(
                    pattern.getEvents().get(pattern.getEvents().size()-1).getTime() -
                            pattern.getEvents().get(0).getTime());
            System.out.println(patternMatch += index + " sumTime distance : " + tmp + '\n');
            if(tmp > 1) return false;

            timePercents.add(tmp);
            System.out.print(patternMatch += "Time AVG:" + timePercents.getAvg() + ", angle AVG: " + anglePercents.getAvg() + ", point AVG:" + pointPercents.getAvg() + '\n');

            result = timePercents.getAvg() < 0.5 && anglePercents.getAvg() + pointPercents.getAvg() < 1.0;
        }

        return result;
	}
}
