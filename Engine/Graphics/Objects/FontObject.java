package Engine.Graphics.Objects;

import java.awt.Graphics2D;

import Engine.ECSystem.Types.ECObject;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;

public class FontObject extends ECObject implements Renderable {
    private Font mFont;
    private String mString;

    public FontObject(String fontsheet, String text) {
        mFont = new Font(fontsheet, 16, 16); 
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
        mString = text;
    }

    @Override
    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        mFont.Render(g, mString, new Vector2D<>(GetPosition().x - camerapos.x, GetPosition().y - camerapos.y), (int)(float)GetScale().x, (int)(float)GetScale().y, 56, 0);
    }

    protected void finalize() throws Throwable {  
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(null);
    }
}