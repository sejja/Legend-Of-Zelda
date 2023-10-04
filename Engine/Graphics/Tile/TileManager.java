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
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.ECSystem.Types.ECObject;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class TileManager extends ECObject implements Renderable {
    public static ArrayList<Tilemap>  mLayers;
    private String mPath;
    private Vector2D<Float> mPosition;
    private AABB mBounds;

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
        mPath = path;
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Add Tilemap
    *
    *   From a file and the sizes, configure the tilemap to be render-ready
    */ //----------------------------------------------------------------------
    public void CreateTileMap(int blockwith, int blockheigh, Vector2D<Float> position) {
        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileColumns;
        int layers = 0;
        Spritesheet sprite;

        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(mPath).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            NodeList tilesetnode = doc.getElementsByTagName("tileset");
            Element eElement = (Element) node;
            Element elementnode = (Element) tilesetnode.item(0);

            Document aux = builder.parse(new File(getClass().getClassLoader().getResource("Content/TiledProject/" + elementnode.getAttribute("source")).toURI()));
            aux.getDocumentElement().normalize();
            NodeList tilesetdata = aux.getElementsByTagName("tileset");
            NodeList imagedata = aux.getElementsByTagName("image");
            Node node2 = tilesetdata.item(0);
            Element element2 = (Element) node2;
            Node node3 = imagedata.item(0);
            Element element3 = (Element) node3;

            imagePath = element3.getAttribute("source");
            tileWidth = Integer.parseInt(element2.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(element2.getAttribute("tileheight"));
            tileColumns = Integer.parseInt(element2.getAttribute("columns"));
            
            sprite = new Spritesheet("Content/Tiles/" + imagePath, tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for(int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if(i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i >= 1) {
                    mLayers.add(new TilemapNorm(position, data[i], sprite, width, height, blockwith, blockheigh, tileColumns));
                } else {
                    mLayers.add(new TilemapObject(position, data[i], sprite, width, height, blockwith, blockheigh, tileColumns));
                }
            }

            mBounds = new AABB(position, new Vector2D<Float>((float)(width * blockwith), (float)(height * blockheigh)));

        } catch(Exception e) {
            System.out.println("ERROR - TILEMANAGER: can not read tilemap:");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Vector2D<Float> GetPosition() {
        return mPosition;
    }

    public AABB EstimateBounds(int blockheigh, int blockwith) {
        int layers = 0;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(mPath).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            NodeList tilesetnode = doc.getElementsByTagName("tileset");
            Element eElement = (Element) node;
            Element elementnode = (Element) tilesetnode.item(0);

            Document aux = builder.parse(new File(getClass().getClassLoader().getResource("Content/TiledProject/" + elementnode.getAttribute("source")).toURI()));
            aux.getDocumentElement().normalize();
            NodeList tilesetdata = aux.getElementsByTagName("tileset");
            NodeList imagedata = aux.getElementsByTagName("image");
            Node node2 = tilesetdata.item(0);
            Element element2 = (Element) node2;
            Node node3 = imagedata.item(0);
            Element element3 = (Element) node3;
            int width = 0;
            int height = 0;

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for(int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if(i <= 0) {
                    width = Math.max(width, Integer.parseInt(eElement.getAttribute("width")));
                    height = Math.min(height, Integer.parseInt(eElement.getAttribute("height")));
                }
            }

            return new AABB(new Vector2D<>(0.f, 0.f), new Vector2D<Float>((float)(width * blockwith), (float)(height * blockheigh)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public AABB GetBounds() {
        return mBounds;
    }

    public void Render(Graphics2D g, CameraComponent camerapos) {
        for(int i= 0; i < mLayers.size(); i++) {
            mLayers.get(i).Render(g, camerapos);
        }
    }
}
 