//
//	ZeldaCameraComponent.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import Engine.ECSystem.Types.Actor;
import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Vector2D;

public class ZeldaCameraComponent extends CameraComponent {
    private Vector2D<Float> mTopRight;
    private Vector2D<Float> mBottomLeft;

    public ZeldaCameraComponent(Actor parent) {
        super(parent);
        mTopRight = new Vector2D<>(0.f, 0.f);
        mBottomLeft = new Vector2D<>(0.f, 0.f);
    }
    
    public void SetBounds(Vector2D<Float> topright, Vector2D<Float> bottomleft) {
        mTopRight = topright;
        mBottomLeft = bottomleft;
    }

    public Vector2D<Float> GetCoordinates() {
        Vector2D<Float> pos = GetParent().GetPosition();
        Vector2D<Integer> camera = GraphicsPipeline.GetGraphicsPipeline().GetDimensions();
        Vector2D<Float> temp = new Vector2D<>();
        temp.x = Math.max(Math.min(pos.x, mBottomLeft.x), mTopRight.x) - camera.x / 2;
        temp.y = Math.max(Math.min(pos.y, mBottomLeft.y), mTopRight.y) - camera.y / 2;
        return temp;
    }
}
