import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";

function AddEditSweetModal({ show, handleClose, sweet, onSave }) {
  // State for the form
  const [formData, setFormData] = useState({
    name: "",
    category: "",
    price: "",
    quantity: "",
  });

  // Pre-fill if editing
  useEffect(() => {
    if (sweet) {
      setFormData({
        name: sweet.name || "",
        category: sweet.category || "",
        price: sweet.price?.toString() || "",
        quantity: sweet.quantity?.toString() || "",
      });
    } else {
      setFormData({
        name: "",
        category: "",
        price: "",
        quantity: "",
      });
    }
  }, [sweet]);

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Handle Save button
  const handleSubmit = () => {
    if (!formData.name || !formData.category || !formData.price || !formData.quantity) {
      alert("⚠️ All fields are required!");
      return;
    }

    // Pass sanitized data to parent
    onSave({
      ...formData,
      price: parseFloat(formData.price),
      quantity: parseInt(formData.quantity, 10),
    });

    handleClose();
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>{sweet ? "✏️ Edit Sweet" : "➕ Add New Sweet"}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3">
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              placeholder="Enter sweet name"
              value={formData.name}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Category</Form.Label>
            <Form.Control
              type="text"
              name="category"
              placeholder="Enter category"
              value={formData.category}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Price</Form.Label>
            <Form.Control
              type="number"
              step="0.01"
              name="price"
              placeholder="Enter price"
              value={formData.price}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Quantity</Form.Label>
            <Form.Control
              type="number"
              name="quantity"
              placeholder="Enter quantity"
              value={formData.quantity}
              onChange={handleChange}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Cancel
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          {sweet ? "Update Sweet" : "Add Sweet"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddEditSweetModal;
