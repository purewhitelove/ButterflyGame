// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Bonfire.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;

public class Bonfire extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/Bonfire$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN CHOOSE;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            CHOOSE = new CUR_SCREEN("CHOOSE", 1);
            COMPLETE = new CUR_SCREEN("COMPLETE", 2);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, CHOOSE, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public Bonfire()
    {
        super(NAME, DIALOG_1, "images/events/bonfire.jpg");
        screen = CUR_SCREEN.INTRO;
        offeredCard = null;
        cardSelect = false;
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_GOOP");
    }

    public void update()
    {
        super.update();
        if(cardSelect && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            offeredCard = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];
                static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$Bonfire$CUR_SCREEN[];

                static 
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$Bonfire$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$shrines$Bonfire$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$shrines$Bonfire$CUR_SCREEN[CUR_SCREEN.CHOOSE.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$events$shrines$Bonfire$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror4) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror5) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror6) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 5;
                    }
                    catch(NoSuchFieldError nosuchfielderror7) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 6;
                    }
                    catch(NoSuchFieldError nosuchfielderror8) { }
                }
            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[offeredCard.rarity.ordinal()])
            {
            case 1: // '\001'
                logMetricRemoveCardAndObtainRelic("Bonfire Elementals", "Offered Curse", offeredCard, new SpiritPoop());
                break;

            case 2: // '\002'
                logMetricCardRemoval("Bonfire Elementals", "Offered Basic", offeredCard);
                break;

            case 3: // '\003'
                logMetricCardRemovalAndHeal("Bonfire Elementals", "Offered Common", offeredCard, 5);
                // fall through

            case 4: // '\004'
                logMetricCardRemovalAndHeal("Bonfire Elementals", "Offered Special", offeredCard, 5);
                break;

            case 5: // '\005'
                int heal = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
                logMetricCardRemovalAndHeal("Bonfire Elementals", "Offered Uncommon", offeredCard, heal);
                break;

            case 6: // '\006'
                int heal2 = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
                logMetricCardRemovalHealMaxHPUp("Bonfire Elementals", "Offered Rare", offeredCard, heal2, 10);
                break;
            }
            setReward(offeredCard.rarity);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(offeredCard, Settings.WIDTH / 2, Settings.HEIGHT / 2));
            AbstractDungeon.player.masterDeck.removeCard(offeredCard);
            imageEventText.updateDialogOption(0, OPTIONS[1]);
            screen = CUR_SCREEN.COMPLETE;
            cardSelect = false;
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.Bonfire.CUR_SCREEN[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            imageEventText.updateBodyText(DIALOG_2);
            imageEventText.updateDialogOption(0, OPTIONS[2]);
            screen = CUR_SCREEN.CHOOSE;
            break;

        case 2: // '\002'
            if(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0)
            {
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);
                cardSelect = true;
            } else
            {
                imageEventText.updateBodyText(DESCRIPTIONS[4]);
                imageEventText.updateDialogOption(0, OPTIONS[1]);
                screen = CUR_SCREEN.COMPLETE;
            }
            break;

        case 3: // '\003'
            openMap();
            break;
        }
    }

    private void setReward(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        String dialog = DIALOG_3;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            dialog = (new StringBuilder()).append(dialog).append(DESCRIPTIONS[3]).toString();
            if(!AbstractDungeon.player.hasRelic("Spirit Poop"))
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, RelicLibrary.getRelic("Spirit Poop").makeCopy());
            else
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(drawX, drawY, new Circlet());
            break;

        case 2: // '\002'
            dialog = (new StringBuilder()).append(dialog).append(DESCRIPTIONS[4]).toString();
            break;

        case 3: // '\003'
        case 4: // '\004'
            dialog = (new StringBuilder()).append(dialog).append(DESCRIPTIONS[5]).toString();
            AbstractDungeon.player.heal(5);
            break;

        case 5: // '\005'
            dialog = (new StringBuilder()).append(dialog).append(DESCRIPTIONS[6]).toString();
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
            break;

        case 6: // '\006'
            dialog = (new StringBuilder()).append(dialog).append(DESCRIPTIONS[7]).toString();
            AbstractDungeon.player.increaseMaxHp(10, false);
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
            break;
        }
        imageEventText.updateBodyText(dialog);
    }

    public static final String ID = "Bonfire Elementals";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private CUR_SCREEN screen;
    private AbstractCard offeredCard;
    private boolean cardSelect;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Bonfire Elementals");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }
}
