// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DropdownMenu.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            DropdownMenuListener, OptionsPanel

public class DropdownMenu
    implements ScrollBarListener
{
    private class DropdownRow
    {

        private void position(float x, float y)
        {
            hb.x = x;
            hb.y = y - hb.height;
            hb.cX = hb.x + hb.width / 2.0F;
            hb.cY = hb.y + hb.height / 2.0F;
        }

        private boolean update()
        {
            hb.update();
            if(hb.hovered && InputHelper.justClickedLeft)
                hb.clickStarted = true;
            if(hb.clicked || hb.hovered && CInputActionSet.select.isJustPressed())
            {
                hb.clicked = false;
                return true;
            } else
            {
                return false;
            }
        }

        private void renderRow(SpriteBatch sb)
        {
            hb.render(sb);
            Color renderTextColor = textColor;
            if(hb.hovered)
                renderTextColor = Settings.GREEN_TEXT_COLOR;
            else
            if(isSelected)
                renderTextColor = Settings.GOLD_COLOR;
            float textX = hb.x + Settings.scale * 10F;
            float a = approximateRowHeight();
            float b = textFont.getCapHeight();
            float yOffset = (a - b) / 2.0F;
            float textY = hb.y - yOffset;
            FontHelper.renderSmartText(sb, textFont, text, textX, textY + hb.height, 3.402823E+038F, 3.402823E+038F, renderTextColor);
        }

        String text;
        Hitbox hb;
        boolean isSelected;
        int index;
        final DropdownMenu this$0;




        DropdownRow(String text, float width, float height, int index)
        {
            this$0 = DropdownMenu.this;
            super();
            isSelected = false;
            this.text = text;
            hb = new Hitbox(width, height);
            this.index = index;
        }
    }


    public DropdownMenu(DropdownMenuListener listener, String options[], BitmapFont font, Color textColor)
    {
        this(listener, new ArrayList(Arrays.asList(options)), font, textColor);
    }

    public DropdownMenu(DropdownMenuListener listener, ArrayList options, BitmapFont font, Color textColor)
    {
        this(listener, options, font, textColor, 15);
    }

    public DropdownMenu(DropdownMenuListener listener, String options[], BitmapFont font, Color textColor, int maxRows)
    {
        this(listener, new ArrayList(Arrays.asList(options)), font, textColor, maxRows);
    }

    public DropdownMenu(DropdownMenuListener listener, ArrayList options, BitmapFont font, Color textColor, int maxRows)
    {
        isOpen = false;
        topVisibleRowIndex = 0;
        shouldSnapCursorToSelectedIndex = false;
        rowsHaveBeenPositioned = false;
        cachedMaxWidth = -1F;
        this.listener = listener;
        textFont = font;
        this.textColor = textColor;
        this.maxRows = maxRows;
        rows = new ArrayList(options.size());
        scrollBar = new ScrollBar(this);
        float width = approximateOverallWidth(options) - scrollBar.width();
        if(options.size() > 0)
        {
            selectionBox = new DropdownRow((String)options.get(0), cachedMaxWidth, approximateRowHeight(), 0);
            selectionBox.isSelected = true;
            String option;
            for(Iterator iterator = options.iterator(); iterator.hasNext(); rows.add(new DropdownRow(option, width, approximateRowHeight(), rows.size())))
                option = (String)iterator.next();

            ((DropdownRow)rows.get(0)).isSelected = true;
        }
    }

    public void updateResolutionLabels(int mode)
    {
        rows.clear();
        ArrayList options = CardCrawlGame.mainMenuScreen.optionPanel.getResolutionLabels(mode);
        float width = approximateOverallWidth(options) - scrollBar.width();
        if(options.size() > 0)
        {
            selectionBox = new DropdownRow((String)options.get(0), cachedMaxWidth, approximateRowHeight(), 0);
            selectionBox.isSelected = true;
            String option;
            for(Iterator iterator = options.iterator(); iterator.hasNext(); rows.add(new DropdownRow(option, width, approximateRowHeight(), rows.size())))
                option = (String)iterator.next();

            ((DropdownRow)rows.get(rows.size() - 1)).isSelected = true;
            CardCrawlGame.mainMenuScreen.optionPanel.resetResolutionDropdownSelection();
        }
    }

    boolean shouldShowSlider()
    {
        return rows.size() > maxRows;
    }

    public Hitbox getHitbox()
    {
        return selectionBox.hb;
    }

    private int visibleRowCount()
    {
        return Math.min(rows.size(), maxRows);
    }

    void layoutRowsBelow(float originX, float originY)
    {
        selectionBox.position(originX, yPositionForRowBelow(originY, 0, 0.0F));
        for(int i = 0; i < visibleRowCount(); i++)
            ((DropdownRow)rows.get(topVisibleRowIndex + i)).position(originX, yPositionForRowBelow(originY, i + 1, 0.0F));

        if(shouldShowSlider())
        {
            float top = yPositionForRowBelow(originY, 1, 0.0F);
            float bottom = yPositionForRowBelow(originY, visibleRowCount() + 1, 0.0F);
            float right = (originX + cachedMaxWidth) - SCROLLBAR_RIGHT_PADDING;
            scrollBar.positionWithinOnRight(right, top, bottom);
        }
        rowsHaveBeenPositioned = true;
    }

    public float approximateRowHeight()
    {
        float extraSpace = Math.min(Math.max(textFont.getCapHeight(), 15F) * Settings.scale, 15F);
        return textFont.getCapHeight() + extraSpace;
    }

    private float approximateWidthForText(String option)
    {
        return FontHelper.getSmartWidth(textFont, option, 3.402823E+038F, 3.402823E+038F);
    }

    public float approximateOverallWidth()
    {
        if(cachedMaxWidth > 0.0F)
            return cachedMaxWidth;
        ArrayList options = new ArrayList();
        DropdownRow row;
        for(Iterator iterator = rows.iterator(); iterator.hasNext(); options.add(row.text))
            row = (DropdownRow)iterator.next();

        return approximateOverallWidth(options);
    }

    private float approximateOverallWidth(ArrayList options)
    {
        if(cachedMaxWidth > 0.0F)
            return cachedMaxWidth;
        for(Iterator iterator = options.iterator(); iterator.hasNext();)
        {
            String option = (String)iterator.next();
            cachedMaxWidth = Math.max(cachedMaxWidth, approximateWidthForText(option) + ICON_WIDTH);
        }

        return cachedMaxWidth;
    }

    private float yPositionForRowBelow(float originY, int rowIndex, float scrollAmount)
    {
        float rowHeight = approximateRowHeight();
        float extraHeight = rowIndex <= 0 ? 0.0F : PADDING_FROM_SOURCE_HITBOX;
        return originY - rowHeight * (float)rowIndex - extraHeight;
    }

    public void setSelectedIndex(int i)
    {
        if(selectionBox.index == i)
            return;
        if(i < rows.size())
            changeSelectionToRow((DropdownRow)rows.get(i));
        topVisibleRowIndex = Math.min(i, rows.size() - visibleRowCount());
        float scrollPercent = scrollPercentForTopVisibleRowIndex(topVisibleRowIndex);
        scrollBar.parentScrolledToPercent(scrollPercent);
    }

    public float scrollPercentForTopVisibleRowIndex(int topIndex)
    {
        int maxRow = rows.size() - visibleRowCount();
        return (float)topIndex / (float)maxRow;
    }

    public int topVisibleRowIndexForScrollPercent(float percent)
    {
        int maxRow = rows.size() - visibleRowCount();
        return (int)((float)maxRow * percent);
    }

    public int getSelectedIndex()
    {
        return selectionBox.index;
    }

    private void changeSelectionToRow(DropdownRow newSelection)
    {
        selectionBox.text = newSelection.text;
        selectionBox.index = newSelection.index;
        for(Iterator iterator = rows.iterator(); iterator.hasNext();)
        {
            DropdownRow row = (DropdownRow)iterator.next();
            row.isSelected = row == newSelection;
        }

        if(listener != null)
            listener.changedSelectionTo(this, newSelection.index, newSelection.text);
    }

    public void update()
    {
        if(rows.size() == 0)
            return;
        if(isOpen)
        {
            updateNonMouseInput();
            for(int i = 0; i < visibleRowCount(); i++)
            {
                DropdownRow row = (DropdownRow)rows.get(i + topVisibleRowIndex);
                if(row.update())
                {
                    changeSelectionToRow(row);
                    isOpen = false;
                    CardCrawlGame.sound.play("UI_CLICK_2");
                }
            }

            if(InputHelper.scrolledDown)
            {
                topVisibleRowIndex = Integer.min(topVisibleRowIndex + 1, rows.size() - visibleRowCount());
                scrollBar.parentScrolledToPercent(scrollPercentForTopVisibleRowIndex(topVisibleRowIndex));
            } else
            if(InputHelper.scrolledUp)
            {
                topVisibleRowIndex = Integer.max(0, topVisibleRowIndex - 1);
                scrollBar.parentScrolledToPercent(scrollPercentForTopVisibleRowIndex(topVisibleRowIndex));
            }
            boolean sliderClicked = false;
            if(shouldShowSlider())
                sliderClicked = scrollBar.update();
            boolean shouldCloseMenu = InputHelper.justReleasedClickLeft && !sliderClicked || CInputActionSet.cancel.isJustPressed();
            if(shouldCloseMenu)
            {
                if(Settings.isControllerMode)
                {
                    CInputActionSet.cancel.unpress();
                    CInputHelper.setCursor(getHitbox());
                }
                isOpen = false;
            }
        } else
        if(selectionBox.update())
        {
            isOpen = true;
            updateNonMouseStartPosition();
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
    }

    private boolean isUsingNonMouseControl()
    {
        return Settings.isControllerMode || InputActionSet.up.isJustPressed() || InputActionSet.down.isJustPressed();
    }

    private void updateNonMouseStartPosition()
    {
        if(!isUsingNonMouseControl())
        {
            return;
        } else
        {
            shouldSnapCursorToSelectedIndex = true;
            return;
        }
    }

    private void updateNonMouseInput()
    {
        if(!isUsingNonMouseControl())
            return;
        if(shouldSnapCursorToSelectedIndex && rowsHaveBeenPositioned)
        {
            CInputHelper.setCursor(((DropdownRow)rows.get(selectionBox.index)).hb);
            shouldSnapCursorToSelectedIndex = false;
            return;
        }
        int hoveredIndex = -1;
        int i = topVisibleRowIndex;
        do
        {
            if(i >= topVisibleRowIndex + visibleRowCount())
                break;
            if(((DropdownRow)rows.get(i)).hb.hovered)
            {
                hoveredIndex = i;
                break;
            }
            i++;
        } while(true);
        if(hoveredIndex < 0)
            return;
        boolean didInputUp = CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed() || InputActionSet.up.isJustPressed();
        boolean didInputDown = CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed() || InputActionSet.down.isJustPressed();
        boolean isMoving = didInputUp || didInputDown;
        if(!isMoving)
            return;
        int targetHoverIndexOffset = didInputDown ? 1 : -1;
        int targetHoverIndex = (hoveredIndex + targetHoverIndexOffset + rows.size()) % rows.size();
        boolean isAboveTheTop = targetHoverIndex < topVisibleRowIndex;
        boolean isBelowTheBottom = targetHoverIndex >= topVisibleRowIndex + visibleRowCount();
        if(isAboveTheTop)
        {
            if(didInputDown)
                CInputHelper.setCursor(((DropdownRow)rows.get(topVisibleRowIndex)).hb);
            topVisibleRowIndex = targetHoverIndex;
        } else
        if(isBelowTheBottom)
        {
            if(didInputUp)
            {
                CInputHelper.setCursor(((DropdownRow)rows.get((topVisibleRowIndex + visibleRowCount()) - 1)).hb);
                ((DropdownRow)rows.get(targetHoverIndex)).hb.hovered = true;
            }
            topVisibleRowIndex = (targetHoverIndex - visibleRowCount()) + 1;
        } else
        {
            CInputHelper.setCursor(((DropdownRow)rows.get(targetHoverIndex)).hb);
        }
        if(shouldShowSlider())
            scrollBar.parentScrolledToPercent(scrollPercentForTopVisibleRowIndex(topVisibleRowIndex));
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        if(rows.size() == 0)
            return;
        int rowCount = isOpen ? visibleRowCount() : 0;
        float heightOfSelector = approximateRowHeight() * 1.0F - BOX_EDGE_H * 2.5F;
        layoutRowsBelow(x, y);
        float topY = yPositionForRowBelow(y, 0, 0.0F);
        float bottomY = yPositionForRowBelow(y, rowCount + 1, 0.0F);
        if(isOpen)
            renderBorder(sb, x, bottomY, cachedMaxWidth, topY - bottomY);
        renderBorderFromTop(sb, x, y, cachedMaxWidth, heightOfSelector);
        selectionBox.renderRow(sb);
        if(isOpen)
        {
            for(int i = 0; i < visibleRowCount(); i++)
                ((DropdownRow)rows.get(i + topVisibleRowIndex)).renderRow(sb);

            if(shouldShowSlider())
                scrollBar.render(sb);
        }
        float ARROW_ICON_W = 30F * Settings.scale;
        float ARROW_ICON_H = 30F * Settings.scale;
        float arrowIconX = (x + cachedMaxWidth) - ARROW_ICON_W - Settings.scale * 10F;
        float arrowIconY = y - ARROW_ICON_H;
        Texture dropdownArrowIcon = isOpen ? ImageMaster.OPTION_TOGGLE_ON : ImageMaster.FILTER_ARROW;
        sb.draw(dropdownArrowIcon, arrowIconX, arrowIconY, ARROW_ICON_W, ARROW_ICON_H);
    }

    private void renderBorder(SpriteBatch sb, float x, float bottom, float width, float height)
    {
        float border = Settings.scale * 10F;
        float BOX_W = width + 2.0F * border;
        float FRAME_X = x - border;
        float rowHeight = approximateRowHeight();
        sb.setColor(Color.WHITE);
        float bottomY = bottom - border;
        sb.draw(ImageMaster.KEYWORD_BOT, FRAME_X, bottomY, BOX_W, rowHeight);
        float middleHeight = height - 2.0F * rowHeight - border;
        sb.draw(ImageMaster.KEYWORD_BODY, FRAME_X, bottomY + rowHeight, BOX_W, middleHeight);
        sb.draw(ImageMaster.KEYWORD_TOP, FRAME_X, bottom + middleHeight + border, BOX_W, rowHeight);
    }

    private void renderBorderFromTop(SpriteBatch sb, float x, float top, float width, float height)
    {
        float border = Settings.scale * 10F;
        float BORDER_TOP_Y = (top - BOX_EDGE_H) + border;
        float BOX_W = width + 2.0F * border;
        float FRAME_X = x - border;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.KEYWORD_TOP, FRAME_X, BORDER_TOP_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, FRAME_X, BORDER_TOP_Y - height - BOX_EDGE_H, BOX_W, height + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, FRAME_X, BORDER_TOP_Y - height - BOX_BODY_H, BOX_W, BOX_EDGE_H);
    }

    public void scrolledUsingBar(float newPercent)
    {
        topVisibleRowIndex = topVisibleRowIndexForScrollPercent(newPercent);
        scrollBar.parentScrolledToPercent(newPercent);
    }

    private DropdownMenuListener listener;
    public ArrayList rows;
    public boolean isOpen;
    public int topVisibleRowIndex;
    private int maxRows;
    private Color textColor;
    private BitmapFont textFont;
    private DropdownRow selectionBox;
    private ScrollBar scrollBar;
    private static final int DEFAULT_MAX_ROWS = 15;
    private static final float PADDING_FROM_SOURCE_HITBOX;
    private static final float SCROLLBAR_RIGHT_PADDING;
    private static final float BOX_EDGE_H;
    private static final float BOX_BODY_H;
    private boolean shouldSnapCursorToSelectedIndex;
    private boolean rowsHaveBeenPositioned;
    private float cachedMaxWidth;
    private static final float ICON_WIDTH;

    static 
    {
        PADDING_FROM_SOURCE_HITBOX = 10F * Settings.scale;
        SCROLLBAR_RIGHT_PADDING = 2.0F * Settings.scale;
        BOX_EDGE_H = 32F * Settings.scale;
        BOX_BODY_H = 64F * Settings.scale;
        ICON_WIDTH = 68F * Settings.scale;
    }


}
