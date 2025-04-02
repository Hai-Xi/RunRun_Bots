import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Avatar from "@mui/material/Avatar";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import IconButton from "@mui/material/IconButton";
import PublicIcon from "@mui/icons-material/Public";
import { TOKEN_KEY } from "../constants";

function ResponsiveAppBar(props) {
  const { isLoggedIn, handleLogout } = props;
  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  const handleLogoutAndRedirect = () => {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem("username");
    handleLogout();
    navigate("/login");
  };

  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleProfileClick = () => {
    handleMenuClose();
    navigate("/userprofile");
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
              <IconButton onClick={handleMenuOpen} sx={{ p: 0 }}>
                <Avatar>{username[0]?.toUpperCase() || "?"}</Avatar>
              </IconButton>
              <Menu anchorEl={anchorEl} open={open} onClose={handleMenuClose}>
                <MenuItem disabled style={{ color: "black" }}>
                  Hello, {username}
                </MenuItem>{" "}
                {/* ✅ black text */}
                <hr
                  style={{
                    margin: "4px 0",
                    border: "none",
                    borderTop: "1px solid black",
                  }}
                />{" "}
                {/* ✅ black line */}
                <MenuItem onClick={handleProfileClick}>Profile</MenuItem>
                <MenuItem onClick={handleLogoutAndRedirect}>Logout</MenuItem>
              </Menu>
            </Box>
          )}
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default ResponsiveAppBar;
