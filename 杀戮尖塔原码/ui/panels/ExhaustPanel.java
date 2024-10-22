// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhaustPanel.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.vfx.ExhaustPileParticle;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.panels:
//            AbstractPanel

public class ExhaustPanel extends AbstractPanel
{

    public ExhaustPanel()
    {
        super((float)Settings.WIDTH - 70F * Settings.scale, 184F * Settings.scale, (float)Settings.WIDTH + 100F * Settings.scale, 184F * Settings.scale, 0.0F, 0.0F, null, false);
        hb = new Hitbox(0.0F, 0.0F, 100F * Settings.scale, 100F * Settings.scale);
    }

    public void updatePositions()
    {
        super.updatePositions();
        if(!isHidden && AbstractDungeon.player.exhaustPile.size() > 0)
        {
            hb.update();
            updateVfx();
        }
        if(hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.EXHAUST_VIEW || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.CARD_REWARD && AbstractDungeon.overlayMenu.combatPanelsShown))
        {
            AbstractDungeon.overlayMenu.hoveredTip = true;
            if(InputHelper.justClickedLeft)
                hb.clickStarted = true;
        }
        if((hb.clicked || InputActionSet.exhaustPile.isJustPressed() || CInputActionSet.pageRightViewExhaust.isJustPressed()) && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.EXHAUST_VIEW)
        {
            hb.clicked = false;
            hb.hovered = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
            return;
        }
        if((hb.clicked || InputActionSet.exhaustPile.isJustPressed() || CInputActionSet.pageRightViewExhaust.isJustPressed()) && AbstractDungeon.overlayMenu.combatPanelsShown && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.player.isDead && !AbstractDungeon.player.exhaustPile.isEmpty())
        {
            hb.clicked = false;
            hb.hovered = false;
            if(AbstractDungeon.isScreenUp)
            {
                if(AbstractDungeon.previousScreen == null)
                    AbstractDungeon.previousScreen = AbstractDungeon.screen;
            } else
            {
                AbstractDungeon.previousScreen = null;
            }
            openExhaustPile();
        }
    }

    private void openExhaustPile()
    {
        if(AbstractDungeon.player.hoveredCard != null)
            AbstractDungeon.player.releaseCard();
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.exhaustPileViewScreen.open();
        hb.hovered = false;
        InputHelper.justClickedLeft = false;
    }

    private void updateVfx()
    {
        energyVfxTimer -= Gdx.graphics.getDeltaTime();
        if(energyVfxTimer < 0.0F && !Settings.hideLowerElements)
        {
            AbstractDungeon.effectList.add(new ExhaustPileParticle(current_x, current_y));
            energyVfxTimer = 0.05F;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!AbstractDungeon.player.exhaustPile.isEmpty())
        {
            hb.move(current_x, current_y);
            String msg = Integer.toString(AbstractDungeon.player.exhaustPile.size());
            sb.setColor(Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            sb.draw(ImageMaster.DECK_COUNT_CIRCLE, current_x - COUNT_CIRCLE_W / 2.0F, current_y - COUNT_CIRCLE_W / 2.0F, COUNT_CIRCLE_W, COUNT_CIRCLE_W);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, current_x, current_y + 2.0F * Settings.scale, Settings.PURPLE_COLOR);
            if(Settings.isControllerMode)
            {
                sb.setColor(Color.WHITE);
                sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (current_x - 32F) + 30F * Settings.scale, current_y - 32F - 30F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 64, 64, false, false);
            }
            hb.render(sb);
            if(hb.hovered && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp)
                if(Settings.isConsoleBuild)
                    TipHelper.renderGenericTip(1550F * Settings.scale, 450F * Settings.scale, (new StringBuilder()).append(LABEL[0]).append(" (").append(InputActionSet.exhaustPile.getKeyString()).append(")").toString(), MSG[1]);
                else
                    TipHelper.renderGenericTip(1550F * Settings.scale, 450F * Settings.scale, (new StringBuilder()).append(LABEL[0]).append(" (").append(InputActionSet.exhaustPile.getKeyString()).append(")").toString(), MSG[0]);
        }
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    public static float fontScale = 1.0F;
    public static final float FONT_POP_SCALE = 2F;
    private static final float COUNT_CIRCLE_W;
    public static int totalCount = 0;
    private Hitbox hb;
    public static float energyVfxTimer = 0.0F;
    public static final float ENERGY_VFX_TIME = 2F;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Exhaust Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        COUNT_CIRCLE_W = 128F * Settings.scale;
    }
}
