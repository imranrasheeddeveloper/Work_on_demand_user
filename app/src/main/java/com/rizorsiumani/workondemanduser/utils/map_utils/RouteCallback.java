package com.rizorsiumani.workondemanduser.utils.map_utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface RouteCallback {
    void onRouteSuccess(@Nullable DirectionRoute directionRoute);

    /**
     * Retrieve the response from direction request with error result.
     *
     * @param t A throwable from the response of Google Direction API.
     * @since 1.0.0
     */
    void onRouteFailure(@NonNull Throwable t);
}
