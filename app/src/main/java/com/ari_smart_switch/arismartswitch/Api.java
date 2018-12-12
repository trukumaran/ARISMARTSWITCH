package com.ari_smart_switch.arismartswitch;

public class Api {

private static final String ROOT_URL = "http://arismartswitch.com/smart_switch/update.php?apicall=";
    public static final String URL_CREATE_DEVICE = ROOT_URL + "createdevice";
    public static final String URL_SHOW_DEVICES =  ROOT_URL + "getdevices";
    public static final String URL_UPDATE_DEVICE = ROOT_URL + "updatedevice";
    public static final String URL_DELETE_DEVICE = ROOT_URL + "deletedevice&id=";

}