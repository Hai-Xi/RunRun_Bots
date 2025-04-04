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

const defaultCenter = {
  lat: 37.7749,
  lng: -122.4194,
};

const GoogleMapComponent = ({ routeRequest, deliveryMethod }) => {
  const [directionsResult, setDirectionsResult] = useState(null);
  const [directionsRequestDone, setDirectionsRequestDone] = useState(false);
  const [droneCoords, setDroneCoords] = useState(null);

  useEffect(() => {
    setDirectionsResult(null);
    setDirectionsRequestDone(false);
    setDroneCoords(null);

    // Only geocode if DRONE and routeRequest exists
    if (deliveryMethod === "DRONE" && routeRequest) {
      const geocoder = new window.google.maps.Geocoder();

      geocoder.geocode({ address: routeRequest.origin }, (originResults, status1) => {
        if (status1 === "OK" && originResults[0]) {
          geocoder.geocode({ address: routeRequest.destination }, (destResults, status2) => {
            if (status2 === "OK" && destResults[0]) {
              const originLocation = originResults[0].geometry.location;
              const destLocation = destResults[0].geometry.location;

              setDroneCoords([
                { lat: originLocation.lat(), lng: originLocation.lng() },
                { lat: destLocation.lat(), lng: destLocation.lng() },
              ]);
            } else {
              console.error("Failed to geocode destination:", status2);
            }
          });
        } else {
          console.error("Failed to geocode origin:", status1);
        }
      });
    }
  }, [routeRequest, deliveryMethod]);

  // Compute center for drone mode
  const center =
    droneCoords?.length === 2
      ? {
          lat: (droneCoords[0].lat + droneCoords[1].lat) / 2,
          lng: (droneCoords[0].lng + droneCoords[1].lng) / 2,
        }
      : defaultCenter;

  return (
    <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={12}
        options={{ streetViewControl: false }}
      >
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

        {directionsResult && deliveryMethod === "ROBOT" && (
          <DirectionsRenderer directions={directionsResult} />
        )}

        {/* ✅ DRONE line as polyline after geocoding */}
        {deliveryMethod === "DRONE" && droneCoords?.length === 2 && (
  <>
    {console.log("📏 Rendering drone polyline:", droneCoords)}
    
    {/* Origin Marker - A */}
    <Marker
      position={droneCoords[0]}
      label={{ text: "A", color: "white", fontWeight: "bold" }}
    />
    
    {/* Destination Marker - B */}
    <Marker
      position={droneCoords[1]}
      label={{ text: "B", color: "white", fontWeight: "bold" }}
    />

    {/* Drone route line (styled to match Google Maps driving route) */}
    <Polyline
      path={droneCoords}
      options={{
        strokeColor: "#4285F4", // Google Maps blue for driving route
        strokeOpacity: 1.0,
        strokeWeight: 4,
        geodesic: true,
      }}
    />
  </>
)}
      </GoogleMap>
    </LoadScript>
  );
};

export default GoogleMapComponent;
