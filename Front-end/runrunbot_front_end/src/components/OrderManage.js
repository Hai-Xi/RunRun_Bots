import React, { useState, useEffect } from "react";
import GoogleMapComponent from "./GoogleMapComponent";
import { useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import axios from "axios";
import { API_ROOT, TOKEN_KEY } from "../constants";

const OrderManage = () => {
  const [orders, setOrders] = useState([]);
  const [routeRequest, setRouteRequest] = useState(null);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const token = localStorage.getItem(TOKEN_KEY);
        const response = await axios.get(`${API_ROOT}/api/orders/orderList`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (response.data.code === 100) {
          setOrders(response.data.data);
        }
      } catch (err) {
        console.error("Failed to fetch orders:", err);
      }
    };
    fetchOrders();
  }, []);

  const handleOrderClick = (order) => {
    setSelectedOrder(order);
    setRouteRequest({
      origin: order.pickupLocation,
      destination: order.deliveryLocation,
      travelMode: "DRIVING",
    });
  };

  const handleCreateNewOrder = () => {
    navigate("/createneworder");
  };

  const handleReceiptAndManage = () => {
    navigate("/selectedorder", { state: { order: selectedOrder } });
  };

  const formatDateTime = (dateString) => {
    const options = { dateStyle: "medium", timeStyle: "short" };
    return new Date(dateString).toLocaleString(undefined, options);
  };

  const getButtonColor = (status) => {
    if (status === "Canceled") return "#d3d3d3"; // grey
    if (status === "Delivered") return "#ccffcc"; // shallow green
    return "#e0f0ff"; // shallow blue
  };

  return (
    <div style={{ marginTop: "70px", display: "flex", gap: "20px" }}>
      {/* Left Section */}
      <div style={{ flex: 1 }}>
        <h3>Orders:</h3>
        {orders.map((order) => (
          <button
            key={order.orderId}
            onClick={() => handleOrderClick(order)}
            style={{
              display: "block",
              marginBottom: "10px",
              backgroundColor: getButtonColor(order.status),
              padding: "8px",
              borderRadius: "5px",
            }}
          >
            Order #{order.orderId} | Ordered At: {formatDateTime(order.createdAt)}
          </button>
        ))}

        {/* Display selected order details */}
        {selectedOrder && (
          <div
            style={{
              marginTop: "20px",
              padding: "10px",
              border: "1px solid #ccc",
            }}
          >
            <h4>Selected Order:</h4>
            <p><strong>Item Description:</strong> {selectedOrder.itemDescription}</p>
            <p><strong>Sending from:</strong> {selectedOrder.pickupLocation}</p>
            <p><strong>Deliver to:</strong> {selectedOrder.deliveryLocation}</p>
            <p><strong>Delivery Method:</strong> {selectedOrder.deliveryMethod}</p>
            <p><strong>Status:</strong> {selectedOrder.status}</p>
            <p><strong>Ordered At:</strong> {formatDateTime(selectedOrder.createdAt)}</p>

            <Button
              variant="primary"
              style={{ marginTop: "10px" }}
              onClick={handleReceiptAndManage}
            >
              Receipt and Manage
            </Button>
          </div>
        )}
      </div>

      {/* Right Section */}
      <div
        style={{
          flex: 2,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <GoogleMapComponent
          routeRequest={routeRequest}
          deliveryMethod={selectedOrder?.deliveryMethod}
          status={selectedOrder?.status}
        />

        <Button
          variant="success"
          onClick={handleCreateNewOrder}
          style={{ marginTop: "20px", width: "200px" }}
        >
          ➕ Create New Order
        </Button>
      </div>
    </div>
  );
};

export default OrderManage;
