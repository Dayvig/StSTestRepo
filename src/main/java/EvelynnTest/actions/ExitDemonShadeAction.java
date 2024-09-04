package EvelynnTest.actions;

import EvelynnTest.cards.AbstractShiftingCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExitDemonShadeAction extends AbstractGameAction {

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c instanceof AbstractShiftingCard){
                if (((AbstractShiftingCard) c).currentForm.equals(AbstractShiftingCard.Form.DEMON)){
                    ((AbstractShiftingCard) c).Shift(false);
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c instanceof AbstractShiftingCard){
                if (((AbstractShiftingCard) c).currentForm.equals(AbstractShiftingCard.Form.DEMON)){
                    ((AbstractShiftingCard) c).Shift();
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c instanceof AbstractShiftingCard){
                if (((AbstractShiftingCard) c).currentForm.equals(AbstractShiftingCard.Form.DEMON)){
                    ((AbstractShiftingCard) c).Shift(false);
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if (c instanceof AbstractShiftingCard){
                if (((AbstractShiftingCard) c).currentForm.equals(AbstractShiftingCard.Form.DEMON)){
                    ((AbstractShiftingCard) c).Shift(false);
                }
            }
        }
        this.isDone = true;
    }
}
