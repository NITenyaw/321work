package eu.q5x.a321work.Model;

import java.util.ArrayList;

/**
 * Class represents points of interests, which are displayed in the DetailAcitivity
 */
public class PointOfInterest {
    private double latitude;
    private double longitude;
    private String poi;
    private String houseNumber;
    private String street;
    private String opening;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>");
        sb.append(poi);
        sb.append("</b>");
        sb.append("<br>");
        sb.append(street);
        sb.append(" ");
        sb.append(houseNumber);
        sb.append("<br>");
        sb.append(opening);
        return sb.toString();
    }
}
