package com.iznaroth.industrizer.capability.client;

public class ClientCurrencyData {
    private static int playerMDD;
    private static boolean hasWallet;

    public static void setPlayerMDD(int playerMDD){
        ClientCurrencyData.playerMDD = playerMDD;
    }

    public static int getPlayerMDD(){
        return playerMDD;
    }

    public static void setHasWallet(boolean trueOrFalse){
        hasWallet = trueOrFalse;
    }

    public static boolean getHasWallet(){
        return hasWallet;
    }
}
