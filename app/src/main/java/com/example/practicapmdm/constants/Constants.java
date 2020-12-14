package com.example.practicapmdm.constants;

import com.example.practicapmdm.models.Pool;

import java.util.ArrayList;

public class Constants {
    public static final String TAG = Constants.class.getName();
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String NAME = "NAME";
    public static final String INTENT_LOCALIZATION_ACTION = "location-event-position";
    public static final int DISTANCE = 8000;
    public static ArrayList<Pool> favourites = new ArrayList<>();
    public static final String FILENAME = "favourites.txt";
    public static final int SPLASH_TIME = 3000;

    //API REST METHODS
    public static final String HEADER_URL = "https://datos.madrid.es/egob/";
    public static final String END_POINT = "catalogo/210227-0-piscinas-publicas.json";
    public static final String END_POINT_SPORT = "catalogo/200186-0-polideportivos.json";

    //LINKS INTEREST LINKS
    public static final String LINKTOURISM = "https://www.esmadrid.com/?utm_referrer=https%3A%2F%2Fwww.google.com%2F";
    public static final String LINKPOOL = "https://www.esmadrid.com/piscinas-de-verano-en-madrid?utm_referrer=https%3A%2F%2Fwww.google.com%2F";
    public static final String LINKSPORTCENTER = "https://www.comunidad.madrid/servicios/deportes/instalaciones";
    public static final String LINKSPORT = "https://www.madrid.es/portales/munimadrid/es/Inicio/Cultura-ocio-y-deporte/Deportes?vgnextfmt=default&vgnextchannel=c7a8efff228fe410VgnVCM2000000c205a0aRCRD";
}
