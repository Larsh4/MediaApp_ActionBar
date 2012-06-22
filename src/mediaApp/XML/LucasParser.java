package mediaApp.XML;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mediaApp.main.MediaApplication;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LucasParser extends DefaultHandler
{
	private static final String	RECORD			= "record";
	private static final String	TITLE			= "title";
	private static final String	URL				= "url";
	private static final String	AUTHOR			= "creator";
	private static final String	DATE			= "issued";
	private static final String	ISSN			= "issn";
	private static final String	ISBN			= "isbn";
	private static final String	SOURCE			= "databaseName";
	private static final String	SOURCE_ID		= "databaseId";
	private static final String	NR_OF_RECORDS	= "numberOfRecords";

	private List<LucasResult>	results;
	private LucasResult			currentResult;
	private StringBuilder		builder;
	private MediaApplication	mediaApp;

	public LucasParser(MediaApplication mediaApp)
	{
		this.mediaApp = mediaApp;
	}

	public List<LucasResult> parse(String xml) throws RuntimeException
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			parser.parse(is, this);
			return getResults();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<LucasResult> getResults()
	{
		return results;
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

		if (this.currentResult != null)
		{
			if (localName.equalsIgnoreCase(TITLE))
			{
				currentResult.setTitle(builder.toString());
			}
			else if (localName.equalsIgnoreCase(URL))
			{
				currentResult.setUrl(builder.toString());
			}
			else if (localName.equalsIgnoreCase(AUTHOR))
			{
				if (currentResult.getAuthor() == null)
					currentResult.setAuthor(builder.toString());
			}
			else if (localName.equalsIgnoreCase(DATE))
			{
				currentResult.setDate(builder.toString());
			}
			else if (localName.equalsIgnoreCase(ISSN))
			{
				currentResult.setIssn(builder.toString());
			}
			else if (localName.equalsIgnoreCase(ISBN))
			{
				currentResult.setIsbn(builder.toString());
			}
			else if (localName.equalsIgnoreCase(SOURCE))
			{
				currentResult.setSource(builder.toString());
			}
			else if (localName.equalsIgnoreCase(SOURCE_ID))
			{
				currentResult.setSourceId(builder.toString().trim());
			}
			else if (localName.equalsIgnoreCase(RECORD))
			{
				if (currentResult.getTitle() != null && currentResult.getUrl() != null)
					results.add(currentResult.copy());
			}
		}
		else if (localName.equalsIgnoreCase(NR_OF_RECORDS))
		{
			mediaApp.setCurNrOfRecords(Integer.parseInt(builder.toString().trim()));
		}
		builder.setLength(0);
	}

	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		results = new ArrayList<LucasResult>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase(RECORD))
		{
			this.currentResult = new LucasResult();
		}
	}
}
