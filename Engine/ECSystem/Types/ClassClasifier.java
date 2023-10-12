package Engine.ECSystem.Types;
/*  The purpose of this interfave is control the classCode given
 *      -> Override it if you want to give the current specific classcode
 *  @Default 
 *      -> return nearlestSuperclass
 *  
 */
public interface ClassClasifier{
    public Class GetSuperClass();
}