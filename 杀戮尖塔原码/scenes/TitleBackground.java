// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TitleBackground.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.TitleDustEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.scenes:
//            TitleCloud

public class TitleBackground
{

    public TitleBackground()
    {
        topClouds = new ArrayList();
        midClouds = new ArrayList();
        slider = 1.0F;
        timer = 1.0F;
        activated = false;
        dust = new ArrayList();
        dust2 = new ArrayList();
        flame = new ArrayList();
        dustTimer = 2.0F;
        flameTimer = 0.2F;
        logoAlpha = 1.0F;
        promptTextColor = Settings.CREAM_COLOR.cpy();
        promptTextColor.a = 0.0F;
        if(atlas == null)
            atlas = new TextureAtlas(Gdx.files.internal("title/title.atlas"));
        sky = atlas.findRegion("jpg/sky");
        mg3Bot = atlas.findRegion("mg3Bot");
        mg3Top = atlas.findRegion("mg3Top");
        topGlow = atlas.findRegion("mg3TopGlow1");
        topGlow2 = atlas.findRegion("mg3TopGlow2");
        botGlow = atlas.findRegion("mg3BotGlow");
        for(int i = 1; i < 7; i++)
            topClouds.add(new TitleCloud(atlas.findRegion((new StringBuilder()).append("topCloud").append(Integer.toString(i)).toString()), MathUtils.random(10F, 50F) * Settings.scale, MathUtils.random(-1920F, 1920F) * Settings.scale));

        for(int i = 1; i < 13; i++)
            midClouds.add(new TitleCloud(atlas.findRegion((new StringBuilder()).append("midCloud").append(Integer.toString(i)).toString()), MathUtils.random(-50F, -10F) * Settings.scale, MathUtils.random(-1920F, 1920F) * Settings.scale));

        if(titleLogoImg == null)
        {
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[] = new int[com.megacrit.cardcrawl.core.Settings.GameLanguage.values().length];

            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[Settings.language.ordinal()])
            {
            default:
                titleLogoImg = ImageMaster.loadImage("images/ui/title_logo/eng.png");
                break;
            }
            W = titleLogoImg.getWidth();
            H = titleLogoImg.getHeight();
        }
    }

    public void slideDownInstantly()
    {
        activated = true;
        timer = 0.0F;
        slider = 0.0F;
    }

    public void update()
    {
        if(CardCrawlGame.mainMenuScreen.darken)
            logoAlpha = MathHelper.slowColorLerpSnap(logoAlpha, 0.25F);
        else
            logoAlpha = MathHelper.slowColorLerpSnap(logoAlpha, 1.0F);
        if(InputHelper.justClickedLeft && !activated)
        {
            activated = true;
            timer = 1.0F;
        }
        if(activated && timer != 0.0F)
        {
            timer -= Gdx.graphics.getDeltaTime();
            if(timer < 0.0F)
                timer = 0.0F;
            if(timer < 1.0F)
                slider = Interpolation.pow4In.apply(0.0F, 1.0F, timer);
        }
        TitleCloud c;
        for(Iterator iterator = topClouds.iterator(); iterator.hasNext(); c.update())
            c = (TitleCloud)iterator.next();

        TitleCloud c;
        for(Iterator iterator1 = midClouds.iterator(); iterator1.hasNext(); c.update())
            c = (TitleCloud)iterator1.next();

        if(!Settings.DISABLE_EFFECTS)
            updateDust();
        if(!CardCrawlGame.mainMenuScreen.isFadingOut)
            updateFlame();
        else
            flame.clear();
    }

    private void updateFlame()
    {
        flameTimer -= Gdx.graphics.getDeltaTime();
        if(flameTimer < 0.0F)
        {
            flameTimer = 0.05F;
            flame.add(new LogoFlameEffect());
        }
        Iterator e = flame.iterator();
        do
        {
            if(!e.hasNext())
                break;
            LogoFlameEffect effect = (LogoFlameEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
    }

    private void updateDust()
    {
        dustTimer -= Gdx.graphics.getDeltaTime();
        if(dustTimer < 0.0F)
        {
            dustTimer = 0.05F;
            dust.add(new TitleDustEffect());
        }
        Iterator e = dust.iterator();
        do
        {
            if(!e.hasNext())
                break;
            TitleDustEffect effect = (TitleDustEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
    }

    public void render(SpriteBatch sb)
    {
        renderRegion(sb, sky, 0.0F, -100F * Settings.scale * slider);
        renderRegion(sb, mg3Bot, 0.0F, MathUtils.round((-45F * Settings.scale * slider + (float)Settings.HEIGHT) - 2219F * Settings.scale));
        renderRegion(sb, mg3Top, 0.0F, MathUtils.round((-45F * Settings.scale * slider + (float)Settings.HEIGHT) - 1080F * Settings.scale));
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 0.2F, 0.1F, 0.1F + (MathUtils.cosDeg((System.currentTimeMillis() / 16L) % 360L) + 1.25F) / 5F));
        renderRegion(sb, botGlow, 0.0F, MathUtils.round((-45F * Settings.scale * slider + (float)Settings.HEIGHT) - 2220F * Settings.scale));
        renderRegion(sb, topGlow, 0.0F, (-45F * Settings.scale * slider + (float)Settings.HEIGHT) - 1080F * Settings.scale);
        renderRegion(sb, topGlow2, 0.0F, (-45F * Settings.scale * slider + (float)Settings.HEIGHT) - 1080F * Settings.scale);
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 771);
        TitleDustEffect e;
        for(Iterator iterator = dust2.iterator(); iterator.hasNext(); e.render(sb, 0.0F, (-50F * Settings.scale * slider + (float)Settings.HEIGHT) - 1300F * Settings.scale))
            e = (TitleDustEffect)iterator.next();

        TitleDustEffect e;
        for(Iterator iterator1 = dust.iterator(); iterator1.hasNext(); e.render(sb, 0.0F, (-50F * Settings.scale * slider + (float)Settings.HEIGHT) - 1300F * Settings.scale))
            e = (TitleDustEffect)iterator1.next();

        sb.setColor(Color.WHITE);
        TitleCloud c;
        for(Iterator iterator2 = midClouds.iterator(); iterator2.hasNext(); c.render(sb, slider))
            c = (TitleCloud)iterator2.next();

        TitleCloud c;
        for(Iterator iterator3 = topClouds.iterator(); iterator3.hasNext(); c.render(sb, slider))
            c = (TitleCloud)iterator3.next();

        sb.setColor(new Color(1.0F, 1.0F, 1.0F, logoAlpha));
        sb.draw(titleLogoImg, 930F * Settings.xScale - (float)W / 2.0F, ((-70F * Settings.scale * slider + (float)Settings.HEIGHT / 2.0F) - (float)H / 2.0F) + 14F * Settings.scale, (float)W / 2.0F, (float)H / 2.0F, W, H, Settings.scale, Settings.scale, 0.0F, 0, 0, W, H, false, false);
        sb.setBlendFunction(770, 1);
        Iterator iterator4 = flame.iterator();
        do
        {
            if(!iterator4.hasNext())
                break;
            LogoFlameEffect e = (LogoFlameEffect)iterator4.next();
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[Settings.language.ordinal()])
            {
            default:
                e.render(sb, (float)Settings.WIDTH / 2.0F, (-70F * Settings.scale * slider + (float)Settings.HEIGHT / 2.0F) - 260F * Settings.scale);
                break;
            }
        } while(true);
        sb.setBlendFunction(770, 771);
    }

    private void renderRegion(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, float x, float y)
    {
        if(Settings.isLetterbox)
            sb.draw(region.getTexture(), region.offsetX * Settings.scale + x, region.offsetY * Settings.scale + y, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.xScale, Settings.xScale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
        else
            sb.draw(region.getTexture(), region.offsetX * Settings.scale + x, region.offsetY * Settings.scale + y, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.scale, Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    protected static TextureAtlas atlas;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg3Bot;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion mg3Top;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion topGlow;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion topGlow2;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion botGlow;
    protected final com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion sky;
    private static Texture titleLogoImg = null;
    private static int W = 0;
    private static int H = 0;
    protected ArrayList topClouds;
    protected ArrayList midClouds;
    public float slider;
    private float timer;
    public boolean activated;
    private ArrayList dust;
    private ArrayList dust2;
    private ArrayList flame;
    private float dustTimer;
    private float flameTimer;
    private static final float FLAME_INTERVAL = 0.05F;
    private float logoAlpha;
    private Color promptTextColor;

}
