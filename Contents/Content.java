package Contents;

import java.awt.*;
import java.net.URL;
import java.time.Duration;
import java.util.Date;

public abstract class Content
{

    public String title;
    public Image cover;
    public Date date;
    public Duration duration;
    public String creator;
    public int timesConsumed;
    public URL link;

    /**
     * Sharing the original link of the consumed content to the social media applications such as WhatsApp and Twitter.
     * @return a boolean type for success feedback
     */
    public boolean share()
    {
        //TODO
        return false;
    }


}
