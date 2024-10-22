// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProceedButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.beyond.MysteriousSphere;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.events.city.MaskedBandits;
import com.megacrit.cardcrawl.events.exordium.DeadAdventurer;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.events.shrines.Lab;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.ui.FtueTip;
import java.util.*;

public class ProceedButton
{

    public ProceedButton()
    {
        current_x = HIDE_X;
        current_y = DRAW_Y;
        target_x = current_x;
        wavyTimer = 0.0F;
        isHidden = true;
        label = TEXT[0];
        font = FontHelper.buttonLabelFont;
        callingBellCheck = true;
        hb = new Hitbox(SHOW_X, current_y, HITBOX_W, HITBOX_H);
        isHovered = false;
        hb.move(SHOW_X, current_y);
    }

    public void setLabel(String newLabel)
    {
        label = newLabel;
        if(FontHelper.getSmartWidth(FontHelper.buttonLabelFont, label, 9999F, 0.0F) > 160F * Settings.scale)
            font = FontHelper.topPanelInfoFont;
        else
            font = FontHelper.buttonLabelFont;
    }

    public void update()
    {
        if(!isHidden)
        {
            wavyTimer += Gdx.graphics.getDeltaTime() * 3F;
            if(current_x - SHOW_X < CLICKABLE_DIST)
                hb.update();
            isHovered = hb.hovered;
            if(hb.hovered && InputHelper.justClickedLeft)
            {
                CardCrawlGame.sound.play("UI_CLICK_1");
                hb.clickStarted = true;
            }
            if(hb.justHovered && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD)
            {
                RewardItem i;
                for(Iterator iterator = AbstractDungeon.combatRewardScreen.rewards.iterator(); iterator.hasNext(); i.flash())
                    i = (RewardItem)iterator.next();

            }
            if(hb.clicked || CInputActionSet.proceed.isJustPressed())
            {
                hb.clicked = false;
                AbstractRoom currentRoom = AbstractDungeon.getCurrRoom();
                if(currentRoom instanceof MonsterRoomBoss)
                    if(AbstractDungeon.id.equals("TheBeyond"))
                    {
                        if(AbstractDungeon.ascensionLevel >= 20 && AbstractDungeon.bossList.size() == 2)
                            goToDoubleBoss();
                        else
                        if(!Settings.isEndless)
                            goToVictoryRoomOrTheDoor();
                    } else
                    if(AbstractDungeon.id.equals("TheEnding"))
                        goToTrueVictoryRoom();
                if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD && !(AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss))
                {
                    if(currentRoom instanceof MonsterRoomBoss)
                        goToTreasureRoom();
                    else
                    if(currentRoom instanceof EventRoom)
                    {
                        if(!(currentRoom.event instanceof Mushrooms) && !(currentRoom.event instanceof MaskedBandits) && !(currentRoom.event instanceof DeadAdventurer) && !(currentRoom.event instanceof Lab) && !(currentRoom.event instanceof Colosseum) && !(currentRoom.event instanceof MysteriousSphere) && !(currentRoom.event instanceof MindBloom))
                        {
                            AbstractDungeon.closeCurrentScreen();
                            hide();
                        } else
                        {
                            AbstractDungeon.closeCurrentScreen();
                            AbstractDungeon.dungeonMapScreen.open(false);
                            AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
                        }
                    } else
                    {
                        if(!((Boolean)TipTracker.tips.get("CARD_REWARD_TIP")).booleanValue() && !AbstractDungeon.combatRewardScreen.rewards.isEmpty())
                        {
                            if(Settings.isConsoleBuild)
                            {
                                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[1], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.CARD_REWARD);
                                TipTracker.neverShowAgain("CARD_REWARD_TIP");
                            } else
                            {
                                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.CARD_REWARD);
                                TipTracker.neverShowAgain("CARD_REWARD_TIP");
                            }
                            return;
                        }
                        int relicCount = 0;
                        Iterator iterator1 = AbstractDungeon.combatRewardScreen.rewards.iterator();
                        do
                        {
                            if(!iterator1.hasNext())
                                break;
                            RewardItem i = (RewardItem)iterator1.next();
                            if(i.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC)
                                relicCount++;
                        } while(true);
                        if(relicCount == 3 && callingBellCheck)
                        {
                            callingBellCheck = false;
                            if(!AbstractDungeon.combatRewardScreen.rewards.isEmpty())
                            {
                                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.CARD_REWARD);
                                return;
                            }
                        }
                        if(AbstractDungeon.getCurrRoom() instanceof NeowRoom)
                            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                        if(!AbstractDungeon.combatRewardScreen.hasTakenAll)
                        {
                            Iterator iterator2 = AbstractDungeon.combatRewardScreen.rewards.iterator();
                            do
                            {
                                if(!iterator2.hasNext())
                                    break;
                                RewardItem item = (RewardItem)iterator2.next();
                                if(item.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD)
                                    item.recordCardSkipMetrics();
                            } while(true);
                        }
                        AbstractDungeon.closeCurrentScreen();
                        AbstractDungeon.dungeonMapScreen.open(false);
                        AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
                    }
                } else
                if(currentRoom instanceof TreasureRoomBoss)
                {
                    if(Settings.isDemo)
                        goToDemoVictoryRoom();
                    else
                        goToNextDungeon(currentRoom);
                } else
                if(!(currentRoom instanceof MonsterRoomBoss))
                {
                    AbstractDungeon.dungeonMapScreen.open(false);
                    hide();
                }
            }
        }
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
                current_x = target_x;
        }
    }

    private void goToTreasureRoom()
    {
        CardCrawlGame.music.fadeOutTempBGM();
        MapRoomNode node = new MapRoomNode(-1, 15);
        node.room = new TreasureRoomBoss();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        hide();
    }

    private void goToTrueVictoryRoom()
    {
        CardCrawlGame.music.fadeOutBGM();
        MapRoomNode node = new MapRoomNode(3, 4);
        node.room = new TrueVictoryRoom();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        hide();
    }

    private void goToVictoryRoomOrTheDoor()
    {
        CardCrawlGame.music.fadeOutBGM();
        CardCrawlGame.music.fadeOutTempBGM();
        MapRoomNode node = new MapRoomNode(-1, 15);
        node.room = new VictoryRoom(com.megacrit.cardcrawl.rooms.VictoryRoom.EventType.HEART);
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        hide();
    }

    private void goToDoubleBoss()
    {
        AbstractDungeon.bossKey = (String)AbstractDungeon.bossList.get(0);
        CardCrawlGame.music.fadeOutBGM();
        CardCrawlGame.music.fadeOutTempBGM();
        MapRoomNode node = new MapRoomNode(-1, 15);
        node.room = new MonsterRoomBoss();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        hide();
    }

    private void goToDemoVictoryRoom()
    {
        MapRoomNode node = new MapRoomNode(-1, 15);
        node.room = new VictoryRoom(com.megacrit.cardcrawl.rooms.VictoryRoom.EventType.NONE);
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        hide();
    }

    private void goToNextDungeon(AbstractRoom room)
    {
        if(!((TreasureRoomBoss)room).choseRelic)
            AbstractDungeon.bossRelicScreen.noPick();
        int relicCount = 0;
        Iterator iterator = AbstractDungeon.combatRewardScreen.rewards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RewardItem i = (RewardItem)iterator.next();
            if(i.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC)
                relicCount++;
        } while(true);
        if(relicCount == 3 && callingBellCheck)
        {
            callingBellCheck = false;
            if(!AbstractDungeon.combatRewardScreen.rewards.isEmpty())
            {
                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.CARD_REWARD);
                return;
            }
        }
        CardCrawlGame.music.fadeOutBGM();
        CardCrawlGame.music.fadeOutTempBGM();
        AbstractDungeon.fadeOut();
        AbstractDungeon.isDungeonBeaten = true;
        hide();
    }

    public void hideInstantly()
    {
        current_x = HIDE_X;
        target_x = HIDE_X;
        isHidden = true;
    }

    public void hide()
    {
        if(!isHidden)
        {
            target_x = HIDE_X;
            isHidden = true;
        }
    }

    public void show()
    {
        if(isHidden)
        {
            target_x = SHOW_X;
            isHidden = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(Settings.hideEndTurn)
            return;
        sb.setColor(Color.WHITE);
        renderShadow(sb);
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD)
            sb.setColor(new Color(1.0F, 0.9F, 0.2F, MathUtils.cos(wavyTimer) / 5F + 0.6F));
        else
            sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
        sb.draw(ImageMaster.PROCEED_BUTTON_OUTLINE, current_x - 256F, current_y - 256F, 256F, 256F, 512F, 512F, Settings.scale * 1.1F + MathUtils.cos(wavyTimer) / 50F, Settings.scale * 1.1F + MathUtils.cos(wavyTimer) / 50F, 0.0F, 0, 0, 512, 512, false, false);
        sb.setColor(Color.WHITE);
        renderButton(sb);
        if(Settings.isControllerMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.proceed.getKeyImg(), current_x - 32F - 38F * Settings.scale - FontHelper.getSmartWidth(font, label, 99999F, 0.0F) / 2.0F, current_y - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(hb.hovered)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        if(hb.hovered && !hb.clickStarted)
            FontHelper.renderFontCentered(sb, font, label, current_x, current_y, Settings.CREAM_COLOR);
        else
        if(hb.clickStarted)
            FontHelper.renderFontCentered(sb, font, label, current_x, current_y, Color.LIGHT_GRAY);
        else
            FontHelper.renderFontCentered(sb, font, label, current_x, current_y, Settings.LIGHT_YELLOW_COLOR);
        if(!isHidden)
            hb.render(sb);
    }

    private void renderShadow(SpriteBatch sb)
    {
        sb.draw(ImageMaster.PROCEED_BUTTON_SHADOW, current_x - 256F, current_y - 256F, 256F, 256F, 512F, 512F, Settings.scale * 1.1F + MathUtils.cos(wavyTimer) / 50F, Settings.scale * 1.1F + MathUtils.cos(wavyTimer) / 50F, 0.0F, 0, 0, 512, 512, false, false);
    }

    private void renderButton(SpriteBatch sb)
    {
        sb.draw(ImageMaster.PROCEED_BUTTON, current_x - 256F, current_y - 256F, 256F, 256F, 512F, 512F, Settings.scale * 1.1F + MathUtils.cos(wavyTimer) / 50F, Settings.scale * 1.1F + MathUtils.cos(wavyTimer) / 50F, 0.0F, 0, 0, 512, 512, false, false);
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.3F);
    private static final float SHOW_X;
    private static final float DRAW_Y;
    private static final float HIDE_X;
    private float current_x;
    private float current_y;
    private float target_x;
    private float wavyTimer;
    private boolean isHidden;
    private String label;
    private static final int W = 512;
    private BitmapFont font;
    private boolean callingBellCheck;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    private Hitbox hb;
    private static final float CLICKABLE_DIST;
    public boolean isHovered;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Rewards Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        uiStrings = CardCrawlGame.languagePack.getUIString("Proceed Button");
        TEXT = uiStrings.TEXT;
        SHOW_X = 1670F * Settings.xScale;
        DRAW_Y = 320F * Settings.scale;
        HIDE_X = SHOW_X + 500F * Settings.scale;
        HITBOX_W = 280F * Settings.scale;
        HITBOX_H = 156F * Settings.scale;
        CLICKABLE_DIST = 25F * Settings.scale;
    }
}
