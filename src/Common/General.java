package Common;

public class General {

	public static < E > boolean isNullOrEmpty (E obj  )
	{
		return (obj==null||obj.equals(null)||obj.equals("")||obj.toString().length()==0);
	}
	
}
