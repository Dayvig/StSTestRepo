package EvelynnTest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static EvelynnTest.EvelynnTestMod.makeID;

public class SlapHarder extends AbstractShiftingCard {
    public final static String ID = makeID("SlapHarder");
    public final static int MAGIC = 1;

    public final static int UPG_MAGIC = 1;
    public final static int UPG_DAMAGE = 1;

    public final static int DAMAGE = 2;
    public final static int DAMAGE2 = 4;

    public SlapHarder() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDemonValues(0, CardType.ATTACK, CardTarget.ENEMY, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseDamage = damage = DAMAGE;
        this.baseSecondDamage = secondDamage = DAMAGE2;
    }

    public SlapHarder(boolean isCopy){
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY, Form.NORMAL, isCopy);
        setDemonValues(0, CardType.ATTACK, CardTarget.ENEMY, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseDamage = damage = DAMAGE;
        this.baseSecondDamage = secondDamage = DAMAGE2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void useNormal(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new StrengthPower(abstractMonster, -magicNumber), -magicNumber));
        if (abstractMonster != null && !abstractMonster.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new GainStrengthPower(abstractMonster, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void useDemon(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i < 2; i++){
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, secondDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public AbstractShiftingCard makeShiftingCopy() {
        return new SlapHarder(true);
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        upgradeDamage(UPG_DAMAGE);
        upgradeSecondDamage(UPG_DAMAGE);
        initializeDescription();
    }
}