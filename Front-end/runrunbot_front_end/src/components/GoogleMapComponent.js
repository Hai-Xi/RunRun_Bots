import React, { useState, useEffect } from "react";
import { GoogleMap, LoadScript, DirectionsService, DirectionsRenderer, Polyline } from "@react-google-maps/api";

const containerStyle = {
  width: '100%',
  height: '400px'
};

const center = {
  lat: 37.7749,
  lng: -122.4194
};

const GoogleMapComponent = ({ routeRequest, deliveryMethod }) => {
  const [directionsResult, setDirectionsResult] = useState(null);
  const [directionsRequestDone, setDirectionsRequestDone] = useState(false);

  // For drone path
  const [dronePath, setDronePath] = useState(null);

  useEffect(() => {
    setDirectionsResult(null);
    setDronePath(null);
    setDirectionsRequestDone(false);

    if (routeRequest && deliveryMethod === "Drone") {
      // Direct path for drone
      setDronePath([
        routeRequest.origin,
        routeRequest.destination
      ]);
    }

  }, [routeRequest, deliveryMethod]);

  const toLatLng = (address) => {
    // For dev, let's mock latLng positions roughly
    // You would replace this with geocoding if you want accurate later
    const fakePositions = {
      "1 Market St, San Francisco, CA": { lat: 37.7936, lng: -122.3950 },
      "Golden Gate Park, San Francisco, CA": { lat: 37.7694, lng: -122.4862 },
      "Ferry Building, San Francisco, CA": { lat: 37.7955, lng: -122.3937 },
      "Twin Peaks, San Francisco, CA": { lat: 37.7544, lng: -122.4477 },
      "Pier 39, San Francisco, CA": { lat: 37.8087, lng: -122.4098 },
      "San Francisco Zoo, San Francisco, CA": { lat: 37.7325, lng: -122.5030 }
    };
    return fakePositions[address] || center;
  }

  return (
    <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={12}
      >
        {/* Drone -> direct flight line */}
        {dronePath && (
          <Polyline 
            path={[
              toLatLng(dronePath[0]),
              toLatLng(dronePath[1])
            ]}
            options={{
              strokeColor: "#FF0000",
              strokeOpacity: 0.8,
              strokeWeight: 2,
              geodesic: true
            }}
          />
        )}

        {/* Robot or others -> driving directions */}
        {routeRequest && deliveryMethod !== "Drone" && !directionsResult && !directionsRequestDone && (
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