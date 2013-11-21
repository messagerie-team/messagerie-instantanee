package userInterface;

import java.util.Map.Entry;

public class ClientListData implements Entry<String, String> 
{
	private final String key;
	private String value;

	public ClientListData(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}

	@Override
	public String getValue()
	{
		return this.value;
	}

	@Override
	public String setValue(String value)
	{
		this.value=value;
		return this.value;
	}
	
	@Override
	public String toString()
	{
		return this.value;
	}

}
