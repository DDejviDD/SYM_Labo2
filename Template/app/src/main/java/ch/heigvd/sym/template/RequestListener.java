package ch.heigvd.sym.template;

import java.io.IOException;
import java.util.EventListener;
import java.util.zip.DataFormatException;

public interface RequestListener extends EventListener{

    boolean handlerForRequest(String response) throws IOException, DataFormatException;
}
