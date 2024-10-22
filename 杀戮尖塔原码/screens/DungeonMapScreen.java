// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DungeonMapScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.Flight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.relics.WingBoots;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.MapCircleEffect;
import com.megacrit.cardcrawl.vfx.scene.LevelTransitionTextOverlayEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class DungeonMapScreen
{

    public DungeonMapScreen()
    {
        map = new DungeonMap();
        visibleMapNodes = new ArrayList();
        dismissable = false;
        targetOffsetY = offsetY;
        grabStartY = 0.0F;
        grabbedScreen = false;
        clicked = false;
        clickTimer = 0.0F;
        scrollWaitTimer = 0.0F;
        oscillatingColor = Settings.GOLD_COLOR.cpy();
        scrollBackTimer = 0.0F;
        mapNodeHb = null;
        oscillatingFader = 0.0F;
        oscillatingTimer = 0.0F;
        oscillatingColor.a = 0.0F;
    }

    public void update()
    {
        if(scrollWaitTimer < 0.0F && Settings.isControllerMode && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP && !map.legend.isLegendHighlighted && scrollBackTimer > 0.0F)
        {
            scrollBackTimer -= Gdx.graphics.getDeltaTime();
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.85F)
                targetOffsetY += Settings.SCROLL_SPEED * 2.0F;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.15F)
                targetOffsetY -= Settings.SCROLL_SPEED * 2.0F;
            if(targetOffsetY > MAP_SCROLL_LOWER)
                targetOffsetY = MAP_SCROLL_LOWER;
            else
            if(targetOffsetY < mapScrollUpperLimit)
                targetOffsetY = mapScrollUpperLimit;
            offsetY = MathUtils.lerp(offsetY, targetOffsetY, Gdx.graphics.getDeltaTime() * 12F);
        }
        map.update();
        if(AbstractDungeon.isScreenUp)
        {
            MapRoomNode n;
            for(Iterator iterator = visibleMapNodes.iterator(); iterator.hasNext(); n.update())
                n = (MapRoomNode)iterator.next();

        }
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP && !dismissable && scrollWaitTimer < 0.0F)
            oscillateColor();
        for(Iterator iterator1 = AbstractDungeon.topLevelEffects.iterator(); iterator1.hasNext();)
        {
            AbstractGameEffect e = (AbstractGameEffect)iterator1.next();
            if(e instanceof MapCircleEffect)
                return;
        }

        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP)
            updateYOffset();
        updateMouse();
        updateControllerInput();
        if(Settings.isControllerMode && mapNodeHb != null && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP)
        {
            int tmpY = (int)((float)Settings.HEIGHT - mapNodeHb.cY);
            if(tmpY < 1)
                tmpY = 1;
            else
            if(tmpY > Settings.HEIGHT - 1)
                tmpY = Settings.HEIGHT - 1;
            Gdx.input.setCursorPosition((int)mapNodeHb.cX, tmpY);
        }
    }

    private void updateMouse()
    {
        if(clicked)
            clicked = false;
        if(InputHelper.justReleasedClickLeft && clickTimer < 0.4F && Vector2.dst(clickStartX, clickStartY, InputHelper.mX, InputHelper.mY) < Settings.CLICK_DIST_THRESHOLD)
            clicked = true;
        if(InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed() && AbstractDungeon.topPanel.potionUi.isHidden && !AbstractDungeon.topPanel.selectPotionMode)
        {
            clickTimer = 0.0F;
            clickStartX = InputHelper.mX;
            clickStartY = InputHelper.mY;
        } else
        if(InputHelper.isMouseDown)
            clickTimer += Gdx.graphics.getDeltaTime();
        if(CInputActionSet.select.isJustPressed() && clickTimer < 0.4F && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP)
            clicked = true;
    }

    private void updateControllerInput()
    {
        if(scrollWaitTimer > 0.0F || !Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || map.legend.isLegendHighlighted || AbstractDungeon.player.viewingRelics)
        {
            mapNodeHb = null;
            return;
        }
        if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
        {
            targetOffsetY += Settings.SCROLL_SPEED * 4F;
            return;
        }
        if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
        {
            targetOffsetY -= Settings.SCROLL_SPEED * 4F;
            return;
        }
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            scrollBackTimer = 0.1F;
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP)
        {
            ArrayList nodes = new ArrayList();
            if(!AbstractDungeon.firstRoomChosen)
            {
                Iterator iterator = visibleMapNodes.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    MapRoomNode n = (MapRoomNode)iterator.next();
                    if(n.y == 0)
                        nodes.add(n);
                } while(true);
            } else
            {
                Iterator iterator1 = visibleMapNodes.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    MapRoomNode n = (MapRoomNode)iterator1.next();
                    boolean flightMatters = AbstractDungeon.player.hasRelic("WingedGreaves") || ModHelper.isModEnabled("Flight");
                    if(AbstractDungeon.currMapNode.isConnectedTo(n) || flightMatters && AbstractDungeon.currMapNode.wingedIsConnectedTo(n))
                        nodes.add(n);
                } while(true);
            }
            boolean anyHovered = false;
            int index = 0;
            Iterator iterator2 = nodes.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                MapRoomNode n = (MapRoomNode)iterator2.next();
                if(n.hb.hovered)
                {
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
            if(!anyHovered && mapNodeHb == null && !nodes.isEmpty())
            {
                Gdx.input.setCursorPosition((int)((MapRoomNode)nodes.get(nodes.size() / 2)).hb.cX, Settings.HEIGHT - (int)((MapRoomNode)nodes.get(nodes.size() / 2)).hb.cY);
                mapNodeHb = ((MapRoomNode)nodes.get(nodes.size() / 2)).hb;
            } else
            if(!anyHovered && nodes.isEmpty())
            {
                Gdx.input.setCursorPosition((int)AbstractDungeon.dungeonMapScreen.map.bossHb.cX, Settings.HEIGHT - (int)AbstractDungeon.dungeonMapScreen.map.bossHb.cY);
                mapNodeHb = null;
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                if(--index < 0)
                    index = nodes.size() - 1;
                Gdx.input.setCursorPosition((int)((MapRoomNode)nodes.get(index)).hb.cX, Settings.HEIGHT - (int)((MapRoomNode)nodes.get(index)).hb.cY);
                mapNodeHb = ((MapRoomNode)nodes.get(index)).hb;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                if(++index > nodes.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((MapRoomNode)nodes.get(index)).hb.cX, Settings.HEIGHT - (int)((MapRoomNode)nodes.get(index)).hb.cY);
                mapNodeHb = ((MapRoomNode)nodes.get(index)).hb;
            }
        }
    }

    private void updateYOffset()
    {
        if(grabbedScreen)
        {
            if(InputHelper.isMouseDown)
                targetOffsetY = (float)InputHelper.mY - grabStartY;
            else
                grabbedScreen = false;
        } else
        if(scrollWaitTimer < 0.0F)
        {
            if(InputHelper.scrolledDown)
                targetOffsetY += Settings.MAP_SCROLL_SPEED;
            else
            if(InputHelper.scrolledUp)
                targetOffsetY -= Settings.MAP_SCROLL_SPEED;
            if(InputHelper.justClickedLeft && scrollWaitTimer < 0.0F)
            {
                grabbedScreen = true;
                grabStartY = (float)InputHelper.mY - targetOffsetY;
            }
        }
        resetScrolling();
        updateAnimation();
    }

    private void resetScrolling()
    {
        if(targetOffsetY < mapScrollUpperLimit)
            targetOffsetY = MathHelper.scrollSnapLerpSpeed(targetOffsetY, mapScrollUpperLimit);
        else
        if(targetOffsetY > MAP_SCROLL_LOWER)
            targetOffsetY = MathHelper.scrollSnapLerpSpeed(targetOffsetY, MAP_SCROLL_LOWER);
    }

    private void updateAnimation()
    {
        scrollWaitTimer -= Gdx.graphics.getDeltaTime();
        if(scrollWaitTimer < 0.0F)
            offsetY = MathUtils.lerp(offsetY, targetOffsetY, Gdx.graphics.getDeltaTime() * 12F);
        else
        if(scrollWaitTimer < 3F)
            offsetY = Interpolation.exp10.apply(MAP_SCROLL_LOWER, mapScrollUpperLimit, scrollWaitTimer / 3F);
    }

    public void updateImage()
    {
        visibleMapNodes.clear();
        for(Iterator iterator = CardCrawlGame.dungeon.getMap().iterator(); iterator.hasNext();)
        {
            ArrayList rows = (ArrayList)iterator.next();
            Iterator iterator1 = rows.iterator();
            while(iterator1.hasNext()) 
            {
                MapRoomNode node = (MapRoomNode)iterator1.next();
                if(node.hasEdges())
                    visibleMapNodes.add(node);
            }
        }

    }

    public void render(SpriteBatch sb)
    {
        map.render(sb);
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP)
        {
            MapRoomNode n;
            for(Iterator iterator = visibleMapNodes.iterator(); iterator.hasNext(); n.render(sb))
                n = (MapRoomNode)iterator.next();

        }
        map.renderBossIcon(sb);
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP && !dismissable && scrollWaitTimer < 0.0F)
            FontHelper.renderDeckViewTip(sb, TEXT[0], 80F * Settings.scale, oscillatingColor);
        renderControllerUi(sb);
    }

    private void renderControllerUi(SpriteBatch sb)
    {
        if(!Settings.isControllerMode)
            return;
        if(mapNodeHb != null)
            renderReticle(sb, mapNodeHb);
    }

    private void oscillateColor()
    {
        oscillatingFader += Gdx.graphics.getDeltaTime();
        if(oscillatingFader > 1.0F)
        {
            oscillatingFader = 1.0F;
            oscillatingTimer += Gdx.graphics.getDeltaTime() * 5F;
        }
        oscillatingColor.a = (0.33F + (MathUtils.cos(oscillatingTimer) + 1.0F) / 3F) * oscillatingFader;
    }

    public void open(boolean doScrollingAnimation)
    {
        mapNodeHb = null;
        if(!AbstractDungeon.id.equals("TheEnding"))
            mapScrollUpperLimit = MAP_UPPER_SCROLL_DEFAULT;
        else
            mapScrollUpperLimit = MAP_UPPER_SCROLL_FINAL_ACT;
        AbstractDungeon.player.releaseCard();
        map.legend.isLegendHighlighted = false;
        if(Settings.isDebug)
            doScrollingAnimation = false;
        InputHelper.justClickedLeft = false;
        clicked = false;
        clickTimer = 999F;
        grabbedScreen = false;
        AbstractDungeon.topPanel.unhoverHitboxes();
        map.show();
        dismissable = !doScrollingAnimation;
        if(MathUtils.randomBoolean())
            CardCrawlGame.sound.play("MAP_OPEN", 0.1F);
        else
            CardCrawlGame.sound.play("MAP_OPEN_2", 0.1F);
        if(doScrollingAnimation)
        {
            mapNodeHb = null;
            AbstractDungeon.topLevelEffects.add(new LevelTransitionTextOverlayEffect(AbstractDungeon.name, AbstractDungeon.levelNum));
            scrollWaitTimer = 4F;
            offsetY = mapScrollUpperLimit;
            targetOffsetY = MAP_SCROLL_LOWER;
        } else
        {
            scrollWaitTimer = 0.0F;
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
            if(AbstractDungeon.getCurrMapNode() == null)
                offsetY = mapScrollUpperLimit;
            else
                offsetY = -50F * Settings.scale + (float)AbstractDungeon.getCurrMapNode().y * -ICON_SPACING_Y;
            targetOffsetY = offsetY;
        }
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP;
        AbstractDungeon.isScreenUp = true;
        grabStartY = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.overlayMenu.endTurnButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        updateImage();
    }

    public void close()
    {
        map.hide();
        AbstractDungeon.overlayMenu.cancelButton.hide();
        clicked = false;
    }

    public void closeInstantly()
    {
        map.hideInstantly();
        if(AbstractDungeon.overlayMenu != null)
            AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        clicked = false;
    }

    public void renderReticle(SpriteBatch sb, Hitbox hb)
    {
        renderReticleCorner(sb, -hb.width / 2.0F - RETICLE_DIST, hb.height / 2.0F + RETICLE_DIST, hb, false, false);
        renderReticleCorner(sb, hb.width / 2.0F + RETICLE_DIST, hb.height / 2.0F + RETICLE_DIST, hb, true, false);
        renderReticleCorner(sb, -hb.width / 2.0F - RETICLE_DIST, -hb.height / 2.0F - RETICLE_DIST, hb, false, true);
        renderReticleCorner(sb, hb.width / 2.0F + RETICLE_DIST, -hb.height / 2.0F - RETICLE_DIST, hb, true, true);
    }

    private void renderReticleCorner(SpriteBatch sb, float x, float y, Hitbox hb, boolean flipX, boolean flipY)
    {
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, map.targetAlpha / 4F));
        sb.draw(ImageMaster.RETICLE_CORNER, ((hb.cX + x) - 18F) + 4F * Settings.scale, (hb.cY + y) - 18F - 4F * Settings.scale, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, map.targetAlpha));
        sb.draw(ImageMaster.RETICLE_CORNER, (hb.cX + x) - 18F, (hb.cY + y) - 18F, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public DungeonMap map;
    private ArrayList visibleMapNodes;
    public boolean dismissable;
    private float mapScrollUpperLimit;
    private static final float MAP_UPPER_SCROLL_DEFAULT;
    private static final float MAP_UPPER_SCROLL_FINAL_ACT;
    private static final float MAP_SCROLL_LOWER;
    public static final float ICON_SPACING_Y;
    public static float offsetY;
    private float targetOffsetY;
    private float grabStartY;
    private boolean grabbedScreen;
    public boolean clicked;
    public float clickTimer;
    private float clickStartX;
    private float clickStartY;
    private float scrollWaitTimer;
    private static final float SCROLL_WAIT_TIME = 1F;
    private static final float SPECIAL_ANIMATE_TIME = 3F;
    private float oscillatingTimer;
    private float oscillatingFader;
    private Color oscillatingColor;
    private float scrollBackTimer;
    public Hitbox mapNodeHb;
    private static final float RETICLE_DIST;
    private static final int RETICLE_W = 36;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DungeonMapScreen");
        TEXT = uiStrings.TEXT;
        MAP_UPPER_SCROLL_DEFAULT = -2300F * Settings.scale;
        MAP_UPPER_SCROLL_FINAL_ACT = -300F * Settings.scale;
        MAP_SCROLL_LOWER = 190F * Settings.scale;
        ICON_SPACING_Y = 120F * Settings.scale;
        offsetY = -100F * Settings.scale;
        RETICLE_DIST = 20F * Settings.scale;
    }
}
