import React, { useState, useEffect } from "react";
import {
  GoogleMap,
  LoadScript,
  DirectionsService,
  DirectionsRenderer,
  Polyline,
  Marker,
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
  const [dronePath, setDronePath] = useState(null);

  useEffect(() => {
    setDirectionsResult(null);
    setDronePath(null);
    setDirectionsRequestDone(false);

    if (routeRequest && deliveryMethod === "Drone") {
      // Prepare direct path if it's a drone
      setDronePath([routeRequest.origin, routeRequest.destination]);
    }
  }, [routeRequest, deliveryMethod]);

  // Temporary mocked geocode for development
  const toLatLng = (address) => {
    const fakePositions = {
      "1 Market St, San Francisco, CA": { lat: 37.7936, lng: -122.395 },
      "Golden Gate Park, San Francisco, CA": { lat: 37.7694, lng: -122.4862 },
      "Ferry Building, San Francisco, CA": { lat: 37.7955, lng: -122.3937 },
      "Twin Peaks, San Francisco, CA": { lat: 37.7544, lng: -122.4477 },
      "Pier 39, San Francisco, CA": { lat: 37.8087, lng: -122.4098 },
      "San Francisco Zoo, San Francisco, CA": { lat: 37.7325, lng: -122.503 },
    };
    return fakePositions[address] || center;
  };

  return (
    <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={12}
        options={{
          streetViewControl: false,
        }}
      >
        {/* DRONE: Direct Flight Path with Markers */}
        {dronePath && (
          <>
            <Marker position={toLatLng(dronePath[0])} label="A" />
            <Marker position={toLatLng(dronePath[1])} label="B" />
            <Polyline
              path={[toLatLng(dronePath[0]), toLatLng(dronePath[1])]}
              options={{
                strokeColor: "#4285F4", // Google Maps default blue
                strokeOpacity: 0.5, // 50% transparent
                strokeWeight: 4, // Normal route thickness
                geodesic: true, // Smooth line
              }}
            />
          </>
        )}

        {/* NON-DRONE: Normal Driving Route */}
        {routeRequest &&
          deliveryMethod !== "Drone" &&
          !directionsResult &&
          !directionsRequestDone && (
            <DirectionsService
              options={routeRequest}
              callback={(res, status) => {
                if (status === "OK") {
                  setDirectionsResult(res);
                  setDirectionsRequestDone(true);
                } else {
                  console.error("Directions request failed:", status);
                  setDirectionsRequestDone(true);
                }
              }}
            />
          )}

        {directionsResult && (
          <DirectionsRenderer directions={directionsResult} />
        )}
      </GoogleMap>
    </LoadScript>
  );
};

export default GoogleMapComponent;
