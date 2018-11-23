package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class SerialiseActivity extends Activity {
    /*
    <TextView
            android:id="@+id/label_serializable_first_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="50dp"
            android:text="string/serializable_first_name" />

        <EditText
            android:id="@+id/edit_serializable_first_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="serializable_first_name" />

        <TextView
            android:id="@+id/label_serializable_last_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:text="string/serializable_last_name" />

        <EditText
            android:id="@+id/edit_serializable_last_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="serializable_lastname" />

        <Button
            android:id="@+id/sendRequest"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="string/serializable_send" />

        <TextView
            android:id="@+id/response_serializable_first_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:text="string/waiting" />

        <TextView
            android:id="@+id/response_serializable_last_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="string/waiting" />
    */



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
