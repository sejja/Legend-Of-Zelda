package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.Block;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Window.PresentBuffer;
import Engine.Window.Window;

public class DialogueWindow extends Component implements Renderable{

    private static String dialogue;
    private static DialogueWindow g;

    protected DialogueWindow(Actor parent) {
        super(parent);
        //TODO Auto-generated constructor stub
        this.g = this;
    }

    @Override
    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        // TODO Auto-generated method stub
        //Window
        int x = Block.getWidth()/2;
        int y = Block.getHeight()/2;
        int width = 1280  - (Block.getWidth()*4);;
        int height = Block.getWidth()*4;

        Font mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);

        drawSubWindow(x,y,width,height, g);

        //g.setFont(mFont);
        x += 100;
        y += 100;
        g.drawString("hadoken", x, y);
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
        // TODO Auto-generated method stub
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    public static void drawSubWindow(int x, int y, int width, int height, Graphics2D g) {
        
        Color c = new Color(0,0,0, 200);
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g.setColor(c);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }
    
    public static DialogueWindow getDialgueWindow(){
        return g;
    }
}
