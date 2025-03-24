import React from "react";
import ReactDOM from "react-dom/client";
// import "./styles/index.css";
import 'bootstrap/dist/css/bootstrap.min.css'; // ✅ 现在可以用了
import App from "./components/App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);

reportWebVitals();
