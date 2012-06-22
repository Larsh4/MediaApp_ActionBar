package mediaApp.main;

import java.util.List;
import mediaApp.XML.LucasResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter
{
	private List<LucasResult>		results;
	private final LayoutInflater	inflater;

	public SearchResultAdapter(LayoutInflater layoutInflater, List<LucasResult> results)
	{
		this.results = results;
		this.inflater = layoutInflater;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = null;

		if (convertView == null)
			view = inflater.inflate(R.layout.searchresultitem, parent, false);
		else
			view = convertView;

		LucasResult curResult = results.get(position);

		TextView textView = (TextView) view.findViewById(R.id.TVResultTitle);
		textView.setText(curResult.getTitle());

		String text = curResult.getAuthor();
		textView = (TextView) view.findViewById(R.id.TVResultAuthor);
		if (text != null)
			textView.setText(text);
		else
			textView.setVisibility(View.GONE);

		text = curResult.getDate();
		textView = (TextView) view.findViewById(R.id.TVResultDate);

		if (text != null)
			textView.setText(curResult.getDate());
		else
			textView.setVisibility(View.GONE);

		text = curResult.getSource();
		textView = (TextView) view.findViewById(R.id.TVResultSource);

		if (text != null)
			textView.setText("Source : " + curResult.getSource());
		else
			textView.setVisibility(View.GONE);

		return view;
	}

	public int getCount()
	{
		return results.size();
	}

	public Object getItem(int idx)
	{
		return results != null ? results.get(idx) : null;
	}

	public long getItemId(int position)
	{
		return position;
	}
}
