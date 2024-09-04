package EvelynnTest.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import EvelynnTest.EvelynnTestMod;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CharmPower extends AbstractEasyPower implements HealthBarRenderPower {
    // intellij stuff Example, buff, false
    private static final String SIMPLE_NAME = "CharmPower";
    public static final String POWER_ID = EvelynnTestMod.makeID(SIMPLE_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String LOC_NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CharmPower(AbstractCreature owner, int amount) {
        super(SIMPLE_NAME, PowerType.BUFF, false, owner, amount);
        name = LOC_NAME;
        updateDescription();
    }

    @Override
    public void onInitialApplication(){
        if (this.amount >= this.owner.currentHealth && !this.owner.hasPower(MindControlledPower.POWER_ID)){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new MindControlledPower(this.owner)));
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(CharmPower.POWER_ID) && (power.amount + owner.getPower(CharmPower.POWER_ID).amount) >= this.owner.currentHealth && !this.owner.hasPower(MindControlledPower.POWER_ID)){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new MindControlledPower(this.owner)));
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > owner.currentBlock){
            addToTop(new ReducePowerAction(this.owner, this.owner, this, damageAmount - owner.currentBlock));
        }
        return damageAmount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= this.owner.currentHealth && !this.owner.hasPower(MindControlledPower.POWER_ID)){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new MindControlledPower(this.owner)));
        }
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        if (this.amount >= this.owner.currentHealth){
            return Color.PURPLE.cpy();
        }
        return Color.PINK.cpy();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}