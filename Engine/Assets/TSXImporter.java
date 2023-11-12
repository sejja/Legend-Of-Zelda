package Engine.Assets;

import java.awt.Color;
import java.util.logging.Level;

import Engine.Developer.Logger.Logger;
import Engine.Graphics.Tile.TSXDocument;

public class TSXImporter implements Importer {
    @Override
    public Asset ImportFromFile(String file) {
        Logger.Instance().Log(Logger.Instance().GetLog("Engine"), 
                "Importing new TSX File: " + file, Level.INFO, 1, Color.blue);
        return new Asset(new TSXDocument(file));
    }
}
