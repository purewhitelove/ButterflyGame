// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Vampires.java

package com.megacrit.cardcrawl.events.city;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;

public class Vampires extends AbstractImageEvent
{

    public Vampires()
    {
        super(NAME, "test", "images/events/vampires.jpg");
        screenNum = 0;
        body = AbstractDungeon.player.getVampireText();
        maxHpLoss = MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.3F);
        if(maxHpLoss >= AbstractDungeon.player.maxHealth)
            maxHpLoss = AbstractDungeon.player.maxHealth - 1;
        bites = new ArrayList();
        hasVial = AbstractDungeon.player.hasRelic("Blood Vial");
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(maxHpLoss).append(OPTIONS[1]).toString(), new Bite());
        if(hasVial)
        {
            String vialName = (new BloodVial()).name;
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(vialName).append(OPTIONS[4]).toString(), new Bite());
        }
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed)
    {
label0:
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                imageEventText.updateBodyText(ACCEPT_BODY);
                AbstractDungeon.player.decreaseMaxHealth(maxHpLoss);
                replaceAttacks();
                logMetricObtainCardsLoseMapHP("Vampires", "Became a vampire", bites, maxHpLoss);
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[5]);
                imageEventText.clearRemainingOptions();
                break label0;

            case 1: // '\001'
                if(hasVial)
                {
                    CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                    imageEventText.updateBodyText(GIVE_VIAL);
                    AbstractDungeon.player.loseRelic("Blood Vial");
                    replaceAttacks();
                    logMetricObtainCardsLoseRelic("Vampires", "Became a vampire (Vial)", bites, new BloodVial());
                    screenNum = 1;
                    imageEventText.updateDialogOption(0, OPTIONS[5]);
                    imageEventText.clearRemainingOptions();
                    break label0;
                }
                // fall through

            default:
                logMetricIgnored("Vampires");
                imageEventText.updateBodyText(EXIT_BODY);
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[5]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        case 1: // '\001'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    private void replaceAttacks()
    {
        ArrayList masterDeck = AbstractDungeon.player.masterDeck.group;
        for(int i = masterDeck.size() - 1; i >= 0; i--)
        {
            AbstractCard card = (AbstractCard)masterDeck.get(i);
            if(card.tags.contains(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_STRIKE))
                AbstractDungeon.player.masterDeck.removeCard(card);
        }

        for(int i = 0; i < 5; i++)
        {
            AbstractCard c = new Bite();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            bites.add(c.cardID);
        }

    }

    public static final String ID = "Vampires";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final String GIVE_VIAL;
    private static final float HP_DRAIN = 0.3F;
    private int maxHpLoss;
    private int screenNum;
    private boolean hasVial;
    private List bites;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Vampires");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
        GIVE_VIAL = DESCRIPTIONS[4];
    }
}
