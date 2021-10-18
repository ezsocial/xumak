package net.ezmovil.xumak;

public class Album2 {
    private String name;
    private String nickname;
    private int episode;
    private String uri;

    private String occupation;
    private String status;
    private String portrayed;

    public Album2() {
    }

    public Album2(String name, String nickname, int episode, String uri, String occupation, String status, String portrayed) {
        this.name = name;
        this.nickname = nickname;
        this.episode = episode;
        this.uri = uri;

        this.occupation = occupation;
        this.status = status;
        this.portrayed = portrayed;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPortrayed() {
        return portrayed;
    }

    public void setPortrayed(String portrayed) {
        this.portrayed = portrayed;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}