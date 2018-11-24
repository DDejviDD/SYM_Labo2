package ch.heigvd.sym.template;

import java.util.EventListener;

public interface RequestListener extends EventListener{

    boolean handlerForRequest(String response);
}
