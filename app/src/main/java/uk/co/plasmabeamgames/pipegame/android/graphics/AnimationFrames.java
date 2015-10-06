package uk.co.plasmabeamgames.pipegame.android.graphics;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.plasmabeamgames.pipegame.android.graphics.drawable.TileImageDrawable;

public class AnimationFrames {

    private List<TileImageDrawable> tileImageDrawables;
    private int angle;
    private float scaleX;

    public AnimationFrames(Resources resources, int resourceId, int angle, float scaleX) {
        this.angle = angle;
        this.scaleX = scaleX;
        parseXml(resources, resourceId);
    }

    private void parseXml(Resources resources, int resourceId) {
        XmlResourceParser resourceParser = resources.getXml(resourceId);
        try {
            int eventType = resourceParser.getEventType();
            String tagName;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    tagName = resourceParser.getName();
                    if (tagName.equals("frame-list")) {
                        if (tileImageDrawables == null) {
                            tileImageDrawables = new ArrayList<>();
                        }
                        parseFrameList(resourceParser, resources);
                    }
                }
                resourceParser.next();
                eventType = resourceParser.getEventType();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resourceParser != null) {
                resourceParser.close();
            }
        }
    }

    private void parseFrameList(XmlResourceParser resourceParser, Resources resources) throws XmlPullParserException, IOException {
        int eventType = resourceParser.getEventType();
        TypedArray attributes;
        while (!(eventType == XmlResourceParser.END_TAG && resourceParser.getName().equals("frame-list"))) {
            if (eventType == XmlResourceParser.START_TAG && resourceParser.getName().equals("item")) {
                attributes = resources.obtainAttributes(resourceParser, new int[]{android.R.attr.drawable});
                Drawable drawable = attributes.getDrawable(0);
                if (drawable != null && drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    TileImageDrawable tileImageDrawable = new TileImageDrawable(resources, bitmap);
                    tileImageDrawable.setAngle(angle);
                    tileImageDrawable.setScaleX(scaleX);
                    tileImageDrawables.add(tileImageDrawable);

                }
                attributes.recycle();
            }
            resourceParser.next();
            eventType = resourceParser.getEventType();
        }
    }

    public List<TileImageDrawable> getTileImageDrawables() {
        return tileImageDrawables;
    }

}
