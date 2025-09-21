// SweetCard.jsx
import React from "react";
import { Card, Button } from "react-bootstrap";

function SweetCard({ sweet, isAdmin, onPurchase, onEdit, onDelete }) {
  return (
    <Card className="shadow-sm">
      <Card.Body>
        <Card.Title>{sweet.name}</Card.Title>
        <Card.Text>
          Category: {sweet.category} <br />
          Price: {sweet.price} <br />
          Quantity: {sweet.quantity}
        </Card.Text>
        <div className="d-flex justify-content-between mt-3">
          <Button
            variant="primary"
            onClick={() => onPurchase(sweet.id)}
            disabled={sweet.quantity <= 0}
          >
            Purchase
          </Button>
          {isAdmin && (
            <>
              <Button variant="warning" onClick={() => onEdit(sweet)}>
                Edit
              </Button>
              <Button variant="danger" onClick={() => onDelete(sweet.id)}>
                Delete
              </Button>
            </>
          )}
        </div>
      </Card.Body>
    </Card>
  );
}

export default SweetCard; // ✅ default export
