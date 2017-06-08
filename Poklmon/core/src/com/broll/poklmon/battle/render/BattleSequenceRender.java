package com.broll.poklmon.battle.render;

import java.util.HashMap;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.render.sequence.CatchPoklmonSequence;
import com.broll.poklmon.battle.render.sequence.PlayerPoklmonIntro;
import com.broll.poklmon.battle.render.sequence.PoklmonAttributeChangeAnimation;
import com.broll.poklmon.battle.render.sequence.PoklmonDamageSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonDefeatedSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonEvolutionSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonExpSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonHealSequence;
import com.broll.poklmon.battle.render.sequence.SequenceRender;
import com.broll.poklmon.battle.render.sequence.TrainerIntroSequence;
import com.broll.poklmon.battle.render.sequence.TrainerPoklmonIntro;
import com.broll.poklmon.battle.render.sequence.WildPoklmonIntro;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.data.basics.Graphics;

public class BattleSequenceRender
{


    private HashMap<BattleSequences, SequenceRender> sequences = new HashMap<BattleSequences, SequenceRender>();

    public BattleSequenceRender(BattleManager battleManager)
    {
        //create sequences
        sequences.put(BattleSequences.PLAYER_INTRO, new PlayerPoklmonIntro(battleManager));
        sequences.put(BattleSequences.WILD_POKLMON_INTRO, new WildPoklmonIntro(battleManager));
        sequences.put(BattleSequences.POKLMON_TAKING_DAMAGE, new PoklmonDamageSequence(battleManager));
        sequences.put(BattleSequences.POKLMON_DEFEATED, new PoklmonDefeatedSequence(battleManager));
        sequences.put(BattleSequences.POKLMON_ATTRIBUTE_CHANGE, new PoklmonAttributeChangeAnimation(battleManager));
        sequences.put(BattleSequences.POKLMON_HEAL_ANIMATION, new PoklmonHealSequence(battleManager));
        sequences.put(BattleSequences.PLAYER_GAINEXP, new PoklmonExpSequence(battleManager));
        sequences.put(BattleSequences.POKLMON_EVOLUTION, new PoklmonEvolutionSequence(battleManager));
        sequences.put(BattleSequences.THROW_POKLBALL, new CatchPoklmonSequence(battleManager));       
        sequences.put(BattleSequences.ENEMY_INTRO, new TrainerPoklmonIntro(battleManager));
        sequences.put(BattleSequences.TRAINER_INTRO, new TrainerIntroSequence(battleManager));
        
     
    }

    public SequenceRender getSequenceRender(BattleSequences sequence)
    {
        return sequences.get(sequence);
    }
    
    public void showAnimation(BattleSequences sequence, ProcessThreadHandler exit)
    {
        //start sequence
        sequences.get(sequence).invoke(exit);
    }

    public void render(Graphics g)
    {
        for (SequenceRender sequence : sequences.values())
        {
            if (sequence.isRunning())
            {
                sequence.render(g);
            }
        }
    }

    public void update(float delta)
    {
        for (SequenceRender sequence : sequences.values())
        {
            if (sequence.isRunning())
            {
                sequence.update(delta);
            }
        }
    }


}
