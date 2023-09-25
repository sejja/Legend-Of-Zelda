//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 21/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Vector2D;

public class CameraComponent extends Component {

    private float widthLimit = 0;
    private float heightLimit = 0;

    public CameraComponent(Actor parent) {
        super(parent);
    }

    @Override
    public void Init() {
    }

    @Override
    public void Update() {
    }

    public void Bind() {
        GraphicsPipeline.GetGraphicsPipeline().BindCamera(this);
    }

    public void SetLimits(float width, float height) {
        
    }

    public Vector2D<Float> GetCoordinates() {
        Vector2D<Float> pos = GetParent().GetPosition();
        Vector2D<Integer> camera = GraphicsPipeline.GetGraphicsPipeline().GetDimensions();
        Vector2D<Float> temp = new Vector2D<>();
        temp.x = pos.x - camera.x / 2;
        temp.y = pos.y - camera.y / 2;

        return temp;
    }

    @Override
    public void ShutDown() {
    }
    
}
