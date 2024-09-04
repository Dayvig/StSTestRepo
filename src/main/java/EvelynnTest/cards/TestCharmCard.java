package EvelynnTest.cards;

import EvelynnTest.powers.CharmPower;
import EvelynnTest.powers.MindControlledPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EvelynnTest.EvelynnTestMod.makeID;

public class TestCharmCard extends AbstractEasyCard {
    public final static String ID = makeID("TestCharmCard");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public TestCharmCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
        this.baseMagicNumber = magicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new CharmPower(m, magicNumber), magicNumber));
    }

    public void upp() {
        upgradeBlock(3);
    }
}