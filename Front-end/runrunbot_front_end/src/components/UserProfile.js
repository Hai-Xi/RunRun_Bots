import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Card, Form, Button } from "react-bootstrap";
import axios from "axios";
import { API_ROOT, TOKEN_KEY } from "../constants";

function UserProfile() {
  const navigate = useNavigate();
  const username = localStorage.getItem("username") || "";

  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");

  const [originalEmail, setOriginalEmail] = useState("");
  const [originalPhone, setOriginalPhone] = useState("");

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem(TOKEN_KEY);
        const response = await axios.get(`${API_ROOT}/api/auth/user`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (response.data.code === 100) {
          const data = response.data.data;
          setEmail(data.email);
          setPhone(data.phone);
          setOriginalEmail(data.email);
          setOriginalPhone(data.phone);
        }
      } catch (error) {
        console.error("Failed to fetch user info:", error);
      }
    };
    fetchUserInfo();
  }, []);

  const isChanged = email !== originalEmail || phone !== originalPhone;

  const handleConfirmChange = async () => {
    try {
      const token = localStorage.getItem(TOKEN_KEY);
      await axios.put(
        `${API_ROOT}/api/auth/user`,
        { email, phoneNumber: phone },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Profile updated successfully.");
      setOriginalEmail(email);
      setOriginalPhone(phone);
    } catch (err) {
      console.error("Failed to update user info:", err);
      alert("Failed to update profile. Please try again.");
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "80vh" }}>
      <Card style={{ width: "500px", padding: "20px", borderRadius: "12px" }}>
        <h3 className="mb-4">User Profile</h3>

        <Form.Group className="mb-3">
          <Form.Label>Username</Form.Label>
          <Form.Control type="text" value={username} disabled />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Phone Number</Form.Label>
          <Form.Control
            type="text"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Reset Password (Coming Soon)</Form.Label>
          <Form.Control type="password" placeholder="Placeholder" disabled />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Confirm Password (Coming Soon)</Form.Label>
          <Form.Control type="password" placeholder="Placeholder" disabled />
        </Form.Group>

        <Button
          variant="primary"
          disabled={!isChanged}
          style={{ width: "100%" }}
          onClick={handleConfirmChange}
        >
          Confirm Change
        </Button>

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