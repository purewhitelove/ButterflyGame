// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowEvent.java

package com.megacrit.cardcrawl.neow;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.scene.LevelTransitionTextOverlayEffect;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.neow:
//            NeowReward

public class NeowEvent extends AbstractEvent
{

    public NeowEvent(boolean isDone)
    {
        screenNum = 2;
        setPhaseToEvent = false;
        rewards = new ArrayList();
        pickCard = false;
        waitingToSave = false;
        if(npc == null)
            npc = new AnimatedNpc(1534F * Settings.xScale, AbstractDungeon.floorY - 60F * Settings.yScale, "images/npcs/neow/skeleton.atlas", "images/npcs/neow/skeleton.json", "idle");
        roomEventText.clear();
        playSfx();
        if(!Settings.isEndless || AbstractDungeon.floorNum <= 1)
            if(Settings.isStandardRun() || Settings.isEndless && AbstractDungeon.floorNum <= 1)
            {
                bossCount = CardCrawlGame.playerPref.getInteger((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_SPIRITS").toString(), 0);
                AbstractDungeon.bossCount = bossCount;
            } else
            if(Settings.seedSet)
                bossCount = 1;
            else
                bossCount = 0;
        body = "";
        if(Settings.isEndless && AbstractDungeon.floorNum > 1)
        {
            talk(TEXT[MathUtils.random(12, 14)]);
            screenNum = 999;
            roomEventText.addDialogOption(OPTIONS[0]);
        } else
        if(shouldSkipNeowDialog())
        {
            screenNum = 10;
            talk(TEXT[10]);
            roomEventText.addDialogOption(OPTIONS[1]);
        } else
        if(!isDone)
        {
            if(!((Boolean)TipTracker.tips.get("NEOW_INTRO")).booleanValue())
            {
                screenNum = 0;
                TipTracker.neverShowAgain("NEOW_INTRO");
                talk(TEXT[0]);
                roomEventText.addDialogOption(OPTIONS[1]);
            } else
            {
                screenNum = 1;
                talk(TEXT[MathUtils.random(1, 3)]);
                roomEventText.addDialogOption(OPTIONS[1]);
            }
            AbstractDungeon.topLevelEffects.add(new LevelTransitionTextOverlayEffect(AbstractDungeon.name, AbstractDungeon.levelNum, true));
        } else
        {
            screenNum = 99;
            talk(TEXT[8]);
            roomEventText.addDialogOption(OPTIONS[3]);
        }
        hasDialog = true;
        hasFocus = true;
    }

    public NeowEvent()
    {
        this(false);
    }

    private boolean shouldSkipNeowDialog()
    {
        if(Settings.seedSet && !Settings.isTrial && !Settings.isDailyRun)
            return false;
        else
            return !Settings.isStandardRun();
    }

    public void update()
    {
        super.update();
        if(pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard c;
            for(Iterator iterator1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator1.hasNext(); group.addToBottom(c.makeCopy()))
                c = (AbstractCard)iterator1.next();

            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, TEXT[11]);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        NeowReward r;
        for(Iterator iterator = rewards.iterator(); iterator.hasNext(); r.update())
            r = (NeowReward)iterator.next();

        if(!setPhaseToEvent)
        {
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.EVENT;
            setPhaseToEvent = true;
        }
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
        if(waitingToSave && !AbstractDungeon.isScreenUp && AbstractDungeon.topLevelEffects.isEmpty() && AbstractDungeon.player.relicsDoneAnimating())
        {
            boolean doneAnims = true;
            Iterator iterator2 = AbstractDungeon.player.relics.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator2.next();
                if(r.isDone)
                    continue;
                doneAnims = false;
                break;
            } while(true);
            if(doneAnims)
            {
                waitingToSave = false;
                SaveHelper.saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.POST_NEOW);
            }
        }
    }

    private void talk(String msg)
    {
        AbstractDungeon.effectList.add(new InfiniteSpeechBubble(DIALOG_X, DIALOG_Y, msg));
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            dismissBubble();
            talk(TEXT[4]);
            if(bossCount != 0 || Settings.isTestingNeow)
                blessing();
            else
                miniBlessing();
            break;

        case 1: // '\001'
            dismissBubble();
            logger.info(Integer.valueOf(bossCount));
            if(bossCount == 0 && !Settings.isTestingNeow)
                miniBlessing();
            else
                blessing();
            break;

        case 2: // '\002'
            if(buttonPressed == 0)
                blessing();
            else
                openMap();
            break;

        case 3: // '\003'
            dismissBubble();
            roomEventText.clearRemainingOptions();
            switch(buttonPressed)
            {
            case 0: // '\0'
                ((NeowReward)rewards.get(0)).activate();
                talk(TEXT[8]);
                break;

            case 1: // '\001'
                ((NeowReward)rewards.get(1)).activate();
                talk(TEXT[8]);
                break;

            case 2: // '\002'
                ((NeowReward)rewards.get(2)).activate();
                talk(TEXT[9]);
                break;

            case 3: // '\003'
                ((NeowReward)rewards.get(3)).activate();
                talk(TEXT[9]);
                break;
            }
            screenNum = 99;
            roomEventText.updateDialogOption(0, OPTIONS[3]);
            roomEventText.clearRemainingOptions();
            waitingToSave = true;
            break;

        case 10: // '\n'
            dailyBlessing();
            roomEventText.clearRemainingOptions();
            roomEventText.updateDialogOption(0, OPTIONS[3]);
            screenNum = 99;
            break;

        case 999: 
            endlessBlight();
            roomEventText.clearRemainingOptions();
            roomEventText.updateDialogOption(0, OPTIONS[3]);
            screenNum = 99;
            break;

        default:
            openMap();
            break;
        }
    }

    private void endlessBlight()
    {
        if(AbstractDungeon.player.hasBlight("DeadlyEnemies"))
        {
            AbstractBlight tmp = AbstractDungeon.player.getBlight("DeadlyEnemies");
            tmp.incrementUp();
            tmp.flash();
        } else
        {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new Spear());
        }
        if(AbstractDungeon.player.hasBlight("ToughEnemies"))
        {
            AbstractBlight tmp = AbstractDungeon.player.getBlight("ToughEnemies");
            tmp.incrementUp();
            tmp.flash();
        } else
        {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new Shield());
        }
        uniqueBlight();
    }

    private void uniqueBlight()
    {
        AbstractBlight temp = AbstractDungeon.player.getBlight("MimicInfestation");
        if(temp != null)
        {
            temp = AbstractDungeon.player.getBlight("TimeMaze");
            if(temp != null)
            {
                temp = AbstractDungeon.player.getBlight("FullBelly");
                if(temp != null)
                {
                    temp = AbstractDungeon.player.getBlight("GrotesqueTrophy");
                    if(temp != null)
                        AbstractDungeon.player.getBlight("GrotesqueTrophy").stack();
                    else
                        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new GrotesqueTrophy());
                } else
                {
                    AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new Muzzle());
                }
            } else
            {
                AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new TimeMaze());
            }
        } else
        {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new MimicInfestation());
            return;
        }
    }

    private void dailyBlessing()
    {
        rng = new Random(Settings.seed);
        dismissBubble();
        talk(TEXT[8]);
        if(ModHelper.isModEnabled("Heirloom"))
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, AbstractDungeon.returnRandomRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE));
        boolean addedCards = false;
        CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        if(ModHelper.isModEnabled("Allstar"))
        {
            addedCards = true;
            for(int i = 0; i < 5; i++)
            {
                AbstractCard colorlessCard = AbstractDungeon.getColorlessCardFromPool(AbstractDungeon.rollRareOrUncommon(0.5F));
                UnlockTracker.markCardAsSeen(colorlessCard.cardID);
                group.addToBottom(colorlessCard.makeCopy());
            }

        }
        if(ModHelper.isModEnabled("Specialized"))
            if(!ModHelper.isModEnabled("SealedDeck") && !ModHelper.isModEnabled("Draft"))
            {
                addedCards = true;
                AbstractCard rareCard = AbstractDungeon.returnTrulyRandomCard();
                UnlockTracker.markCardAsSeen(rareCard.cardID);
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
            } else
            {
                AbstractCard rareCard = AbstractDungeon.returnTrulyRandomCard();
                for(int i = 0; i < 5; i++)
                {
                    AbstractCard tmpCard = rareCard.makeCopy();
                    AbstractDungeon.topLevelEffectsQueue.add(new FastCardObtainEffect(tmpCard, MathUtils.random((float)Settings.WIDTH * 0.2F, (float)Settings.WIDTH * 0.8F), MathUtils.random((float)Settings.HEIGHT * 0.3F, (float)Settings.HEIGHT * 0.7F)));
                }

            }
        if(addedCards)
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, TEXT[11]);
        if(ModHelper.isModEnabled("Draft"))
            AbstractDungeon.cardRewardScreen.draftOpen();
        pickCard = true;
        if(ModHelper.isModEnabled("SealedDeck"))
        {
            CardGroup sealedGroup = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            for(int i = 0; i < 30; i++)
            {
                AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity());
                if(!sealedGroup.contains(card))
                    sealedGroup.addToBottom(card.makeCopy());
                else
                    i--;
            }

            AbstractCard c;
            for(Iterator iterator = sealedGroup.group.iterator(); iterator.hasNext(); UnlockTracker.markCardAsSeen(c.cardID))
                c = (AbstractCard)iterator.next();

            AbstractDungeon.gridSelectScreen.open(sealedGroup, 10, OPTIONS[4], false);
        }
        roomEventText.clearRemainingOptions();
        screenNum = 99;
    }

    private void miniBlessing()
    {
        AbstractDungeon.bossCount = 0;
        dismissBubble();
        talk(TEXT[MathUtils.random(4, 6)]);
        rewards.add(new NeowReward(true));
        rewards.add(new NeowReward(false));
        roomEventText.clearRemainingOptions();
        roomEventText.updateDialogOption(0, ((NeowReward)rewards.get(0)).optionLabel);
        roomEventText.addDialogOption(((NeowReward)rewards.get(1)).optionLabel);
        screenNum = 3;
    }

    private void blessing()
    {
        logger.info("BLESSING");
        rng = new Random(Settings.seed);
        logger.info((new StringBuilder()).append("COUNTER: ").append(rng.counter).toString());
        AbstractDungeon.bossCount = 0;
        dismissBubble();
        talk(TEXT[7]);
        rewards.add(new NeowReward(0));
        rewards.add(new NeowReward(1));
        rewards.add(new NeowReward(2));
        rewards.add(new NeowReward(3));
        roomEventText.clearRemainingOptions();
        roomEventText.updateDialogOption(0, ((NeowReward)rewards.get(0)).optionLabel);
        roomEventText.addDialogOption(((NeowReward)rewards.get(1)).optionLabel);
        roomEventText.addDialogOption(((NeowReward)rewards.get(2)).optionLabel);
        roomEventText.addDialogOption(((NeowReward)rewards.get(3)).optionLabel);
        screenNum = 3;
    }

    private void dismissBubble()
    {
        Iterator iterator = AbstractDungeon.effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(e instanceof InfiniteSpeechBubble)
                ((InfiniteSpeechBubble)e).dismiss();
        } while(true);
    }

    private void playSfx()
    {
        int roll = MathUtils.random(3);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_NEOW_1A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_NEOW_1B");
        else
        if(roll == 2)
            CardCrawlGame.sound.play("VO_NEOW_2A");
        else
            CardCrawlGame.sound.play("VO_NEOW_2B");
    }

    public void logMetric(String actionTaken)
    {
        AbstractEvent.logMetric(NAME, actionTaken);
    }

    public void render(SpriteBatch sb)
    {
        npc.render(sb);
    }

    public void dispose()
    {
        super.dispose();
        if(npc != null)
        {
            logger.info("Disposing Neow asset.");
            npc.dispose();
            npc = null;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/neow/NeowEvent.getName());
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    public static final String OPTIONS[];
    private AnimatedNpc npc;
    public static final String NAME;
    private int screenNum;
    private int bossCount;
    private boolean setPhaseToEvent;
    private ArrayList rewards;
    public static Random rng = null;
    private boolean pickCard;
    public static boolean waitingToSave = false;
    private static final float DIALOG_X;
    private static final float DIALOG_Y;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Neow Event");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        OPTIONS = characterStrings.OPTIONS;
        NAME = NAMES[0];
        DIALOG_X = 1100F * Settings.xScale;
        DIALOG_Y = AbstractDungeon.floorY + 60F * Settings.yScale;
    }
}
