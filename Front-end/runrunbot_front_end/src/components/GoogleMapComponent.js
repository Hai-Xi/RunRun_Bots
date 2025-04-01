import React, { useState, useEffect } from "react";
import {
  GoogleMap,
  LoadScript,
  DirectionsService,
  DirectionsRenderer,
} from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "400px",
};

const center = {
  lat: 37.7749,
  lng: -122.4194,
};

const GoogleMapComponent = ({ routeRequest, deliveryMethod }) => {
  const [directionsResult, setDirectionsResult] = useState(null);
  const [directionsRequestDone, setDirectionsRequestDone] = useState(false);

  useEffect(() => {
    setDirectionsResult(null);
    setDirectionsRequestDone(false);
  }, [routeRequest, deliveryMethod]);

  return (
    <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={12}
        options={{ streetViewControl: false }}
      >
        {/* ✅ DRONE Route */}
        {routeRequest &&
          deliveryMethod === "DRONE" &&
          !directionsResult &&
          !directionsRequestDone && (
            <DirectionsService
              options={{
                origin: routeRequest.origin,
                destination: routeRequest.destination,
                travelMode: "DRIVING", // Placeholder, you can later change this
              }}
              callback={(res, status) => {
                if (status === "OK") {
                  setDirectionsResult(res);
                  setDirectionsRequestDone(true);
                } else {
                  console.error("DRONE Directions request failed:", status);
                  setDirectionsRequestDone(true);
                }
              }}
            />
          )}

        {/* ✅ ROBOT Route */}
        {routeRequest &&
          deliveryMethod === "ROBOT" &&
          !directionsResult &&
          !directionsRequestDone && (
            <DirectionsService
              options={{
                origin: routeRequest.origin,
                destination: routeRequest.destination,
                travelMode: "DRIVING",
              }}
              callback={(res, status) => {
                if (status === "OK") {
                  setDirectionsResult(res);
                  setDirectionsRequestDone(true);
                } else {
                  console.error("ROBOT Directions request failed:", status);
                  setDirectionsRequestDone(true);
                }
              }}
            />
          )}

        {/* ✅ Render the result */}
        {directionsResult && (
          <DirectionsRenderer directions={directionsResult} />
        )}
      </GoogleMap>
    </LoadScript>
  );
};

export default GoogleMapComponent;
