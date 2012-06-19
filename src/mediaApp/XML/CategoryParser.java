package mediaApp.XML;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CategoryParser extends DefaultHandler
{

	private static final String	CATEGORY	= "categorie";
	private static final String	NAME		= "name";
	private static final String	ID			= "catGroupID";
	private static final String	SUB_ID		= "catID";

	private List<Category>		categories;
	private StringBuilder		builder;
	private String				currentName, currentId, currentSubId;

	public List<Category> parse(String xml)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			parser.parse(is, this);
			return getCategories();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<Category> getCategories()
	{
		return categories;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		super.endElement(uri, localName, name);

		if (localName.equalsIgnoreCase(NAME))
		{
			currentName = builder.toString().trim();
		}
		else if (localName.equalsIgnoreCase(ID))
		{
			currentId = builder.toString().trim();
		}
		else if (localName.equalsIgnoreCase(SUB_ID))
		{
			if (currentSubId != null && currentSubId.length() > 0)
				currentSubId += "," + builder.toString().trim();
			else
				currentSubId = builder.toString().trim();
		}
		else if (localName.equalsIgnoreCase(CATEGORY))
		{
			categories.add(new Category(currentName, currentId, currentSubId));
			currentSubId = "";
		}
		builder.setLength(0);
	}

	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		categories = new ArrayList<Category>();
		builder = new StringBuilder();
	}
}
