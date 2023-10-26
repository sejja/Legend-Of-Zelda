package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Tile.Block;
import Engine.Math.Vector2D;

public class DialogueWindow extends Component implements Renderable{
    
    private DialogueWindow window;
    private int x; //the x position of the DialogueWindow
    private int y; //the y position of the DialogueWindow
    private int width; //the width of the DialogueWindow
    private int height; //the height of the DialogueWindow
    private static int j = 0; //Index del String del arraylist del dialogo
    private int k = 0;
    private Npc npc; //the Npc we are interacting with
    private String texto;
    private String line;
    private File f = new File("Gameplay\\NPC\\Dialogues.txt");
    private String[] a = new String[4];
    private int l = 0;
    protected ArrayList<String> dialogue = new ArrayList<>();

    protected DialogueWindow(Npc npc) {
        super(npc);
        //TODO Auto-generated constructor stub
        this.npc = npc;
        this.window = this;
    }
    
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        x = Block.getWidth()*2;
        y = Block.getHeight()*8;
        width = 1280  - (Block.getWidth()*4);;
        height = Block.getWidth()*3;
        Font mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);
        drawSubWindow(x,y,width,height, g);
        x += 20;
        y += 20;
        /*for(int i=l; i < a.length;i++){
            System.out.println(i);
            if(a[i] != null){
                if(i%4 != 0 && i != 0) {
                    mFont.Render(g, a[i], new Vector2D<Float>((float)x, (float)y), 32, 32, 15, 0);
                    y += 40; 
                }

            }
            l++;
        }*/
        for(String line: dialogue.get(j).split("\n")){
            mFont.Render(g, line, new Vector2D<Float>((float)x, (float)y), 32, 32, 15, 0);
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
    
    public DialogueWindow getDialgueWindow(){return this;}
    public int getJ(){return j;}
    public void setJ(int index){j = index;}
    public void setSiguiente(){
        j++;
    }
    
    public String[] getA() {
        return a;
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
            String linea = br.readLine();
            String[] a;
            
            while(linea != null){
                if(linea.startsWith(npc.getName())){
                    dialogue.clear();
                    linea = br.readLine();
                    while(linea.length() != 0){
                        System.out.println(linea.length());
                        linea = linea.replaceAll(",","]");
                        String print = "";
                        l=0;
                        int space = 0;
                        for(int i=0; i< linea.length(); i++){
                            if(linea.charAt(i) == ' '){
                                space = i;
                            }
                            if((i-62)%62 == 0 && i!= 0){
                                if(linea.charAt(i-62) == ' '){
                                    print = print + linea.substring(l+1,i) + "\n";
                                }else if(linea.charAt(i-1) != ' ' && linea.charAt(i) != ' '){
                                    print = print + linea.substring(l, space) + "\n";
                                    l=space+1;
                                }else{
                                    print = print + linea.substring(l,i) + "\n";
                                    l=i;
                                }
                            }   
                        }
                        if(linea.charAt(linea.length()-(linea.length()-l)) == ' '){
                            print = print + linea.substring(l+1,linea.length());
                        }else{
                            print = print + linea.substring(l,linea.length());
                        }
                        dialogue.add(print);
                        linea = br.readLine();
                    }
                    /* texto = (String) br.readLine();
                    String print = "";
                    texto = texto.replaceAll(",","]");
                    int space = 0;
                    l=0;
                    dialogue.clear();
                    for(int i=0; i< texto.length(); i++){
                        if(texto.charAt(i) == ' '){
                            space = i;
                        }
                        if((i%((62*4)+1) == 0 && i != 0) ){
                            dialogue.add(print);
                            print = "";
                        }else
                        if((i-62)%62 == 0 && i!= 0){
                            if(texto.charAt(i-62) == ' '){
                                print = print + texto.substring(l+1,i) + "\n";
                            }else if(texto.charAt(i-1) != ' ' && texto.charAt(i) != ' '){
                                print = print + texto.substring(l, space) + "\n";
                                l=space+1;
                            }else{
                                print = print + texto.substring(l,i) + "\n";
                                l=i;
                            }
                        }
                    }
                    if(texto.charAt(texto.length()-(texto.length()-l)) == ' '){
                        print = print + texto.substring(l+1,texto.length());
                    }else{
                        
                        print = print + texto.substring(l,texto.length());
                    }
                    dialogue.add(print);
                    */
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
