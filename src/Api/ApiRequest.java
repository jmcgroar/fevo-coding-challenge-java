package Api;

import java.io.IOException;
import com.google.gson.*;

public interface ApiRequest<T> {
    public abstract JsonObject ProcessApiRequest(T t) throws IOException, InterruptedException;
}
