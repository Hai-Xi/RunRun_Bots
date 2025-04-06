import { Loader } from "@googlemaps/js-api-loader";

const loader = new Loader({
  apiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
  libraries: ["places"]
});

export const estimateCostAndTime = async (pickupAddress, deliveryAddress, deliveryMethod, createdTimeISO) => {
  await loader.load();
  const geocoder = new window.google.maps.Geocoder();
  const distanceMatrix = new window.google.maps.DistanceMatrixService();

  const geocode = (address) => {
    return new Promise((resolve, reject) => {
      geocoder.geocode({ address }, (results, status) => {
        if (status === "OK" && results[0]) {
          resolve(results[0].geometry.location);
        } else {
          reject("Failed to geocode address: " + address);
        }
      });
    });
  };

  const calculateDrivingDistance = (origin, destination) => {
    return new Promise((resolve, reject) => {
      distanceMatrix.getDistanceMatrix({
        origins: [origin],
        destinations: [destination],
        travelMode: "DRIVING",
      }, (response, status) => {
        if (status === "OK") {
          const element = response.rows[0].elements[0];
          if (element.status === "OK") {
            const miles = element.distance.value / 1609.34;
            resolve(miles);
          } else {
            reject("Distance Matrix element error");
          }
        } else {
          reject("Distance Matrix request failed");
        }
      });
    });
  };

  try {
    const originLatLng = await geocode(pickupAddress);
    const destinationLatLng = await geocode(deliveryAddress);

    let distanceMiles;
    let speedMph;
    let baseCost;
    let perMileCost;
    let dispatchMinutes;

    if (deliveryMethod === "DRONE") {
      const R = 3958.8; // Radius of Earth in miles
      const dLat = (destinationLatLng.lat() - originLatLng.lat()) * Math.PI / 180;
      const dLng = (destinationLatLng.lng() - originLatLng.lng()) * Math.PI / 180;
      const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(originLatLng.lat() * Math.PI / 180) *
        Math.cos(destinationLatLng.lat() * Math.PI / 180) *
        Math.sin(dLng / 2) * Math.sin(dLng / 2);
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      distanceMiles = R * c;
      speedMph = 50;
      baseCost = 12;
      perMileCost = 1.25;
      dispatchMinutes = 7;
    } else if (deliveryMethod === "ROBOT") {
      distanceMiles = await calculateDrivingDistance(originLatLng, destinationLatLng);
      speedMph = 40;
      baseCost = 5;
      perMileCost = 0.75;
      dispatchMinutes = 12;
    } else {
      throw new Error("Invalid delivery method");
    }

    const travelMinutes = (distanceMiles / speedMph) * 60;
    const totalMinutes = travelMinutes + dispatchMinutes;

    const createdDate = new Date(createdTimeISO);
    const estimatedArrival = new Date(createdDate.getTime() + totalMinutes * 60000);

    const formattedTime1 = estimatedArrival.toLocaleString();
    const formattedTime2 = estimatedArrival.toISOString();
    const estimatedCost = `$${(baseCost + perMileCost * distanceMiles).toFixed(2)}`;

    return {
      estimatedTimeString: formattedTime1,
      estimatedTimeISO: formattedTime2,
      estimatedCost
    };
  } catch (err) {
    console.error("Estimation error:", err);
    throw err;
  }
};
