package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Engine.Assets.AssetManager;
import Engine.ECSystem.Types.Actor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Tile.Block;
import Engine.Math.Vector2D;

public class DialogueWindow extends Component implements Renderable{
    
    private int x; //the x position of the DialogueWindow
    private int y; //the y position of the DialogueWindow
    private int width; //the width of the DialogueWindow
    private int height; //the height of the DialogueWindow
    private static int j = 0; //Index del String del arraylist del dialogo
    private Npc npc; //the Npc we are interacting with
    private String linea;
    private File f = new File("Gameplay/NPC/Dialogues.txt");
    private int l = 0;
    protected ArrayList<String> dialogue = new ArrayList<>();

    protected DialogueWindow(Npc npc) {
        super(npc);
        //TODO Auto-generated constructor stub
        this.npc = npc;
    }
    
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        x = Block.GetWidth()*2;
        y = Block.GetHeight()*8;
        width = 1280  - (Block.GetWidth()*4);;
        height = Block.GetWidth()*3;
        Font mFont = new Font(AssetManager.Instance().GetResource("Content/Fonts/ZeldaFont.png"), new Vector2D<>(16, 16));
        drawSubWindow(x,y,width,height, g);
        x += 20;
        y += 20;

        for(String line: dialogue.get(j).split("\n")){
            mFont.Render(g, line, new Vector2D<Float>((float)x, (float)y), new Vector2D<>(32, 32), new Vector2D<>(15, 0));
            y += 40;
        }
    }
    @Override
    public void Init() {
        // TODO Auto-generated method stub
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
        readText();
        textModification();
    }
    @Override
    public void Update() {
        // TODO Auto-generated method stub
    }
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    public static void drawSubWindow(int x, int y, int width, int height, Graphics2D g) {
        Color c = new Color(0,0,0, 160); //we set the background color to black with some opacity
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 35, 35); //we made the rectangel of the textbox
        c = new Color(255, 255, 255); //se set the outside line color to white
        g.setColor(c);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
        g.setStroke(new BasicStroke(1));
    }

    public int getJ(){return j;}
    public void setJ(int index){j = index;}
    public void setSiguiente(){
        j++;
    }
    
    public ArrayList<String> getDialogue(){
        return dialogue;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }
    public void textModification(){

    }

    public void readText(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            linea = br.readLine();
            while(linea != null){
                if(linea.startsWith(npc.getName())){
                    dialogue.clear();
                    linea = br.readLine();
                    while(linea.length() != 0){
                        linea = linea.replaceAll(",","]");
                        linea = linea.replaceAll("!","_");
                        String modifiedText = "";
                        l=0;
                        int space = 0;
                        for(int i=0; i< linea.length(); i++){
                            if(linea.charAt(i) == ' '){
                                space = i;
                            }
                            if((i-60)%60 == 0 && i!= 0){
                                if(linea.charAt(i-60) == ' '){
                                    l = l+1;
                                }
                                if(linea.charAt(i-1) != ' ' && linea.charAt(i) != ' '){
                                    modifiedText = modifiedText + linea.substring(l, space) + "\n";
                                    l=space+1;
                                }else{
                                    modifiedText = modifiedText + linea.substring(l,i) + "\n";
                                    l=i;
                                }
                            }   
                        }
                        if(linea.charAt(linea.length()-(linea.length()-l)) == ' '){
                            modifiedText = modifiedText + linea.substring(l+1,linea.length());
                        }else{
                            modifiedText = modifiedText + linea.substring(l,linea.length());
                        }
                        dialogue.add(modifiedText);
                        linea = br.readLine();
                    }
                }
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
