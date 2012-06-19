package mediaApp.XML;

public class Database
{

	private String	name, id;
	private boolean	proxy;

	public Database(String name, String id, boolean proxy)
	{
		this.name = name;
		this.id = id;
		this.proxy = proxy;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the proxy
	 */
	public boolean isProxy()
	{
		return proxy;
	}

	/**
	 * @param proxy
	 *            the proxy to set
	 */
	public void setProxy(boolean proxy)
	{
		this.proxy = proxy;
	}

}
