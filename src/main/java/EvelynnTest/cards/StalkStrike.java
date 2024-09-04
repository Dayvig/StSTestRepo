package EvelynnTest.cards;

import EvelynnTest.powers.CharmPower;
import EvelynnTest.stances.DemonShadeStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static EvelynnTest.EvelynnTestMod.makeID;

public class StalkStrike extends AbstractShiftingCard {
    public final static String ID = makeID("StalkStrike");
    public final static int DAMAGE = 9;
    public final static int UPG_DAMAGE = 1;

    public final static int BLOCK = 11;
    public final static int UPG_BLOCK = 3;

    public StalkStrike() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setDemonValues(1, CardType.ATTACK, CardTarget.ENEMY, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseBlock = block = BLOCK;
        this.baseDamage = damage = DAMAGE;
    }

    public StalkStrike(boolean isCopy){
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, Form.NORMAL, isCopy);
        setDemonValues(1, CardType.ATTACK, CardTarget.ENEMY, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseBlock = block = BLOCK;
        this.baseDamage = damage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void useNormal(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new ChangeStanceAction(new DemonShadeStance()));
    }

    @Override
    public void useDemon(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i<2;i++){
            dmg(abstractMonster, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                updateCost(1);
                this.isDone = true;
            }
        });
    }

    @Override
    public void Shift(){
        super.Shift();
        if (currentForm.equals(Form.DEMON)) {
            this.updateCost(-(this.cost - demonCost));
        }
    }

    @Override
    public AbstractShiftingCard makeShiftingCopy() {
        return new StalkStrike(true);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
        initializeDescription();
    }
}