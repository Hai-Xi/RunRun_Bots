import React, { useState, useEffect } from "react";
import GoogleMapComponent from "./GoogleMapComponent";
import { useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import axios from "axios";
import { API_ROOT, TOKEN_KEY } from "../constants";

const ORDERS_PER_PAGE = 5;

const OrderManage = () => {
  const [orders, setOrders] = useState([]);
  const [routeRequest, setRouteRequest] = useState(null);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
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
    navigate("/selectedorder", { state: { orderId: selectedOrder.orderId } });
  };

  const formatDateTime = (dateString) => {
    const options = { dateStyle: "medium", timeStyle: "short" };
    return new Date(dateString).toLocaleString(undefined, options);
  };

  const getButtonColor = (status) => {
    if (status === "CANCELLED") return "#d3d3d3";
    if (status === "DELIVERED") return "#ccffcc";
    return "#e0f0ff";
  };

  // Sorted orders
  const sortedOrders = [...orders].sort(
    (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
  );

  const totalPages = Math.ceil(sortedOrders.length / ORDERS_PER_PAGE);
  const paginatedOrders = sortedOrders.slice(
    (currentPage - 1) * ORDERS_PER_PAGE,
    currentPage * ORDERS_PER_PAGE
  );

  return (
    <div
      style={{
        marginTop: "70px",
        marginLeft: "100px",
        marginRight: "100px",
        display: "flex",
        gap: "20px",
      }}
    >
      {/* Left Section */}
      <div style={{ flex: 1 }}>
        <h3>Orders:</h3>

        {paginatedOrders.map((order) => (
          <div key={order.orderId}>
            <button
              onClick={() => handleOrderClick(order)}
              style={{
                display: "block",
                marginBottom: "10px",
                backgroundColor: getButtonColor(order.status),
                padding: "8px",
                borderRadius: "5px",
                width: "100%",
              }}
            >
              Order #{order.orderId} | Ordered At:{" "}
              {formatDateTime(order.createdAt)}
            </button>

            {selectedOrder?.orderId === order.orderId && (
              <div
                style={{
                  marginBottom: "20px",
                  padding: "10px",
                  border: "1px solid #ccc",
                }}
              >
                <h4>Selected Order:</h4>
                <p>
                  <strong>Item Description:</strong> {order.itemDescription}
                </p>
                <p>
                  <strong>Sending from:</strong> {order.pickupLocation}
                </p>
                <p>
                  <strong>Deliver to:</strong> {order.deliveryLocation}
                </p>
                <p>
                  <strong>Delivery Method:</strong> {order.deliveryMethod}
                </p>
                <p>
                  <strong>Status:</strong> {order.status}
                </p>
                <p>
                  <strong>Estimated Arrival Time:</strong>{" "}
                  {order.estimatedArrivalTime
                    ? formatDateTime(order.estimatedArrivalTime)
                    : "--"}
                </p>
                <p>
                  <strong>Payment Amount:</strong> $
                  {order.payment?.amount?.toFixed(2) ?? "--"}
                </p>
                <p>
                  <strong>Ordered At:</strong> {formatDateTime(order.createdAt)}
                </p>

                <Button
                  variant="primary"
                  style={{ marginTop: "10px", width: "100%" }}
                  onClick={handleReceiptAndManage}
                >
                  Receipt and Manage
                </Button>
              </div>
            )}
          </div>
        ))}

        {/* Pagination Controls */}
        <div style={{ marginTop: "20px", textAlign: "center" }}>
          <Button
            variant="outline-primary"
            size="sm"
            onClick={() => setCurrentPage((p) => Math.max(1, p - 1))}
            disabled={currentPage === 1}
            style={{ marginRight: "5px" }}
          >
            Prev
          </Button>
          {[...Array(totalPages)].map((_, idx) => {
            const page = idx + 1;
            return (
              <Button
                key={page}
                variant={page === currentPage ? "primary" : "outline-primary"}
                size="sm"
                onClick={() => setCurrentPage(page)}
                style={{ marginRight: "5px" }}
              >
                {page}
              </Button>
            );
          })}
          <Button
            variant="outline-primary"
            size="sm"
            onClick={() => setCurrentPage((p) => Math.min(totalPages, p + 1))}
            disabled={currentPage === totalPages}
          >
            Next
          </Button>
        </div>
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
