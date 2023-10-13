package Engine.Assets;

public class Asset {
    Object mResource;

    public Asset(Object res) {
        mResource = res;
    }

    public Object Raw() {
        return mResource;
    }
}
