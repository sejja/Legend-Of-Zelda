//
//	Renderable.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import java.awt.Graphics2D;

import Engine.Math.Vector2D;

public interface Renderable {
    public void Render(Graphics2D g, CameraComponent camerapos);
}
