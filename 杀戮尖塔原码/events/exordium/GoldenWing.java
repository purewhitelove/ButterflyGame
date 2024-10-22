// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldenWing.java

package com.megacrit.cardcrawl.events.exordium;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class GoldenWing extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/exordium/GoldenWing$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN PURGE;
        public static final CUR_SCREEN MAP;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            PURGE = new CUR_SCREEN("PURGE", 1);
            MAP = new CUR_SCREEN("MAP", 2);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, PURGE, MAP
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public GoldenWing()
    {
        super(NAME, INTRO, "images/events/goldenWing.jpg");
        damage = 7;
        purgeResult = false;
        screen = CUR_SCREEN.INTRO;
        canAttack = CardHelper.hasCardWithXDamage(10);
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(damage).append(OPTIONS[1]).toString());
        if(canAttack)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(50).append(OPTIONS[3]).append(80).append(OPTIONS[4]).toString());
        else
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(10).append(OPTIONS[6]).toString(), !canAttack);
        imageEventText.setDialogOption(OPTIONS[7]);
    }

    public void update()
    {
        super.update();
        purgeLogic();
        if(waitForInput)
            buttonEffect(GenericEventDialog.getSelectedOption());
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoldenWing$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoldenWing$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoldenWing$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoldenWing$CUR_SCREEN[CUR_SCREEN.PURGE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$exordium$GoldenWing$CUR_SCREEN[CUR_SCREEN.MAP.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

label0:
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.exordium.GoldenWing.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(AGREE_DIALOG);
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, damage));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
                screen = CUR_SCREEN.PURGE;
                imageEventText.updateDialogOption(0, OPTIONS[8]);
                imageEventText.removeDialogOption(1);
                imageEventText.removeDialogOption(1);
                break label0;

            case 1: // '\001'
                if(canAttack)
                {
                    goldAmount = AbstractDungeon.miscRng.random(50, 80);
                    AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmount));
                    AbstractDungeon.player.gainGold(goldAmount);
                    AbstractEvent.logMetricGainGold("Golden Wing", "Gained Gold", goldAmount);
                    imageEventText.updateBodyText(SPECIAL_OPTION);
                    screen = CUR_SCREEN.MAP;
                    imageEventText.updateDialogOption(0, OPTIONS[7]);
                    imageEventText.removeDialogOption(1);
                    imageEventText.removeDialogOption(1);
                }
                break;

            default:
                imageEventText.updateBodyText(DISAGREE_DIALOG);
                AbstractEvent.logMetricIgnored("Golden Wing");
                screen = CUR_SCREEN.MAP;
                imageEventText.updateDialogOption(0, OPTIONS[7]);
                imageEventText.removeDialogOption(1);
                imageEventText.removeDialogOption(1);
                break;
            }
            break;

        case 2: // '\002'
            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[9], false, false, false, true);
            imageEventText.updateDialogOption(0, OPTIONS[7]);
            purgeResult = true;
            screen = CUR_SCREEN.MAP;
            break;

        case 3: // '\003'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    private void purgeLogic()
    {
        if(purgeResult && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2));
            AbstractEvent.logMetricCardRemovalAndDamage("Golden Wing", "Card Removal", c, damage);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            purgeResult = false;
        }
    }

    public static final String ID = "Golden Wing";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private int damage;
    private static final String INTRO;
    private static final String AGREE_DIALOG;
    private static final String SPECIAL_OPTION;
    private static final String DISAGREE_DIALOG;
    private boolean canAttack;
    private boolean purgeResult;
    private static final int MIN_GOLD = 50;
    private static final int MAX_GOLD = 80;
    private static final int REQUIRED_DAMAGE = 10;
    private int goldAmount;
    private CUR_SCREEN screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Golden Wing");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        SPECIAL_OPTION = DESCRIPTIONS[2];
        DISAGREE_DIALOG = DESCRIPTIONS[3];
    }
}
