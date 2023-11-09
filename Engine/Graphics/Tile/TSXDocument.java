//
//	TSXDocument.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 09/11/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Engine.Assets.AssetManager;
import Engine.Developer.Logger.Logger;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;

public class TSXDocument {
    public int mTileWidth;
    public int mTileHeight;
    public int mColumns;
    public Spritesheet mImage;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Caches the tileset data from the TSX file
    */ //----------------------------------------------------------------------
    public TSXDocument(final String path) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            final Document aux = builder.parse(
                new File(getClass().getClassLoader().getResource(path).toURI()));
                aux.getDocumentElement().normalize();
                final Element element2 = (Element) aux.getElementsByTagName("tileset").item(0);
                mTileWidth = Integer.parseInt(element2.getAttribute("tilewidth")); 
                mTileHeight = Integer.parseInt(element2.getAttribute("tileheight")); 
                mColumns = Integer.parseInt(element2.getAttribute("columns"));
                mImage = new Spritesheet(
                    AssetManager.Instance().GetResource("Content/Tiles/" +
                     ((Element) aux.getElementsByTagName("image")
                     .item(0)).getAttribute("source")), 
                    new Vector2D<>(mTileHeight, mTileHeight));
        } catch (ParserConfigurationException | SAXException | IOException | URISyntaxException e) {
            Logger.Instance().Log(Logger.Instance().GetLog("Engine"), 
            "Error - Can't read TSX File " + e.getMessage(), Level.SEVERE, 2, Color.red);
        }
    }
}
