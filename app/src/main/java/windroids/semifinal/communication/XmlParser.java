package windroids.semifinal.communication;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import windroids.semifinal.logic.pattern.KeyEvent;
import windroids.semifinal.logic.pattern.Pattern;
import windroids.semifinal.util.KeyCode;
import windroids.semifinal.util.KeyCodeParser;

public class XmlParser {

    public static List<Pattern> parseXmlFile(String xmlString) throws XmlPullParserException, IOException {
        return parser(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
    }

    private static List<Pattern> parser(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readTcs(parser);
        } finally {
            in.close();
        }
    }

    private static List<Pattern> readTcs(XmlPullParser parser) throws XmlPullParserException, IOException {
        List patternList = new ArrayList<Pattern>();

        parser.require(XmlPullParser.START_TAG, null, "tcs");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("pattern")) {
                patternList.add(readPattern(parser));
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, null, "tcs");
        return patternList;
    }

    private static Pattern readPattern(XmlPullParser parser) throws XmlPullParserException, IOException {
        List keyEventList = new ArrayList<KeyEvent>();
        parser.require(XmlPullParser.START_TAG, null, "pattern");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("key-down")) {
                keyEventList.add(readKeyEntry(parser, KeyEvent.Type.DOWN));
            } else if (name.equals("key-up")) {
                keyEventList.add(readKeyEntry(parser, KeyEvent.Type.UP));
            } else {
                skip(parser);
            }
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, null, "pattern");
        return new Pattern(keyEventList);
    }

    private static KeyEvent readKeyEntry(XmlPullParser parser, KeyEvent.Type type) throws IOException, XmlPullParserException {

        long time = Long.valueOf(parser.getAttributeValue(null, "posix-time"));

        String keyCodeRaw = parser.getAttributeValue(null, "code");
        KeyCode keyCode = KeyCodeParser.parse(keyCodeRaw);

        String posXRaw = parser.getAttributeValue(null, "relative-pos-x");
        double posX = Double.valueOf(posXRaw.substring(0, posXRaw.length() - 1));
        String posYRaw = parser.getAttributeValue(null, "relative-pos-y");
        double posY = Double.valueOf(posYRaw.substring(0, posXRaw.length() - 1));

        return new KeyEvent(type, time, keyCode, posX, posY);
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}