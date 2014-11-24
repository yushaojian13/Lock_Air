
package com.xmht.lock.utils;

import java.io.IOException;

import org.xml.sax.XMLReader;

import com.xmht.lock.debug.LOG;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;

public class XMTagHandler implements TagHandler {
    private Context context;
    private Typeface tf;
    public XMTagHandler(Context context) {
        this.context = context;
//        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");
        try {
            String[] pathsStrings = context.getAssets().list("fonts");
            for (String string : pathsStrings) {
                LOG.d("path:+"+string);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Helvetica-Light.ttf");
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.startsWith("fs")) {
            int size = 16;
            try {
                size = Integer.parseInt(tag.substring("fs".length()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            processSize(opening, output, size);
        } else if(tag.startsWith("ft-")){
            String fontPath = tag.substring("ft-".length());
            processFont(opening, output,fontPath);
            
        }else if (tag.equalsIgnoreCase("strike") || tag.equals("s")) {
            processStrike(opening, output);
        }
    }

    private void processFont(boolean opening, Editable output, String fontPath) {
//        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/"+fontPath);
//        if(tf==null){
//            LOG.d("tf is null");
//        }else{
//            LOG.d("tf is "+tf.getStyle());
//        }
        if(opening){
            start(output, new CustomTypefaceSpan("",tf));
        }else{
            end(output, CustomTypefaceSpan.class, new CustomTypefaceSpan("",tf));
        }
        
    }

    private void processSize(boolean opening, Editable output, int size) {
        if (opening) {
            start(output, new AbsoluteSizeSpan(size,true));
        } else {
            end(output, AbsoluteSizeSpan.class, new AbsoluteSizeSpan(size,true));
        }
    }

    private void processStrike(boolean opening, Editable output) {
        if (opening) {
            start(output, new StrikethroughSpan());
        } else {
            end(output, StrikethroughSpan.class, new StrikethroughSpan());
        }
    }

    private static void start(Editable text, Object mark) {
        int len = text.length();
        text.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK);
    }

    private static void end(Editable text, Class kind,
            Object repl) {
        int len = text.length();
        Object obj = getLast(text, kind);
        int where = text.getSpanStart(obj);

        text.removeSpan(obj);
        if (where != len) {
            text.setSpan(repl, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private static Object getLast(Spanned text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            return objs[objs.length - 1];
        }
    }

}
