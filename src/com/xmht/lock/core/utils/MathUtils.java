
package com.xmht.lock.core.utils;

public class MathUtils {
    public static float computeY(float startX, float endX, float startY, float endY, float x) {
        if (x <= startX) {
            return startY;
        } else if (x >= endX) {
            return endY;
        }

        float k = (endY - startY) / (endX - startX);
        float y = startY + (x - startX) * k;
        return y;
    }
}
