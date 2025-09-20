import React, { useState, useEffect, useCallback } from "react";
import {
  Container,
  Navbar,
  Nav,
  Button,
  Row,
  Col,
  Form,
  FormControl,
  Alert,
  Spinner,
  Modal,
} from "react-bootstrap";
import * as jwtDecode from "jwt-decode"; 
import api from "../api"; // axios instance with baseURL
import SweetCard from "./SweetCard";
import AddEditSweetModal from "./AddEditSweetModal";

export default function Dashboard({ token, onLogout }) {
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [user, setUser] = useState(null);

  // Search state
  const [searchParams, setSearchParams] = useState({
    name: "",
    category: "",
    minPrice: "",
    maxPrice: "",
  });

  // Modal state
  const [showModal, setShowModal] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);

  // Promote modal
  const [showPromote, setShowPromote] = useState(false);
  const [usernameToPromote, setUsernameToPromote] = useState("");

  // Fetch sweets
  const fetchSweets = useCallback(async () => {
    setLoading(true);
    try {
      const response = await api.get("/sweets/search", {
        params: searchParams,
        headers: { Authorization: `Bearer ${token}` },
      });
      setSweets(response.data);
      setError("");
    } catch (err) {
      console.error(err);
      setError("Failed to fetch sweets. Please try again later.");
      setSweets([]);
    } finally {
      setLoading(false);
    }
  }, [searchParams, token]);

  // Decode JWT to get user info
  useEffect(() => {
    if (!token) return;
    try {
     const decoded = jwtDecode.default(token);
      setUser({ username: decoded.sub, role: decoded.role });
      fetchSweets();
    } catch (err) {
      console.error("Invalid token", err);
      onLogout();
    }
  }, [token, fetchSweets, onLogout]);

  const handleSearchChange = (e) => {
    setSearchParams({ ...searchParams, [e.target.name]: e.target.value });
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    fetchSweets();
  };

  // USER actions
  const handlePurchase = async (sweetId) => {
    try {
      await api.post(
        `/sweets/${sweetId}/purchase?quantity=1`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchSweets();
    } catch (err) {
      alert(err.response?.data?.message || "Purchase failed.");
    }
  };

  // ADMIN actions
  const handleAddSweet = () => {
    setEditingSweet(null);
    setShowModal(true);
  };

  const handleEditSweet = (sweet) => {
    setEditingSweet(sweet);
    setShowModal(true);
  };

  const handleDeleteSweet = async (sweetId) => {
    if (!window.confirm("Are you sure to delete this sweet?")) return;
    try {
      await api.delete(`/sweets/${sweetId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchSweets();
    } catch (err) {
      alert(err.response?.data?.message || "Failed to delete sweet.");
    }
  };

  const handleRestock = async (sweetId) => {
    const qty = prompt("Enter quantity to restock:");
    if (!qty || isNaN(qty) || qty <= 0) return;
    try {
      await api.post(
        `/sweets/${sweetId}/restock?quantity=${qty}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchSweets();
    } catch (err) {
      alert(err.response?.data?.message || "Failed to restock.");
    }
  };

  const handlePromote = async () => {
    if (!usernameToPromote) return;
    try {
      await api.post(
        `/auth/promote`,
        { username: usernameToPromote },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert(`${usernameToPromote} promoted to Admin!`);
      setUsernameToPromote("");
      setShowPromote(false);
    } catch (err) {
      alert(err.response?.data?.message || "Failed to promote user.");
    }
  };

  return (
    <div style={{ minHeight: "100vh", background: "#f8f9fa" }}>
      {/* Navbar */}
      <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
        <Container>
          <Navbar.Brand>🍬 Sweet Shop</Navbar.Brand>
          <Navbar.Toggle />
          <Navbar.Collapse>
            <Nav className="me-auto">
              <Navbar.Text>
                Welcome, {user?.username} ({user?.role})
              </Navbar.Text>
            </Nav>
            <Button variant="outline-light" onClick={onLogout}>
              Logout
            </Button>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container className="mt-4">
        {/* Search / Filter */}
        <div className="p-4 bg-white rounded shadow-sm mb-4">
          <Form onSubmit={handleSearchSubmit}>
            <Row className="g-3 align-items-center">
              <Col sm={6} md={3}>
                <FormControl
                  name="name"
                  placeholder="Search by name"
                  value={searchParams.name}
                  onChange={handleSearchChange}
                />
              </Col>
              <Col sm={6} md={3}>
                <FormControl
                  name="category"
                  placeholder="Category"
                  value={searchParams.category}
                  onChange={handleSearchChange}
                />
              </Col>
              <Col sm={6} md={2}>
                <FormControl
                  name="minPrice"
                  type="number"
                  placeholder="Min Price"
                  value={searchParams.minPrice}
                  onChange={handleSearchChange}
                />
              </Col>
              <Col sm={6} md={2}>
                <FormControl
                  name="maxPrice"
                  type="number"
                  placeholder="Max Price"
                  value={searchParams.maxPrice}
                  onChange={handleSearchChange}
                />
              </Col>
              <Col md={2} className="d-grid">
                <Button type="submit">Filter</Button>
              </Col>
            </Row>

            {/* Admin tools */}
            {user?.role === "ADMIN" && (
              <div className="mt-3 d-flex justify-content-between">
                <Button onClick={handleAddSweet}>+ Add New Sweet</Button>
                <Button variant="warning" onClick={() => setShowPromote(true)}>
                  Promote User ➡️ Admin
                </Button>
              </div>
            )}
          </Form>
        </div>

        {error && <Alert variant="danger">{error}</Alert>}

        {/* Sweet List */}
        {loading ? (
          <div className="text-center p-5">
            <Spinner animation="border" />
          </div>
        ) : (
          <Row xs={1} md={2} lg={3} className="g-4">
            {sweets.length ? (
              sweets.map((sweet) => (
                <Col key={sweet.id}>
                  <SweetCard
                    sweet={sweet}
                    isAdmin={user?.role === "ADMIN"}
                    onPurchase={handlePurchase}
                    onEdit={handleEditSweet}
                    onDelete={handleDeleteSweet}
                    onRestock={handleRestock}
                  />
                </Col>
              ))
            ) : (
              <p className="text-center w-100 mt-4">
                No sweets found. Try searching or add a new sweet!
              </p>
            )}
          </Row>
        )}
      </Container>

      {/* Add/Edit Modal */}
      {user?.role === "ADMIN" && (
        <AddEditSweetModal
          show={showModal}
          handleClose={() => setShowModal(false)}
          sweet={editingSweet}
          onSave={fetchSweets}
          token={token}
        />
      )}

      {/* Promote Modal */}
      <Modal show={showPromote} onHide={() => setShowPromote(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Promote User to Admin</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group>
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter username"
              value={usernameToPromote}
              onChange={(e) => setUsernameToPromote(e.target.value)}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowPromote(false)}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handlePromote}>
            Promote
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
