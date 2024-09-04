package EvelynnTest.cards;

import EvelynnTest.powers.CharmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ChokePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static EvelynnTest.EvelynnTestMod.makeID;

public class FlirtWatch extends AbstractShiftingCard {
    public final static String ID = makeID("FlirtWatch");
    public final static int MAGIC = 6;
    public final static int SECONDMAGIC = 2;

    public final static int UPG_MAGIC1 = 1;
    public final static int UPG_MAGIC2 = 2;

    public final static int BLOCK = 9;

    public FlirtWatch() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDemonValues(1, CardType.SKILL, CardTarget.SELF, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseSecondMagic = secondMagic = SECONDMAGIC;
        this.baseBlock = block = BLOCK;
    }

    public FlirtWatch(boolean isCopy){
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY, Form.NORMAL, isCopy);
        setDemonValues(1, CardType.SKILL, CardTarget.SELF, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseSecondMagic = secondMagic = SECONDMAGIC;
        this.baseBlock = block = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void useNormal(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new CharmPower(abstractMonster, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new WeakPower(abstractMonster, secondMagic, false), secondMagic));
    }

    @Override
    public void useDemon(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new VigorPower(abstractMonster, secondMagic), secondMagic));
    }

    @Override
    public AbstractShiftingCard makeShiftingCopy() {
        return new FlirtWatch(true);
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC2);
        upgradeSecondMagic(UPG_MAGIC1);
        upgradeBlock(UPG_MAGIC2);
        initializeDescription();
    }
}