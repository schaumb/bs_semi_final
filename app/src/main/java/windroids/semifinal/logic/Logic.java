package windroids.semifinal.logic;

import java.io.Serializable;
import java.util.List;

import windroids.semifinal.logic.pattern.Pattern;

public class Logic implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private List<Pattern> training;
	private String password;

	public Logic(String password, List<Pattern> training)
	{
		this.password = password;
		this.training = training;
	}

	public void init()
	{
		
	}

	public boolean isMatching(Pattern pattern)
	{

	}
}
