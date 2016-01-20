import java.io.IOException;

/**
 * Created by Administrator on 2016/1/7.
 */
public class StaticResponseProcessor {
    public void process(Request request,Response response)
    {
        try{
            response.sendSaticResource();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
