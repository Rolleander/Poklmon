package com.broll.poklmon.battle.attack;

import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.attack.script.SpecialScript;
import com.broll.poklmon.battle.field.GlobalEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;

import java.util.ArrayList;

public class UseAttack {
    public final static int NO_CUSTOM_ANIMATION = -1;
    private boolean stopAttackProcessing;

    private ElementType element;
    private AttackType type;
    private float hitchance;
    private double percentDamage;
    private double percentCurrentDamage;

    private int damage;
    private int fixDamage;
    private int customAnimation = -1;
    private boolean hitsAlways;
    private boolean ignoreConfusion;
    private boolean ignoreIce;
    private boolean ignoreSleep;
    private boolean ignoreParalyze;
    private boolean skipDamage = false;

    private int volltrefferChance = 1;
    private boolean hasNoEffect;

    private MainFightStatus changeStatusTarget;
    private MainFightStatus changeStatusUser;
    private EffectStatus effectStatusTarget;
    private EffectStatus effectStatusUser;

    private GlobalEffect globalEffect;
    private WeatherEffect weatherEffect;
    private TeamEffect teamEffectTarget;
    private TeamEffect teamEffectUser;

    private ArrayList<AttributeChange> attributeChangeUser = new ArrayList<AttributeChange>();
    private ArrayList<AttributeChange> attributeChangeTarget = new ArrayList<AttributeChange>();

    private int multihitCount = 1;
    private double absorbeDamagePercent = 0;
    private double selfDamagePercent = 0;
    private double lifeLose;
    private double kpHealPercent;
    private SpecialScript specialFunction;


    public void clearAttrbiuteChangeTarget() {
        attributeChangeTarget.clear();
    }

    public void clearAttrbiuteChangeUser() {
        attributeChangeUser.clear();
    }

    public void setFixDamage(int fixDamage) {
        this.fixDamage = fixDamage;
    }

    public int getFixDamage() {
        return fixDamage;
    }

    public void setCustomAnimation(int customAnimation) {
        this.customAnimation = customAnimation;
    }

    public boolean isSkipDamage() {
        return skipDamage;
    }

    public void setSkipDamage(boolean skipDamage) {
        this.skipDamage = skipDamage;
    }

    public int getCustomAnimation() {
        return customAnimation;
    }

    public GlobalEffect getGlobalEffect() {
        return globalEffect;
    }

    public TeamEffect getTeamEffectTarget() {
        return teamEffectTarget;
    }

    public TeamEffect getTeamEffectUser() {
        return teamEffectUser;
    }

    public WeatherEffect getWeatherEffect() {
        return weatherEffect;
    }

    public void setGlobalEffect(GlobalEffect globalEffect) {
        this.globalEffect = globalEffect;
    }

    public void setTeamEffectTarget(TeamEffect teamEffectTarget) {
        this.teamEffectTarget = teamEffectTarget;
    }

    public void setTeamEffectUser(TeamEffect teamEffectUser) {
        this.teamEffectUser = teamEffectUser;
    }

    public void setWeatherEffect(WeatherEffect weatherEffect) {
        this.weatherEffect = weatherEffect;
    }

    public void setPercentCurrentDamage(double percentCurrentDamage) {
        this.percentCurrentDamage = percentCurrentDamage;
    }

    public double getPercentCurrentDamage() {
        return percentCurrentDamage;
    }

    public void setIgnoreParalyze(boolean ignoreParalyze) {
        this.ignoreParalyze = ignoreParalyze;
    }

    public void setPercentDamage(double percentDamage) {
        this.percentDamage = percentDamage;
    }

    public double getPercentDamage() {
        return percentDamage;
    }

    public void setIgnoreSleep(boolean ignoreSleep) {
        this.ignoreSleep = ignoreSleep;
    }

    public boolean isIgnoreParalyze() {
        return ignoreParalyze;
    }

    public boolean isIgnoreSleep() {
        return ignoreSleep;
    }

    public void setIgnoreConfusion(boolean ignoreConfusion) {
        this.ignoreConfusion = ignoreConfusion;
    }

    public void setIgnoreIce(boolean ignoreIce) {
        this.ignoreIce = ignoreIce;
    }

    public boolean isIgnoreConfusion() {
        return ignoreConfusion;
    }

    public boolean isIgnoreIce() {
        return ignoreIce;
    }

    public void setSpecialFunction(SpecialScript specialFunction) {
        this.specialFunction = specialFunction;
    }

    public SpecialScript getSpecialFunction() {
        return specialFunction;
    }

    public void setKpHealPercent(double kpHealPercent) {
        this.kpHealPercent = kpHealPercent;
    }

    public double getKpHealPercent() {
        return kpHealPercent;
    }

    public void setLifeLose(double lifeLose) {
        this.lifeLose = lifeLose;
    }

    public double getLifeLose() {
        return lifeLose;
    }

    public void setVolltrefferChance(int volltrefferChance) {
        this.volltrefferChance = volltrefferChance;
    }

    public double getSelfDamagePercent() {
        return selfDamagePercent;
    }

    public void setSelfDamagePercent(double selfDamagePercent) {
        this.selfDamagePercent = selfDamagePercent;
    }

    public double getAbsorbeDamagePercent() {
        return absorbeDamagePercent;
    }

    public void setAbsorbeDamagePercent(double dvar) {
        this.absorbeDamagePercent = dvar;
    }

    public int getDamage() {
        return damage;
    }

    public MainFightStatus getChangeStatusTarget() {
        return changeStatusTarget;
    }

    public void setChangeStatusTarget(MainFightStatus changeStatusTarget) {
        this.changeStatusTarget = changeStatusTarget;
    }

    public MainFightStatus getChangeStatusUser() {
        return changeStatusUser;
    }

    public void setChangeStatusUser(MainFightStatus changeStatusUser) {
        this.changeStatusUser = changeStatusUser;
    }

    public EffectStatus getEffectStatusTarget() {
        return effectStatusTarget;
    }

    public void setEffectStatusTarget(EffectStatus effectStatusTarget) {
        this.effectStatusTarget = effectStatusTarget;
    }

    public EffectStatus getEffectStatusUser() {
        return effectStatusUser;
    }

    public void setEffectStatusUser(EffectStatus effectStatusUser) {
        this.effectStatusUser = effectStatusUser;
    }

    public int getMultihitCount() {
        return multihitCount;
    }

    public void setMultihitCount(int multihitCount) {
        this.multihitCount = multihitCount;
    }

    public boolean isMultihitAttack() {
        return multihitCount > 1;
    }

    public ArrayList<AttributeChange> getAttributeChangeTarget() {
        return attributeChangeTarget;
    }

    public ArrayList<AttributeChange> getAttributeChangeUser() {
        return attributeChangeUser;
    }

    public ElementType getElement() {
        return element;
    }

    public float getHitchance() {
        return hitchance;
    }

    public AttackType getType() {
        return type;
    }

    public boolean isHitsAlways() {
        return hitsAlways;
    }

    public int getVolltrefferChance() {
        return volltrefferChance;
    }

    public boolean isHasNoEffect() {
        return hasNoEffect;
    }

    /**
     * Sets the {@link #element}.
     *
     * @param element The new element to set.
     */
    public void setElement(ElementType element) {
        this.element = element;
    }

    /**
     * Sets the {@link #type}.
     *
     * @param type The new type to set.
     */
    public void setType(AttackType type) {
        this.type = type;
    }

    /**
     * Sets the {@link #hitchance}.
     *
     * @param hitchance The new hitchance to set.
     */
    public void setHitchance(float hitchance) {
        this.hitchance = hitchance;
    }

    /**
     * Sets the {@link #damage}.
     *
     * @param damage The new damage to set.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets the {@link #hitsAlways}.
     *
     * @param hitsAlways The new hitsAlways to set.
     */
    public void setHitsAlways(boolean hitsAlways) {
        this.hitsAlways = hitsAlways;
    }

    /**
     * Sets the {@link #hasNoEffect}.
     *
     * @param hasNoEffect The new hasNoEffect to set.
     */
    public void setHasNoEffect(boolean hasNoEffect) {
        this.hasNoEffect = hasNoEffect;
    }


    public boolean hasSpecialDamage() {
        return percentDamage > 0 || percentCurrentDamage > 0 || fixDamage > 0;
    }

    public boolean isStopAttackProcessing() {
        return stopAttackProcessing;
    }

    public void stopAttackProcessing() {
        this.stopAttackProcessing = true;
    }
}
