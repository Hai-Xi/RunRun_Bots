import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./Login";
import Register from "./Register";
import Collection from "./Collection";
import Landing from "./Landing";

function Main(props) {
  const { isLoggedIn, handleLoggedIn } = props;

  // auth gating

  const showLogin = () => {
    return isLoggedIn ? (
      <Navigate to="/create" />
    ) : (
      <Login handleLoggedIn={handleLoggedIn} />
    );
  };

  const showRegister = () => {
    return isLoggedIn ? <Navigate to="/create" /> : <Register />;
  };

  const showLanding = () => {
    return isLoggedIn ? <Landing /> : <Navigate to="/login" />;
  };

  const showCollection = () => {
    return isLoggedIn ? <Collection /> : <Navigate to="/login" />;
  };

  return (
    <div className="main">
      <Routes>
        <Route path="/" exact element={showLogin()} />
        <Route path="/login" element={showLogin()} />
        <Route path="/register" element={showRegister()} />
        <Route path="/create" element={showLanding()} />
        <Route path="/collection" element={showCollection()} />
      </Routes>
    </div>
  );
}

export default Main;
