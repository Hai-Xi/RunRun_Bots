// Token-based Auth

import React, { useState } from "react";
import Main from "./Main";
import { TOKEN_KEY } from "../constants";
import Login from "./Login"; // 确保路径正确
import Register from "./Register"; // 确保路径正确
import ResponsiveAppBar from "./ResponsiveBar"; // 确保路径正确
import "bootstrap/dist/css/bootstrap.min.css"; // 加载 Bootstrap 样式

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(
    !!localStorage.getItem(TOKEN_KEY)
  );

  const handleLoggedIn = (token) => {
    if (token) {
      localStorage.setItem(TOKEN_KEY, token);
      setIsLoggedIn(true);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem(TOKEN_KEY);
    setIsLoggedIn(false);
  };


  return (
    <div className="App">
      <ResponsiveAppBar isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
      <Main
        isLoggedIn={isLoggedIn}
        handleLoggedIn={handleLoggedIn}
      />
    </div>
  );
}

export default App;

// Single page testing:

// import React from 'react';
// import Login from './Login';  // 确保路径正确
// import Register from './Register';  // 确保路径正确
// import OrderManage from './OrderManage';  // 确保路径正确
// import CreateNewOrder from "./CreateNewOrder"; // 确保路径正确
// import 'bootstrap/dist/css/bootstrap.min.css';  // 加载 Bootstrap 样式

// function App() {
//   return <OrderManage />;
// }

// export default App;
