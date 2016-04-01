package hck.cslmobilebuyer.common;

import java.util.HashMap;

/**
 * Created by hck on 2/4/2016.
 */
public class PhoneData {
    public final static String InformationPage = "https://shop.hkt.com/MobOs/stspersondtl.html";
    public final static String ConformPage = "https://shop.hkt.com/MobOs/stsconfirmation.html";

    public final static HashMap<String, String> PhoneMap = getPhoneMap();
    public final static HashMap<String, HashMap<String, String>> PhoneColorMap = getPhoneColorMap();

    private static HashMap<String, String> getPhoneMap(){
        HashMap<String, String> phoneMap = new HashMap<>();
        phoneMap.put("S7", "https://shop.hkt.com/MobOs/stsadditem.html?modelId=608");
        phoneMap.put("S7Edge", "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609");
        phoneMap.put("NOTE5", "https://shop.hkt.com/MobOs/stsadditem.html?modelId=543");
        return phoneMap;
    }

    private static HashMap<String, HashMap<String, String>> getPhoneColorMap(){
        HashMap<String, HashMap<String, String>> phoneColorMap = new HashMap<>();
        HashMap<String, String> colorMap;

        colorMap = new HashMap<>();
        colorMap.put("铂金", "402000921");
        colorMap.put("白色", "402000922");
        phoneColorMap.put("S7", colorMap);

        colorMap = new HashMap<>();
        colorMap.put("铂金", "402000923");
        colorMap.put("鋼黑色", "402000925");
        colorMap.put("鈦銀色", "402000924");
        phoneColorMap.put("S7Edge", colorMap);

        colorMap = new HashMap<>();
        colorMap.put("金色", "402000826");
        phoneColorMap.put("NOTE5", colorMap);

        return phoneColorMap;
    }
}
