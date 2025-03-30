import React, { useState, useEffect } from "react";
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
import { Form, Button, ButtonGroup, Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const containerStyle = {
  width: "100%",
  height: "400px",
};

const center = {
  lat: 37.7749,
  lng: -122.4194,
};

function CreateNewOrder() {
  const navigate = useNavigate();

  // --- Form states ---
  const [itemDescription, setItemDescription] = useState("");
  const [pickup, setPickup] = useState("");
  const [destination, setDestination] = useState("");
  const [deliveryMethod, setDeliveryMethod] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("");

  // --- UI states ---
  const [errorMsg, setErrorMsg] = useState("");
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [countdown, setCountdown] = useState(5);
  const [orderId, setOrderId] = useState("");

  // --- New confirm modal state ---
  const [showConfirmModal, setShowConfirmModal] = useState(false);

  // --- Bill & ETA ---
  const estimatedTime = pickup && destination ? "1h 30min" : "--";
  const bill = pickup && destination ? "$25.00" : "--";

  // --- Fake geocoder ---
  const toLatLng = (address) => {
    const fakePositions = {
      "1 Market St": { lat: 37.7936, lng: -122.395 },
      "Golden Gate Park": { lat: 37.7694, lng: -122.4862 },
      "Ferry Building": { lat: 37.7955, lng: -122.3937 },
      "Twin Peaks": { lat: 37.7544, lng: -122.4477 },
      "Pier 39": { lat: 37.8087, lng: -122.4098 },
      "San Francisco Zoo": { lat: 37.7325, lng: -122.503 },
    };
    return fakePositions[address] || null;
  };

  // --- Handle Confirm Payment ---
  const handleConfirmPayment = () => {
    if (
      !itemDescription ||
      !pickup ||
      !destination ||
      !deliveryMethod ||
      !paymentMethod
    ) {
      setErrorMsg("Please complete the information above.");
      return;
    }

    // reset error and generate order
    setErrorMsg("");
    setOrderId("ORD-" + Math.floor(100000 + Math.random() * 900000)); // fake order id
    setShowSuccessModal(true);
    setCountdown(5);
  };

  // --- Countdown effect ---
  useEffect(() => {
    if (!showSuccessModal) return;
    if (countdown === 0) {
      navigate("/ordermanage");
      return;
    }
    const timer = setTimeout(() => setCountdown(countdown - 1), 1000);
    return () => clearTimeout(timer);
  }, [countdown, showSuccessModal, navigate]);

  // --- New functions for manage orders confirm ---
  const handleManageOrdersClick = () => {
    setShowConfirmModal(true);
  };

  const handleConfirmNavigation = () => {
    setShowConfirmModal(false);
    navigate("/ordermanage");
  };

  return (
    <div
      style={{
        display: "flex",
        marginTop: "70px",
        padding: "20px",
        gap: "20px",
      }}
    >
      {/* Left Side */}
      <div style={{ flex: 1 }}>
        <h3>Create New Order</h3>

        {/* Item Description */}
        <Form.Group className="mb-3">
          <Form.Label>Item Description</Form.Label>
          <Form.Control
            type="text"
            placeholder="e.g., Electronics"
            value={itemDescription}
            onChange={(e) => setItemDescription(e.target.value)}
          />
        </Form.Group>

        {/* Sending Location */}
        <Form.Group className="mb-3">
          <Form.Label>Sending Location</Form.Label>
          <Form.Control
            type="text"
            placeholder="e.g., Ferry Building"
            value={pickup}
            onChange={(e) => setPickup(e.target.value)}
          />
        </Form.Group>

        {/* Delivery Location */}
        <Form.Group className="mb-3">
          <Form.Label>Delivery Location</Form.Label>
          <Form.Control
            type="text"
            placeholder="e.g., Twin Peaks"
            value={destination}
            onChange={(e) => setDestination(e.target.value)}
          />
        </Form.Group>

        {/* Delivery Method */}
        <Form.Group className="mb-3">
          <Form.Label>Delivery Method</Form.Label>
          <div>
            <ButtonGroup>
              {["Robot", "Drone"].map((method) => (
                <Button
                  key={method}
                  variant={
                    deliveryMethod === method ? "primary" : "outline-primary"
                  }
                  onClick={() => setDeliveryMethod(method)}
                >
                  {method}
                </Button>
              ))}
            </ButtonGroup>
          </div>
        </Form.Group>

        <hr />

        {/* Bill & ETA */}
        <div style={{ marginBottom: "20px" }}>
          <p>
            <strong>Bill to Pay:</strong> {bill}
          </p>
          <p>
            <strong>Estimated Time:</strong> {estimatedTime}
          </p>
        </div>

        {/* Payment Method */}
        <Form.Group className="mb-3">
          <Form.Label>Payment Method</Form.Label>
          <div>
            <ButtonGroup>
              {["Credit Card", "PayPal", "Venmo"].map((method) => (
                <Button
                  key={method}
                  variant={
                    paymentMethod === method ? "primary" : "outline-primary"
                  }
                  onClick={() => setPaymentMethod(method)}
                >
                  {method}
                </Button>
              ))}
            </ButtonGroup>
          </div>
        </Form.Group>

        {/* Confirm Payment */}
        <Button
          variant="success"
          style={{ width: "100%" }}
          onClick={handleConfirmPayment}
        >
          Confirm Payment
        </Button>

        {/* Error Message */}
        {errorMsg && (
          <div style={{ color: "red", marginTop: "10px" }}>{errorMsg}</div>
        )}
      </div>

      {/* Right Side */}
      <div
        style={{
          flex: 2,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <LoadScript
          googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}
        >
          <GoogleMap
            mapContainerStyle={containerStyle}
            center={center}
            zoom={12}
            options={{ streetViewControl: false }}
          >
            {toLatLng(pickup) && (
              <Marker position={toLatLng(pickup)} label="A" />
            )}
            {toLatLng(destination) && (
              <Marker position={toLatLng(destination)} label="B" />
            )}
          </GoogleMap>
        </LoadScript>

        {/* Manage Orders Button */}
        <Button
          variant="secondary"
          style={{ marginTop: "20px", width: "200px" }}
          onClick={handleManageOrdersClick}
        >
          Manage My Orders
        </Button>
      </div>

      {/* Success Modal */}
      <Modal show={showSuccessModal} centered>
        <Modal.Header>
          <Modal.Title>Payment Success</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Payment Success!
          <br />
          Your order ID is <strong>{orderId}</strong>.<br />
          Navigating to home page in {countdown}...
        </Modal.Body>
      </Modal>

      {/* Confirm Navigation Modal */}
      <Modal
        show={showConfirmModal}
        onHide={() => setShowConfirmModal(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>Leave This Page?</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          The current new order progress will not be saved.
          <br />
          Are you sure you want to go back to the home page?
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setShowConfirmModal(false)}
          >
            Cancel
          </Button>
          <Button variant="danger" onClick={handleConfirmNavigation}>
            Confirm
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default CreateNewOrder;
