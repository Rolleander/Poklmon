package com.broll.poklmon.battle.process;

import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.CacheWildCalculator;
import com.broll.poklmon.battle.item.ItemScriptExecutor;
import com.broll.poklmon.battle.item.MedicineItemRunner;
import com.broll.poklmon.battle.item.PoklballItemRunner;
import com.broll.poklmon.battle.item.WearableItemVariables;
import com.broll.poklmon.battle.poklmon.CoughtPoklmonFactory;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.battle.render.BattleSequences;
import com.broll.poklmon.battle.render.sequence.CatchPoklmonSequence;
import com.broll.poklmon.save.PoklmonData;

import java.util.List;

public class BattleProcessItems extends BattleProcessControl {

    private ItemScriptExecutor itemScriptExecutor;
    private WearableItemVariables wearableItemVariables;
    private boolean catchedPoklmon;

    public BattleProcessItems(BattleManager manager, BattleProcessCore handler) {
        super(manager, handler);
        itemScriptExecutor = new ItemScriptExecutor();
        wearableItemVariables = new WearableItemVariables();
    }

    public void initWearables() {
        initWearables(manager.getParticipants().getPlayerTeam());
        initWearables(manager.getParticipants().getEnemyTeam());
    }

    private void initWearables(List<FightPoklmon> poklmons) {
        for (FightPoklmon poklmon : poklmons) {
            int item = poklmon.getCarryItem();
            if (item != -1) {
                Item wear = manager.getData().getItems().getItem(item);
                if (wear.getType() == ItemType.WEARABLE) {
                    itemScriptExecutor.buildWearable(wear, poklmon, manager, core);
                }
            }
        }
        // update poklmon stats (so items effect them)
        for (FightPoklmon poklmon : poklmons) {
            FightPokemonBuilder.updateFightPoklmon(manager, poklmon);
        }
    }

    public void useItem(int id, String user, FightPoklmon target) {
        Item item = manager.getData().getItems().getItem(id);
        manager.getPlayer().getInventarControl().useItem(id);
        if (item.getType() == ItemType.POKLBALL) {
            showText(user + " wirft " + item.getName() + "!");
            catchPoklmon(item);

        } else if (item.getType() == ItemType.MEDICIN) {
            showText(user + " gibt " + target.getName() + " " + item.getName() + " !");
            MedicineItemRunner medicine = itemScriptExecutor.executeMedicineScript(target, item, manager, core);
            if (medicine.isCancel()) {
                showText("Das Item hatte keine Wirkung!");
            }
        }
    }

    public boolean isCatchedPoklmon() {
        return catchedPoklmon;
    }

    public void catchPoklmon(Item item) {
        catchedPoklmon = false;
        PoklballItemRunner poklball = itemScriptExecutor.buildPoklball(item, manager, core);
        float strength = poklball.getBallStrength();
        boolean success = false;
        int wobbleCount = 0;
        int poklballIcon = poklball.getBallIcon();
        if (poklball.isCatchAlways()) {
            success = true;
            wobbleCount = 3;
        } else {
            success = CacheWildCalculator.cacheWildPoklmon(manager.getParticipants().getEnemy(), strength);
            wobbleCount = CacheWildCalculator.getWobbleCount();
        }
        CatchPoklmonSequence sequence = (CatchPoklmonSequence) manager.getBattleRender().getSequenceRender()
                .getSequenceRender(BattleSequences.THROW_POKLBALL);
        sequence.init(poklballIcon, success, wobbleCount);
        manager.getBattleRender().getSequenceRender()
                .showAnimation(BattleSequences.THROW_POKLBALL, processThreadHandler);
        waitForResume();
        if (success) {
            String name = manager.getParticipants().getEnemy().getName();
            WildPoklmon poklmon = (WildPoklmon) manager.getParticipants().getEnemy();
            PoklmonData poklData = CoughtPoklmonFactory.caughtPoklmon(manager.getData(), manager.getPlayer(), poklmon);
            poklData.setPoklball(poklballIcon);
            showText("Super! Du hast " + name + " gefangen!");

            if (manager.getPlayer().getPokldexControl().getCachedCount(poklmon.getPoklmon().getId()) == 0) {
                showText("Für " + name + " wird ein neuer Eintrag im PoklDex hinzugefügt!");
            }
            if (showCancelableSelection("Möchtest du " + name + " einen Namen geben?", new String[]{"Ja", "Nein"}) == 0) {
                name = showInput("");
                poklData.setName(name);
            }

            if (manager.getPlayer().getPoklmonControl().getPoklmonsInTeam().size() == 6) {
                showText(name == null ? poklmon.getName() : name + " wurde in deine Box übertragen!");
            }
            manager.getPlayer().getPoklmonControl().addNewPoklmon(poklData);
            catchedPoklmon = true;
        } else {
            showText("Das war knapp!");
        }
    }

    public WearableItemVariables getWearableItemVariables() {
        return wearableItemVariables;
    }
}
