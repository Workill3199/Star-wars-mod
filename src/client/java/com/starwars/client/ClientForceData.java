package com.starwars.client;

public class ClientForceData {
    private static int force;

    public static void setForce(int force) {
        ClientForceData.force = force;
    }

    public static int getForce() {
        return force;
    }
}
