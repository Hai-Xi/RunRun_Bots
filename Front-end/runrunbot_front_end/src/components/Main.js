import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./Login";
import Register from "./Register";
import OrderManage from "./OrderManage";
import CreateNewOrder from "./CreateNewOrder";
// import Landing from "./Landing";
import UserProfile from "./UserProfile"
import SelectedOrder from "./SelectedOrder";

function Main(props) {
  const { isLoggedIn, handleLoggedIn} = props;

  // auth gating

  const showLogin = () => {
    return isLoggedIn ? (
      <Navigate to="/ordermanage" />
    ) : (
      <Login handleLoggedIn={handleLoggedIn} />
    );
  };

  const showRegister = () => {
    return isLoggedIn ? <Navigate to="/ordermanage" /> : <Register />;
  };

  // const showLanding = () => {
  //   return isLoggedIn ? <Landing /> : <Navigate to="/login" />;
  // };

    const showUserProfile = () => {
    return isLoggedIn ? <UserProfile /> : <Navigate to="/login" />;
  };

  const showSelectedOrder = () => {
    return isLoggedIn ? <SelectedOrder /> : <Navigate to="/login" />;
  };

  const showOrderManage = () => {
    return isLoggedIn ? <OrderManage /> : <Navigate to="/login" />;
  };

  const showCreateNewOrder = () => {
    return isLoggedIn ? <CreateNewOrder /> : <Navigate to="/login" />;
  };

  return (
    <div className="main">
      <Routes>
        <Route path="/" exact element={showLogin()} />
        <Route path="/login" element={showLogin()} />
        <Route path="/register" element={showRegister()} />
        {/* <Route path="/create" element={showLanding()} /> */}
        <Route path="/userprofile" element={showUserProfile()} />
        <Route path="/ordermanage" element={showOrderManage()} />
        <Route path="/createneworder" element={showCreateNewOrder()} />
        <Route path="/selectedorder" element={showSelectedOrder()} />
      </Routes>
    </div>
  );
}

export default Main;
