package org.zero.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import org.zero.zxing.zxing.DecodeActivity;

import java.util.EnumMap;
import java.util.Map;

/**
 * 扫描条形码、二维码，将字符串编码成二维码图片的工具类
 * Created by Zero on 2016/2/25.
 */
public class ZxingUtil {
    private static ZxingUtil zxingUtil;
    private ZxingUtil(){}

    public static ZxingUtil getInstance() {
        if (zxingUtil == null) {
            zxingUtil = new ZxingUtil();
        }
        return zxingUtil;
    }

    /**
     * 将文字编码成二维码图片
     */
    public Bitmap encodeAsBitmap(Context context, String contents) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType,Object> hints = null;
        String encoding = null;
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                encoding="UTF-8";
                break;
            }
        }
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }

        WindowManager manager = (WindowManager)  context.getSystemService(context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int displayWidth = displaySize.x;
        int displayHeight = displaySize.y;
        int smallerDimension = displayWidth < displayHeight ? displayWidth : displayHeight;
        smallerDimension = smallerDimension * 7 / 8;

        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 扫描条形码和二维码
     */
    public void decode(Context context) {
        context.startActivity(new Intent(context,DecodeActivity.class));
    }

}
