package com.broll.poklmon.battle.process;

import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.TypeCompare;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.AttackBuilder;
import com.broll.poklmon.battle.attack.AttackBuilderImpl;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.calc.AttackHitCalculator;
import com.broll.poklmon.battle.calc.AttackStatsManipulation;
import com.broll.poklmon.battle.calc.DamageCalculator;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.special.SpecialScriptAttacks;
import com.broll.poklmon.battle.render.BattleHUDRender;
import com.broll.poklmon.battle.render.BattleSequences;
import com.broll.poklmon.battle.render.sequence.PoklmonDamageSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonDefeatedSequence;
import com.broll.poklmon.battle.render.sequence.PoklmonHealSequence;
import com.broll.poklmon.battle.util.ScriptValue;
import com.broll.poklmon.battle.util.flags.DamageTaken;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.game.items.callbacks.AfterAttackCallback;
import com.broll.poklmon.game.items.callbacks.AttackHealCalculationCallback;
import com.broll.poklmon.game.items.callbacks.BeforeAttackCallback;
import com.broll.poklmon.game.items.callbacks.DamageCalculationCallback;
import com.broll.poklmon.network.NetworkException;

public class BattleProcessAttack extends BattleProcessControl {

	private AttackBuilder attackBuilder;
	private SpecialScriptAttacks scriptAttacks;

	public BattleProcessAttack(BattleManager manager, BattleProcessCore handler) {
		super(manager, handler);
		attackBuilder = new AttackBuilderImpl(manager.getData(), manager.getFieldEffects());
		scriptAttacks = new SpecialScriptAttacks(manager, handler);
	}

	public synchronized void processAttack(FightAttack attack, FightPoklmon user, FightPoklmon target) {
		// reset enemy damage taken flag
		core.getEventFlags().poklmonTookDamage(target, null);
		// show hud text
		final String name = attack.getAttack().getName();
		final String poklmonName = user.getName();
		boolean attackMirrored = false;
		if (user == manager.getParticipants().getEnemy()) {
			attackMirrored = true;
		}
		// check status changes hit effect
		UseAttack atk = attackBuilder.useAttack(attack, user, target);
		// before attack
		for (CustomScriptCall script : manager.getScriptCalls()) {
			if (script instanceof BeforeAttackCallback) {
				((BeforeAttackCallback) script).call(user, atk);
			}
		}

		if (core.getEffectProcess().getHandicapProcess().canAttack(user, atk)) {
			final String text = TextContainer.get("attackText",poklmonName,name);
			final BattleHUDRender hud = manager.getBattleRender().getHudRender();
			showText(text);
			// reduce ap
			user.useAttack(attack);
			// check hit
			boolean hitAttack = AttackHitCalculator.hitsAttack(user, target, atk);
			String noHitText;
			if (atk.isHasNoEffect()) {
				noHitText = TextContainer.get("attackNoEffect");
				hitAttack = false;
			} else {
				noHitText = TextContainer.get("attackNoHit",poklmonName);
			}
			if (hitAttack) {
				// mark attack as last used
				core.getEventFlags().poklmonUsedAttack(user, attack);
				hud.showInfo(text);

				if (atk.getSpecialFunction() != null) {
					scriptAttacks.useSpecialScript(atk, user, target);
					if (scriptAttacks.cancelPlainAttack(atk)) {
						return;
					}
				}

				// show attack animation
				int id = attack.getAttack().getAnimationID();
				if (atk.getCustomAnimation() != UseAttack.NO_CUSTOM_ANIMATION) {
					id = atk.getCustomAnimation();
				}
				Animation animation = manager.getData().getAnimations().getAnimation(id);
				boolean multi = atk.isMultihitAttack();
				int hitCount = atk.getMultihitCount();
				for (int i = 0; i < hitCount; i++) {
					if (target.isFainted()) {
						hitCount = i;
						break;
					}
					showAnimation(animation, attackMirrored);
					// do damage effects (dont show effect text when multi
					// attack)
					processAttackEffect(atk, user, target, !multi);
				}

				if (multi) {
					String msg = TextContainer.get("multihit",hitCount);
					showText(msg);
					// show effect text if needed
					TypeCompare comp = DamageCalculator.getTypeBonus();
					if (comp != TypeCompare.STANDARD) {
						showText(comp.getName());
					}
				}
			} else {
				// attack no effect / miss
				showText(noHitText);
				hud.setShowText(false);
			}
		} else {
			// cant attack because of effects
			// check confusion self hit
			if (core.getEffectProcess().getHandicapProcess().isConfusionStrikes()) {
				UseAttack confusedAttack = StateEffectCalc.getConfusedAttack();
				// show confusion animation
				showAnimation(
						manager.getData().getAnimations()
								.getAnimation(BattleProcessEffects.CONFUSION_SELFHIT_ANIMATION_ID), attackMirrored);
				// do self damage (target and user same poklmon)
				processAttackEffect(confusedAttack, user, user, false);
			}
		}
	}

	private synchronized void processAttackEffect(UseAttack attack, FightPoklmon user, FightPoklmon target,
			boolean showText) {
		AttackType type = attack.getType();
		DamageTaken damageTaken = new DamageTaken();
		damageTaken.setAttackType(type);
		damageTaken.setElement(attack.getElement());
		AttackStatsManipulation.doChanges(attack, user, target);
		if (!attack.isSkipDamage()) {
			if (type == AttackType.STATUS) { // status attack

				// check kp opfer
				double kplose = attack.getLifeLose();
				if (kplose > 0) {
					int kpd = (int) (user.getAttributes().getMaxhealth() * kplose);
					String text =  TextContainer.get("kpopfer",user.getName());
					doDamage(user, text, kpd);
					if (user.isFainted()) {
						return;
					}
				}
			} else { // damage attack
				boolean volltreffer = false;
				int damage = 0;

				if (attack.hasSpecialDamage()) {
					// percent damage
					damage = DamageCalculator.getSpeicalDamage(user, target, attack);
				} else {

					// normal damage calc
					volltreffer = AttackHitCalculator.isVolltreffer(attack);
					damage = DamageCalculator.getDamage(user, target, attack, volltreffer);

				}

				ScriptValue scriptValue = new ScriptValue(damage);
				// script callback
				for (CustomScriptCall script : manager.getScriptCalls()) {
					if (script instanceof DamageCalculationCallback) {
						scriptValue.value = ((DamageCalculationCallback) script).call(user, attack, scriptValue.value);
					}
				}
				damage = scriptValue.value;

				TypeCompare comp = DamageCalculator.getTypeBonus();
				String damageText = null;
				if (comp != TypeCompare.STANDARD && showText) {
					damageText = comp.getName();
				}
				if (volltreffer) {
					// show volltreffer message
					String disp = TextContainer.get("volltreffer");
					showText(disp);
				}
				damageTaken.setDamage(damage);
				// do damage to target (+animation)
				int effectiveDamage = damage;
				// effective damage cant be greater than target current kp (no
				// overkill!)
				if (effectiveDamage > target.getAttributes().getHealth()) {
					effectiveDamage = target.getAttributes().getHealth();
				}
				doDamage(target, damageText, damage);

				// check absorbe effect
				double absorb = attack.getAbsorbeDamagePercent();
				if (absorb > 0) {
					// show heal animation and heal kp
					int heal = (int) Math.max(effectiveDamage * absorb, 1);
					ScriptValue sv = new ScriptValue(heal);
					for (CustomScriptCall script : manager.getScriptCalls()) {
						if (script instanceof AttackHealCalculationCallback) {
							sv.value = ((AttackHealCalculationCallback) script).call(user, attack, sv.value);
						}
					}
					heal = sv.value;
					core.getEffectProcess().getInflictprocess().healPoklmon(user, null, heal);
				}

				// check selfdamage effect
				double selfdamage = attack.getSelfDamagePercent();
				if (selfdamage > 0) {
					// do self damage
					int sdamage = (int) (effectiveDamage * selfdamage);
					if (sdamage > 0) {
						String text =  TextContainer.get("rueckstoss",user.getName());
						doDamage(user, text, sdamage);
					}
				}
			}

			// check selfheal effect
			double selfheal = attack.getKpHealPercent();
			if (selfheal > 0) {
				int kpheal = (int) (user.getAttributes().getMaxhealth() * selfheal);
				ScriptValue scriptValue = new ScriptValue(kpheal);
				for (CustomScriptCall script : manager.getScriptCalls()) {
					if (script instanceof AttackHealCalculationCallback) {
						scriptValue.value = ((AttackHealCalculationCallback) script).call(user, attack,
								scriptValue.value);
					}
				}
				kpheal = scriptValue.value;
				String text = TextContainer.get("selfheal",user.getName());
				// showText(text);
				core.getEffectProcess().getInflictprocess().healPoklmon(user, text, kpheal);
			}
		}
		core.getEventFlags().poklmonTookDamage(target, damageTaken);
		// check stat changes and effects, if enemy is not killed

		// stat effects
		core.getEffectProcess().doAttackEffects(attack, user, target);
		// param effects
		core.getParamProcess().doParamChanges(attack, user, target);
		// attack done
		for (CustomScriptCall script : manager.getScriptCalls()) {
			if (script instanceof AfterAttackCallback) {
				((AfterAttackCallback) script).call(user, attack);
			}
		}
	}

	public synchronized void doDamage(FightPoklmon target, String damageText, int damage) {
		if (target.isFainted()) {
			return;
		}
		PoklmonDamageSequence anim = (PoklmonDamageSequence) manager.getBattleRender().getSequenceRender()
				.getSequenceRender(BattleSequences.POKLMON_TAKING_DAMAGE);
		anim.init(target, damage, damageText);
		manager.getBattleRender().getSequenceRender()
				.showAnimation(BattleSequences.POKLMON_TAKING_DAMAGE, processThreadHandler);
		waitForResume();
	}

	public synchronized void doHeal(FightPoklmon target, String healText, int heal) {
		if (target.isFullHealth()) {
			return;
		}
		if (target == manager.getParticipants().getPlayer() || target == manager.getParticipants().getEnemy()) {
			PoklmonHealSequence anim = (PoklmonHealSequence) manager.getBattleRender().getSequenceRender()
					.getSequenceRender(BattleSequences.POKLMON_HEAL_ANIMATION);
			anim.init(target, heal, healText);
			manager.getBattleRender().getSequenceRender()
					.showAnimation(BattleSequences.POKLMON_HEAL_ANIMATION, processThreadHandler);
			waitForResume();
		} else {
			// just heal
			target.doHeal(heal);
		}
	}

	public synchronized void showAnimation(Animation animation, boolean enemyAttack) {
		manager.getBattleRender().getHudRender().setShowHud(false);
		manager.getBattleRender().getAttackAnimationRender()
				.showAnimation(animation, enemyAttack, false, processThreadHandler);
		waitForResume();
		manager.getBattleRender().getHudRender().setShowHud(true);
	}

	public synchronized void showOverlayAnimation(Animation animation, boolean enemyAttack) {
		manager.getBattleRender().getHudRender().setShowHud(false);
		manager.getBattleRender().getAttackAnimationRender()
				.showAnimation(animation, enemyAttack, true, processThreadHandler);
		waitForResume();
		manager.getBattleRender().getHudRender().setShowHud(true);
	}

	public synchronized void doPokmlonDefeated() throws NetworkException {
		boolean enemyFainted = manager.getParticipants().getEnemy().isFainted();
		boolean playerFainted = manager.getParticipants().getPlayer().isFainted();
		if (enemyFainted) {
			// enemy defeated
			PoklmonDefeatedSequence sequence = (PoklmonDefeatedSequence) manager.getBattleRender().getSequenceRender()
					.getSequenceRender(BattleSequences.POKLMON_DEFEATED);
			sequence.init(manager.getParticipants().getEnemy());
			manager.getBattleRender().getSequenceRender()
					.showAnimation(BattleSequences.POKLMON_DEFEATED, processThreadHandler);
			waitForResume();
		}
		if (playerFainted) {
			PoklmonDefeatedSequence sequence = (PoklmonDefeatedSequence) manager.getBattleRender().getSequenceRender()
					.getSequenceRender(BattleSequences.POKLMON_DEFEATED);
			sequence.init(manager.getParticipants().getPlayer());
			manager.getBattleRender().getSequenceRender()
					.showAnimation(BattleSequences.POKLMON_DEFEATED, processThreadHandler);
			waitForResume();
		}

		if (enemyFainted) {
			core.enemyPoklmonDefeated();
		}
		if (playerFainted) {
			if (core.isBattleRunning()) {
				core.playerPoklmonDefeated();
			}
		}
	}

}
