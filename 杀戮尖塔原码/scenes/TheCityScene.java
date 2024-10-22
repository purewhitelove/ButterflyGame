// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheCityScene.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.scene.CeilingDustEffect;
import com.megacrit.cardcrawl.vfx.scene.FireFlyEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.scenes:
//            AbstractScene

public class TheCityScene extends AbstractScene
{
    private static final class PillarConfig extends Enum
    {

        public static PillarConfig[] values()
        {
            return (PillarConfig[])$VALUES.clone();
        }

        public static PillarConfig valueOf(String name)
        {
            return (PillarConfig)Enum.valueOf(com/megacrit/cardcrawl/scenes/TheCityScene$PillarConfig, name);
        }

        public static final PillarConfig OPEN;
        public static final PillarConfig SIDES_ONLY;
        public static final PillarConfig FULL;
        public static final PillarConfig LEFT_1;
        public static final PillarConfig LEFT_2;
        private static final PillarConfig $VALUES[];

        static 
        {
            OPEN = new PillarConfig("OPEN", 0);
            SIDES_ONLY = new PillarConfig("SIDES_ONLY", 1);
            FULL = new PillarConfig("FULL", 2);
            LEFT_1 = new PillarConfig("LEFT_1", 3);
            LEFT_2 = new PillarConfig("LEFT_2", 4);
            $VALUES = (new PillarConfig[] {
                OPEN, SIDES_ONLY, FULL, LEFT_1, LEFT_2
            });
        }

        private PillarConfig(String s, int i)
        {
            super(s, i);
        }
    }


    public TheCityScene()
    {
        super("cityScene/scene.atlas");
        overlayColor = Color.WHITE.cpy();
        whiteColor = Color.WHITE.cpy();
        yellowTint = new Color(1.0F, 1.0F, 0.9F, 1.0F);
        pillarConfig = PillarConfig.OPEN;
        ceilingDustTimer = 1.0F;
        fireFlies = new ArrayList();
        bg = atlas.findRegion("mod/bg1");
        bgGlow = atlas.findRegion("mod/bgGlowv2");
        bgGlow2 = atlas.findRegion("mod/bgGlowBlur");
        bg2 = atlas.findRegion("mod/bg2");
        bg2Glow = atlas.findRegion("mod/bg2Glow");
        floor = atlas.findRegion("mod/floor");
        ceiling = atlas.findRegion("mod/ceiling");
        wall = atlas.findRegion("mod/wall");
        chains = atlas.findRegion("mod/chains");
        chainsGlow = atlas.findRegion("mod/chainsGlow");
        pillar1 = atlas.findRegion("mod/p1");
        pillar2 = atlas.findRegion("mod/p2");
        pillar3 = atlas.findRegion("mod/p3");
        pillar4 = atlas.findRegion("mod/p4");
        pillar5 = atlas.findRegion("mod/p5");
        throne = atlas.findRegion("mod/throne");
        throneGlow = atlas.findRegion("mod/throneGlow");
        mg = atlas.findRegion("mod/mg1");
        mgGlow = atlas.findRegion("mod/mg1Glow");
        mg2 = atlas.findRegion("mod/mg2");
        fg = atlas.findRegion("mod/fg");
        fgGlow = atlas.findRegion("mod/fgGlow");
        fg2 = atlas.findRegion("mod/fgHideWindow");
        ambianceName = "AMBIANCE_CITY";
        fadeInAmbiance();
    }

    public void update()
    {
        super.update();
        updateFireFlies();
        if(!(AbstractDungeon.getCurrRoom() instanceof RestRoom) && !(AbstractDungeon.getCurrRoom() instanceof EventRoom))
            updateParticles();
    }

    private void updateFireFlies()
    {
        Iterator e = fireFlies.iterator();
        do
        {
            if(!e.hasNext())
                break;
            FireFlyEffect effect = (FireFlyEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
        if(fireFlies.size() < 9 && !Settings.DISABLE_EFFECTS && MathUtils.randomBoolean(0.1F))
            if(blueFlies)
                fireFlies.add(new FireFlyEffect(new Color(MathUtils.random(0.1F, 0.2F), MathUtils.random(0.6F, 0.8F), MathUtils.random(0.8F, 1.0F), 1.0F)));
            else
                fireFlies.add(new FireFlyEffect(new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.5F, 0.8F), MathUtils.random(0.3F, 0.5F), 1.0F)));
    }

    private void updateParticles()
    {
        if(Settings.DISABLE_EFFECTS)
            return;
        ceilingDustTimer -= Gdx.graphics.getDeltaTime();
        if(ceilingDustTimer < 0.0F)
        {
            int roll = MathUtils.random(4);
            if(roll == 0)
            {
                AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                playDustSfx(false);
            } else
            if(roll == 1)
            {
                AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                playDustSfx(false);
            } else
            {
                AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                if(!Settings.isBackgrounded)
                    playDustSfx(true);
            }
            ceilingDustTimer = MathUtils.random(0.5F, 60F);
        }
    }

    private void playDustSfx(boolean boom)
    {
        if(boom)
        {
            int roll = MathUtils.random(2);
            if(roll == 0)
                CardCrawlGame.sound.play("CEILING_BOOM_1", 0.2F);
            else
            if(roll == 1)
                CardCrawlGame.sound.play("CEILING_BOOM_2", 0.2F);
            else
                CardCrawlGame.sound.play("CEILING_BOOM_3", 0.2F);
        } else
        {
            int roll = MathUtils.random(2);
            if(roll == 0)
                CardCrawlGame.sound.play("CEILING_DUST_1", 0.2F);
            else
            if(roll == 1)
                CardCrawlGame.sound.play("CEILING_DUST_2", 0.2F);
            else
                CardCrawlGame.sound.play("CEILING_DUST_3", 0.2F);
        }
    }

    public void randomizeScene()
    {
        hasFlies = MathUtils.randomBoolean();
        blueFlies = MathUtils.randomBoolean();
        overlayColor.r = MathUtils.random(0.8F, 0.9F);
        overlayColor.g = MathUtils.random(0.8F, 0.9F);
        overlayColor.b = MathUtils.random(0.95F, 1.0F);
        darkDay = MathUtils.randomBoolean(0.33F);
        if(darkDay)
        {
            overlayColor.r = 0.6F;
            overlayColor.g = MathUtils.random(0.7F, 0.8F);
            overlayColor.b = MathUtils.random(0.8F, 0.95F);
        }
        renderAltBg = MathUtils.randomBoolean();
        renderMg = true;
        if(renderMg)
        {
            renderMgAlt = MathUtils.randomBoolean();
            if(!renderMgAlt)
                renderMgGlow = MathUtils.randomBoolean();
        }
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
            renderWall = false;
        else
            renderWall = MathUtils.random(4) == 4;
        if(renderWall)
            renderChains = MathUtils.randomBoolean();
        else
            renderChains = false;
        renderFg2 = MathUtils.randomBoolean();
        if(renderWall)
        {
            int roll = MathUtils.random(2);
            if(roll == 0)
                pillarConfig = PillarConfig.OPEN;
            else
            if(roll == 1)
                pillarConfig = PillarConfig.LEFT_1;
            else
                pillarConfig = PillarConfig.LEFT_2;
        } else
        {
            int roll = MathUtils.random(2);
            if(roll == 0)
                pillarConfig = PillarConfig.OPEN;
            else
            if(roll == 1)
                pillarConfig = PillarConfig.SIDES_ONLY;
            else
                pillarConfig = PillarConfig.FULL;
        }
        if((AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) && AbstractDungeon.getCurrRoom().monsters.getMonster("TheCollector") != null)
            renderThrone = true;
        else
            renderThrone = false;
    }

    public void nextRoom(AbstractRoom room)
    {
        super.nextRoom(room);
        fireFlies.clear();
        randomizeScene();
        if(room instanceof MonsterRoomBoss)
            CardCrawlGame.music.silenceBGM();
        fadeInAmbiance();
    }

    public void renderCombatRoomBg(SpriteBatch sb)
    {
        sb.setColor(overlayColor);
        renderAtlasRegionIf(sb, bg, true);
        sb.setBlendFunction(770, 1);
        renderAtlasRegionIf(sb, bgGlow, true);
        if(darkDay)
        {
            sb.setColor(Color.WHITE);
            renderAtlasRegionIf(sb, bgGlow2, true);
            renderAtlasRegionIf(sb, bgGlow2, true);
        }
        sb.setBlendFunction(770, 771);
        renderAtlasRegionIf(sb, bg2, renderAltBg);
        sb.setBlendFunction(770, 1);
        renderAtlasRegionIf(sb, bg2Glow, renderAltBg);
        sb.setBlendFunction(770, 771);
        sb.setColor(overlayColor);
        renderAtlasRegionIf(sb, floor, true);
        renderAtlasRegionIf(sb, ceiling, true);
        renderAtlasRegionIf(sb, wall, renderWall);
        renderAtlasRegionIf(sb, chains, renderChains);
        if(renderChains)
        {
            sb.setBlendFunction(770, 1);
            whiteColor.a = MathUtils.cosDeg((System.currentTimeMillis() / 1L) % 360L) / 10F + 0.9F;
            sb.setColor(whiteColor);
            renderAtlasRegionIf(sb, chainsGlow, true);
            renderAtlasRegionIf(sb, chainsGlow, true);
            sb.setBlendFunction(770, 771);
            sb.setColor(overlayColor);
        }
        renderAtlasRegionIf(sb, mg, renderMg);
        sb.setBlendFunction(770, 1);
        if(renderMgGlow)
        {
            whiteColor.a = MathUtils.cosDeg((System.currentTimeMillis() / 10L) % 360L) / 2.0F + 0.5F;
            sb.setColor(whiteColor);
            renderAtlasRegionIf(sb, mgGlow, renderMg);
            renderAtlasRegionIf(sb, mgGlow, renderMg);
            sb.setColor(yellowTint);
        } else
        {
            renderAtlasRegionIf(sb, mgGlow, renderMg);
        }
        sb.setBlendFunction(770, 771);
        renderAtlasRegionIf(sb, mg2, renderMgAlt);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig = new int[PillarConfig.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig[PillarConfig.OPEN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig[PillarConfig.SIDES_ONLY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig[PillarConfig.FULL.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig[PillarConfig.LEFT_1.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheCityScene$PillarConfig[PillarConfig.LEFT_2.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.scenes.TheCityScene.PillarConfig[pillarConfig.ordinal()])
        {
        case 2: // '\002'
            renderAtlasRegionIf(sb, pillar1, true);
            renderAtlasRegionIf(sb, pillar5, true);
            break;

        case 3: // '\003'
            renderAtlasRegionIf(sb, pillar1, true);
            renderAtlasRegionIf(sb, pillar2, true);
            renderAtlasRegionIf(sb, pillar3, true);
            renderAtlasRegionIf(sb, pillar4, true);
            renderAtlasRegionIf(sb, pillar5, true);
            break;

        case 4: // '\004'
            renderAtlasRegionIf(sb, pillar1, true);
            break;

        case 5: // '\005'
            renderAtlasRegionIf(sb, pillar1, true);
            renderAtlasRegionIf(sb, pillar2, true);
            break;
        }
        renderAtlasRegionIf(sb, throne, renderThrone);
        sb.setBlendFunction(770, 1);
        renderAtlasRegionIf(sb, throneGlow, renderThrone);
        sb.setBlendFunction(770, 771);
    }

    public void renderCombatRoomFg(SpriteBatch sb)
    {
        if(!isCamp && hasFlies)
        {
            FireFlyEffect e;
            for(Iterator iterator = fireFlies.iterator(); iterator.hasNext(); e.render(sb))
                e = (FireFlyEffect)iterator.next();

        }
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, fg, true);
        sb.setBlendFunction(770, 1);
        renderAtlasRegionIf(sb, fgGlow, true);
        sb.setBlendFunction(770, 771);
        renderAtlasRegionIf(sb, fg2, renderFg2);
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

    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bg;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bgGlow;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bgGlow2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bg2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bg2Glow;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion floor;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceiling;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion wall;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion chains;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion chainsGlow;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion pillar1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion pillar2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion pillar3;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion pillar4;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion pillar5;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion throne;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion throneGlow;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mgGlow;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion fg;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion fgGlow;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion fg2;
    private Color overlayColor;
    private Color whiteColor;
    private Color yellowTint;
    private boolean renderAltBg;
    private boolean renderMg;
    private boolean renderMgGlow;
    private boolean renderMgAlt;
    private boolean renderWall;
    private boolean renderChains;
    private boolean renderThrone;
    private boolean renderFg2;
    private boolean darkDay;
    private PillarConfig pillarConfig;
    private float ceilingDustTimer;
    private ArrayList fireFlies;
    private boolean hasFlies;
    private boolean blueFlies;
}
