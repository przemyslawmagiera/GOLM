package client.pl.golm.controller;

/**
 * Created by sazzad on 2/15/16
 */
public class GameInfo {

    private String info;

    public String getInfo() {
        return info;
    }

    public GameInfo(String name) {
        this.info = name;
    }

    public GameInfo() {
    }

    public void setInfo(String name) {
        this.info = name;
    }
}
