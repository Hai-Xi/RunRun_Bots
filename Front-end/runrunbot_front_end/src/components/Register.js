import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API_ROOT, TOKEN_KEY } from "../constants";
import { Container, Card, Form, Button } from "react-bootstrap";

const Register = ({ handleLoggedIn }) => {
  const navigate = useNavigate();

  // 本地状态
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  // const [username, setUsername] = useState("");
  const [phone, setPhone] = useState("");
  const [role, setRole] = useState("ROLE_USER");

  // 注册逻辑
  const handleRegister = async () => {
    try {
      // const response = await axios.post(`${API_ROOT}/register`, {
      const response = await axios.post(`${API_ROOT}/api/auth/signup`, {
        // name,
        // email,
        // password,
        username: name,
        email,
        phone,
        password,
        role,
      });

      const { token } = response.data;
      localStorage.setItem(TOKEN_KEY, token);
      handleLoggedIn(token);
    } catch (err) {
      setErrorMsg("Register failed. Please try again.");
    }
  };

  return (
    <Container
      className="d-flex justify-content-center align-items-center vh-100"
      style={{ backgroundColor: "#BDBDBD" }}
    >
      <Card
        className="p-4 shadow-lg"
        style={{ width: "500px", borderRadius: "12px" }}
      >
        {/* Logo */}
        <div className="mb-3 text-center">
          <img src="/logo.png" alt="Logo" style={{ width: "150px" }} />
        </div>

        {/* Form */}
        <Form>
          <Form.Group controlId="name">
            <Form.Label className="fw-bold">Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter your name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="email" className="mt-3">
            <Form.Label className="fw-bold">Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="password" className="mt-3">
            <Form.Label className="fw-bold">Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </Form.Group>
          //new phone number:
          <Form.Group controlId="phone" className="mt-3">
            <Form.Label className="fw-bold">Phone</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter your phone number"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              required
            />
          </Form.Group>
          {/* 错误提示 */}
          {errorMsg && (
            <div className="text-danger mt-2" style={{ fontSize: "0.9rem" }}>
              {errorMsg}
            </div>
          )}
          <Button
            className="w-100 mt-4"
            variant="dark"
            onClick={handleRegister}
          >
            Create An Account
          </Button>
        </Form>

        {/* Login redirect */}
        <div className="mt-3 d-flex justify-content-between align-items-center">
          <span className="text-muted" style={{ fontSize: "0.9rem" }}>
            Already a User?
          </span>
          <Button
            variant="dark"
            size="sm"
            style={{ minWidth: "150px" }}
            onClick={() => navigate("/login")}
          >
            Login
          </Button>
        </div>
      </Card>
    </Container>
  );
};

export default Register;
