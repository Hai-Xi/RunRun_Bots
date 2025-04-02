import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button, ProgressBar } from "react-bootstrap";
import axios from "axios";
import { API_ROOT, TOKEN_KEY } from "../constants";

const statusSteps = ["CREATED", "PENDING", "PAID", "IN_PROGRESS", "DELIVERED"];

const SelectedOrder = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const orderId = location.state?.orderId;

  const [order, setOrder] = useState(null);

  useEffect(() => {
    const fetchOrder = async () => {
      if (!orderId) return;
      const token = localStorage.getItem(TOKEN_KEY);
      if (!token) {
        alert("Missing token. Please login again.");
        return;
      }
      try {
        const response = await axios.get(`${API_ROOT}/api/orders/${orderId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (response.data.code === 100) {
          setOrder(response.data.data);
        } else {
          alert("Failed to fetch order info.");
        }
      } catch (err) {
        console.error("Fetch failed:", err);
        alert("Failed to fetch order info.");
      }
    };

    fetchOrder();
  }, [orderId]);

  if (!order) {
    return <div>Loading order info...</div>;
  }

  const formatDateTime = (dateString) => {
    const options = { dateStyle: "medium", timeStyle: "short" };
    return new Date(dateString).toLocaleString(undefined, options);
  };

  const handleCancelOrder = async () => {
    try {
      const token = localStorage.getItem(TOKEN_KEY);
      if (!token) {
        alert("Missing token. Please login again.");
        return;
      }

      await axios.put(
        `${API_ROOT}/api/orders/${order.orderId}/updateOrderStatus`,
        {
          status: "CANCELLED",
          estimate: "1.2.3",
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      alert("Order cancelled successfully.");
      window.location.reload(); // Refresh
    } catch (err) {
      console.error("Failed to cancel order:", err);
      alert("Failed to cancel order. Please try again.");
    }
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

      <div style={{ marginTop: "15px", display: "flex", flexDirection: "column", gap: "8px" }}>
        <Button
          variant="danger"
          size="sm"
          style={{ width: "400px", alignSelf: "center" }}
          disabled={order.status === "IN_PROGRESS" || order.status === "DELIVERED" || order.status === "CANCELLED"}
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
