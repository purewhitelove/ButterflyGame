// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheBeyondScene.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rooms.*;

// Referenced classes of package com.megacrit.cardcrawl.scenes:
//            AbstractScene

public class TheBeyondScene extends AbstractScene
{
    private static final class ColumnConfig extends Enum
    {

        public static ColumnConfig[] values()
        {
            return (ColumnConfig[])$VALUES.clone();
        }

        public static ColumnConfig valueOf(String name)
        {
            return (ColumnConfig)Enum.valueOf(com/megacrit/cardcrawl/scenes/TheBeyondScene$ColumnConfig, name);
        }

        public static final ColumnConfig OPEN;
        public static final ColumnConfig SMALL_ONLY;
        public static final ColumnConfig SMALL_PLUS_LEFT;
        public static final ColumnConfig SMALL_PLUS_RIGHT;
        private static final ColumnConfig $VALUES[];

        static 
        {
            OPEN = new ColumnConfig("OPEN", 0);
            SMALL_ONLY = new ColumnConfig("SMALL_ONLY", 1);
            SMALL_PLUS_LEFT = new ColumnConfig("SMALL_PLUS_LEFT", 2);
            SMALL_PLUS_RIGHT = new ColumnConfig("SMALL_PLUS_RIGHT", 3);
            $VALUES = (new ColumnConfig[] {
                OPEN, SMALL_ONLY, SMALL_PLUS_LEFT, SMALL_PLUS_RIGHT
            });
        }

        private ColumnConfig(String s, int i)
        {
            super(s, i);
        }
    }


    public TheBeyondScene()
    {
        super("beyondScene/scene.atlas");
        columnConfig = ColumnConfig.OPEN;
        overlayColor = new Color(1.0F, 1.0F, 1.0F, 0.2F);
        tmpColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        whiteColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        bg1 = atlas.findRegion("mod/bg1");
        bg2 = atlas.findRegion("mod/bg2");
        floor = atlas.findRegion("mod/floor");
        ceiling = atlas.findRegion("mod/ceiling");
        fg = atlas.findRegion("mod/fg");
        mg1 = atlas.findRegion("mod/mod1");
        mg2 = atlas.findRegion("mod/mod2");
        mg3 = atlas.findRegion("mod/mod3");
        mg4 = atlas.findRegion("mod/mod4");
        c1 = atlas.findRegion("mod/c1");
        c2 = atlas.findRegion("mod/c2");
        c3 = atlas.findRegion("mod/c3");
        c4 = atlas.findRegion("mod/c4");
        f1 = atlas.findRegion("mod/f1");
        f2 = atlas.findRegion("mod/f2");
        f3 = atlas.findRegion("mod/f3");
        f4 = atlas.findRegion("mod/f4");
        f5 = atlas.findRegion("mod/f5");
        i1 = atlas.findRegion("mod/i1");
        i2 = atlas.findRegion("mod/i2");
        i3 = atlas.findRegion("mod/i3");
        i4 = atlas.findRegion("mod/i4");
        i5 = atlas.findRegion("mod/i5");
        s1 = atlas.findRegion("mod/s1");
        s2 = atlas.findRegion("mod/s2");
        s3 = atlas.findRegion("mod/s3");
        s4 = atlas.findRegion("mod/s4");
        s5 = atlas.findRegion("mod/s5");
        ambianceName = "AMBIANCE_BEYOND";
        fadeInAmbiance();
    }

    public void randomizeScene()
    {
        overlayColor.r = MathUtils.random(0.7F, 0.9F);
        overlayColor.g = MathUtils.random(0.7F, 0.9F);
        overlayColor.b = MathUtils.random(0.7F, 1.0F);
        overlayColor.a = MathUtils.random(0.0F, 0.2F);
        renderAltBg = MathUtils.randomBoolean(0.2F);
        renderM1 = false;
        renderM2 = false;
        renderM3 = false;
        renderM4 = false;
        if(!renderAltBg && MathUtils.randomBoolean(0.8F))
        {
            renderM1 = MathUtils.randomBoolean();
            renderM2 = MathUtils.randomBoolean();
            renderM3 = MathUtils.randomBoolean();
            if(!renderM3)
                renderM4 = MathUtils.randomBoolean();
        }
        if(MathUtils.randomBoolean(0.6F))
            columnConfig = ColumnConfig.OPEN;
        else
        if(MathUtils.randomBoolean())
            columnConfig = ColumnConfig.SMALL_ONLY;
        else
        if(MathUtils.randomBoolean())
            columnConfig = ColumnConfig.SMALL_PLUS_LEFT;
        else
            columnConfig = ColumnConfig.SMALL_PLUS_RIGHT;
        renderF1 = false;
        renderF2 = false;
        renderF3 = false;
        renderF4 = false;
        renderF5 = false;
        int floaterCount = 0;
        renderF1 = MathUtils.randomBoolean(0.25F);
        if(renderF1)
            floaterCount++;
        renderF2 = MathUtils.randomBoolean(0.25F);
        if(renderF2)
            floaterCount++;
        if(floaterCount < 2)
        {
            renderF3 = MathUtils.randomBoolean(0.25F);
            if(renderF3)
                floaterCount++;
        }
        if(floaterCount < 2)
        {
            renderF4 = MathUtils.randomBoolean(0.25F);
            if(renderF4)
                floaterCount++;
        }
        if(floaterCount < 2)
            renderF5 = MathUtils.randomBoolean(0.25F);
        if(MathUtils.randomBoolean(0.3F) || Settings.DISABLE_EFFECTS)
        {
            renderF1 = false;
            renderF2 = false;
            renderF3 = false;
            renderF4 = false;
            renderF5 = false;
        }
        renderIce = MathUtils.randomBoolean();
        if(renderIce)
        {
            renderIce = true;
            renderI1 = MathUtils.randomBoolean();
            renderI2 = MathUtils.randomBoolean();
            renderI3 = MathUtils.randomBoolean();
            renderI4 = MathUtils.randomBoolean();
            renderI5 = MathUtils.randomBoolean();
        } else
        {
            renderI1 = false;
            renderI2 = false;
            renderI3 = false;
            renderI4 = false;
            renderI5 = false;
        }
        renderStalactites = MathUtils.randomBoolean();
        if(renderStalactites)
        {
            renderStalactites = true;
            renderS1 = MathUtils.randomBoolean();
            renderS2 = MathUtils.randomBoolean();
            renderS3 = MathUtils.randomBoolean();
            renderS4 = MathUtils.randomBoolean();
            renderS5 = MathUtils.randomBoolean();
        } else
        {
            renderS1 = false;
            renderS2 = false;
            renderS3 = false;
            renderS4 = false;
            renderS5 = false;
        }
    }

    public void nextRoom(AbstractRoom room)
    {
        super.nextRoom(room);
        randomizeScene();
        if(room instanceof MonsterRoomBoss)
            CardCrawlGame.music.silenceBGM();
        fadeInAmbiance();
    }

    public void renderCombatRoomBg(SpriteBatch sb)
    {
        float prevAlpha = overlayColor.a;
        overlayColor.a = 1.0F;
        sb.setColor(overlayColor);
        overlayColor.a = prevAlpha;
        renderAtlasRegionIf(sb, floor, true);
        renderAtlasRegionIf(sb, ceiling, true);
        renderAtlasRegionIf(sb, bg1, true);
        renderAtlasRegionIf(sb, bg2, renderAltBg);
        renderAtlasRegionIf(sb, mg2, renderM2);
        renderAtlasRegionIf(sb, mg1, renderM1);
        renderAtlasRegionIf(sb, mg3, renderM3);
        renderAtlasRegionIf(sb, mg4, renderM4);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$scenes$TheBeyondScene$ColumnConfig[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$scenes$TheBeyondScene$ColumnConfig = new int[ColumnConfig.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheBeyondScene$ColumnConfig[ColumnConfig.OPEN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheBeyondScene$ColumnConfig[ColumnConfig.SMALL_ONLY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheBeyondScene$ColumnConfig[ColumnConfig.SMALL_PLUS_LEFT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$scenes$TheBeyondScene$ColumnConfig[ColumnConfig.SMALL_PLUS_RIGHT.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.scenes.TheBeyondScene.ColumnConfig[columnConfig.ordinal()])
        {
        case 2: // '\002'
            renderAtlasRegionIf(sb, c1, true);
            renderAtlasRegionIf(sb, c4, true);
            break;

        case 3: // '\003'
            renderAtlasRegionIf(sb, c1, true);
            renderAtlasRegionIf(sb, c2, true);
            renderAtlasRegionIf(sb, c4, true);
            break;

        case 4: // '\004'
            renderAtlasRegionIf(sb, c1, true);
            renderAtlasRegionIf(sb, c3, true);
            renderAtlasRegionIf(sb, c4, true);
            break;
        }
        renderAtlasRegionIf(sb, s1, renderS1);
        renderAtlasRegionIf(sb, s2, renderS2);
        renderAtlasRegionIf(sb, s3, renderS3);
        renderAtlasRegionIf(sb, s4, renderS4);
        renderAtlasRegionIf(sb, s5, renderS5);
        sb.setColor(overlayColor);
        sb.setBlendFunction(770, 1);
        renderAtlasRegionIf(sb, bg1, true);
        renderAtlasRegionIf(sb, bg2, renderAltBg);
        renderAtlasRegionIf(sb, mg2, renderM2);
        renderAtlasRegionIf(sb, mg1, renderM1);
        renderAtlasRegionIf(sb, mg3, renderM3);
        renderAtlasRegionIf(sb, mg4, renderM4);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.scenes.TheBeyondScene.ColumnConfig[columnConfig.ordinal()])
        {
        case 2: // '\002'
            renderAtlasRegionIf(sb, c1, true);
            renderAtlasRegionIf(sb, c4, true);
            break;

        case 3: // '\003'
            renderAtlasRegionIf(sb, c1, true);
            renderAtlasRegionIf(sb, c2, true);
            renderAtlasRegionIf(sb, c4, true);
            break;

        case 4: // '\004'
            renderAtlasRegionIf(sb, c1, true);
            renderAtlasRegionIf(sb, c3, true);
            renderAtlasRegionIf(sb, c4, true);
            break;
        }
        renderAtlasRegionIf(sb, s1, renderS1);
        renderAtlasRegionIf(sb, s2, renderS2);
        renderAtlasRegionIf(sb, s3, renderS3);
        renderAtlasRegionIf(sb, s4, renderS4);
        renderAtlasRegionIf(sb, s5, renderS5);
        sb.setBlendFunction(770, 771);
        overlayColor.a = 1.0F;
        sb.setColor(overlayColor);
        overlayColor.a = prevAlpha;
        renderAtlasRegionIf(sb, i1, renderI1);
        renderAtlasRegionIf(sb, i2, renderI2);
        renderAtlasRegionIf(sb, i3, renderI3);
        renderAtlasRegionIf(sb, i4, renderI4);
        renderAtlasRegionIf(sb, i5, renderI5);
        tmpColor.r = (1.0F + overlayColor.r) / 2.0F;
        tmpColor.g = (1.0F + overlayColor.g) / 2.0F;
        tmpColor.b = (1.0F + overlayColor.b) / 2.0F;
        sb.setColor(tmpColor);
        renderAtlasRegionIf(sb, MathUtils.cosDeg(((System.currentTimeMillis() + 180L) / 180L) % 360L) * 40F * Settings.xScale, MathUtils.cosDeg(((System.currentTimeMillis() + 500L) / 72L) % 360L) * 20F * Settings.scale, MathUtils.cosDeg(((System.currentTimeMillis() + 180L) / 180L) % 360L), f1, renderF1);
        renderAtlasRegionIf(sb, MathUtils.cosDeg(((System.currentTimeMillis() + 0x1664bL) / 72L) % 360L) * 20F, 0.0F, (System.currentTimeMillis() / 120L) % 360L, f2, renderF2);
        renderAtlasRegionIf(sb, -80F * Settings.scale, MathUtils.cosDeg(System.currentTimeMillis() + 73L) * 10F - 90F * Settings.scale, (float)((System.currentTimeMillis() / 1000L) % 360L) * 2.0F, f3, renderF3);
        renderAtlasRegionIf(sb, 0.0F, MathUtils.cosDeg(((System.currentTimeMillis() + 4442L) / 20L) % 360L) * 30F * Settings.scale, MathUtils.cosDeg(((System.currentTimeMillis() + 4442L) / 10L) % 360L) * 20F, f4, renderF4);
        renderAtlasRegionIf(sb, 0.0F, MathUtils.cosDeg((System.currentTimeMillis() / 48L) % 360L) * 20F, 0.0F, f5, renderF5);
    }

    public void renderCombatRoomFg(SpriteBatch sb)
    {
        sb.setColor(tmpColor);
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

    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bg1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion bg2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion floor;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion ceiling;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion fg;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg3;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg4;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion c1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion c2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion c3;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion c4;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion f1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion f2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion f3;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion f4;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion f5;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion i1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion i2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion i3;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion i4;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion i5;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion s1;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion s2;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion s3;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion s4;
    private final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion s5;
    private boolean renderAltBg;
    private boolean renderM1;
    private boolean renderM2;
    private boolean renderM3;
    private boolean renderM4;
    private boolean renderF1;
    private boolean renderF2;
    private boolean renderF3;
    private boolean renderF4;
    private boolean renderF5;
    private boolean renderIce;
    private boolean renderI1;
    private boolean renderI2;
    private boolean renderI3;
    private boolean renderI4;
    private boolean renderI5;
    private boolean renderStalactites;
    private boolean renderS1;
    private boolean renderS2;
    private boolean renderS3;
    private boolean renderS4;
    private boolean renderS5;
    private ColumnConfig columnConfig;
    private Color overlayColor;
    private Color tmpColor;
    private Color whiteColor;
}
