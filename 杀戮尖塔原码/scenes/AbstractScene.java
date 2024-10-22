// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractScene.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractScene
{

    public AbstractScene(String atlasUrl)
    {
        bgOverlayColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        bgOverlayTarget = 0.0F;
        isCamp = false;
        vertY = 0.0F;
        ambianceSoundId = 0L;
        ambianceSoundKey = null;
        atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        bg = atlas.findRegion("bg");
        campfireBg = atlas.findRegion("campfire");
        campfireGlow = atlas.findRegion("mod/campfireGlow");
        campfireKindling = atlas.findRegion("mod/campfireKindling");
        event = atlas.findRegion("event");
    }

    public void update()
    {
        updateBgOverlay();
        if(vertY != 0.0F)
            vertY = MathHelper.uiLerpSnap(vertY, 0.0F);
    }

    protected void updateBgOverlay()
    {
        if(bgOverlayColor.a != bgOverlayTarget)
        {
            bgOverlayColor.a = MathUtils.lerp(bgOverlayColor.a, bgOverlayTarget, Gdx.graphics.getDeltaTime() * 2.0F);
            if(Math.abs(bgOverlayColor.a - bgOverlayTarget) < 0.01F)
                bgOverlayColor.a = bgOverlayTarget;
        }
    }

    public void nextRoom(AbstractRoom room)
    {
        bgOverlayColor = new Color(0.0F, 0.0F, 0.0F, 0.5F);
        bgOverlayTarget = 0.5F;
    }

    public void changeOverlay(float target)
    {
        bgOverlayTarget = target;
    }

    public abstract void renderCombatRoomBg(SpriteBatch spritebatch);

    public abstract void renderCombatRoomFg(SpriteBatch spritebatch);

    public abstract void renderCampfireRoom(SpriteBatch spritebatch);

    public abstract void randomizeScene();

    public void renderEventRoom(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        if(Settings.isFourByThree)
            sb.draw(event.getTexture(), event.offsetX * Settings.scale, event.offsetY * Settings.scale, 0.0F, 0.0F, event.packedWidth, event.packedHeight, Settings.yScale, Settings.yScale, 0.0F, event.getRegionX(), event.getRegionY(), event.getRegionWidth(), event.getRegionHeight(), false, false);
        else
            sb.draw(event.getTexture(), event.offsetX * Settings.scale, event.offsetY * Settings.scale, 0.0F, 0.0F, event.packedWidth, event.packedHeight, Settings.xScale, Settings.xScale, 0.0F, event.getRegionX(), event.getRegionY(), event.getRegionWidth(), event.getRegionHeight(), false, false);
    }

    public void dispose()
    {
        atlas.dispose();
    }

    public void fadeOutAmbiance()
    {
        if(ambianceSoundKey != null)
        {
            logger.info((new StringBuilder()).append("Fading out ambiance: ").append(ambianceSoundKey).toString());
            CardCrawlGame.sound.fadeOut(ambianceSoundKey, ambianceSoundId);
            ambianceSoundKey = null;
        }
    }

    public void fadeInAmbiance()
    {
        if(ambianceSoundKey == null)
        {
            logger.info((new StringBuilder()).append("Fading in ambiance: ").append(ambianceName).toString());
            ambianceSoundKey = ambianceName;
            ambianceSoundId = CardCrawlGame.sound.playAndLoop(ambianceName);
            updateAmbienceVolume();
        }
    }

    public void muteAmbienceVolume()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.adjustVolume(ambianceName, ambianceSoundId, 0.0F);
    }

    public void updateAmbienceVolume()
    {
        if(ambianceSoundId != 0L)
            if(Settings.AMBIANCE_ON)
                CardCrawlGame.sound.adjustVolume(ambianceName, ambianceSoundId);
            else
                CardCrawlGame.sound.adjustVolume(ambianceName, ambianceSoundId, 0.0F);
    }

    protected void renderAtlasRegionIf(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, boolean condition)
    {
        if(condition)
            if(Settings.isFourByThree)
                sb.draw(region.getTexture(), region.offsetX * Settings.scale, region.offsetY * Settings.yScale, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.scale, Settings.yScale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
            else
            if(Settings.isLetterbox)
                sb.draw(region.getTexture(), region.offsetX * Settings.xScale, region.offsetY * Settings.xScale, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.xScale, Settings.xScale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
            else
                sb.draw(region.getTexture(), region.offsetX * Settings.scale, region.offsetY * Settings.scale, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.scale, Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    protected void renderAtlasRegionIf(SpriteBatch sb, float x, float y, float angle, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, boolean condition)
    {
        if(condition)
            sb.draw(region.getTexture(), region.offsetX * Settings.scale + x, region.offsetY * Settings.scale + y, (float)region.packedWidth / 2.0F, (float)region.packedHeight / 2.0F, region.packedWidth, region.packedHeight, Settings.scale, Settings.scale, angle, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    protected void renderQuadrupleSize(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, boolean condition)
    {
        if(condition)
            if(Settings.isFourByThree)
                sb.draw(region.getTexture(), region.offsetX * Settings.scale * 2.0F, region.offsetY * Settings.yScale, 0.0F, 0.0F, region.packedWidth * 2, region.packedHeight * 2, Settings.scale, Settings.yScale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
            else
                sb.draw(region.getTexture(), region.offsetX * Settings.xScale * 2.0F, region.offsetY * Settings.scale, 0.0F, 0.0F, region.packedWidth * 2, region.packedHeight * 2, Settings.xScale, Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/scenes/AbstractScene.getName());
    private Color bgOverlayColor;
    private float bgOverlayTarget;
    protected final TextureAtlas atlas;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bg;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion campfireBg;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion campfireGlow;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion campfireKindling;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion event;
    protected boolean isCamp;
    private float vertY;
    protected long ambianceSoundId;
    protected String ambianceSoundKey;
    protected String ambianceName;

}
