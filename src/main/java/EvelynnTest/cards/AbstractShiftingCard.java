package EvelynnTest.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EvelynnTest.EvelynnTestMod.makeImagePath;

public abstract class AbstractShiftingCard extends AbstractEasyCard {

    public boolean upgradedThirdMagic;
    public int baseThirdMagic;
    public int thirdMagic;
    public boolean isThirdMagicNumberModified;

    public enum Form {
        NORMAL,
        DEMON
    }

    public Form currentForm;
    public int demonCost;
    public CardType demonType;
    public CardTarget demonTarget;
    public int originalCost;
    public CardType originalType;
    public CardTarget originalTarget;
    public String demonName;
    public String originalName;

    public AbstractCard previewCard;
    boolean isCopy;

    public AbstractShiftingCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        isCopy = false;
        currentForm = Form.NORMAL;
        this.textureImg = getShiftingCardTextureString(name, type);
        if (textureImg != null) {
            this.loadCardImage(textureImg);
        }
        if (!isCopy) {
            previewCard = this.makeShiftingCopy();
            ((AbstractShiftingCard) previewCard).Shift(false);
            this.cardsToPreview = previewCard;
        }
    }

    public AbstractShiftingCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, Form form, boolean copied) {
        super(cardID, cost, type, rarity, target);
        isCopy = copied;
        currentForm = form;
        this.textureImg = getShiftingCardTextureString(name, type);
        if (textureImg != null) {
            this.loadCardImage(textureImg);
        }
        if (!isCopy) {
            previewCard = this.makeShiftingCopy();
            ((AbstractShiftingCard) previewCard).Shift(false);
            this.cardsToPreview = previewCard;
        }
    }

    public void setDemonValues(int newCost, CardType newType, CardTarget newTarget, String newName){
        originalTarget = target;
        originalType = type;
        originalCost = cost;
        originalName = name;
        demonCost = newCost;
        demonType = newType;
        demonTarget = newTarget;
        demonName = newName;
    }

    public AbstractShiftingCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        currentForm = Form.NORMAL;
        this.textureImg = getShiftingCardTextureString(name, type);
        if (textureImg != null) {
            this.loadCardImage(textureImg);
        }
    }

    public abstract AbstractShiftingCard makeShiftingCopy();
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicNumberModified = true;
        }
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (currentForm){
            case NORMAL:
                useNormal(abstractPlayer, abstractMonster);
                break;
            case DEMON:
                useDemon(abstractPlayer, abstractMonster);
                break;
            default:
                useNormal(abstractPlayer, abstractMonster);
        }
    }

    public abstract void useNormal(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);

    public abstract void useDemon(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);

    public static String getFormString(Form f){
        if (f.equals(Form.NORMAL)){
            return "Normal";
        }
        return "Demon";
    }

    public String getShiftingCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString;

        switch (cardType) {
            case ATTACK:
            case POWER:
            case SKILL:
                textureString = makeImagePath("cards/" + cardName + getFormString(currentForm) + ".png");
                break;
            default:
                textureString = makeImagePath("ui/missing.png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
    }

    public String setFormDesc(boolean hasUpgrade, Form current){
        if (current.equals(Form.NORMAL)){
            if (upgraded && cardStrings.UPGRADE_DESCRIPTION != null){
                return cardStrings.UPGRADE_DESCRIPTION;
            }
            return cardStrings.DESCRIPTION;
        }
        else {
            if (upgraded && cardStrings.EXTENDED_DESCRIPTION[2] != null){
                return cardStrings.EXTENDED_DESCRIPTION[2];
            }
            return cardStrings.EXTENDED_DESCRIPTION[1];
        }
    }

    public void Shift(boolean inCombat){
        if (currentForm.equals(Form.NORMAL)){
            currentForm = Form.DEMON;
            this.textureImg = getShiftingCardTextureString(name, type);
            if (textureImg != null) {
                this.loadCardImage(textureImg);
            }

            if (target.equals(CardTarget.ALL_ENEMY) && !demonTarget.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = false;
            }
            if (demonTarget.equals(CardTarget.ALL_ENEMY) && !target.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = true;
            }
            target = demonTarget;
            type = demonType;
            updateCost(demonCost-originalCost);
            name = demonName;
            if (upgraded){
                name += "+";
                if (timesUpgraded > 1){
                    name += timesUpgraded;
                }
            }

            this.rawDescription = setFormDesc(upgraded, currentForm);
            initializeDescription();
            if (inCombat) {
                this.flash(Color.RED.cpy());
                applyPowers();
            }
        }
        else {
            currentForm = Form.NORMAL;
            this.textureImg = getShiftingCardTextureString(name, type);
            if (textureImg != null) {
                this.loadCardImage(textureImg);
            }
            if (target.equals(CardTarget.ALL_ENEMY) && !demonTarget.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = false;
            }
            if (demonTarget.equals(CardTarget.ALL_ENEMY) && !target.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = true;
            }
            target = originalTarget;
            type = originalType;
            updateCost(originalCost-demonCost);
            name = originalName;
            if (upgraded){
                name += "+";
                if (timesUpgraded > 1){
                    name += timesUpgraded;
                }
            }

            this.rawDescription = setFormDesc(upgraded, currentForm);
            initializeDescription();
            if (inCombat) {
                this.flash(Color.PINK.cpy());
                applyPowers();
            }
        }
        if (cardsToPreview != null){
            ((AbstractShiftingCard)cardsToPreview).Shift(false);
        }
    }

    public void Shift(){
        if (currentForm.equals(Form.NORMAL)){
            currentForm = Form.DEMON;
            this.textureImg = getShiftingCardTextureString(name, type);
            if (textureImg != null) {
                this.loadCardImage(textureImg);
            }
            this.flash(Color.RED.cpy());
            if (target.equals(CardTarget.ALL_ENEMY) && !demonTarget.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = false;
            }
            if (demonTarget.equals(CardTarget.ALL_ENEMY) && !target.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = true;
            }
            target = demonTarget;
            type = demonType;
            updateCost(demonCost-originalCost);
            name = demonName;
            if (upgraded){
                name += "+";
                if (timesUpgraded > 1){
                    name += timesUpgraded;
                }
            }

            this.rawDescription = setFormDesc(upgraded, currentForm);
            initializeDescription();
            applyPowers();
        }
        else {
            currentForm = Form.NORMAL;
            this.textureImg = getShiftingCardTextureString(name, type);
            if (textureImg != null) {
                this.loadCardImage(textureImg);
            }
            this.flash(Color.PINK.cpy());
            if (target.equals(CardTarget.ALL_ENEMY) && !demonTarget.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = false;
            }
            if (demonTarget.equals(CardTarget.ALL_ENEMY) && !target.equals(CardTarget.ALL_ENEMY)){
                isMultiDamage = true;
            }
            target = originalTarget;
            type = originalType;
            updateCost(originalCost-demonCost);
            name = originalName;
            if (upgraded){
                name += "+";
                if (timesUpgraded > 1){
                    name += timesUpgraded;
                }
            }

            this.rawDescription = setFormDesc(upgraded, currentForm);
            initializeDescription();
            applyPowers();
        }
        if (cardsToPreview != null){
            ((AbstractShiftingCard)cardsToPreview).Shift();
        }
    }

}
