import React, { useState } from "react";
import { Container, Row, Col, Button, ListGroup, Image } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import GoogleMapComponent from './GoogleMapComponent'; // Import the map
// import ResponsiveAppBar from "./ResponsiveBar";

function OrderManage() {
  const [selectedOrder, setSelectedOrder] = useState(null);
  const navigate = useNavigate();

  const handleSelectOrder = (orderName) => {
    setSelectedOrder(orderName);
  };

  const handleCreateNewOrder = () => {
    navigate("/create-new-order");
  };

  return (
    <div>
      {/* <ResponsiveAppBar isLoggedIn={true} handleLogout={() => {}} /> */}

      <Container fluid className="mt-5 pt-3">
        <Row>
          {/* Left Column: 4 units wide */}
          <Col md={4}>
            <h2>Order History</h2>
            <ListGroup>
              {["order1", "order2", "order3"].map((order, index) => (
                <ListGroup.Item
                  key={order}
                  action
                  active={selectedOrder === order}
                  onClick={() => handleSelectOrder(order)}
                >
                  {order}
                </ListGroup.Item>
              ))}
            </ListGroup>

            {/* Display Order Details */}
            {selectedOrder && (
              <div className="mt-4">
                <p>
                  <strong>Order ID:</strong>{" "}
                  {selectedOrder === "order1"
                    ? 1
                    : selectedOrder === "order2"
                    ? 2
                    : 3}
                </p>
                <p>
                  <strong>Sending from:</strong> A
                </p>
                <p>
                  <strong>Deliver to:</strong> B
                </p>
                <p>
                  <strong>Status:</strong> In process
                </p>
                <p>
                  <strong>Estimated time:</strong> 12:00pm
                </p>
              </div>
            )}
          </Col>

          {/* Right Column: 8 units wide */}
          <Col md={8}>
            <Row className="justify-content-center">
              {/* <Image
                src="/Map_placeholder.png"
                alt="Map Placeholder"
                fluid
                style={{ width: "70%" }}
              /> */}
              <GoogleMapComponent />
            </Row>
            <Row className="justify-content-center mt-3">
              <Button
                variant="primary"
                onClick={handleCreateNewOrder}
                style={{ width: "200px" }}
              >
                Create New Order
              </Button>
            </Row>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default OrderManage;
