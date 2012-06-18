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

	private final int				count;
	private final List<LucasResult>	results;
	private final LayoutInflater	inflater;

	public SearchResultAdapter(LayoutInflater layoutInflater, List<LucasResult> results)
	{
		this.results = results;
		count = results.size();
		this.inflater = layoutInflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = null;
		if (convertView == null)
		{
			LucasResult curResult = results.get(position);

			view = inflater.inflate(R.layout.searchresultitem, parent, false);

			TextView textView = (TextView) view.findViewById(R.id.TVResultTitle);
			textView.setText(curResult.getTitle());

			textView = (TextView) view.findViewById(R.id.TVResultAuthor);
			textView.setText(curResult.getAuthor());

			textView = (TextView) view.findViewById(R.id.TVResultDate);
			textView.setText(curResult.getDate());
			
			textView = (TextView) view.findViewById(R.id.TVResultSource);
			textView.setText("Source : "+curResult.getSource());
		}
		else
			view = convertView;

		return view;
	}

	@Override
	public int getCount()
	{
		return count;
	}

	@Override
	public Object getItem(int idx)
	{
		return results != null ? results.get(idx) : null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}
}
