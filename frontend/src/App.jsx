// C:\Users\Adarsh Kumar Choubey\Downloads\SweetShop_Frontend\sweetshop-frontend\src\App.jsx

import React, { useState } from "react";
import axios from "axios";
import { Container, Form, Button, Alert, Card } from "react-bootstrap";
import { jwtDecode } from "jwt-decode";
import Dashboard from "./components/Dashboard"; // ✅ Import real Dashboard

// Your working API instance
const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

// Helper function to check if a token is valid and not expired
const isTokenValid = (token) => {
  if (!token) return false;
  try {
    const { exp } = jwtDecode(token);
    if (Date.now() >= exp * 1000) {
      localStorage.removeItem("token"); // Clean up expired token
      return false;
    }
    return true;
  } catch (e) {
    localStorage.removeItem("token"); // Clean up invalid token
    return false;
  }
};

export default function App() {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [isRegister, setIsRegister] = useState(false);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    setError("");

    if (isRegister && formData.password !== formData.confirmPassword) {
      setError("❌ Passwords do not match!");
      setLoading(false);
      return;
    }

    try {
      if (isRegister) {
        const res = await api.post("/auth/register", {
          username: formData.username,
          password: formData.password,
        });
        setMessage(
          res.data.message || "✅ Registered successfully! Please login."
        );
        setIsRegister(false);
        setFormData({ username: "", password: "", confirmPassword: "" });
      } else {
        const res = await api.post("/auth/login", {
          username: formData.username,
          password: formData.password,
        });
        if (res.data.token) {
          localStorage.setItem("token", res.data.token);
          setToken(res.data.token); // ✅ triggers Dashboard
        } else {
          setError("Login successful, but no token was received.");
        }
      }
    } catch (err) {
      const errorMessage =
        err.response?.data?.message ||
        "❌ An error occurred. Please try again.";
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  // ✅ Redirect to Dashboard if logged in
  if (isTokenValid(token)) {
    return <Dashboard onLogout={() => setToken(null)} />;
  }

  // --- Login / Register Form ---
  return (
    <div
      className="d-flex align-items-center justify-content-center"
      style={{
        minHeight: "100vh",
        background: "linear-gradient(to right, #e0c3fc, #8ec5fc)",
      }}
    >
      <Container>
        <Card
          style={{ maxWidth: "450px", margin: "auto", borderRadius: "15px" }}
          className="shadow-lg"
        >
          <Card.Body className="p-5">
            <h2 className="text-center fw-bold mb-4">
              {isRegister ? "🍬 Create Account" : "🔐 Welcome Back"}
            </h2>

            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3" controlId="username">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  name="username"
                  placeholder="Enter username"
                  value={formData.username}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="password">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  placeholder="Password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              {isRegister && (
                <Form.Group className="mb-4" controlId="confirmPassword">
                  <Form.Label>Confirm Password</Form.Label>
                  <Form.Control
                    type="password"
                    name="confirmPassword"
                    placeholder="Confirm password"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    required
                  />
                </Form.Group>
              )}

              {message && (
                <Alert variant="success" className="mt-4">
                  {message}
                </Alert>
              )}
              {error && (
                <Alert variant="danger" className="mt-4">
                  {error}
                </Alert>
              )}

              <div className="d-grid mt-4">
                <Button variant="primary" type="submit" disabled={loading}>
                  {loading
                    ? isRegister
                      ? "Registering..."
                      : "Logging in..."
                    : isRegister
                    ? "Register"
                    : "Login"}
                </Button>
              </div>
            </Form>

            <div className="text-center mt-3">
              <Button
                variant="link"
                onClick={() => {
                  setIsRegister(!isRegister);
                  setMessage("");
                  setError("");
                }}
              >
                {isRegister
                  ? "Already have an account? Login"
                  : "Don't have an account? Register"}
              </Button>
            </div>
          </Card.Body>
        </Card>
      </Container>
    </div>
  );
}
