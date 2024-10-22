// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DungeonMap.java

package com.megacrit.cardcrawl.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            Legend, MapEdge, MapRoomNode

public class DungeonMap
{

    public DungeonMap()
    {
        targetAlpha = 0.0F;
        bossNodeColor = NOT_TAKEN_COLOR.cpy();
        baseMapColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        atBoss = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        legend = new Legend();
        if(top == null)
        {
            top = ImageMaster.loadImage("images/ui/map/mapTop.png");
            mid = ImageMaster.loadImage("images/ui/map/mapMid.png");
            bot = ImageMaster.loadImage("images/ui/map/mapBot.png");
            blend = ImageMaster.loadImage("images/ui/map/mapBlend.png");
        }
        bossHb = new Hitbox(400F * Settings.scale, 360F * Settings.scale);
    }

    public void update()
    {
        legend.update(baseMapColor.a, AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP);
        baseMapColor.a = MathHelper.fadeLerpSnap(baseMapColor.a, targetAlpha);
        bossHb.move((float)Settings.WIDTH / 2.0F, DungeonMapScreen.offsetY + mapOffsetY + BOSS_OFFSET_Y + BOSS_W / 2.0F);
        bossHb.update();
        updateReticle();
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP && (Settings.isDebug || AbstractDungeon.getCurrMapNode().y == 14 || AbstractDungeon.id.equals("TheEnding") && AbstractDungeon.getCurrMapNode().y == 2) && bossHb.hovered && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()))
        {
            AbstractDungeon.getCurrMapNode().taken = true;
            MapRoomNode node2 = AbstractDungeon.getCurrMapNode();
            MapRoomNode node = node2.getEdges().iterator();
            do
            {
                if(!node.hasNext())
                    break;
                MapEdge e = (MapEdge)node.next();
                if(e != null)
                    e.markAsTaken();
            } while(true);
            InputHelper.justClickedLeft = false;
            CardCrawlGame.music.fadeOutTempBGM();
            node = new MapRoomNode(-1, 15);
            node.room = new MonsterRoomBoss();
            AbstractDungeon.nextRoom = node;
            if(AbstractDungeon.pathY.size() > 1)
            {
                AbstractDungeon.pathX.add(AbstractDungeon.pathX.get(AbstractDungeon.pathX.size() - 1));
                AbstractDungeon.pathY.add(Integer.valueOf(((Integer)AbstractDungeon.pathY.get(AbstractDungeon.pathY.size() - 1)).intValue() + 1));
            } else
            {
                AbstractDungeon.pathX.add(Integer.valueOf(1));
                AbstractDungeon.pathY.add(Integer.valueOf(15));
            }
            AbstractDungeon.nextRoomTransitionStart();
            bossHb.hovered = false;
        }
        if(bossHb.hovered || atBoss)
            bossNodeColor = MapRoomNode.AVAILABLE_COLOR.cpy();
        else
            bossNodeColor.lerp(NOT_TAKEN_COLOR, Gdx.graphics.getDeltaTime() * 8F);
        bossNodeColor.a = baseMapColor.a;
    }

    private void updateReticle()
    {
        if(!Settings.isControllerMode)
            return;
        if(bossHb.hovered)
        {
            reticleColor.a += Gdx.graphics.getDeltaTime() * 3F;
            if(reticleColor.a > 1.0F)
                reticleColor.a = 1.0F;
        } else
        {
            reticleColor.a = 0.0F;
        }
    }

    private float calculateMapSize()
    {
        if(AbstractDungeon.id.equals("TheEnding"))
            return Settings.MAP_DST_Y * 4F - 1380F * Settings.scale;
        else
            return Settings.MAP_DST_Y * 16F - 1380F * Settings.scale;
    }

    public void show()
    {
        targetAlpha = 1.0F;
        mapMidDist = calculateMapSize();
        mapOffsetY = mapMidDist - 120F * Settings.scale;
    }

    public void hide()
    {
        targetAlpha = 0.0F;
    }

    public void hideInstantly()
    {
        targetAlpha = 0.0F;
        baseMapColor.a = 0.0F;
        legend.c.a = 0.0F;
    }

    public void render(SpriteBatch sb)
    {
        if(!AbstractDungeon.id.equals("TheEnding"))
            renderNormalMap(sb);
        else
            renderFinalActMap(sb);
    }

    private void renderNormalMap(SpriteBatch sb)
    {
        sb.setColor(baseMapColor);
        if(!Settings.isMobile)
            sb.draw(top, 0.0F, H + DungeonMapScreen.offsetY + mapOffsetY, Settings.WIDTH, 1080F * Settings.scale);
        else
            sb.draw(top, (float)(-Settings.WIDTH) * 0.05F, H + DungeonMapScreen.offsetY + mapOffsetY, (float)Settings.WIDTH * 1.1F, 1080F * Settings.scale);
        renderMapCenters(sb);
        if(!Settings.isMobile)
            sb.draw(bot, 0.0F, -mapMidDist + DungeonMapScreen.offsetY + mapOffsetY + 1.0F, Settings.WIDTH, 1080F * Settings.scale);
        else
            sb.draw(bot, (float)(-Settings.WIDTH) * 0.05F, -mapMidDist + DungeonMapScreen.offsetY + mapOffsetY + 1.0F, (float)Settings.WIDTH * 1.1F, 1080F * Settings.scale);
        renderMapBlender(sb);
        legend.render(sb);
    }

    private void renderFinalActMap(SpriteBatch sb)
    {
        sb.setColor(baseMapColor);
        if(!Settings.isMobile)
        {
            sb.draw(top, 0.0F, H + DungeonMapScreen.offsetY + mapOffsetY, Settings.WIDTH, 1080F * Settings.scale);
            sb.draw(bot, 0.0F, -mapMidDist + DungeonMapScreen.offsetY + mapOffsetY + 1.0F, Settings.WIDTH, 1080F * Settings.scale);
        } else
        {
            sb.draw(top, (float)(-Settings.WIDTH) * 0.05F, H + DungeonMapScreen.offsetY + mapOffsetY, (float)Settings.WIDTH * 1.1F, 1080F * Settings.scale);
            sb.draw(bot, (float)(-Settings.WIDTH) * 0.05F, -mapMidDist + DungeonMapScreen.offsetY + mapOffsetY + 1.0F, (float)Settings.WIDTH * 1.1F, 1080F * Settings.scale);
        }
        renderMapBlender(sb);
        legend.render(sb);
    }

    public void renderBossIcon(SpriteBatch sb)
    {
        if(boss != null)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, bossNodeColor.a));
            if(!Settings.isMobile)
            {
                sb.draw(bossOutline, (float)Settings.WIDTH / 2.0F - BOSS_W / 2.0F, DungeonMapScreen.offsetY + mapOffsetY + BOSS_OFFSET_Y, BOSS_W, BOSS_W);
                sb.setColor(bossNodeColor);
                sb.draw(boss, (float)Settings.WIDTH / 2.0F - BOSS_W / 2.0F, DungeonMapScreen.offsetY + mapOffsetY + BOSS_OFFSET_Y, BOSS_W, BOSS_W);
            } else
            {
                sb.draw(bossOutline, (float)Settings.WIDTH / 2.0F - BOSS_W / 2.0F, DungeonMapScreen.offsetY + mapOffsetY + BOSS_OFFSET_Y, BOSS_W, BOSS_W);
                sb.setColor(bossNodeColor);
                sb.draw(boss, (float)Settings.WIDTH / 2.0F - BOSS_W / 2.0F, DungeonMapScreen.offsetY + mapOffsetY + BOSS_OFFSET_Y, BOSS_W, BOSS_W);
            }
        }
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP)
        {
            bossHb.render(sb);
            if(Settings.isControllerMode && AbstractDungeon.dungeonMapScreen.map.bossHb.hovered)
                renderReticle(sb, AbstractDungeon.dungeonMapScreen.map.bossHb);
        }
    }

    private void renderMapCenters(SpriteBatch sb)
    {
        if(!Settings.isMobile)
            sb.draw(mid, 0.0F, DungeonMapScreen.offsetY + mapOffsetY, Settings.WIDTH, 1080F * Settings.scale);
        else
            sb.draw(mid, (float)(-Settings.WIDTH) * 0.05F, DungeonMapScreen.offsetY + mapOffsetY, (float)Settings.WIDTH * 1.1F, 1080F * Settings.scale);
    }

    public void renderReticle(SpriteBatch sb, Hitbox hb)
    {
        float offset = Interpolation.fade.apply(24F * Settings.scale, 12F * Settings.scale, reticleColor.a);
        sb.setColor(reticleColor);
        renderReticleCorner(sb, -hb.width / 2.0F + offset, hb.height / 2.0F - offset, hb, false, false);
        renderReticleCorner(sb, hb.width / 2.0F - offset, hb.height / 2.0F - offset, hb, true, false);
        renderReticleCorner(sb, -hb.width / 2.0F + offset, -hb.height / 2.0F + offset, hb, false, true);
        renderReticleCorner(sb, hb.width / 2.0F - offset, -hb.height / 2.0F + offset, hb, true, true);
    }

    private void renderReticleCorner(SpriteBatch sb, float x, float y, Hitbox hb, boolean flipX, boolean flipY)
    {
        sb.draw(ImageMaster.RETICLE_CORNER, (hb.cX + x) - 18F, (hb.cY + y) - 18F, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
    }

    private void renderMapBlender(SpriteBatch sb)
    {
        if(!AbstractDungeon.id.equals("TheEnding"))
            if(!Settings.isMobile)
            {
                sb.draw(blend, 0.0F, DungeonMapScreen.offsetY + mapOffsetY + 800F * Settings.scale, Settings.WIDTH, BLEND_H);
                sb.draw(blend, 0.0F, (DungeonMapScreen.offsetY + mapOffsetY) - 220F * Settings.scale, Settings.WIDTH, BLEND_H);
            } else
            {
                sb.draw(blend, (float)(-Settings.WIDTH) * 0.05F, DungeonMapScreen.offsetY + mapOffsetY + 800F * Settings.scale, (float)Settings.WIDTH * 1.1F, BLEND_H);
                sb.draw(blend, (float)(-Settings.WIDTH) * 0.05F, (DungeonMapScreen.offsetY + mapOffsetY) - 220F * Settings.scale, (float)Settings.WIDTH * 1.1F, BLEND_H);
            }
    }

    private static Texture top;
    private static Texture mid;
    private static Texture bot;
    private static Texture blend;
    public static Texture boss;
    public static Texture bossOutline;
    public float targetAlpha;
    private static final Color NOT_TAKEN_COLOR = new Color(0.34F, 0.34F, 0.34F, 1.0F);
    private Color bossNodeColor;
    private Color baseMapColor;
    private float mapMidDist;
    private static float mapOffsetY;
    private static final float BOSS_W;
    private static final float BOSS_OFFSET_Y;
    private static final float H;
    private static final float BLEND_H;
    public Hitbox bossHb;
    public boolean atBoss;
    private Color reticleColor;
    public Legend legend;

    static 
    {
        BOSS_W = Settings.isMobile ? 560F * Settings.scale : 512F * Settings.scale;
        BOSS_OFFSET_Y = 1416F * Settings.scale;
        H = 1020F * Settings.scale;
        BLEND_H = 512F * Settings.scale;
    }
}
