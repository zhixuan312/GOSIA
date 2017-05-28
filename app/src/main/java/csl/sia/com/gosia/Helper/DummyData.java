package csl.sia.com.gosia.Helper;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xuan on 22/4/17.
 */

public class DummyData {

    public static ArrayList<HashMap<String, String>> loadRequestDummyData(Context context) {
        try {
            JSONObject obj = new JSONObject(Global.loadJSON(context, "requests"));
            JSONArray m_jArry = obj.getJSONArray("requestList");
            ArrayList<HashMap<String, String>> dummyList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String requestId_value = jo_inside.getString("requestId");
                String name_value = jo_inside.getString("name");
                String from_value = jo_inside.getString("from");
                String to_value = jo_inside.getString("to");
                String riderNum_value = jo_inside.getString("totalRider");
                String imageUrl = jo_inside.getString("imageUrl");
                String date_value = jo_inside.getString("date");

                m_li = new HashMap<String, String>();
                m_li.put("requestId", requestId_value);
                m_li.put("name", name_value);
                m_li.put("from", from_value);
                m_li.put("to", to_value);
                m_li.put("riderNum", riderNum_value);
                m_li.put("imageUrl", imageUrl);
                m_li.put("date", date_value);

                dummyList.add(m_li);
            }
            return dummyList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<HashMap<String, String>> loadPastTripsDummyData(Context context) {
        try {
            JSONObject obj = new JSONObject(Global.loadJSON(context, "past_trips"));
            JSONArray m_jArry = obj.getJSONArray("requestList");
            ArrayList<HashMap<String, String>> dummyList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String requestId_value = jo_inside.getString("requestId");
                String name_value = jo_inside.getString("name");
                String from_value = jo_inside.getString("from");
                String to_value = jo_inside.getString("to");
                String riderNum_value = jo_inside.getString("totalRider");
                String imageUrl = jo_inside.getString("imageUrl");
                String date_value = jo_inside.getString("date");
                String status_value = jo_inside.getString("status");
                String from_name_value = jo_inside.getString("fromName");
                String to_name_value = jo_inside.getString("toName");

                m_li = new HashMap<String, String>();
                m_li.put("requestId", requestId_value);
                m_li.put("name", name_value);
                m_li.put("from", from_value);
                m_li.put("to", to_value);
                m_li.put("riderNum", riderNum_value);
                m_li.put("imageUrl", imageUrl);
                m_li.put("date", date_value);
                m_li.put("status", status_value);
                m_li.put("fromName", from_name_value);
                m_li.put("toName", to_name_value);

                dummyList.add(m_li);
            }
            return dummyList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<HashMap<String, String>> loadUpcomingTripsDummyData(Context context) {
        try {
            JSONObject obj = new JSONObject(Global.loadJSON(context, "upcoming_trips"));
            JSONArray m_jArry = obj.getJSONArray("requestList");
            ArrayList<HashMap<String, String>> dummyList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String requestId_value = jo_inside.getString("requestId");
                String name_value = jo_inside.getString("name");
                String from_value = jo_inside.getString("from");
                String to_value = jo_inside.getString("to");
                String riderNum_value = jo_inside.getString("totalRider");
                String imageUrl = jo_inside.getString("imageUrl");
                String date_value = jo_inside.getString("date");
                String status_value = jo_inside.getString("status");
                String from_name_value = jo_inside.getString("fromName");
                String to_name_value = jo_inside.getString("toName");

                m_li = new HashMap<String, String>();
                m_li.put("requestId", requestId_value);
                m_li.put("name", name_value);
                m_li.put("from", from_value);
                m_li.put("to", to_value);
                m_li.put("riderNum", riderNum_value);
                m_li.put("imageUrl", imageUrl);
                m_li.put("date", date_value);
                m_li.put("status", status_value);
                m_li.put("fromName", from_name_value);
                m_li.put("toName", to_name_value);

                dummyList.add(m_li);
            }
            return dummyList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<HashMap<String, String>> loadFavouriteDummyData(Context context) {
        try {
            JSONObject obj = new JSONObject(Global.loadJSON(context, "favourite"));
            JSONArray m_jArry = obj.getJSONArray("favouriteList");
            ArrayList<HashMap<String, String>> dummyList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String favouriteId_value = jo_inside.getString("favouriteId");
                String title_value = jo_inside.getString("title");
                String address_value = jo_inside.getString("address");
                String lat_value = jo_inside.getString("lat");
                String lng_value = jo_inside.getString("lng");

                m_li = new HashMap<String, String>();
                m_li.put("favouriteId", favouriteId_value);
                m_li.put("title", title_value);
                m_li.put("address", address_value);
                m_li.put("lat", lat_value);
                m_li.put("lng", lng_value);

                dummyList.add(m_li);
            }
            return dummyList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<HashMap<String, String>> loadDriverMainFavouriteIconDummyData(Context context) {
        try {
            JSONObject obj = new JSONObject(Global.loadJSON(context, "favourite"));
            JSONArray m_jArry = obj.getJSONArray("favouriteList");
            ArrayList<HashMap<String, String>> dummyList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String favouriteId_value = jo_inside.getString("favouriteId");
                String title_value = jo_inside.getString("title");
                String address_value = jo_inside.getString("address");
                String lat_value = jo_inside.getString("lat");
                String lng_value = jo_inside.getString("lng");
                String addToMain_value = jo_inside.getString("addToMain");

                m_li = new HashMap<String, String>();
                m_li.put("favouriteId", favouriteId_value);
                m_li.put("title", title_value);
                m_li.put("address", address_value);
                m_li.put("lat", lat_value);
                m_li.put("lng", lng_value);
                m_li.put("addToMain", addToMain_value);

                if (addToMain_value.equals("1")) {
                    dummyList.add(m_li);
                }

            }
            return dummyList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkDummyProfile(String role, String email, String password) {
        String[] dummyProfile = {"driver-driver@singaporeair.com.sg-1234567q", "rider-rider@singaporeair.com.sg-1234567q"};
        for (String record : dummyProfile) {
            String[] recordDetail = record.split("-");
            if (role.equals(recordDetail[0]) && email.equals(recordDetail[1]) && password.equals(recordDetail[2])) {
                return true;
            }
        }
        return false;
    }
}
