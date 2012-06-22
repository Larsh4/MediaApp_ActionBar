package mediaApp.RSS;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mediaApp.main.R;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RSSListActivity extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        itemlist = new ArrayList<RSSItem>();        
        new RetrieveRSSFeeds().execute();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);        
        RSSItem data = itemlist.get(position);        
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(data.link));        
        startActivity(intent);
    }

    private void retrieveRSSFeed(String urlToRssFeed,ArrayList<RSSItem> list)
    {
        try
        {
           URL url = new URL(urlToRssFeed);
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser parser = factory.newSAXParser();
           XMLReader xmlreader = parser.getXMLReader();
           RSSParser theRssHandler = new RSSParser(list);
           xmlreader.setContentHandler(theRssHandler);
           InputSource is = new InputSource(url.openStream());
           xmlreader.parse(is);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private class RetrieveRSSFeeds extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog progress = null;
        
        @Override
        protected Void doInBackground(Void... params) {
            //retrieveRSSFeed("http://www.krvarma.com/feed",itemlist);
        	retrieveRSSFeed("http://www.mediatheek.hu.nl/Rss.ashx?ID={971F23C4-AB46-4386-BD4C-6343799B7281}&parentID={98653ABB-C8A4-4848-B883-87EE3C7B28FA}",itemlist);
            rssadaptor = new RSSListAdaptor(RSSListActivity.this, R.layout.rssitemview,itemlist);
            
            return null;
        }

        @Override
        protected void onCancelled() {
        	super.onCancelled();
        }
        
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(RSSListActivity.this, null, 
            			getResources().getString(R.string.dialogLoadingRSS));
            
            super.onPreExecute();
        }
        
        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(rssadaptor);            
            progress.dismiss();            
            super.onPostExecute(result);
        }
        
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    
    private class RSSListAdaptor extends ArrayAdapter<RSSItem>{
        private List<RSSItem> objects = null;
        
        public RSSListAdaptor(Context context, int textviewid, List<RSSItem> objects) {
            super(context, textviewid, objects);            
            this.objects = objects;
        }
        
        @Override
        public int getCount() {
            return ((null != objects) ? objects.size() : 0);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public RSSItem getItem(int position) {
            return ((null != objects) ? objects.get(position) : null);
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            
            if(null == view)
            {
                LayoutInflater vi = (LayoutInflater)RSSListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.rssitemview, null);
            }
            
            RSSItem data = objects.get(position);
            
            if(null != data)
            {
                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                TextView date = (TextView)view.findViewById(R.id.txtDate);
                TextView description = (TextView)view.findViewById(R.id.txtDescription);                
                title.setText(data.title);
                date.setText(data.date);
                description.setText(data.description);
            }            
            return view;
        }
    }
}