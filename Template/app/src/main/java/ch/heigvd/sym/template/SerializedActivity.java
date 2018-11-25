package ch.heigvd.sym.template;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import org.xmlpull.v1.XmlPullParserException;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import okhttp3.MediaType;

public class SerializedActivity extends AppCompatActivity {

    private TextView label_serializable_first_name;
    private EditText edit_serializable_first_name;
    private TextView label_serializable_last_name;
    private EditText edit_serializable_last_name;
    private Button   serializable_send_xml_request;
    private Button   serializable_send_json_request;
    private TextView response_serializable_response;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serialized);

        label_serializable_first_name = findViewById(R.id.label_serializable_first_name);
        edit_serializable_first_name = findViewById(R.id.edit_serializable_first_name);
        label_serializable_last_name = findViewById(R.id.label_serializable_last_name);
        edit_serializable_last_name = findViewById(R.id.edit_serializable_last_name);
        serializable_send_xml_request = findViewById(R.id.serializable_send_xml_request);
        serializable_send_json_request = findViewById(R.id.serializable_send_json_request);
        response_serializable_response = findViewById(R.id.response_serializable_response);

        serializable_send_xml_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xmlSender();
            }
        });
        serializable_send_json_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonSender();
            }
        });

    }

    public void xmlSender() {
        XmlSerializer xmlData = Xml.newSerializer();
        StringWriter xml = new StringWriter();
        try {
            xmlData.setOutput(xml);
            xmlData.startDocument("UTF-8", null);
            xmlData.docdecl(" directory SYSTEM \"http://sym.iict.ch/directory.dtd\"");


            xmlData.startTag("","directory");

            xmlData.startTag("", "person");

            // open tag: <name>
            xmlData.startTag("", "name");
            xmlData.text(edit_serializable_last_name.getText().toString());
            xmlData.endTag("", "name");

            // open tag: <firstname>
            xmlData.startTag("", "firstname");
            xmlData.text(edit_serializable_first_name.getText().toString());
            xmlData.endTag("", "firstname");

            // open tag: <middlename>
            xmlData.startTag("", "middlename");
            xmlData.endTag("", "middlename");

            // open tag: <gender>
            xmlData.startTag("", "gender");
            xmlData.text("M");
            xmlData.endTag("", "gender");

            // open tag: <phone>
            xmlData.startTag("", "phone");
            xmlData.attribute("", "type", "home");
            xmlData.text("1234567890");
            xmlData.endTag("", "phone");

            // close tag: </person>
            xmlData.endTag("", "person");

            xmlData.endTag("", "directory");
            xmlData.endDocument();
            sendXMLRequest(xml.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendXMLRequest(String request) {
        AsynchronousRequest aRequest = new AsynchronousRequest();
        aRequest.newListener(new RequestListener() {
            @Override
            public boolean handlerForRequest(final String response) {
                InputStream is = new ByteArrayInputStream(response.getBytes());
                XmlPullParserFactory factory;
                String firstName=null;
                String lastName=null;
                try {
                    factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(is, "utf-8");
                    int eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("name")) {
                                parser.next();
                                lastName = (parser.getText());
                            } else if (parser.getName().equals("firstname")){
                                parser.next();
                                firstName = (parser.getText());
                            }
                        }
                        eventType = parser.next();
                    }
                }
                catch (XmlPullParserException | IOException e){
                    e.printStackTrace();
                }

                final String finalType = "xml";
                final String finalFirstName = firstName;
                final String finalLastName = lastName;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String tmp = finalType + " " + finalFirstName + " " + finalLastName + "\n" + response_serializable_response.getText().toString();
                        response_serializable_response.setText(tmp);
                    }
                });

                return true;
            }
        });
        aRequest.postRequest(request, "http://sym.iict.ch/rest/xml", MediaType.parse("application/xml; charset=utf-8"));
    }

    public void jsonSender(){
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("type", "json");
            jsonData.put(label_serializable_first_name.getText().toString(), edit_serializable_first_name.getText().toString());
            jsonData.put(label_serializable_last_name.getText().toString(), edit_serializable_last_name.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendJsonRequest(jsonData.toString());
    }
    public void sendJsonRequest(final String request) {
        AsynchronousRequest aRequest = new AsynchronousRequest();
        aRequest.newListener(
                new RequestListener() {
                    @Override
                    public boolean handlerForRequest(String response) {
                        JSONObject received;
                        String type = "";
                        String firstName = "";
                        String lastName = "";

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
                                String tmp = finalType + " " + finalFirstName + " " + finalLastName + "\n" + response_serializable_response.getText().toString();
                                response_serializable_response.setText(tmp);
                            }
                        });
                        return true;
                    }
                }
        );
        aRequest.postRequest(request,"http://sym.iict.ch/rest/json", MediaType.parse("application/json; charset=utf-8"));
    }
}
