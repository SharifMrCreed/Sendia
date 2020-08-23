package com.alle.san.sendia.utils;

import java.util.Random;

public class Globals {

    public static final String INTENT_USER = "userData";
    public static final int COLUMNS = 2;
    public static final String NO_RESULTS = "Result Not found";
    public static final String INTENT_MESSAGE = "messageData";
    public static final String  CHATS = "Chats";
    public static final String CHAT = "Chat";
    public static final String SETTINGS = "Settings";
    public static final String HOME = "Home";
    public static final String CONNECTIONS = "Connections";
    public static final String PROFILE = "Profile";
    public static final String MY_PROFILE = "My Profile";

    /** methods
     *
     * @return
     */
    public static int random (){
        Random random = new Random();
        return random.nextInt(15);
    }
}
