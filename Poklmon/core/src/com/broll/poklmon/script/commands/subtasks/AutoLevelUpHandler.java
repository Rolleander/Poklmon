package com.broll.poklmon.script.commands.subtasks;

import com.badlogic.gdx.math.MathUtils;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.poklmon.util.LevelCalcListener;
import com.broll.poklmon.poklmon.util.PoklmonAttackLearning;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.PoklmonData;

public class AutoLevelUpHandler implements LevelCalcListener {
    private DataContainer data;
    private PoklmonData poklmon;
    private PoklmonAttackLearning attackLearning;

    public AutoLevelUpHandler(DataContainer data) {
        this.data = data;
        attackLearning = new PoklmonAttackLearning(data);
    }

    public void initPoklmon(PoklmonData poklmon) {
        this.poklmon = poklmon;
    }

    @Override
    public void newLevel(int level) {
        //nothing to do
    }

    @Override
    public boolean canLearnAttack(int attack) {
        if (!attackLearning.tryLearnAttack(poklmon, attack)) {
            //will forget random previous attack
            attackLearning.learnAttack(poklmon, attack, MathUtils.random(0, 3));
        }
        return true;
    }

    @Override
    public void evolvesTo(int poklmonID) {
        //evolve
        poklmon.setPoklmon(poklmonID);
    }
}
