package com.rizorsiumani.user.utils.map_utils;

import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DirectionRoute {
   public Route route;
   public ArrayList<LatLng> directionPoints = new ArrayList<>();
    public DirectionRoute(Route route, ArrayList<LatLng> directionPoints) {
        this.route = route;
        this.directionPoints = directionPoints;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public ArrayList<LatLng> getDirectionPoints() {
        return directionPoints;
    }

    public void setDirectionPoints(ArrayList<LatLng> directionPoints) {
        this.directionPoints = directionPoints;
    }
}
