package Cache;
public interface GetPutCache<K,V> {
    public V Get(K key);
    public void Put (K key, V value);
}
