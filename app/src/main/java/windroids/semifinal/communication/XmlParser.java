package windroids.semifinal.communication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import windroids.semifinal.logic.pattern.KeyCode;
import windroids.semifinal.logic.pattern.KeyEvent;
import windroids.semifinal.logic.pattern.Pattern;

public class XmlParser {
    //test
    public static List<Pattern> parseTestData(String xmlString) throws XmlPullParserException, IOException {
        List<Pattern> patternList = parserTestData(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
        return patternList;
    }

    public static Pattern parsePattern(String xmlString) throws XmlPullParserException, IOException {
        Pattern pattern = parserPattern(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
        return pattern;
    }

    private static List<Pattern> parserTestData(InputStream in) throws XmlPullParserException, IOException {
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

    private static Pattern parserPattern(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readPattern(parser);
        } finally {
            in.close();
        }
    }

    private static List<Pattern> readTcs(XmlPullParser parser) throws XmlPullParserException, IOException {
        List patternList = new ArrayList<Pattern>();

        parser.require(XmlPullParser.START_TAG, null, "training");
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
        parser.require(XmlPullParser.END_TAG, null, "training");
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
		switch (keyCodeRaw) {
			case "SHIFT":
				keyCodeRaw = "Shift";
				break;
			case "BACKSPACE":
				keyCodeRaw = "Backspace";
				break;
			case "SPACE":
				keyCodeRaw = "Space";
				break;
			case ",":
				keyCodeRaw = "Comma";
				break;
			case ".":
				keyCodeRaw = "Period";
				break;
			case "ENTER":
				keyCodeRaw = "Enter";
				break;
			default:
				keyCodeRaw = keyCodeRaw.toUpperCase();
		}
        KeyCode keyCode = KeyCode.getKeyCode(keyCodeRaw);

        String posXRaw = parser.getAttributeValue(null, "relative-pos-x");
        double posX = Double.valueOf(posXRaw.substring(0, posXRaw.length() - 1));
        String posYRaw = parser.getAttributeValue(null, "relative-pos-y");
        double posY = Double.valueOf(posYRaw.substring(0, posYRaw.length() - 1));

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