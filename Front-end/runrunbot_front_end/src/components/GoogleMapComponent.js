import React from "react";
import { GoogleMap, LoadScript } from "@react-google-maps/api";

// Load API key from environment variables
const apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;

const containerStyle = {
  width: '100%',
  height: '400px'
};

const center = {
  lat: 37.7749,  
  lng: -122.4194 
};

const GoogleMapComponent = () => {
  return (
    <LoadScript googleMapsApiKey={apiKey}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={12}
      >
        { /* Markers or other components go here */ }
      </GoogleMap>
    </LoadScript>
  );
};

export default GoogleMapComponent;