// Token-based Auth

// import React, { useState } from "react";
// import Main from "./Main";
// import { TOKEN_KEY } from "../constants";
// import Login from './Login';  // 确保路径正确
// import Register from './Register';  // 确保路径正确
// import 'bootstrap/dist/css/bootstrap.min.css';  // 加载 Bootstrap 样式
// function App() {
//   const [isLoggedIn, setIsLoggedIn] = useState(
//     localStorage.getItem(TOKEN_KEY) ? true : false
//   );

//   const loggedIn = (token) => {
//     if (token) {
//       localStorage.setItem(TOKEN_KEY, token);
//       setIsLoggedIn(true);
//     }
//   };

//   return (
//     <div className="App">
//       <Main isLoggedIn={isLoggedIn} handleLoggedIn={loggedIn} />
//     </div>
//   );
// }

// export default App;


// Session based Auth


//

import React from 'react';
import Login from './Login';  // 确保路径正确
import Register from './Register';  // 确保路径正确
import OrderManage from './OrderManage';  // 确保路径正确
import 'bootstrap/dist/css/bootstrap.min.css';  // 加载 Bootstrap 样式

function App() {
  return <Login />;
}

export default App;
