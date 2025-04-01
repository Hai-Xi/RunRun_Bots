import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Card, Form, Button } from "react-bootstrap";

function UserProfile() {
  const navigate = useNavigate();

  // Assuming you stored these in localStorage during login/register
  const username = localStorage.getItem("username") || "";
  const [email, setEmail] = useState(localStorage.getItem("email") || "");
  const [phone, setPhone] = useState(localStorage.getItem("phone") || "");

  // Local states to track if values were changed
  const [originalEmail, setOriginalEmail] = useState(email);
  const [originalPhone, setOriginalPhone] = useState(phone);

  const isChanged = email !== originalEmail || phone !== originalPhone;

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "80vh" }}>
      <Card style={{ width: "500px", padding: "20px", borderRadius: "12px" }}>
        <h3 className="mb-4">User Profile</h3>

        {/* Username (non-editable) */}
        <Form.Group className="mb-3">
          <Form.Label>Username</Form.Label>
          <Form.Control type="text" value={username} disabled />
        </Form.Group>

        {/* Email (editable) */}
        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>

        {/* Phone Number (editable) */}
        <Form.Group className="mb-3">
          <Form.Label>Phone Number</Form.Label>
          <Form.Control
            type="text"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
          />
        </Form.Group>

        {/* Confirm Button */}
        <Button variant="primary" disabled={!isChanged} style={{ width: "100%" }}>
          Confirm
        </Button>

        {/* Manage Orders Button */}
        <Button
          variant="secondary"
          style={{ width: "100%", marginTop: "15px" }}
          onClick={() => navigate("/ordermanage")}
        >
          Manage My Orders
        </Button>
      </Card>
    </Container>
  );
}

export default UserProfile;