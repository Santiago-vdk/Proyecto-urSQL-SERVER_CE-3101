/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.Objects;

/**
 *
 * @author David
 */
public class Condition {
    private String _ColA;
    private String _ColB;
    private String _Val;
    private String _Comp;
    
    /**
     *
     * @param pColA
     * @param pColB
     * @param pVal
     * @param pComp
     */
    public Condition(String pColA,String pColB,String pVal,String pComp){
        _ColA=pColA;
        _ColB=pColB;
        _Val=pVal;
        _Comp=pComp;
    }
    
    /**
     *
     * @return
     */
    public String get_ColA(){
        return _ColA;
    }

    /**
     *
     * @return
     */
    public String get_ColB(){
        return _ColB;
    }

    /**
     *
     * @return
     */
    public String get_Val(){
        return _Val;
    }

    /**
     *
     * @return
     */
    public String get_Comp(){
        return _Comp;
    }
}
