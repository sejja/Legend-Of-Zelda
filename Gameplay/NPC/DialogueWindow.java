package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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
    private Npc npc; //the Npc we are interacting with

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
        for(String line: npc.getDialoguesArrayList().get(j).split("\n")){
            mFont.Render(g, line, new Vector2D<Float>((float)x, (float)y), 32, 32, 15, 0);
            y += 40;
        }
    }
    @Override
    public void Init() {
        // TODO Auto-generated method stub
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
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
    }
    
    public DialogueWindow getDialgueWindow(){return this;}
    public int getJ(){return j;}
    public void setJ(int index){j = index;}
    public void setSiguiente(){
        j++;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

}
