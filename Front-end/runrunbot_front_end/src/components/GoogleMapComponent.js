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

const GoogleMapComponent = ({ routeRequest, deliveryMethod, status }) => {
  const [directionsResult, setDirectionsResult] = useState(null);
  const [directionsRequestDone, setDirectionsRequestDone] = useState(false);
  const [dronePath, setDronePath] = useState(null);

  useEffect(() => {
    setDirectionsResult(null);
    setDronePath(null);
    setDirectionsRequestDone(false);

    if (routeRequest && deliveryMethod === "Drone") {
      setDronePath([routeRequest.origin, routeRequest.destination]);
    }
  }, [routeRequest, deliveryMethod]);

  // --- Fake geocode ---
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

  // --- Get mid point on overview path ---
  const getRouteMidpoint = (path) => {
    if (!path || path.length < 2) return null;
    return path[Math.floor(path.length / 2)];
  };

  // --- Get mid point for drone (simple) ---
  const getDroneMidpoint = (from, to) => {
    return {
      lat: (from.lat + to.lat) / 2,
      lng: (from.lng + to.lng) / 2,
    };
  };

  // --- Render Status Marker ---
  const renderStatusMarker = () => {
    if (!status || !routeRequest) return null;

    if (status === "Pending") {
      return <Marker position={toLatLng(routeRequest.origin)} icon={dotIcon} />;
    }
    if (status === "Delivered") {
      return <Marker position={toLatLng(routeRequest.destination)} icon={dotIcon} />;
    }
    if (status === "In process" || status === "In transit") {
      if (deliveryMethod === "Drone" && dronePath) {
        const mid = getDroneMidpoint(
          toLatLng(dronePath[0]),
          toLatLng(dronePath[1])
        );
        return <Marker position={mid} icon={dotIcon} />;
      }
      if (deliveryMethod !== "Drone" && directionsResult) {
        const mid = getRouteMidpoint(directionsResult.routes[0].overview_path);
        return mid ? <Marker position={mid} icon={dotIcon} /> : null;
      }
    }
    return null;
  };

  // --- Red half-transparent dot ---
  const dotIcon = window.google ? {
    path: window.google.maps.SymbolPath.CIRCLE,
    scale: 6,
    fillColor: "#FF0000",
    fillOpacity: 0.5,
    strokeWeight: 0,
  } : null;

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
        {/* Drone route */}
        {dronePath && (
          <>
            <Marker position={toLatLng(dronePath[0])} label="A" />
            <Marker position={toLatLng(dronePath[1])} label="B" />
            <Polyline
              path={[toLatLng(dronePath[0]), toLatLng(dronePath[1])]}
              options={{
                strokeColor: "#4285F4",
                strokeOpacity: 0.5,
                strokeWeight: 4,
                geodesic: true,
              }}
            />
          </>
        )}

        {/* Robot route */}
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

        {/* ✅ Status Marker */}
        {renderStatusMarker()}
      </GoogleMap>
    </LoadScript>
  );
};

export default GoogleMapComponent;
