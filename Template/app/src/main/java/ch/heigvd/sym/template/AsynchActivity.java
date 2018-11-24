package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.MediaType;

public class AsynchActivity extends AppCompatActivity {

    private Button buttonSend;
    private EditText textToSend;
    private TextView textReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynch);

        textToSend = findViewById(R.id.dataToSend);
        buttonSend = findViewById(R.id.sendButton);
        textReceived = findViewById(R.id.dataReceived);

        textToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(textToSend.getText().toString());
                postRequest(textToSend.getText().toString());
            }
        });

    }



    public void postRequest(String rqst){
        AsynchronousRequest request = new AsynchronousRequest() ;
        request.newListener(
                new RequestListener(){
                    public boolean handlerForRequest(final String response) {

                        ArrayList<String> lines = responseParser(response);
                        System.out.println(response);

                        StringBuilder builder = new StringBuilder();
                        for(String l: lines){
                            builder.append(l);
                        }

                        final String serverResponse = builder.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textReceived.setText(serverResponse);
                            }
                        });

                        return true;
                    }
                }
        );

        request.postRequest(rqst,"http://sym.iict.ch/rest/txt", MediaType.parse("text/plain; charset=utf-8"));
    }


    public ArrayList responseParser(String response){

        ArrayList<String> parsedLines = new ArrayList<>();
        Scanner scanner = new Scanner(response);

        while (scanner.hasNextLine()) {
            String parsedLine = scanner.nextLine();
            parsedLines.add(parsedLine);
        }

        scanner.close();

        System.out.println(parsedLines);

        return parsedLines;

    }




}
