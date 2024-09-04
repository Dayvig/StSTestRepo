package EvelynnTest.powers;

import EvelynnTest.EvelynnTestMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Outfit extends AbstractEasyPower {

    AbstractCard outfitCard;

    public Outfit(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount, AbstractCard outfit) {
        super(NAME, powerType, isTurnBased, owner, amount);
        outfitCard = outfit;
    }

    @Override
    public void onInitialApplication(){
        outfitCard.updateCost(-1);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof Outfit){
            if (!power.ID.equals(this.ID)){
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
            addToBot(new MakeTempCardInDrawPileAction(outfitCard, 1, true, false, false));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount != -1) {
            this.fontScale = 8.0F;
            this.amount = Math.max(stackAmount, this.amount);
        }
    }

}
