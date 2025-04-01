import React, { useState } from "react";
import { TOKEN_KEY, API_ROOT } from "../constants";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Container, Card, Form, Button } from "react-bootstrap";
import { FaFacebook, FaTwitter } from "react-icons/fa";
import { GoogleLogin } from "@react-oauth/google";

const Login = ({ handleLoggedIn }) => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleLogin = async () => {
    try {
      // const response = await axios.post(`${API_ROOT}/login`, {
      const response = await axios.post(`${API_ROOT}/api/auth/login`, {
        username: email,
        password,
      });

      const { data } = response.data;
      const token = data.token;
      localStorage.setItem(TOKEN_KEY, token); // 保存 token
      localStorage.setItem("username", email); // <- save username
      handleLoggedIn(token); // 通知 App 登录成功
    } catch (err) {
      console.error("Login error:", err);
      setErrorMsg("Login failed. Please check your credentials.");
    }
  };

  // 处理登录逻辑（当前用临时 token）
  // const handleLogin = async () => {
  //   const token = "temporary_token";
  //   localStorage.setItem(TOKEN_KEY, token);
  //   handleLoggedIn(token);
  // };

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

        {/* Login Form */}
        <Form>
          <Form.Group controlId="email">
            <Form.Label className="fw-bold">Username</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter your username"
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </Form.Group>

          <Form.Group controlId="password" className="mt-3">
            <Form.Label className="fw-bold">Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Enter your password"
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </Form.Group>

          {errorMsg && (
            <div className="text-danger mt-2" style={{ fontSize: "0.9rem" }}>
              {errorMsg}
            </div>
          )}

          <Button className="w-100 mt-4" variant="dark" onClick={handleLogin}>
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
            <Button
              variant="dark"
              size="sm"
              style={{ minWidth: "150px" }}
              onClick={() => navigate("/register")}
            >
              Register
            </Button>
          </div>
        </div>

        {/* Social Login Buttons */}
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
            <FaTwitter size={18} />
          </Button>
        </div>

        {/* Google Login 独立一行居中 ✅
        <div className="d-flex justify-content-center mt-4">
          <GoogleLogin
            onSuccess={(credentialResponse) => {
              const googleToken = credentialResponse.credential;
              localStorage.setItem(TOKEN_KEY, googleToken);
              handleLoggedIn(googleToken);
            }}
            onError={() => {
              console.log("Google Login Failed");
            }}
          />
        </div> */}
      </Card>
    </Container>
  );
};

export default Login;
