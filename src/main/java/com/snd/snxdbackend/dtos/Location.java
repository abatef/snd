package com.snd.snxdbackend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private double latitude;
    private double longitude;

    @JsonIgnore
    private static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public Point toPoint() {
        return geometryFactory.createPoint(new Coordinate(latitude, longitude));
    }

    public static Location of(Point point) {
        Location location = new Location();
        location.setLatitude(point.getCoordinate().getY());
        location.setLongitude(point.getCoordinate().getX());
        return location;
    }

    public static Location of(String coordinates) {
        String[] coordinatesArray = coordinates.split(":");
        Location location = new Location();
        location.setLatitude(Double.parseDouble(coordinatesArray[0]));
        location.setLongitude(Double.parseDouble(coordinatesArray[1]));
        return location;
    }

    public String toString() {
        return latitude + ":" + longitude;
    }
}
