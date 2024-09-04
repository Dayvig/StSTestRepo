package EvelynnTest.cards.cardvars;

import EvelynnTest.cards.AbstractEasyCard;
import EvelynnTest.cards.AbstractShiftingCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static EvelynnTest.EvelynnTestMod.makeID;

public class ThirdMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractShiftingCard) {
            return ((AbstractShiftingCard) card).isThirdMagicNumberModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractShiftingCard) {
            return ((AbstractShiftingCard) card).thirdMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractShiftingCard) {
            ((AbstractShiftingCard) card).isThirdMagicNumberModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractShiftingCard) {
            return ((AbstractShiftingCard) card).baseThirdMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractShiftingCard) {
            return ((AbstractShiftingCard) card).upgradedThirdMagic;
        }
        return false;
    }
}