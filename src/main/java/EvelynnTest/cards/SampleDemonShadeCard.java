package EvelynnTest.cards;

import EvelynnTest.powers.CharmPower;
import EvelynnTest.powers.MindControlledPower;
import EvelynnTest.stances.DemonShadeStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EvelynnTest.EvelynnTestMod.makeID;

public class SampleDemonShadeCard extends AbstractEasyCard {
    public final static String ID = makeID("SampleDemonShadeCard");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SampleDemonShadeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
        this.baseMagicNumber = magicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new DemonShadeStance()));
    }

    public void upp() {
        upgradeBlock(3);
    }
}