package EvelynnTest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ChokePower;

import java.util.Iterator;

import static EvelynnTest.EvelynnTestMod.makeID;

public class Choke extends AbstractShiftingCard {
    public final static String ID = makeID("ChokeTear");
    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 2;
    public final static int DAMAGE = 6;
    public final static int SECONDDAMAGE = 18;

    public Choke() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDemonValues(1, CardType.ATTACK, CardTarget.ENEMY, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseDamage = damage = DAMAGE;
        this.baseSecondDamage = secondDamage = SECONDDAMAGE;
    }

    public Choke(boolean isCopy){
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY, Form.NORMAL, isCopy);
        setDemonValues(1, CardType.ATTACK, CardTarget.ENEMY, cardStrings.EXTENDED_DESCRIPTION[0]);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseDamage = damage = DAMAGE;
        this.baseSecondDamage = secondDamage = SECONDDAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void useNormal(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new ChokePower(abstractMonster, magicNumber), magicNumber));
    }

    @Override
    public void useDemon(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                boolean playedAttack = false;
                for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                    if (c.type.equals(CardType.ATTACK)) {
                        playedAttack = true;
                        break;
                    }
                }
                addToBot(new DamageAction(abstractMonster,
                        new DamageInfo(abstractPlayer, playedAttack ? damage : secondDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (currentForm.equals(Form.DEMON)) {
            boolean playedAttack = false;
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.type.equals(CardType.ATTACK)) {
                    playedAttack = true;
                    break;
                }
            }
            if (!playedAttack && this.isGlowing) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }
    }

    @Override
    public AbstractShiftingCard makeShiftingCopy() {
        return new Choke(true);
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        upgradeDamage(UPG_MAGIC);
        upgradeSecondDamage(UPG_MAGIC);
        initializeDescription();
    }
}