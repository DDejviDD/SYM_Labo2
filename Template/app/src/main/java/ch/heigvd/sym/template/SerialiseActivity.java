package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class SerialiseActivity extends Activity {
    private TextView data;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serialisation);

    }
    public void sender(){
        JSONObject data = new JSONObject();

    }
    public String sendRequest(String url, String request) throws Exception {

        return url;
    }
}
