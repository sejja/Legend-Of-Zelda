package Engine.Assets;

public class Asset {
    Object mResource;

    public Asset(Object res) {
        mResource = res;
    }

    public Object Raw() {
        return mResource;
    }

    public int compareTo(Asset other) {
        return this == other ? 0 : other.mResource.hashCode() - mResource.hashCode();
    }
}
