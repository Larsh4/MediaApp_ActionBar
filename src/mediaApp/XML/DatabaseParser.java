package mediaApp.XML;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DatabaseParser extends DefaultHandler
{
	private static final String	DATABASE	= "databank";
	private static final String	NAME		= "name";
	private static final String	ID			= "dbID";
	private static final String	PROXY		= "proxy";

	private List<Database>		databases;
	private StringBuilder		builder;
	private String				currentName, currentId;
	private boolean				currentProxy;

	public List<Database> parse(String xml)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			parser.parse(is, this);
			return getDatabases();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<Database> getDatabases()
	{
		return databases;
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
		else if (localName.equalsIgnoreCase(PROXY))
		{
			currentProxy = Integer.parseInt(builder.toString().trim()) == 1;
		}
		else if (localName.equalsIgnoreCase(DATABASE))
		{
			databases.add(new Database(currentName, currentId, currentProxy));
		}
		builder.setLength(0);
	}

	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		databases = new ArrayList<Database>();
		builder = new StringBuilder();
	}
}
