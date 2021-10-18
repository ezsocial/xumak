package net.ezmovil.xumak.info;

public final class infoXumakImage {
    public static String _txtImage;
    public static String _name;
    public static String _nickname;
    public static String _geonameid;

    public static String _occupation;
    public static String _status;
    public static String _portrayed;

    public infoXumakImage() {
    	;
    }

    public void setOccupation(String occupation) {
        _occupation = occupation;
    }

    public String getOccupation()
    {
        return _occupation;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public String getStatus()
    {
        return _status;
    }

    public void setPortrayed(String portrayed) {
        _portrayed = portrayed;
    }

    public String getPortrayed()
    {
        return _portrayed;
    }

    public void setTxt(String txtImage) {
        _txtImage = txtImage;
    }
    
    public String getTxt()
    {
        return _txtImage;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }

    public void setNickname(String nickname) {
        _nickname = nickname;
    }

    public String getNickname()
    {
        return _nickname;
    }

    public void setGeonameid(String geonameid) {
        _geonameid = geonameid;
    }

    public String getGeonameid()
    {
        return _geonameid;
    }
}
