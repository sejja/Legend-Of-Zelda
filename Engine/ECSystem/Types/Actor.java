//
//	Entity.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem.Types;

import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Gameplay.Link.Player;

public abstract class Actor extends Entity {
    private ArrayList<Component> mComponents;

    private Vector2D<Float> pseudoPosition;
    private Float differenceX;
    private Float differenceY;

    // ------------------------------------------------------------------------
    /*! Add Component
    *
    *   Adds a Component to the actor
    */ //----------------------------------------------------------------------
    public <T extends Component> T AddComponent(T c) {
        c.Init();
        mComponents.add(c);
        return (T) c;
    }

    // ------------------------------------------------------------------------
    /*! Remove Component
    *
    *   Removes a Component from the Actor
    */ //----------------------------------------------------------------------
    public void RemoveComponent(Component comp) {
        mComponents.remove(comp);
        comp.ShutDown();
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions
    */ //----------------------------------------------------------------------
    public Actor(Vector2D<Float> position) {
        super();
        SetPosition(position);
        mComponents = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions with a name
    */ //----------------------------------------------------------------------
    public Actor(String name, Vector2D<Float> position) {
        super(name);
        SetPosition(position);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the Animation
    */ //----------------------------------------------------------------------
    public void Update() {
        //Update every component
        for(Component x : mComponents)
            x.Update();
        try{
            pseudoPosition.x = differenceX + GetPosition().x;
            pseudoPosition.y = differenceY + GetPosition().y;
        }catch(java.lang.NullPointerException e){
            //System.err.println(this.getClass() + ": Pseudoposition not difined");
        }
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the Size of the Actor
    */ //----------------------------------------------------------------------
    public void SetScale(Vector2D<Float> vec) {
        super.SetScale(vec);
    }

    public ArrayList<Component> getmComponents() {
        return mComponents;
    }

    public void setmComponents(ArrayList<Component> mComponents) {
        this.mComponents = mComponents;
    }

    public void setPseudoPosition(Vector2D<Float> pseudoPosition){
        this.pseudoPosition = pseudoPosition;
        differenceX = pseudoPosition.x - GetPosition().x;
        differenceY = pseudoPosition.y - GetPosition().y;
    }

    public Vector2D<Float> getPSeudoPosition(){
        return this.pseudoPosition;
    }
}
