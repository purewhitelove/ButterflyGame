// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardPilePanel.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.ui.panels:
//            AbstractPanel

public class DiscardPilePanel extends AbstractPanel
{

    public DiscardPilePanel()
    {
        super((float)Settings.WIDTH - 256F * Settings.scale, 0.0F, Settings.WIDTH, -300F * Settings.scale, 8F * Settings.xScale, 0.0F, null, true);
        scale = 1.0F;
        glowColor = Color.WHITE.cpy();
        glowAlpha = 0.0F;
        gl = new GlyphLayout();
        bob = new BobEffect(1.0F);
        vfxAbove = new ArrayList();
        vfxBelow = new ArrayList();
        hb = new Hitbox((float)Settings.WIDTH - HITBOX_W, 0.0F, HITBOX_W, HITBOX_W);
        bannerHb = new Hitbox((float)Settings.WIDTH - HITBOX_W2, 0.0F, HITBOX_W2, HITBOX_W);
    }

    public void updatePositions()
    {
        super.updatePositions();
        bob.update();
        updateVfx();
        if(!isHidden)
        {
            hb.update();
            bannerHb.update();
            updatePop();
        }
        if(hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DISCARD_VIEW || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.CARD_REWARD && AbstractDungeon.overlayMenu.combatPanelsShown))
        {
            AbstractDungeon.overlayMenu.hoveredTip = true;
            if(InputHelper.justClickedLeft)
                hb.clickStarted = true;
        }
        if((hb.clicked || InputActionSet.discardPile.isJustPressed() || CInputActionSet.discardPile.isJustPressed()) && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DISCARD_VIEW)
        {
            hb.clicked = false;
            hb.hovered = false;
            bannerHb.hovered = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            if(AbstractDungeon.previousScreen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DISCARD_VIEW)
                AbstractDungeon.previousScreen = null;
            AbstractDungeon.closeCurrentScreen();
            return;
        }
        glowAlpha += Gdx.graphics.getDeltaTime() * 3F;
        if(glowAlpha < 0.0F)
            glowAlpha *= -1F;
        float tmp = MathUtils.cos(glowAlpha);
        if(tmp < 0.0F)
            glowColor.a = -tmp / 2.0F;
        else
            glowColor.a = tmp / 2.0F;
        if((hb.clicked || InputActionSet.discardPile.isJustPressed() || CInputActionSet.discardPile.isJustPressed()) && AbstractDungeon.overlayMenu.combatPanelsShown && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.player.isDead)
        {
            hb.clicked = false;
            hb.hovered = false;
            bannerHb.hovered = false;
            AbstractDungeon.dynamicBanner.hide();
            if(AbstractDungeon.player.hoveredCard != null)
                AbstractDungeon.player.releaseCard();
            if(AbstractDungeon.isScreenUp)
            {
                if(AbstractDungeon.previousScreen == null)
                    AbstractDungeon.previousScreen = AbstractDungeon.screen;
            } else
            {
                AbstractDungeon.previousScreen = null;
            }
            openDiscardPile();
        }
    }

    private void openDiscardPile()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.discardPile.size() != 0)
            AbstractDungeon.discardPileViewScreen.open();
        else
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3F, TEXT[0], true));
        hb.hovered = false;
        InputHelper.justClickedLeft = false;
    }

    private void updateVfx()
    {
        Iterator i = vfxAbove.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
        i = vfxBelow.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
        if(vfxAbove.size() < 9 && !Settings.DISABLE_EFFECTS)
            vfxAbove.add(new DiscardGlowEffect(true));
        if(vfxBelow.size() < 9 && !Settings.DISABLE_EFFECTS)
            vfxBelow.add(new DiscardGlowEffect(false));
    }

    private void updatePop()
    {
        scale = MathHelper.scaleLerpSnap(scale, Settings.scale);
    }

    public void pop()
    {
        scale = 1.75F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        renderButton(sb);
        String msg = Integer.toString(AbstractDungeon.player.discardPile.size());
        gl.setText(FontHelper.turnNumFont, msg);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DECK_COUNT_CIRCLE, current_x + COUNT_OFFSET_X, current_y + COUNT_OFFSET_Y, COUNT_CIRCLE_W, COUNT_CIRCLE_W);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.discardPile.getKeyImg(), (current_x - 32F) + 220F * Settings.scale, (current_y - 32F) + 40F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, current_x + COUNT_X, current_y + COUNT_Y);
        if(!isHidden)
        {
            hb.render(sb);
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DISCARD_VIEW)
                bannerHb.render(sb);
        }
        if(!isHidden && hb != null && hb.hovered && !AbstractDungeon.isScreenUp && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead())
        {
            if(Settings.isConsoleBuild)
                TipHelper.renderGenericTip(DECK_TIP_X, DECK_TIP_Y, (new StringBuilder()).append(LABEL[0]).append(" (").append(InputActionSet.discardPile.getKeyString()).append(")").toString(), MSG[1]);
            else
                TipHelper.renderGenericTip(DECK_TIP_X, DECK_TIP_Y, (new StringBuilder()).append(LABEL[0]).append(" (").append(InputActionSet.discardPile.getKeyString()).append(")").toString(), MSG[0]);
        } else
        {
            hb.hovered = false;
        }
    }

    private void renderButton(SpriteBatch sb)
    {
        if(hb.hovered || bannerHb.hovered && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DISCARD_VIEW)
            scale = 1.2F * Settings.scale;
        DiscardGlowEffect e;
        for(Iterator iterator = vfxBelow.iterator(); iterator.hasNext(); e.render(sb, current_x - 1664F * Settings.scale, current_y + bob.y * 0.5F))
            e = (DiscardGlowEffect)iterator.next();

        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DISCARD_BTN_BASE, current_x + DECK_X, current_y + DECK_Y + bob.y / 2.0F, 64F, 64F, 128F, 128F, scale, scale, 0.0F, 0, 0, 128, 128, false, false);
        DiscardGlowEffect e;
        for(Iterator iterator1 = vfxAbove.iterator(); iterator1.hasNext(); e.render(sb, current_x - 1664F * Settings.scale, current_y + bob.y * 0.5F))
            e = (DiscardGlowEffect)iterator1.next();

    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final int RAW_W = 128;
    private float scale;
    private static final float COUNT_CIRCLE_W;
    private static final float DECK_X;
    private static final float DECK_Y;
    private static final float COUNT_X;
    private static final float COUNT_Y;
    private static final float COUNT_OFFSET_X;
    private static final float COUNT_OFFSET_Y;
    private Color glowColor;
    private float glowAlpha;
    private GlyphLayout gl;
    private BobEffect bob;
    private ArrayList vfxAbove;
    private ArrayList vfxBelow;
    private static final float DECK_TIP_X;
    private static final float DECK_TIP_Y;
    private static final float HITBOX_W;
    private static final float HITBOX_W2;
    private Hitbox hb;
    private Hitbox bannerHb;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Discard Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPilePanel");
        TEXT = uiStrings.TEXT;
        COUNT_CIRCLE_W = 128F * Settings.scale;
        DECK_X = 180F * Settings.scale - 64F;
        DECK_Y = 70F * Settings.scale - 64F;
        COUNT_X = 134F * Settings.scale;
        COUNT_Y = 48F * Settings.scale;
        COUNT_OFFSET_X = 70F * Settings.scale;
        COUNT_OFFSET_Y = -18F * Settings.scale;
        DECK_TIP_X = 1550F * Settings.xScale;
        DECK_TIP_Y = 470F * Settings.scale;
        HITBOX_W = 120F * Settings.scale;
        HITBOX_W2 = 450F * Settings.xScale;
    }
}
