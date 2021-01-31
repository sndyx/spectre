package dev.sndy.spectre.item.effect;

import java.util.ArrayList;

public class EffectList {

    private final ArrayList<Effect> effects = new ArrayList<>();

    public void add(Effect effect){
        if(effect.type != EffectType.INVALID){
            effects.add(effect);
        }
    }

    public void merge(EffectList effects){
        this.effects.addAll(effects.getEffects());
    }

    public ArrayList<Effect> getEffects(){
        return effects;
    }

}
