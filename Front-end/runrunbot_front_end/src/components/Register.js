import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API_ROOT } from "../constants";
import { Container, Card, Form, Button } from "react-bootstrap";

const Register = () => {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  const role = "ROLE_USER";

  const handleRegister = async () => {
    try {
      const response = await axios.post(`${API_ROOT}/api/auth/signup`, {
        username: name,
        email,
        phone,
        password,
        role,
      });
  
      if (response.status === 200 || response.status === 201) {
        setSuccessMsg("✅ Registered successfully! Redirecting to login...");
        setErrorMsg("");
        setTimeout(() => {
          navigate("/login");
        }, 1000);
      }
    } catch (err) {
      console.error("Register error:", err.response?.data);
      const backendMsg = err?.response?.data?.message?.toLowerCase() || "";
      if (backendMsg.includes("email")) {
        setErrorMsg("This email is already registered.");
      } else if (backendMsg.includes("username")) {
        setErrorMsg("This username is already taken.");
      } else if (backendMsg.includes("password")) {
        setErrorMsg("Password is too weak.");
      } else {
        setErrorMsg("Register failed. Please try again.");
      }
      setSuccessMsg("");
    }
  };
  

// const handleRegister = async () => {
//   // ✅ 假设这些是已注册的用户（你可以从数据库 dump 一份填进来）
//   const existingEmails = ["shen.gao1218@gmail.com", "123@gmail.com"];
//   const existingUsernames = ["user", "test8"];

//   // ✅ 前端预检查
//   if (existingEmails.includes(email.toLowerCase())) {
//     setErrorMsg("This email is already registered.");
//     setSuccessMsg("");
//     return;
//   }
//   if (existingUsernames.includes(name.toLowerCase())) {
//     setErrorMsg("This username is already taken.");
//     setSuccessMsg("");
//     return;
//   }

//   try {
//     const response = await axios.post(`${API_ROOT}/api/auth/signup`, {
//       username: name,
//       email,
//       phone,
//       password,
//       role,
//     });

//     if (response.status === 200 || response.status === 201) {
//       setSuccessMsg("✅ Registered successfully! Redirecting to login...");
//       setErrorMsg("");
//       setTimeout(() => {
//         navigate("/login");
//       }, 1000);
//     }
//   } catch (err) {
//     console.error("Register error:", err.response?.data);
//     const backendMsg = err?.response?.data?.message?.toLowerCase() || "";
//     if (backendMsg.includes("email")) {
//       setErrorMsg("This email is already registered.");
//     } else if (backendMsg.includes("username")) {
//       setErrorMsg("This username is already taken.");
//     } else if (backendMsg.includes("password")) {
//       setErrorMsg("Password is too weak.");
//     } else {
//       setErrorMsg("Register failed. Please try again.");
//     }
//     setSuccessMsg("");
//   }
// };


  return (
    <Container
      className="d-flex justify-content-center align-items-center vh-100"
      style={{ backgroundColor: "#BDBDBD" }}
    >
      <Card className="p-4 shadow-lg" style={{ width: "500px", borderRadius: "12px" }}>
        <div className="mb-3 text-center">
          <img src="/logo.png" alt="Logo" style={{ width: "150px" }} />
        </div>

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

          {/* 提示信息 */}
          {errorMsg && (
            <div className="text-danger mt-2" style={{ fontSize: "0.9rem" }}>
              {errorMsg}
            </div>
          )}
          {successMsg && (
            <div className="text-success mt-2" style={{ fontSize: "0.9rem" }}>
              {successMsg}
            </div>
          )}

          <Button className="w-100 mt-4" variant="dark" onClick={handleRegister}>
            Create An Account
          </Button>
        </Form>

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
