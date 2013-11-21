package userInterface;

import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Map.Entry;

import javax.swing.JComponent;

public class ClientListData extends JComponent implements Entry<String, String>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7871773513120449008L;
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
