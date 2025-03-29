import React from "react";
import { useNavigate } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import PublicIcon from "@mui/icons-material/Public";
import LogoutIcon from "@mui/icons-material/Logout";
import { TOKEN_KEY } from "../constants";

function ResponsiveAppBar(props) {
  const { isLoggedIn, handleLogout } = props;
  const navigate = useNavigate();

  // 统一处理登出逻辑 + 页面跳转
  const handleLogoutAndRedirect = () => {
    localStorage.removeItem(TOKEN_KEY);
    handleLogout();          // 调用 App.js 传下来的函数，更新 isLoggedIn 状态
    navigate("/login");      // 立刻跳转到登录页
  };

  return (
    <AppBar
      position="static"
      style={{ background: "black", top: 0, position: "fixed", zIndex: 1 }}
    >
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <PublicIcon
            sx={{
              display: { xs: isLoggedIn ? "none" : "flex", md: "flex" },
              mr: 1,
            }}
          />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: isLoggedIn ? "none" : "flex", md: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            RunRunBot
          </Typography>

          {isLoggedIn && (
            <Box
              sx={{ flexGrow: 1, display: "flex", justifyContent: "flex-end" }}
            >
              <Button
                variant="outlined"
                startIcon={<LogoutIcon />}
                onClick={handleLogoutAndRedirect} // 触发登出 + 跳转
                sx={{ backgroundColor: "white" }}
              >
                Log out
              </Button>
            </Box>
          )}
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default ResponsiveAppBar;
