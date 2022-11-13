package Cache;

import java.util.HashMap;

public class PhotoCache implements GetPutCache<String,String> {
    private HashMap<String, String> photoCache;

    public PhotoCache() {
        photoCache = new HashMap<String, String>();
    }

    //@Override
    public String Get (String key) {
        return (photoCache.get(key));
    }

    @Override
    public void Put (String key, String value) {
        photoCache.put(key, value);
    }
}
