// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheBottomScene.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.scene.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.scenes:
//            AbstractScene

public class TheBottomScene extends AbstractScene
{

    public TheBottomScene()
    {
        super("bottomScene/scene.atlas");
        overlayColor = Color.WHITE.cpy();
        whiteColor = Color.WHITE.cpy();
        dust = new ArrayList();
        fog = new ArrayList();
        torches = new ArrayList();
        fg = atlas.findRegion("mod/fg");
        mg = atlas.findRegion("mod/mg");
        leftWall = atlas.findRegion("mod/mod1");
        hollowWall = atlas.findRegion("mod/mod2");
        solidWall = atlas.findRegion("mod/midWall");
        ceiling = atlas.findRegion("mod/ceiling");
        ceilingMod1 = atlas.findRegion("mod/ceilingMod1");
        ceilingMod2 = atlas.findRegion("mod/ceilingMod2");
        ceilingMod3 = atlas.findRegion("mod/ceilingMod3");
        ceilingMod4 = atlas.findRegion("mod/ceilingMod4");
        ceilingMod5 = atlas.findRegion("mod/ceilingMod5");
        ceilingMod6 = atlas.findRegion("mod/ceilingMod6");
        ambianceName = "AMBIANCE_BOTTOM";
        fadeInAmbiance();
    }

    public void update()
    {
        super.update();
        updateDust();
        updateFog();
        updateTorches();
    }

    private void updateDust()
    {
        Iterator e = dust.iterator();
        do
        {
            if(!e.hasNext())
                break;
            DustEffect effect = (DustEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
        if(dust.size() < 96 && !Settings.DISABLE_EFFECTS)
            dust.add(new DustEffect());
    }

    private void updateFog()
    {
        if(fog.size() < 50 && !Settings.DISABLE_EFFECTS)
            fog.add(new BottomFogEffect(true));
        Iterator e = fog.iterator();
        do
        {
            if(!e.hasNext())
                break;
            BottomFogEffect effect = (BottomFogEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
    }

    private void updateTorches()
    {
        Iterator e = torches.iterator();
        do
        {
            if(!e.hasNext())
                break;
            InteractableTorchEffect effect = (InteractableTorchEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
    }

    public void nextRoom(AbstractRoom room)
    {
        super.nextRoom(room);
        randomizeScene();
        if(room instanceof MonsterRoomBoss)
            CardCrawlGame.music.silenceBGM();
        if((room instanceof EventRoom) || (room instanceof RestRoom))
            torches.clear();
        fadeInAmbiance();
    }

    public void randomizeScene()
    {
        if(MathUtils.randomBoolean())
        {
            renderSolidMid = false;
            renderLeftWall = false;
            renderHollowMid = true;
            if(MathUtils.randomBoolean())
            {
                renderSolidMid = true;
                if(MathUtils.randomBoolean())
                    renderLeftWall = true;
            }
        } else
        {
            renderLeftWall = false;
            renderHollowMid = false;
            renderSolidMid = true;
            if(MathUtils.randomBoolean())
                renderLeftWall = true;
        }
        renderCeilingMod1 = MathUtils.randomBoolean();
        renderCeilingMod2 = MathUtils.randomBoolean();
        renderCeilingMod3 = MathUtils.randomBoolean();
        renderCeilingMod4 = MathUtils.randomBoolean();
        renderCeilingMod5 = MathUtils.randomBoolean();
        renderCeilingMod6 = MathUtils.randomBoolean();
        randomizeTorch();
        overlayColor.r = MathUtils.random(0.0F, 0.05F);
        overlayColor.g = MathUtils.random(0.0F, 0.2F);
        overlayColor.b = MathUtils.random(0.0F, 0.2F);
    }

    public void renderCombatRoomBg(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, bg, true);
        if(!isCamp)
        {
            BottomFogEffect e;
            for(Iterator iterator = fog.iterator(); iterator.hasNext(); e.render(sb))
                e = (BottomFogEffect)iterator.next();

        }
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, mg, true);
        if(renderHollowMid && (renderSolidMid || renderLeftWall))
            sb.setColor(Color.GRAY);
        renderAtlasRegionIf(sb, solidWall, renderSolidMid);
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, hollowWall, renderHollowMid);
        renderAtlasRegionIf(sb, leftWall, renderLeftWall);
        renderAtlasRegionIf(sb, ceiling, true);
        renderAtlasRegionIf(sb, ceilingMod1, renderCeilingMod1);
        renderAtlasRegionIf(sb, ceilingMod2, renderCeilingMod2);
        renderAtlasRegionIf(sb, ceilingMod3, renderCeilingMod3);
        renderAtlasRegionIf(sb, ceilingMod4, renderCeilingMod4);
        renderAtlasRegionIf(sb, ceilingMod5, renderCeilingMod5);
        renderAtlasRegionIf(sb, ceilingMod6, renderCeilingMod6);
        InteractableTorchEffect e;
        for(Iterator iterator1 = torches.iterator(); iterator1.hasNext(); e.render(sb))
            e = (InteractableTorchEffect)iterator1.next();

        sb.setBlendFunction(768, 1);
        sb.setColor(overlayColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    private void randomizeTorch()
    {
        torches.clear();
        if(MathUtils.randomBoolean(0.1F))
            torches.add(new InteractableTorchEffect(1790F * Settings.xScale, 850F * Settings.yScale, com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize.S));
        if(renderHollowMid && !renderSolidMid)
        {
            int roll = MathUtils.random(2);
            if(roll == 0)
            {
                torches.add(new InteractableTorchEffect(800F * Settings.xScale, 768F * Settings.yScale));
                torches.add(new InteractableTorchEffect(1206F * Settings.xScale, 768F * Settings.yScale));
            } else
            if(roll == 1)
                torches.add(new InteractableTorchEffect(328F * Settings.xScale, 865F * Settings.yScale, com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize.S));
        } else
        if(!renderLeftWall && !renderHollowMid)
        {
            if(MathUtils.randomBoolean(0.75F))
            {
                torches.add(new InteractableTorchEffect(613F * Settings.xScale, 860F * Settings.yScale));
                torches.add(new InteractableTorchEffect(613F * Settings.xScale, 672F * Settings.yScale));
                if(MathUtils.randomBoolean(0.3F))
                {
                    torches.add(new InteractableTorchEffect(1482F * Settings.xScale, 860F * Settings.yScale));
                    torches.add(new InteractableTorchEffect(1482F * Settings.xScale, 672F * Settings.yScale));
                }
            }
        } else
        if(renderSolidMid && renderHollowMid)
        {
            if(!renderLeftWall)
            {
                int roll = MathUtils.random(3);
                if(roll == 0)
                {
                    torches.add(new InteractableTorchEffect(912F * Settings.xScale, 790F * Settings.yScale));
                    torches.add(new InteractableTorchEffect(912F * Settings.xScale, 526F * Settings.yScale));
                    torches.add(new InteractableTorchEffect(844F * Settings.xScale, 658F * Settings.yScale, com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize.S));
                    torches.add(new InteractableTorchEffect(980F * Settings.xScale, 658F * Settings.yScale, com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize.S));
                } else
                if(roll == 1 || roll == 2)
                    torches.add(new InteractableTorchEffect(1828F * Settings.xScale, 720F * Settings.yScale));
            } else
            if(MathUtils.randomBoolean(0.75F))
                torches.add(new InteractableTorchEffect(970F * Settings.xScale, 874F * Settings.yScale, com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize.L));
        } else
        if(renderLeftWall && !renderHollowMid && MathUtils.randomBoolean(0.75F))
        {
            torches.add(new InteractableTorchEffect(970F * Settings.xScale, 873F * Settings.renderScale, com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.TorchSize.L));
            torches.add(new InteractableTorchEffect(616F * Settings.xScale, 813F * Settings.renderScale));
            torches.add(new InteractableTorchEffect(1266F * Settings.xScale, 708F * Settings.renderScale));
        }
        LightFlareMEffect.renderGreen = MathUtils.randomBoolean();
        TorchParticleMEffect.renderGreen = LightFlareMEffect.renderGreen;
    }

    public void renderCombatRoomFg(SpriteBatch sb)
    {
        if(!isCamp)
        {
            DustEffect e;
            for(Iterator iterator = dust.iterator(); iterator.hasNext(); e.render(sb))
                e = (DustEffect)iterator.next();

        }
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, fg, true);
    }

    public void renderCampfireRoom(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, campfireBg, true);
        sb.setBlendFunction(770, 1);
        whiteColor.a = MathUtils.cosDeg((System.currentTimeMillis() / 3L) % 360L) / 10F + 0.8F;
        sb.setColor(whiteColor);
        renderQuadrupleSize(sb, campfireGlow, !CampfireUI.hidden);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, campfireKindling, true);
    }

    private boolean renderLeftWall;
    private boolean renderSolidMid;
    private boolean renderHollowMid;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion fg;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion leftWall;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion hollowWall;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion solidWall;
    private boolean renderCeilingMod1;
    private boolean renderCeilingMod2;
    private boolean renderCeilingMod3;
    private boolean renderCeilingMod4;
    private boolean renderCeilingMod5;
    private boolean renderCeilingMod6;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceiling;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceilingMod1;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceilingMod2;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceilingMod3;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceilingMod4;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceilingMod5;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceilingMod6;
    private Color overlayColor;
    private Color whiteColor;
    private ArrayList dust;
    private ArrayList fog;
    private ArrayList torches;
    private static final int DUST_AMT = 24;
}
