package mediaApp.XML;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LucasResult implements Comparable<LucasResult>
{

	static final SimpleDateFormat	FORMATTER				= new SimpleDateFormat("yyyy-MM-dd");
	static final SimpleDateFormat	MONTH_YEAR_FORMATTER	= new SimpleDateFormat("MMMM-yyyy");
	static final SimpleDateFormat	YEAR_ONLY_FORMATTER		= new SimpleDateFormat("yyyy");

	private String					author, title, issn, isbn, source;
	private URL						url;
	private Date					date;

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title.trim();
	}

	/**
	 * @return the author
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author)
	{
		this.author = author.trim();
	}

	/**
	 * @return the url
	 */
	public URL getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		try
		{
			this.url = new URL(url);
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the date
	 */
	public String getDate()
	{
		return date != null ? YEAR_ONLY_FORMATTER.format(date) : "";
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date)
	{
		try
		{
			this.date = FORMATTER.parse(date.trim());
		}
		catch (ParseException e)
		{
			try
			{
				this.date = YEAR_ONLY_FORMATTER.parse(date.trim());
			}
			catch (ParseException e2)
			{
				try
				{
					this.date = MONTH_YEAR_FORMATTER.parse(date.trim());
				}
				catch (ParseException e3)
				{
					this.date = null;
				}
			}
		}
		if (this.date != null && this.date.getTime() > new Date().getTime())
			this.date = null;
	}

	/**
	 * @return the issn
	 */
	public String getIssn()
	{
		return issn;
	}

	/**
	 * @param issn
	 *            the issn to set
	 */
	public void setIssn(String issn)
	{
		this.issn = issn;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn()
	{
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	public LucasResult copy()
	{
		LucasResult copy = new LucasResult();
		copy.title = title;
		copy.url = url;
		copy.author = author;
		copy.date = date;
		copy.isbn = isbn;
		copy.issn = issn;
		copy.source = source;
		return copy;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ");
		sb.append(title);
		sb.append('\n');
		sb.append("Date: ");
		sb.append(this.getDate());
		sb.append('\n');
		sb.append("URL: ");
		sb.append(url);
		sb.append('\n');
		sb.append("Author: ");
		sb.append(author);
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LucasResult other = (LucasResult) obj;
		if (date == null)
		{
			if (other.date != null)
				return false;
		}
		else if (!date.equals(other.date))
			return false;
		if (author == null)
		{
			if (other.author != null)
				return false;
		}
		else if (!author.equals(other.author))
			return false;
		if (url == null)
		{
			if (other.url != null)
				return false;
		}
		else if (!url.equals(other.url))
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(LucasResult another)
	{
		if (another == null)
			return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}

}
