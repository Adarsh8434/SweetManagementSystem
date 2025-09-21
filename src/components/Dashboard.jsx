import React, { useState, useEffect, useCallback } from "react";
import {
  Container, Navbar, Nav, Button, Row, Col,
  Form, FormControl, Alert, Spinner, Modal
} from "react-bootstrap";
import { jwtDecode } from "jwt-decode";
import api from "../api";
import SweetCard from "./SweetCard";
import AddEditSweetModal from "./AddEditSweetModal";

export default function Dashboard({ token: propToken, onLogout }) {
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [user, setUser] = useState(null);

  // 👇 Use token from props OR fallback to localStorage
  const token = propToken || localStorage.getItem("token");

  const [searchParams, setSearchParams] = useState({
    name: "",
    category: "",
    minPrice: "",
    maxPrice: "",
  });

  const [showModal, setShowModal] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);

  const [showPromote, setShowPromote] = useState(false);
  const [usernameToPromote, setUsernameToPromote] = useState("");

  // ------------------ Fetch Sweets ------------------
  const fetchSweets = useCallback(
    async (useFilter = false) => {
      if (!useFilter) setLoading(true); 
      try {
        const url = useFilter ? "/sweets/search" : "/sweets";
        const response = await api.get(url, { params: useFilter ? searchParams : {} });
        setSweets(response.data);
        setError("");
      } catch (err) {
        console.error(err);
        setError("Failed to fetch sweets. Try again later.");
        setSweets([]);
      } finally {
        if (!useFilter) setLoading(false);
      }
    },
    [searchParams]
  );
  console.log("Dashboard received token:", token);

  // ------------------ JWT Decode ------------------
  useEffect(() => {
    if (!token) return;
    try {
      const decoded = jwtDecode(token);
      console.log("Decoded token:", decoded);
        const role = decoded.role?.startsWith("ROLE_") 
      ? decoded.role.substring(5) 
      : decoded.role;
      setUser({ username: decoded.sub, role });
      fetchSweets(false); // initial load: all sweets
    } catch (err) {
      console.error("Invalid token", err);
      onLogout();
    }
  }, [token, fetchSweets, onLogout]);

  // ------------------ Auto Filter on change ------------------
  useEffect(() => {
    const isEmptyFilter = Object.values(searchParams).every(v => !v);
    fetchSweets(!isEmptyFilter); // if any filter is set, use filtered endpoint
  }, [searchParams, fetchSweets]);

  // ------------------ Handlers ------------------
  const handleSearchChange = (e) =>
    setSearchParams({ ...searchParams, [e.target.name]: e.target.value });

  const handlePurchase = async (sweetId) => {
    try {
      await api.post(`/sweets/${sweetId}/purchase?quantity=1`);
      setSweets(prev =>
        prev.map(s => s.id === sweetId ? { ...s, quantity: s.quantity - 1 } : s)
      );
    } catch (err) {
      alert(err.response?.data?.message || "Purchase failed.");
    }
  };
  const handleOpenAddModal = () => {
  setEditingSweet(null); // clear any editing
  setShowModal(true);
};


 const handleSaveSweet = async (formData) => {
  try {
    if (editingSweet) {
      // Update
      await api.put(`/sweets/${editingSweet.id}`, formData);
    } else {
      // Add new
      await api.post(`/sweets`, formData);
    }
    fetchSweets(false); // refresh list
  } catch (err) {
    alert(err.response?.data?.message || "Failed to save sweet.");
  }
};

  const handleEditSweet = (sweet) => { setEditingSweet(sweet); setShowModal(true); };
  const handleDeleteSweet = async (sweetId) => {
    if (!window.confirm("Are you sure to delete this sweet?")) return;
    try {
      await api.delete(`/sweets/${sweetId}`);
      setSweets(prev => prev.filter(s => s.id !== sweetId));
    } catch (err) {
      alert(err.response?.data?.message || "Failed to delete sweet.");
    }
  };
  const handleRestock = async (sweetId) => {
    const qty = prompt("Enter quantity to restock:");
    if (!qty || isNaN(qty) || qty <= 0) return;
    try {
      await api.post(`/sweets/${sweetId}/restock?quantity=${qty}`);
      setSweets(prev => prev.map(s => s.id === sweetId ? { ...s, quantity: s.quantity + Number(qty) } : s));
    } catch (err) {
      alert(err.response?.data?.message || "Failed to restock.");
    }
  };
  const handlePromote = async () => {
    if (!usernameToPromote) return;
    try {
    await api.post(`/admin/promote/${usernameToPromote}`);

      alert(`${usernameToPromote} promoted to Admin!`);
      setUsernameToPromote("");
      setShowPromote(false);
    } catch (err) {
      alert(err.response?.data?.message || "Failed to promote user.");
    }
  };

  // ------------------ Render ------------------
  return (
    <div style={{ minHeight: "100vh", background: "#f8f9fa" }}>
      <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
        <Container>
          <Navbar.Brand>🍬 Sweet Shop</Navbar.Brand>
          <Navbar.Toggle />
          <Navbar.Collapse>
            <Nav className="me-auto">
              <Navbar.Text>Welcome, {user?.username} ({user?.role})</Navbar.Text>
            </Nav>
            <Button variant="outline-light" onClick={onLogout}>Logout</Button>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container className="mt-4">
        <div className="p-4 bg-white rounded shadow-sm mb-4">
          <Form>
            <Row className="g-3 align-items-center">
              {["name", "category", "minPrice", "maxPrice"].map((field) => (
                <Col sm={6} md={field.includes("Price") ? 2 : 3} key={field}>
                  <FormControl
                    name={field}
                    placeholder={field === "name" ? "Search by name" : field === "category" ? "Category" : field}
                    type={field.includes("Price") ? "number" : "text"}
                    value={searchParams[field]}
                    onChange={handleSearchChange}
                  />
                </Col>
              ))}
            </Row>

            {user?.role === "ADMIN" && (
              <div className="mt-3 d-flex justify-content-between">
                <Button onClick={handleOpenAddModal}>+ Add New Sweet</Button>
                <Button variant="warning" onClick={() => setShowPromote(true)}>Promote User ➡️ Admin</Button>
              </div>
            )}
          </Form>
        </div>

        {error && <Alert variant="danger">{error}</Alert>}

        {loading ? (
          <div className="text-center p-5"><Spinner animation="border" /></div>
        ) : (
          <Row xs={1} md={2} lg={3} className="g-4">
            {sweets.length ? sweets.map((sweet) => (
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
            )) : <p className="text-center w-100 mt-4">No sweets found.</p>}
          </Row>
        )}
      </Container>

      {user?.role === "ADMIN" && (
        <AddEditSweetModal
          show={showModal}
          handleClose={() => setShowModal(false)}
          sweet={editingSweet}
          onSave={handleSaveSweet}
          token={token}
        />
      )}

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
          <Button variant="secondary" onClick={() => setShowPromote(false)}>Cancel</Button>
          <Button variant="primary" onClick={handlePromote}>Promote</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
