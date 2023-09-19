package Engine.ECSystem;

public abstract class Component {
    private Entity mParent;

    protected Component(Entity parent) {
        mParent = parent;
    }

    public Entity GetParent() {
        return mParent;
    }
    public abstract void Init();
    public abstract void Update();
    public abstract void ShutDown();
}
