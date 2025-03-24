import React from "react";
import { Container, Card, Form, Button, Row, Col } from "react-bootstrap";
import { FaFacebook, FaGoogle, FaTwitter } from "react-icons/fa";

const Login = () => {
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
          <Form.Group controlId="email">
            <Form.Label className="fw-bold">Email</Form.Label>
            <Form.Control type="email" placeholder="Enter your email" />
          </Form.Group>

          <Form.Group controlId="password" className="mt-3">
            <Form.Label className="fw-bold">Password</Form.Label>
            <Form.Control type="password" placeholder="Enter your password" />
          </Form.Group>

          <Button className="w-100 mt-4" variant="dark">
            Sign In
          </Button>
        </Form>

        {/* Forgot Password & Register */}
        <div className="mt-3">
          <a href="#" className="text-muted" style={{ fontSize: "0.9rem" }}>
            Forgot password?
          </a>
          <div className="d-flex justify-content-between align-items-center mt-2">
            <span className="text-muted" style={{ fontSize: "0.9rem" }}>
              Don’t have an account?
            </span>
            <Button variant="dark" size="sm" style={{ minWidth: "150px" }}>
              Register
            </Button>
          </div>
        </div>

        {/* Social Icons */}
        <div className="d-flex justify-content-around mt-4">
          <Button
            variant="dark"
            className="rounded-circle d-flex justify-content-center align-items-center"
            style={{ width: "40px", height: "40px" }}
          >
            <FaFacebook size={18} />
          </Button>
          <Button
            variant="dark"
            className="rounded-circle d-flex justify-content-center align-items-center"
            style={{ width: "40px", height: "40px" }}
          >
            <FaGoogle size={18} />
          </Button>
          <Button
            variant="dark"
            className="rounded-circle d-flex justify-content-center align-items-center"
            style={{ width: "40px", height: "40px" }}
          >
            <FaTwitter size={18} />
          </Button>
        </div>
      </Card>
    </Container>
  );
};

export default Login;
