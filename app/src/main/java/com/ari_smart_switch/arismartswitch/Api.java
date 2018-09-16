package com.ari_smart_switch.arismartswitch;

public class Api {

    //private static final String ROOT_URL = "http://192.168.100.116/HeroApi/v1/Api.php?apicall=";
//https://epidermoid-plots.000webhostapp.com/nodmculed/update.php?id=1&status=on
private static final String ROOT_URL = "https://epidermoid-plots.000webhostapp.com/nodmculed/update.php?apicall=";
    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";

}