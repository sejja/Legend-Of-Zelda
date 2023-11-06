package Engine.Assets;

import java.util.HashMap;

public class AssetManager {
    private static AssetManager sInstance = new AssetManager();
    private HashMap<String, Importer> mImporters;
    private HashMap<String, Asset> mAssets;

    public static AssetManager Instance() {
        return sInstance;
    }

    private AssetManager() {
        mImporters = new HashMap<>();
        mAssets = new HashMap<>();
        
        mImporters.put("png", new ImageImporter());
        mImporters.put("wav", new AudioImporter());
        mImporters.put("tsx", new TSXImporter());
    }

    public Asset GetResource(String path) {
        if(mAssets.containsKey(path)) {
            return mAssets.get(path);
        } else {
            return AddResource(path);
        }
    }

    private Importer GetImporterByExtension(String extension) throws AssetException {
        if(mImporters.containsKey(extension))
            return mImporters.get(extension);
        throw new AssetException("Cant handle files with such extension");
    }

    private Asset AddResource(String path) {
        Asset result = null;

        try {
            Importer imp = GetImporterByExtension(path.substring(path.lastIndexOf(".") + 1));
            result = imp.ImportFromFile(path);
            mAssets.put(path, result);
        } catch(AssetException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public void Clean() {
        mAssets.clear();
    }
}
