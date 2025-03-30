import React, { useState, useEffect, useRef } from "react";
import { Modal, Button, Form } from "react-bootstrap";

function AddressValidator({ label, address, setAddress, setLatLng }) {
    const [showModal, setShowModal] = useState(false);
    const [candidates, setCandidates] = useState([]);
    const [error, setError] = useState("");
    const [validated, setValidated] = useState(false); // ✅ Step 1: New validated state

    const geocoderRef = useRef(null);

    useEffect(() => {
        if (window.google && window.google.maps && window.google.maps.Geocoder) {
            geocoderRef.current = new window.google.maps.Geocoder();
        }
    }, []);

    // ✅ This handles input change and reset validated properly
    const handleInputChange = (e) => {
        setAddress(e.target.value);
        setValidated(false); // ✅ Step 3: Reset only when user types
    };

    const handleValidate = () => {
        if (!geocoderRef.current) {
            setError("Google Maps API is not ready");
            return;
        }

        if (!address.trim()) {
            setError("Please enter an address.");
            return;
        }

        geocoderRef.current.geocode({ address: address }, (results, status) => {
            if (status === "OK" && results.length > 0) {
                setCandidates(results);
                setShowModal(true);
                setError("");
            } else {
                setError("No valid address found.");
                setCandidates([]);
                setShowModal(false);
            }
        });
    };

    const handleSelect = (result) => {
        setAddress(result.formatted_address);
        setLatLng(result.geometry.location.toJSON());
        setShowModal(false);
        setValidated(true); // ✅ Step 2: Mark as validated after selection
    };

    return (
        <Form.Group className="mb-3">
            <Form.Label>{label}</Form.Label>
            <div style={{ display: "flex", gap: "8px" }}>
                <Form.Control
                    type="text"
                    placeholder="Enter address"
                    value={address}
                    onChange={handleInputChange}
                />
                <Button
                    variant={validated ? "secondary" : "outline-primary"}
                    onClick={handleValidate}
                    disabled={validated}
                >
                    {validated ? "Validated" : "Validate"}
                </Button>
            </div>
            {error && <div style={{ color: "red", marginTop: "5px" }}>{error}</div>}

            <Modal show={showModal} onHide={() => setShowModal(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Address Suggestions</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Please select the correct address:</p>
                    <ul style={{ paddingLeft: "20px" }}>
                        {candidates.map((result, index) => (
                            <li
                                key={index}
                                style={{ marginBottom: "10px", cursor: "pointer" }}
                                onClick={() => handleSelect(result)}
                            >
                                {result.formatted_address}
                            </li>
                        ))}
                    </ul>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>Close</Button>
                </Modal.Footer>
            </Modal>
        </Form.Group>
    );
}

export default AddressValidator;
