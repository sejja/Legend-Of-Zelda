//
//	Tilemap.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.Graphics.Sprite;

public class TileManager {
    public static ArrayList<Tilemap>  mLayers;

    // ------------------------------------------------------------------------
    /*! Default Constructor
    *
    *   Constructs an empty array of layers
    */ //----------------------------------------------------------------------
    public TileManager() {
        mLayers = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Takes a path, and generates a whole TileManager from it
    */ //----------------------------------------------------------------------
    public TileManager(String path) {
        mLayers = new ArrayList<>();
        AddTileMap(path, 64, 64);
    }

    // ------------------------------------------------------------------------
    /*! Add Tilemap
    *
    *   From a file and the sizes, configure the tilemap to be render-ready
    */ //----------------------------------------------------------------------
    private void AddTileMap(String path, int blockwith, int blockheigh) {
        String imagepath;

        int width = 0;
        int height = 0;
        int tilewidth;
        int tileheight;
        int tilecount;
        int tilecolumns;
        int layers = 0;
        Sprite sprite;
        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = builderFactory.newDocumentBuilder();
            Document doc = db.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
        
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element element = (Element) node;

            imagepath = element.getAttribute("name");
            tilewidth = Integer.parseInt(element.getAttribute("tilewidth"));
            tileheight = Integer.parseInt(element.getAttribute("tileheight"));
            tilecount = Integer.parseInt(element.getAttribute("tilecount"));
            tilecolumns = Integer.parseInt(element.getAttribute("columns"));

            list = doc.getElementsByTagName("layers");
            layers = list.getLength();
            sprite = new Sprite("Tiles/" + imagepath + ".png", tilewidth, tileheight);
        
            //For each layer, get the width and height.
            for(int i = 0; i < layers; i++) {
                node = list.item(0);
                element = (Element) node;

                if(i <= 0) {
                    width = Integer.parseInt(element.getAttribute("width"));
                    height = Integer.parseInt(element.getAttribute("height"));
                }

                data[i] = element.getElementsByTagName("data").item(0).getTextContent();
                
                if(i >= 1) {
                    mLayers.add(new TilemapNorm(data[i], sprite, width, height, blockwith, blockheigh, tilecolumns));
                } else {
                    mLayers.add(new TilemapObject(data[i], sprite, width, height, blockwith, blockheigh, tilecolumns));
                }

            }
        
        } catch(Exception e) {

        }
    }

    public void Render(Graphics2D g) {
        for(int i= 0; i < mLayers.size(); i++) {
            mLayers.get(i).Render(g);
        }
    }
}
 