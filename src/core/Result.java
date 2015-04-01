package core;

public class Result
{
	private int verificationCount;
	private long timeElapsed;
	
	public Result(int verificationCount, long timeElapsed)
	{
		this.verificationCount = verificationCount;
		this.timeElapsed = timeElapsed;
	}

	public int getVerificationCount()
	{
		return verificationCount;
	}

	public long getTimeElapsed()
	{
		return timeElapsed;
	}
}
