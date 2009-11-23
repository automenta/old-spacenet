package automenta.spacenet.plugin.comm;

import java.net.URL;

public class Message {

    public final String text;
    public final URL image;

    public Message(String text) {
        this(text, null);
    }

    public Message(String text, URL image) {
        super();
        this.text = text;
        this.image = image;
    }

    @Override
    public String toString() {
        return text;
    }



}
