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

import Engine.ECSystem.Types.ECObject;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;

public class TileManager extends ECObject implements Renderable {
    public static ArrayList<Tilemap>  mLayers;

    // ------------------------------------------------------------------------
    /*! Default Constructor
    *
    *   Constructs an empty array of layers
    */ //----------------------------------------------------------------------
    public TileManager() {
        mLayers = new ArrayList<>();
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Takes a path, and generates a whole TileManager from it
    */ //----------------------------------------------------------------------
    public TileManager(String path) {
        mLayers = new ArrayList<>();
        AddTileMap(path, 64, 64);
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
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
        Spritesheet sprite;
        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = builderFactory.newDocumentBuilder();
            Document doc = db.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
        
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("map");
            NodeList tilesetnode = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element element = (Element) node;
            Element elementnode = (Element) tilesetnode.item(0);

            Document aux = db.parse(new File(getClass().getClassLoader().getResource("Content/TiledProject/" + elementnode.getAttribute("source")).toURI()));
            aux.getDocumentElement().normalize();
            NodeList tilesetdata = aux.getElementsByTagName("tileset");
            NodeList imagedata = aux.getElementsByTagName("image");
            Node node2 = tilesetdata.item(0);
            Element element2 = (Element) node2;
            Node node3 = imagedata.item(0);
            Element element3 = (Element) node3;

            imagepath = element3.getAttribute("source");
            tilewidth = Integer.parseInt(element2.getAttribute("tilewidth"));
            tileheight = Integer.parseInt(element2.getAttribute("tileheight"));
            tilecount = Integer.parseInt(element2.getAttribute("tilecount"));
            tilecolumns = Integer.parseInt(element2.getAttribute("columns"));

            layers = list.getLength();
            list = doc.getElementsByTagName("layer");
            layers = 3;
            sprite = new Spritesheet("Content/Tiles/" + imagepath, tilewidth, tileheight);
        
            //For each layer, get the width and height.
            for(int i = 0; i < layers; i++) {
                node = list.item(0);
                element = (Element) node;

                if(i <= 0) {
                    width = Integer.parseInt("50"); //Oh yeah, so saucy
                    height = Integer.parseInt("50");
                }

                data[i] = element.getElementsByTagName("data").item(0).getTextContent();
                
                if(i >= 1) {
                    mLayers.add(new TilemapNorm(data[i], sprite, width, height, blockwith, blockheigh, tilecolumns));
                } else {
                    mLayers.add(new TilemapObject(data[i], sprite, width, height, blockwith, blockheigh, tilecolumns));
                }

            }
        
        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        for(int i= 0; i < mLayers.size(); i++) {
            mLayers.get(i).Render(g, camerapos);
        }
    }
}
 