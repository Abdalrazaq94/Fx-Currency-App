package com.example.altaih_abedulalrazaq_s2428152;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class XmlParser {

    public String feedDate = "";
    private boolean rootDateCaptured = false;

    public ArrayList<CurrencyItem> parse(String data) {

        ArrayList<CurrencyItem> list = new ArrayList<>();
        CurrencyItem item = null;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    String tag = xpp.getName();

                    // Capture feed-level pubDate
                    if (tag.equalsIgnoreCase("pubDate") && item == null && !rootDateCaptured) {
                        feedDate = xpp.nextText().trim();
                        rootDateCaptured = true;
                        eventType = xpp.next();
                        continue;
                    }

                    if (tag.equalsIgnoreCase("item")) {
                        item = new CurrencyItem();
                    }
                    else if (tag.equalsIgnoreCase("title") && item != null) {
                        item.title = xpp.nextText().trim();
                    }
                    else if (tag.equalsIgnoreCase("description") && item != null) {
                        item.description = xpp.nextText().trim();
                    }
                    else if (tag.equalsIgnoreCase("pubDate") && item != null) {
                        item.pubDate = xpp.nextText().trim();
                    }
                }

                else if (eventType == XmlPullParser.END_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item") && item != null) {

                        // Extract code (3-letter)
                        try {
                            String t = item.title;
                            int o = t.lastIndexOf("(");
                            int c = t.lastIndexOf(")");
                            if (o != -1 && c != -1 && c > o) {
                                item.code = t.substring(o + 1, c).toUpperCase();
                            }
                        } catch (Exception ex) {
                            item.code = "";
                        }

                        // ---- FIXED: Extract rate correctly ----
                        try {
                            String desc = item.description;     // Example: "£1 = €1.1373"
                            int pos = desc.indexOf('=');

                            if (pos != -1) {
                                String right = desc.substring(pos + 1).trim();
                                // Remove currency symbols, keep digits + decimal
                                right = right.replaceAll("[^0-9.]", "");
                                item.rate = Double.parseDouble(right);
                            } else {
                                item.rate = 0.0;
                            }

                        } catch (Exception ex) {
                            item.rate = 0.0;
                        }

                        list.add(item);
                        item = null;
                    }
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
