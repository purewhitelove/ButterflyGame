// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShiningLight.java

package com.megacrit.cardcrawl.events.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ShiningLight extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/ShiningLight$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            COMPLETE = new CUR_SCREEN("COMPLETE", 1);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public ShiningLight()
    {
        super(NAME, INTRO, "images/events/shiningLight.jpg");
        damage = 0;
        screen = CUR_SCREEN.INTRO;
        if(AbstractDungeon.ascensionLevel >= 15)
            damage = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.3F);
        else
            damage = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.2F);
        if(AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue())
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(damage).append(OPTIONS[1]).toString());
        else
            imageEventText.setDialogOption(OPTIONS[3], true);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_SHINING");
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$ShiningLight$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$exordium$ShiningLight$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$ShiningLight$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.ShiningLight.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            if(buttonPressed == 0)
            {
                imageEventText.updateBodyText(AGREE_DIALOG);
                imageEventText.removeDialogOption(1);
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                screen = CUR_SCREEN.COMPLETE;
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, damage));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
                upgradeCards();
            } else
            {
                imageEventText.updateBodyText(DISAGREE_DIALOG);
                imageEventText.removeDialogOption(1);
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                screen = CUR_SCREEN.COMPLETE;
                AbstractEvent.logMetricIgnored("Shining Light");
            }
            break;

        default:
            openMap();
            break;
        }
    }

    private void upgradeCards()
    {
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        ArrayList upgradableCards = new ArrayList();
        List cardMetrics = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!cardMetrics.hasNext())
                break;
            AbstractCard c = (AbstractCard)cardMetrics.next();
            if(c.canUpgrade())
                upgradableCards.add(c);
        } while(true);
        cardMetrics = new ArrayList();
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if(!upgradableCards.isEmpty())
            if(upgradableCards.size() == 1)
            {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                cardMetrics.add(((AbstractCard)upgradableCards.get(0)).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
            } else
            {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                ((AbstractCard)upgradableCards.get(1)).upgrade();
                cardMetrics.add(((AbstractCard)upgradableCards.get(0)).cardID);
                cardMetrics.add(((AbstractCard)upgradableCards.get(1)).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(1));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F - 190F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(1)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F + 190F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
            }
        AbstractEvent.logMetric("Shining Light", "Entered Light", null, null, null, cardMetrics, null, null, null, damage, 0, 0, 0, 0, 0);
    }

    public static final String ID = "Shining Light";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private int damage;
    private static final float HP_LOSS_PERCENT = 0.2F;
    private static final float A_2_HP_LOSS_PERCENT = 0.3F;
    private CUR_SCREEN screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Shining Light");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
    }
}
