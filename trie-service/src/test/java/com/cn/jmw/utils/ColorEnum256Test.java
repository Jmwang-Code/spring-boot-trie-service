package com.cn.jmw.utils;

import com.cn.jmw.color.ColorEnum256;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorEnum256Test {

    @Test
    public void testFromRgb_validColor() {
        ColorEnum256 color = ColorEnum256.fromRgb(255, 0, 0);
        assertEquals(ColorEnum256.RED, color);
        System.out.println(color.getColoredString("Hello World"));
    }

    @Test
    public void testFromRgb_invalidColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println(ColorEnum256.fromRgb(300, 200, 100).getColoredString("Hello World"));
        });
    }

    @Test
    public void testGetRed() {
        ColorEnum256 color = ColorEnum256.BLUE;
        assertEquals(0, color.getRed());
    }

    @Test
    public void testGetGreen() {
        ColorEnum256 color = ColorEnum256.GREEN;
        assertEquals(128, color.getGreen());
    }

    @Test
    public void testGetBlue() {
        ColorEnum256 color = ColorEnum256.WHITE;
        assertEquals(255, color.getBlue());
    }
}