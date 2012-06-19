package mediaApp.XML;

public class Category
{

	private String	name, groupId, subId;

	public Category(String name, String groupId, String subId)
	{
		this.name = name;
		this.groupId = groupId;
		this.subId = subId;
	}

	public String getURL()
	{
		if (subId != null && subId.length() > 0)
			return groupId + "," + subId;
		else
			return groupId;
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
	 * @return the groupId
	 */
	public String getGroupId()
	{
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	/**
	 * @return the subId
	 */
	public String getSubId()
	{
		return subId;
	}

	/**
	 * @param subId
	 *            the subId to set
	 */
	public void setSubId(String subId)
	{
		this.subId = subId;
	}

}
