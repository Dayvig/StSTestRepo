package EvelynnTest.cards;

import EvelynnTest.stances.DemonShadeStance;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import static EvelynnTest.EvelynnTestMod.makeID;

public class SampleDemonShadeCard2 extends AbstractEasyCard {
    public final static String ID = makeID("SampleDemonShadeCard2");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SampleDemonShadeCard2() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
        this.baseMagicNumber = magicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
        this.addToBot(new ChangeStanceAction("Neutral"));
    }

    public void upp() {
        upgradeBlock(3);
    }
}