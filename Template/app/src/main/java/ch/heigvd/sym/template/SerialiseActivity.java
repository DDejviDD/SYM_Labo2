package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class SerialiseActivity extends AppCompatActivity {

    private TextView label_serializable_first_name;
    private EditText edit_serializable_first_name;
    private TextView label_serializable_last_name;
    private EditText edit_serializable_last_name;
    private Button   serializable_send_request;
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
        response_serializable_first_name = findViewById(R.id.response_serializable_first_name);
        response_serializable_last_name = findViewById(R.id.response_serializable_last_name);

        serializable_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender();
            }
        });

    }
    public void sender(){
        JSONObject data = new JSONObject();
        try {
            data.put(label_serializable_first_name.getText().toString(), edit_serializable_first_name.getText().toString());
            data.put(label_serializable_last_name.getText().toString(), edit_serializable_last_name.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendRequest(data.toString());
    }
    public void sendRequest(String request) {


    }
}
