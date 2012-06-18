package mediaApp.HTTP;

public interface HTTPResponseListener
{

	public void onResponseReceived(String response);

	public void onNullResponseReceived();

}
