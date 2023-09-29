package Engine.Graphics.Objects;

import java.awt.Graphics2D;

import Engine.ECSystem.Types.ECObject;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class FontObject extends ECObject implements Renderable {
    private Font mFont;
    private String mString;
    private int mOffSet;

    public FontObject(String fontsheet, String text, int offSet) {
        mFont = new Font(fontsheet, 16, 16); 
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
        mString = text;
        mOffSet = offSet;
    }

    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        Vector2D<Float> camcoord = camerapos.GetCoordinates();

        if(camerapos.OnBounds(new AABB(GetPosition(), GetScale())))
            mFont.Render(g, mString, new Vector2D<>(GetPosition().x - camcoord.x, GetPosition().y - camcoord.y), (int)(float)GetScale().x, (int)(float)GetScale().y, 56, 0);
    }

    protected void finalize() throws Throwable {  
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(null);
    }
}