package ch.heigvd.sym.template;

import java.io.IOException;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.OkHttpClient;


// Source: https://www.youtube.com/watch?v=oGWJ8xD2W6k
//         http://square.github.io/okhttp/


public class AsynchronousRequest {

    private static final MediaType  JSON_MEDIA
            = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType TEXT_MEDIA
            = MediaType.parse("text/plain; charset=utf-8");


    private OkHttpClient client = new OkHttpClient();

    private List<RequestListener> listeners = new LinkedList<>();

    public void newListener(RequestListener rqstListener){
        if(!listeners.contains(rqstListener))
            listeners.add(rqstListener);
    }

    // Run a new thread for sending the request, so that activity process will not be interrupted
    public void postRequest(final String request,final String urlToSend,final MediaType mediaType) {
        new Thread() {
            public void run() {
                RequestBody data = RequestBody.create(mediaType, request);
                Request.Builder requestBuilder = new Request.Builder().url(urlToSend);
                // added header for compressing
                requestBuilder.addHeader("X-Content-Encoding", "gzip, deflate");

                Request request = requestBuilder.post(data).build();

                try {
                    Response response = client.newCall(request).execute();

                    for (RequestListener event : listeners) {
                        if (event.handlerForRequest(response.body().string())) break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
