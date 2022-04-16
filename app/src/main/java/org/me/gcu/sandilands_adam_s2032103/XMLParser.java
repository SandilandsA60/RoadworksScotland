// Adam Sandilands s2032103
package org.me.gcu.sandilands_adam_s2032103;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XMLParser {

    //private static final String ns = null;
    ArrayList<DataModel> trafficDataList = new ArrayList<>();
    private Date date;

    public XMLParser() {
        date = new Date();
    }

    public String getDescription(String desc) {
        int result = desc.lastIndexOf("<br />");
        if (result == -1) {
            return desc;
        } else {
            return desc.substring(result+6, desc.length());
        }
    }

    public ArrayList<DataModel> parse(String string) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();


        xpp.setInput( new StringReader (string) );
        int event = xpp.getEventType();


        while (event != XmlPullParser.END_DOCUMENT) {
            if(event == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if(event == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("item")) {
                    DataModel trafficDataObj = new DataModel();
                    event = xpp.nextTag();
                    if (xpp.getName().equals("title")) {
                        event = xpp.next();
                        trafficDataObj.setTitle(xpp.getText());

                        event = xpp.nextTag();
                        event = xpp.nextTag();


                    }
                    if (xpp.getName().equals("description")) {
                        event = xpp.next();
                        trafficDataObj.setDescription(getDescription(xpp.getText()));

                        String[] startAndEndDates = getDates(xpp.getText());
                        if (startAndEndDates != null) {

                            trafficDataObj.setStartDate(date.convertRssDateToCalendarObj(startAndEndDates[0]));
                            trafficDataObj.setEndDate(date.convertRssDateToCalendarObj(startAndEndDates[1]));
                            trafficDataObj.setStartDateAsString(date.convertLongDateToShort(startAndEndDates[0]));
                            trafficDataObj.setEndDateAsString(date.convertLongDateToShort(startAndEndDates[1]));
                            trafficDataObj.setRoadworksLength(date.numberOfDays(trafficDataObj.getStartDate(), trafficDataObj.getEndDate()));
                        }
//

                        event = xpp.nextTag();
                        event = xpp.nextTag();

                    }
                    if (xpp.getName().equals("link")) {
                        event = xpp.next();
                        trafficDataObj.setLink(xpp.getText());

                        event = xpp.nextTag();
                        event = xpp.nextTag();

                    }
                    if (xpp.getName().equals("point")) {
                        event = xpp.next();
                        trafficDataObj.setGeorss(xpp.getText());

                        event = xpp.nextTag();
                        event = xpp.nextTag();

                    }
                       event = xpp.nextTag();
                       event = xpp.nextTag();
                       event = xpp.nextTag();
                       event = xpp.nextTag();
                    if (xpp.getName().equals("pubDate")) {
                        event = xpp.next();
                        trafficDataObj.setPubDate(date.convertLongDateToShort(xpp.getText()));
                        event = xpp.nextTag();
                        event = xpp.nextTag();
                    ;
                    }
                    trafficDataList.add(trafficDataObj);
                }
            }
            event = xpp.next();
        }
        System.out.println("End document");
        return trafficDataList;
    }




    public String[] getDates(String date) throws StringIndexOutOfBoundsException {
        if (date.indexOf("Start Date: ") == -1 || date.indexOf("End Date: ") == -1) {
            return null;
        }

        else {

            String startDateIndex = date.substring(date.indexOf("Start Date: "), date.indexOf(':'));
            String data1 = date.substring(startDateIndex.length() + 2,
                    date.indexOf('<'));

            String leftOverString = date.substring(
                    date.indexOf('>'));

            String endDateIndex = leftOverString.substring(leftOverString.indexOf("End Date: "),
                    date.indexOf(':'));
            String data2 = "";

            if (date.indexOf("<br />Delay") != -1) {
                data2 = leftOverString.substring(endDateIndex.length() + 2, leftOverString.indexOf('<'));
            } else {
                data2 = leftOverString.substring(
                        endDateIndex.length() + 2);
            }
            String[] results = new String[2];
            results[0] = data1;
            results[1] = data2;
            return results;
        }

    }



}

