import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button, ProgressBar } from "react-bootstrap";

const statusSteps = ["CREATED", "PENDING", "PAID", "IN PROCESS", "DELIVERED"];

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
    <div style={{ marginTop: "100px", padding: "20px", display: "flex", flexDirection: "column", alignItems: "center" }}>
      
      {/* --- Progress Bar --- */}
      <div style={{ width: "500px", marginBottom: "20px" }}>
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
          style={{ height: "6px", marginTop: "4px" }}
        />
      </div>

      {/* --- Order Information --- */}
      <div style={{ width: "500px", border: "1px solid #ccc", padding: "15px", borderRadius: "8px" }}>
        <h4>Order Receipt & Management</h4>
        <p><strong>Order ID:</strong> {order.orderId}</p>
        <p><strong>Item Description:</strong> {order.itemDescription}</p>
        <p><strong>Pickup Location:</strong> {order.pickupLocation}</p>
        <p><strong>Delivery Location:</strong> {order.deliveryLocation}</p>
        <p><strong>Delivery Method:</strong> {order.deliveryMethod}</p>
        <p><strong>Status:</strong> {order.status}</p>
        <p><strong>Created At:</strong> {formatDateTime(order.createdAt)}</p>
      </div>

      {/* --- Slimmer Buttons --- */}
      <div style={{ marginTop: "15px", display: "flex", flexDirection: "column", gap: "8px" }}>
        <Button
          variant="danger"
          size="sm"
          style={{ width: "400px", alignSelf: "center" }}
          disabled={order.status === "IN PROCESS" || order.status === "DELIVERED"}
          onClick={handleCancelOrder}
        >
          Cancel this order
        </Button>

        <Button
          variant="secondary"
          size="sm"
          style={{ width: "400px", alignSelf: "center" }}
          onClick={handlePrint}
        >
          🖨 Print Receipt
        </Button>

        <Button
          variant="outline-dark"
          size="sm"
          style={{ width: "400px", alignSelf: "center" }}
          onClick={() => navigate(-1)}
        >
          ⬅ Back to Order Manage
        </Button>
      </div>
    </div>
  );
};

export default SelectedOrder;
