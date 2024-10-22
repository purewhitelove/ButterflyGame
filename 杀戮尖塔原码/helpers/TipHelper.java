// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            PowerTip, FontHelper, GameDictionary, ImageMaster

public class TipHelper
{

    public TipHelper()
    {
    }

    public static void render(SpriteBatch sb)
    {
        if(!Settings.hidePopupDetails && renderedTipThisFrame)
        {
            if(AbstractDungeon.player != null && (AbstractDungeon.player.inSingleTargetMode || AbstractDungeon.player.isDraggingCard && !Settings.isTouchScreen))
            {
                HEADER = null;
                BODY = null;
                card = null;
                renderedTipThisFrame = false;
                return;
            }
            if(Settings.isTouchScreen && AbstractDungeon.player != null && AbstractDungeon.player.isHoveringDropZone)
            {
                HEADER = null;
                BODY = null;
                card = null;
                renderedTipThisFrame = false;
                return;
            }
            if(isCard && card != null)
            {
                if(card.current_x > (float)Settings.WIDTH * 0.75F)
                    renderKeywords(card.current_x - AbstractCard.IMG_WIDTH / 2.0F - CARD_TIP_PAD - BOX_W, (card.current_y + AbstractCard.IMG_HEIGHT / 2.0F) - BOX_EDGE_H, sb, KEYWORDS);
                else
                    renderKeywords(card.current_x + AbstractCard.IMG_WIDTH / 2.0F + CARD_TIP_PAD, (card.current_y + AbstractCard.IMG_HEIGHT / 2.0F) - BOX_EDGE_H, sb, KEYWORDS);
                card = null;
                isCard = false;
            } else
            if(HEADER != null)
            {
                textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, BODY, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7F * Settings.scale;
                renderTipBox(drawX, drawY, sb, HEADER, BODY);
                HEADER = null;
            } else
            {
                renderPowerTips(drawX, drawY, sb, POWER_TIPS);
            }
            renderedTipThisFrame = false;
        }
    }

    public static void renderGenericTip(float x, float y, String header, String body)
    {
        if(!Settings.hidePopupDetails)
            if(!renderedTipThisFrame)
            {
                renderedTipThisFrame = true;
                HEADER = header;
                BODY = body;
                drawX = x;
                drawY = y;
            } else
            if(HEADER == null && !KEYWORDS.isEmpty())
                logger.info((new StringBuilder()).append("! ").append((String)KEYWORDS.get(0)).toString());
    }

    public static void queuePowerTips(float x, float y, ArrayList powerTips)
    {
        if(!renderedTipThisFrame)
        {
            renderedTipThisFrame = true;
            drawX = x;
            drawY = y;
            POWER_TIPS = powerTips;
        } else
        if(HEADER == null && !KEYWORDS.isEmpty())
            logger.info((new StringBuilder()).append("! ").append((String)KEYWORDS.get(0)).toString());
    }

    public static void renderTipForCard(AbstractCard c, SpriteBatch sb, ArrayList keywords)
    {
        if(!renderedTipThisFrame)
        {
            isCard = true;
            card = c;
            convertToReadable(keywords);
            KEYWORDS = keywords;
            renderedTipThisFrame = true;
        }
    }

    private static void convertToReadable(ArrayList keywords)
    {
        ArrayList add = new ArrayList();
        keywords.addAll(add);
    }

    private static void renderPowerTips(float x, float y, SpriteBatch sb, ArrayList powerTips)
    {
        float originalY = y;
        boolean offsetLeft = false;
        if(x > (float)Settings.WIDTH / 2.0F)
            offsetLeft = true;
        float offset = 0.0F;
        for(Iterator iterator = powerTips.iterator(); iterator.hasNext();)
        {
            PowerTip tip = (PowerTip)iterator.next();
            textHeight = getPowerTipHeight(tip);
            float offsetChange = textHeight + BOX_EDGE_H * 3.15F;
            if(offset + offsetChange >= (float)Settings.HEIGHT * 0.7F)
            {
                y = originalY;
                offset = 0.0F;
                if(offsetLeft)
                    x -= 324F * Settings.scale;
                else
                    x += 324F * Settings.scale;
            }
            renderTipBox(x, y, sb, tip.header, tip.body);
            gl.setText(FontHelper.tipHeaderFont, tip.header, Color.WHITE, 0.0F, -1, false);
            if(tip.img != null)
            {
                sb.setColor(Color.WHITE);
                sb.draw(tip.img, x + TEXT_OFFSET_X + gl.width + 5F * Settings.scale, y - 10F * Settings.scale, 32F * Settings.scale, 32F * Settings.scale);
            } else
            if(tip.imgRegion != null)
            {
                sb.setColor(Color.WHITE);
                sb.draw(tip.imgRegion, (x + gl.width + POWER_ICON_OFFSET_X) - (float)tip.imgRegion.packedWidth / 2.0F, (y + 5F * Settings.scale) - (float)tip.imgRegion.packedHeight / 2.0F, (float)tip.imgRegion.packedWidth / 2.0F, (float)tip.imgRegion.packedHeight / 2.0F, tip.imgRegion.packedWidth, tip.imgRegion.packedHeight, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F);
            }
            y -= offsetChange;
            offset += offsetChange;
        }

    }

    private static float getPowerTipHeight(PowerTip powerTip)
    {
        return -FontHelper.getSmartHeight(FontHelper.tipBodyFont, powerTip.body, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7F * Settings.scale;
    }

    public static float calculateAdditionalOffset(ArrayList powerTips, float hBcY)
    {
        if(powerTips.isEmpty())
            return 0.0F;
        else
            return (1.0F - hBcY / (float)Settings.HEIGHT) * getTallestOffset(powerTips) - (getPowerTipHeight((PowerTip)powerTips.get(0)) + BOX_EDGE_H * 3.15F) / 2.0F;
    }

    public static float calculateToAvoidOffscreen(ArrayList powerTips, float hBcY)
    {
        if(powerTips.isEmpty())
            return 0.0F;
        else
            return Math.max(0.0F, getTallestOffset(powerTips) - hBcY);
    }

    private static float getTallestOffset(ArrayList powerTips)
    {
        float currentOffset = 0.0F;
        float maxOffset = 0.0F;
        Iterator iterator = powerTips.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            PowerTip p = (PowerTip)iterator.next();
            float offsetChange = getPowerTipHeight(p) + BOX_EDGE_H * 3.15F;
            if(currentOffset + offsetChange >= (float)Settings.HEIGHT * 0.7F)
                currentOffset = 0.0F;
            currentOffset += offsetChange;
            if(currentOffset > maxOffset)
                maxOffset = currentOffset;
        } while(true);
        return maxOffset;
    }

    private static void renderKeywords(float x, float y, SpriteBatch sb, ArrayList keywords)
    {
        if(keywords.size() >= 4)
            y += (float)(keywords.size() - 1) * 62F * Settings.scale;
        for(Iterator iterator = keywords.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            if(!GameDictionary.keywords.containsKey(s))
            {
                logger.info((new StringBuilder()).append("MISSING: ").append(s).append(" in Dictionary!").toString());
            } else
            {
                textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, (String)GameDictionary.keywords.get(s), BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7F * Settings.scale;
                renderBox(sb, s, x, y);
                y -= textHeight + BOX_EDGE_H * 3.15F;
            }
        }

    }

    private static void renderTipBox(float x, float y, SpriteBatch sb, String title, String description)
    {
        float h = textHeight;
        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }

    public static void renderTipEnergy(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, float x, float y)
    {
        sb.setColor(Color.WHITE);
        sb.draw(region.getTexture(), x + region.offsetX * Settings.scale, y + region.offsetY * Settings.scale, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.scale, Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private static void renderBox(SpriteBatch sb, String word, float x, float y)
    {
        float h = textHeight;
        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion currentOrb = AbstractDungeon.player == null ? AbstractCard.orb_red : AbstractDungeon.player.getOrb();
        if(word.equals("[R]") || word.equals("[G]") || word.equals("[B]") || word.equals("[W]") || word.equals("[E]"))
        {
            if(word.equals("[R]"))
                renderTipEnergy(sb, AbstractCard.orb_red, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            else
            if(word.equals("[G]"))
                renderTipEnergy(sb, AbstractCard.orb_green, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            else
            if(word.equals("[B]"))
                renderTipEnergy(sb, AbstractCard.orb_blue, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            else
            if(word.equals("[W]"))
                renderTipEnergy(sb, AbstractCard.orb_purple, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            else
            if(word.equals("[E]"))
                renderTipEnergy(sb, currentOrb, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, capitalize(TEXT[0]), x + TEXT_OFFSET_X * 2.5F, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        } else
        {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, capitalize(word), x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        }
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, (String)GameDictionary.keywords.get(word), x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }

    public static String capitalize(String input)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.length(); i++)
        {
            char tmp = input.charAt(i);
            if(i == 0)
                tmp = Character.toUpperCase(tmp);
            else
                tmp = Character.toLowerCase(tmp);
            sb.append(tmp);
        }

        return sb.toString();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/TipHelper.getName());
    private static boolean renderedTipThisFrame = false;
    private static boolean isCard = false;
    private static float drawX;
    private static float drawY;
    private static ArrayList KEYWORDS = new ArrayList();
    private static ArrayList POWER_TIPS = new ArrayList();
    private static String HEADER = null;
    private static String BODY = null;
    private static AbstractCard card;
    private static final Color BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
    private static final float CARD_TIP_PAD;
    private static final float SHADOW_DIST_Y;
    private static final float SHADOW_DIST_X;
    private static final float BOX_EDGE_H;
    private static final float BOX_BODY_H;
    private static final float BOX_W;
    private static GlyphLayout gl = new GlyphLayout();
    private static float textHeight;
    private static final float TEXT_OFFSET_X;
    private static final float HEADER_OFFSET_Y;
    private static final float ORB_OFFSET_Y;
    private static final float BODY_OFFSET_Y;
    private static final float BODY_TEXT_WIDTH;
    private static final float TIP_DESC_LINE_SPACING;
    private static final float POWER_ICON_OFFSET_X;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("TipHelper");
        TEXT = uiStrings.TEXT;
        CARD_TIP_PAD = 12F * Settings.scale;
        SHADOW_DIST_Y = 14F * Settings.scale;
        SHADOW_DIST_X = 9F * Settings.scale;
        BOX_EDGE_H = 32F * Settings.scale;
        BOX_BODY_H = 64F * Settings.scale;
        BOX_W = 320F * Settings.scale;
        TEXT_OFFSET_X = 22F * Settings.scale;
        HEADER_OFFSET_Y = 12F * Settings.scale;
        ORB_OFFSET_Y = -8F * Settings.scale;
        BODY_OFFSET_Y = -20F * Settings.scale;
        BODY_TEXT_WIDTH = 280F * Settings.scale;
        TIP_DESC_LINE_SPACING = 26F * Settings.scale;
        POWER_ICON_OFFSET_X = 40F * Settings.scale;
    }
}
