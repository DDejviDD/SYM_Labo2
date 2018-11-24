package ch.heigvd.sym.template;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;

public class SerialiseActivity extends AppCompatActivity {

    private TextView label_serializable_first_name;
    private EditText edit_serializable_first_name;
    private TextView label_serializable_last_name;
    private EditText edit_serializable_last_name;
    private Button   serializable_send_request;
    private TextView response_serializable_type;
    private TextView response_serializable_first_name;
    private TextView response_serializable_last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serialisation);

        label_serializable_first_name = findViewById(R.id.label_serializable_first_name);
        edit_serializable_first_name = findViewById(R.id.edit_serializable_first_name);
        label_serializable_last_name = findViewById(R.id.label_serializable_last_name);
        edit_serializable_last_name = findViewById(R.id.edit_serializable_last_name);
        serializable_send_request = findViewById(R.id.serializable_send_request);
        response_serializable_type = findViewById(R.id.response_serializable_type);
        response_serializable_first_name = findViewById(R.id.response_serializable_first_name);
        response_serializable_last_name = findViewById(R.id.response_serializable_last_name);

        serializable_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonSender();
            }
        });

    }
    public void jsonSender(){
        JSONObject data = new JSONObject();
        try {
            data.put("type", "json");
            data.put(label_serializable_first_name.getText().toString(), edit_serializable_first_name.getText().toString());
            data.put(label_serializable_last_name.getText().toString(), edit_serializable_last_name.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendJsonRequest(data.toString());
    }
    public void sendJsonRequest(String request) {
        AsynchronousRequest aRequest = new AsynchronousRequest();
        aRequest.newListener(
                new RequestListener() {
                    @Override
                    public boolean handlerForRequest(String response) {
                        JSONObject received;
                        String type = response_serializable_type.getText().toString();
                        String firstName = response_serializable_first_name.getText().toString();
                        String lastName = response_serializable_last_name.getText().toString();

                        try {
                            received = new JSONObject(response);
                            type = received.getString("type");
                            firstName = received.getString(label_serializable_first_name.getText().toString());
                            lastName = received.getString(label_serializable_last_name.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final String finalType= type;
                        final String finalFirstName = firstName;
                        final String finalLastName = lastName;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                response_serializable_type.setText(finalType);
                                response_serializable_first_name.setText(finalFirstName);
                                response_serializable_last_name.setText(finalLastName);
                            }
                        });
                        return true;
                    }
                }
        );
        aRequest.postRequest(request,"http://sym.iict.ch/rest/json", MediaType.parse("application/json; charset=utf-8"));
    }
}
