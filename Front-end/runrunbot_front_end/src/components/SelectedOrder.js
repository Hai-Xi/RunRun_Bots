import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button, ProgressBar } from "react-bootstrap";

const statusSteps = ["CREATED", "PAID", "PENDING", "IN PROCESS", "DELIVERED"];

const SelectedOrder = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const order = location.state?.order;

  if (!order) {
    return <div>No order selected. Please go back and select an order.</div>;
  }

  const formatDateTime = (dateString) => {
    const options = { dateStyle: "medium", timeStyle: "short" };
    return new Date(dateString).toLocaleString(undefined, options);
  };

  const handleCancelOrder = () => {
    alert("Cancel API will be called here in the future.");
  };

  const handlePrint = () => {
    window.print();
  };

  const getProgress = () => {
    if (order.status === "CANCELED") return 0;
    const index = statusSteps.indexOf(order.status);
    return ((index + 1) / statusSteps.length) * 100;
  };

  return (
    <div style={{ marginTop: "100px", padding: "20px" }}>
      
      {/* --- Shortened Progress Bar at Top --- */}
      <div style={{ width: "50%", margin: "0 auto 30px auto" }}>
        <div style={{ display: "flex", justifyContent: "space-between", fontSize: "0.8rem" }}>
          {order.status === "CANCELED" ? (
            <span style={{ color: "red" }}>CANCELED</span>
          ) : (
            statusSteps.map((step, idx) => (
              <span key={idx} style={{ color: step === order.status ? "blue" : "gray" }}>
                {step}
              </span>
            ))
          )}
        </div>
        <ProgressBar
          now={getProgress()}
          variant={order.status === "CANCELED" ? "danger" : "info"}
          style={{ height: "8px", marginTop: "5px" }}
        />
      </div>

      {/* --- Order Information --- */}
      <h3>Order Receipt & Management</h3>
      <div style={{ border: "1px solid #ccc", padding: "15px", borderRadius: "8px" }}>
        <p><strong>Order ID:</strong> {order.orderId}</p>
        <p><strong>Item Description:</strong> {order.itemDescription}</p>
        <p><strong>Pickup Location:</strong> {order.pickupLocation}</p>
        <p><strong>Delivery Location:</strong> {order.deliveryLocation}</p>
        <p><strong>Delivery Method:</strong> {order.deliveryMethod}</p>
        <p><strong>Status:</strong> {order.status}</p>
        <p><strong>Created At:</strong> {formatDateTime(order.createdAt)}</p>
      </div>

      {/* --- Buttons --- */}
      <div style={{ marginTop: "20px", display: "flex", gap: "10px" }}>
        <Button
          variant="danger"
          disabled={order.status === "IN PROCESS" || order.status === "DELIVERED"}
          onClick={handleCancelOrder}
        >
          Cancel this order
        </Button>

        <Button variant="secondary" onClick={handlePrint}>
          🖨 Print Receipt
        </Button>
      </div>

      {/* --- Back --- */}
      <Button
        variant="outline-dark"
        style={{ marginTop: "20px" }}
        onClick={() => navigate(-1)}
      >
        ⬅ Back to Order Manage
      </Button>
    </div>
  );
};

export default SelectedOrder;
