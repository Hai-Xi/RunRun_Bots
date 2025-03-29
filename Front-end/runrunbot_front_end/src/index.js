import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./components/App";
import { GoogleOAuthProvider } from '@react-oauth/google'; // ✅ 加这行
import 'bootstrap/dist/css/bootstrap.min.css';

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <GoogleOAuthProvider clientId="99758855160-ckll1b7a9pi3ql5reduke8a1vol68qqd.apps.googleusercontent.com">  {/* ✅ 替换成你的 Client ID */}
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </GoogleOAuthProvider>
);
