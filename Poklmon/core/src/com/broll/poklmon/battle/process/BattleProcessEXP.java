package com.broll.poklmon.battle.process;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.EXPGainCalculator;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.render.BattleSequences;
import com.broll.poklmon.battle.render.sequence.PoklmonEvolutionSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonExpSequence;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.battle.process.callbacks.XpReceiverCalculationCallback;
import com.broll.poklmon.battle.process.callbacks.XpValueCalculationCallback;
import com.broll.poklmon.game.GameDifficulty;
import com.broll.poklmon.poklmon.util.FpCalculator;
import com.broll.poklmon.poklmon.util.LevelCalcListener;
import com.broll.poklmon.poklmon.util.PoklmonAttackLearning;
import com.broll.poklmon.poklmon.util.PoklmonLevelCalc;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;

public class BattleProcessEXP extends BattleProcessControl {
    private PlayerPoklmon expPoklmon;
    private PoklmonAttackLearning attackLearning;
    private FpCalculator fpCalculator;

    public BattleProcessEXP(BattleManager manager, BattleProcessCore handler) {
        super(manager, handler);
        attackLearning = new PoklmonAttackLearning(manager.getData());
        fpCalculator = new FpCalculator(manager.getData());
    }

    private class LearnChanges implements LevelCalcListener {
        @Override
        public void newLevel(int level) {
            BattleProcessEXP.this.newLevel(level);
        }

        @Override
        public boolean canLearnAttack(int attack) {
            return BattleProcessEXP.this.learnAtttack(attack);

        }

        @Override
        public void evolvesTo(int poklmonID) {
            BattleProcessEXP.this.evolvesTo(poklmonID);
        }
    }

    private void newLevel(int level) {
        manager.getData().getSounds().playSound("levelup");
        int oldMaxKp = expPoklmon.getAttributes().getMaxhealth();
        // update new stats and heal
        FightPokemonBuilder.updateFightPoklmon(manager, expPoklmon);
        int newMaxKp = expPoklmon.getAttributes().getMaxhealth();
        int diff = newMaxKp - oldMaxKp;
        expPoklmon.doHeal(diff);
        showText(expPoklmon.getName() + " erreicht Level " + level + "!");
    }

    private boolean learnAtttack(int attack) {
        Attack atk = manager.getData().getAttacks().getAttack(attack);
        if (attackLearning.tryLearnAttack(expPoklmon.getPoklmonData(), attack)) {
            showText(expPoklmon.getName() + " hat " + atk.getName() + " erlernt!");
            expPoklmon.setAttack(atk, attackLearning.getLearnedPlace());
            return true;
        } else {
            boolean learning = true;
            while (learning) {
                int selection = showSelection(
                        expPoklmon.getName()
                                + " versucht "
                                + atk.getName()
                                + " zu erlernen, kann aber nicht mehr als vier Attacken gleichzeitig haben. Soll eine andere Attacke für "
                                + atk.getName() + " vergessen werden?", new String[]{"Ja", "Nein"});
                if (selection == 0) {
                    String[] atkNames = new String[4];
                    for (int i = 0; i < 4; i++) {
                        atkNames[i] = expPoklmon.getAttacks()[i].getAttack().getName();
                    }
                    selection = showSelection("Welche Attacke soll für " + atk.getName() + " vergessen werden?",
                            new String[]{"Nicht lernen", atkNames[0], atkNames[1], atkNames[2], atkNames[3]});
                    if (selection > 0) {
                        int place = selection - 1;
                        attackLearning.learnAttack(expPoklmon.getPoklmonData(), attack, place);
                        expPoklmon.setAttack(atk, attackLearning.getLearnedPlace());
                        showText(expPoklmon.getName() + " vegisst " + atkNames[place] + "...");
                        showText(expPoklmon.getName() + " hat " + atk.getName() + " erlernt!");
                        learning = false;
                        return true;
                    }
                } else {
                    learning = false;
                }
            }
        }
        return false;
    }

    private void evolvesTo(int id) {
        // show animation
        PoklmonEvolutionSequence seq = (PoklmonEvolutionSequence) manager.getBattleRender().getSequenceRender()
                .getSequenceRender(BattleSequences.POKLMON_EVOLUTION);
        seq.setPoklmon(expPoklmon);
        manager.getBattleRender().getSequenceRender()
                .showAnimation(BattleSequences.POKLMON_EVOLUTION, processThreadHandler);
        waitForResume();
        manager.getBattleRender().getHudRender().setShowHud(true);
        Poklmon pokl = manager.getData().getPoklmons().getPoklmon(id);
        // update stats and data
        PoklmonData data = expPoklmon.getPoklmonData();
        // check for name update (when u didnt change the name => update to
        // evolution name)
        if (data.getName().equals(expPoklmon.getPoklmon().getName())) {
            data.setName(null);
        }
        data.setPoklmon(id);
        expPoklmon.evolveToPoklmon(pokl, manager.getData());
        FightPokemonBuilder.updateFightPoklmon(manager, expPoklmon);
    }

    public synchronized void enemyReleaseEXP(FightPoklmon enemy) {
        int level = enemy.getLevel();
        int expBase = enemy.getPoklmon().getExpBasePoints();
        boolean trainerBattle = manager.getParticipants().isTrainerFight();

        int exp = EXPGainCalculator.getEXPValue(expBase, level, trainerBattle);
        exp = (int) ((float) exp * GameDifficulty.getExpMultiplicator(manager.getGame().getPlayer().getVariableControl()));
        // script callback
        for (XpValueCalculationCallback script : manager.getScriptCalls(XpValueCalculationCallback.class)) {
            exp = script.call(exp);
        }
        ArrayList<PlayerPoklmon> winners = getGainingPoklmons(enemy);
        int number = winners.size();
        if (number > 0) {
            int exppart = getEXPParts(exp, number);
            for (PlayerPoklmon p : winners) {
                // give exp to poklmons
                giveEXPTo(p, exppart);
                // add fp
                fpCalculator.addFps(p.getPoklmonData(), enemy.getPoklmon().getId());
            }
        }
    }

    private synchronized void giveEXPTo(PlayerPoklmon poklmon, int exp) {
        String name = poklmon.getName();
        String text = TextContainer.get("gainEXP", name, exp);
        showText(text);
        PoklmonLevelCalc levelCalc = new PoklmonLevelCalc(manager.getData());
        expPoklmon = poklmon;
        if (poklmon == manager.getParticipants().getPlayer()) {
            // when poklmon is fighting, show exp gain animation
            PoklmonExpSequence sequence = (PoklmonExpSequence) manager.getBattleRender().getSequenceRender()
                    .getSequenceRender(BattleSequences.PLAYER_GAINEXP);
            sequence.init(levelCalc, exp);
            while (!sequence.isFinished()) {
                // restart
                manager.getBattleRender().getSequenceRender()
                        .showAnimation(BattleSequences.PLAYER_GAINEXP, processThreadHandler);
                waitForResume();
                int newLevel = sequence.getNewLevel();
                if (newLevel != -1) {
                    newLevel(newLevel);
                }
                int evolves = sequence.getEvolvesTo();
                if (evolves != -1) {
                    evolvesTo(evolves);
                }
                int learns = sequence.getLearnAttack();
                if (learns != -1) {
                    learnAtttack(learns);
                }
            }
        } else {
            // else just add the exp
            levelCalc.setLevelCalcListener(new LearnChanges());
            levelCalc.addEXP(poklmon.getPoklmonData(), exp);
            poklmon.updateExp();
        }
    }

    private synchronized int getEXPParts(int exp, int number) {
        if (exp % number == 0) {
            return exp / number;
        } else {
            int r = exp % number;
            return exp / number + r;
        }
    }

    private synchronized ArrayList<PlayerPoklmon> getGainingPoklmons(FightPoklmon enemy) {
        ArrayList<PlayerPoklmon> poklmons = new ArrayList<PlayerPoklmon>();
        for (FightPoklmon p : manager.getParticipants().getPlayerTeam()) {
            PlayerPoklmon pp = (PlayerPoklmon) p;
            if (pp.getExpSources().contains(enemy)) {
                // is a fighter and not fainted => get exp
                if (!pp.isFainted()) {
                    poklmons.add(pp);
                }
            }
        }
        // script callback for exp divider
        for (XpReceiverCalculationCallback script : manager.getScriptCalls(XpReceiverCalculationCallback.class)) {
            script.call(poklmons);
        }
        return poklmons;
    }

}
