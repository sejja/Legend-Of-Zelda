//
//	Tilemap.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Engine.Assets.AssetManager;
import Engine.Developer.Logger.Logger;
import Engine.ECSystem.Types.ECObject;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class TileManager extends ECObject implements Renderable {
    public ArrayList<Tilemap> mLayers;
    public Queue<parseEntity> entityQueue;
    private String mPath;
    private AABB mBounds;
    public static TilemapObject sLevelObjects;

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Takes a path, and generates a whole TileManager from it
    */ //----------------------------------------------------------------------
    public TileManager(final String path) {
        mPath = path;
    }

    // ------------------------------------------------------------------------
    /*! Add Tilemap
    *
    *   From a file and the sizes, configure the tilemap to be render-ready
    */ //----------------------------------------------------------------------
    public void CreateTileMap(final Vector2D<Float> position, final Vector2D<Integer> blockscale) {
        int width = 0;
        int height = 0;
        mLayers = new ArrayList<>();
        mBounds = new AABB(position, new Vector2D<Float>(0.f, 0.f));
;
        ArrayList<Integer> tilecolumns = new ArrayList<>();
        ArrayList<Spritesheet> images = new ArrayList<>(); 
        ArrayList<Integer> ids = new ArrayList<>();

        //In case something can't be read properly, it means the tsx is corrupt
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document doc = builder.parse(new File(getClass().getClassLoader().getResource(mPath).toURI()));
            doc.getDocumentElement().normalize();

            NodeList tilelist = doc.getElementsByTagName("tileset");
            
            //Parse each of the tilesets, with it's image specs and it's image paths
            for(int i = 0, length = tilelist.getLength(); i < length; i++) {
                final Element node = (Element) tilelist.item(i);
                TSXDocument aux = (TSXDocument)AssetManager.Instance().GetResource("Content/TiledProject/" 
                + node.getAttribute("source")).Raw();
                tilecolumns.add(aux.mColumns);
                images.add(aux.mImage);
                ids.add(Integer.parseInt(node.getAttribute("firstgid")));
            }

            tilelist = doc.getElementsByTagName("layer");
            int layers = tilelist.getLength();
            String[] data = new String[layers];

            Element eElement = (Element) tilelist.item(0);
            width = Integer.parseInt(eElement.getAttribute("width"));
            height = Integer.parseInt(eElement.getAttribute("height"));

            //Create each of the layers
            for(int i = 0; i < layers; i++, eElement = (Element) tilelist.item(i)) {
                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(eElement.getAttribute("name").equals("entities")){
                    entityQueue = new TilemapEntities(position, data[i], images, width, height, blockscale.x, blockscale.y, tilecolumns, ids).entityQueue;
                }else if(!eElement.getAttribute("name").equals("colision")) {
                    mLayers.add(new DecorativeLayer(position, data[i], images, new Vector2D<>(width, height), blockscale, tilecolumns, ids));
                }else {
                    mLayers.add(new TilemapObject(position, data[i], images, width, height, blockscale.x, blockscale.y, tilecolumns, ids));
                    sLevelObjects = (TilemapObject)mLayers.get(mLayers.size() - 1);
                }
            }

            mBounds.SetSize(new Vector2D<Float>((float)(width * blockscale.x), (float)(height * blockscale.y)));
            GraphicsPipeline.GetGraphicsPipeline().AddRenderableBottom(this);

        } catch(ParserConfigurationException | SAXException | IOException | URISyntaxException e) {
            Logger.Instance().Log(Logger.Instance().GetLog("Engine"), 
                "Error - Can't read tilemap file. " + e.getMessage(), Level.SEVERE, 2, Color.red);
        }
    }

    // ------------------------------------------------------------------------
    /*! Estimate Bounds
    *
    *   Estimates the Bounds that a Tile Manager might have, even if it hasn't been created yet
    */ //----------------------------------------------------------------------
    public AABB EstimateBounds(final Vector2D<Integer> blockscale) {
        int width = 0;
        int height = 0;

        //In case something can't be read properly, it means the tsx is corrupt
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document doc = builder.parse(new File(getClass().getClassLoader().getResource(mPath).toURI()));
            doc.getDocumentElement().normalize();

            final Element eElement = (Element) doc.getElementsByTagName("layer").item(0);
            width = Integer.parseInt(eElement.getAttribute("width"));
            height = Integer.parseInt(eElement.getAttribute("height"));

            return new AABB(new Vector2D<>(), new Vector2D<Float>((float)(width * blockscale.x), (float)(height * blockscale.y)));

        } catch(ParserConfigurationException | SAXException | IOException | URISyntaxException e) {
            Logger.Instance().Log(Logger.Instance().GetLog("Engine"), 
                "Error - Can't read tilemap file. " + e.getMessage(), Level.SEVERE, 2, Color.red);
            return null;
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Bounds
    *
    *   Returns the level Boundaries
    */ //----------------------------------------------------------------------
    public AABB GetBounds() {
        return mBounds;
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders every layer that the tile manager contains
    */ //----------------------------------------------------------------------
    public void Render(final Graphics2D g, final CameraComponent camerapos) {
        mLayers.forEach(l -> l.Render(g, camerapos, mBounds));
    }
}
 