import React from 'react';
import { Container, Card, Form, Button } from 'react-bootstrap';

const Register = () => {
  return (
      <Container
          className="d-flex justify-content-center align-items-center vh-100"
          style={{ backgroundColor: '#BDBDBD' }}
      >
        <Card className="p-4 shadow-lg" style={{ width: '500px', borderRadius: '12px' }}>
          {/* Logo */}
          <div className="mb-3 text-center">
            <img src="/logo.png" alt="Logo" style={{ width: '150px' }} />
          </div>

          {/* Form */}
          <Form>
            <Form.Group controlId="name">
              <Form.Label className="fw-bold">Name</Form.Label>
              <Form.Control type="text" placeholder="Enter your name" />
            </Form.Group>

            <Form.Group controlId="email" className="mt-3">
              <Form.Label className="fw-bold">Email</Form.Label>
              <Form.Control type="email" placeholder="Enter your email" />
            </Form.Group>

            <Form.Group controlId="password" className="mt-3">
              <Form.Label className="fw-bold">Password</Form.Label>
              <Form.Control type="password" placeholder="Enter your password" />
            </Form.Group>

            <Button className="w-100 mt-4" variant="dark">
              Create An Account
            </Button>
          </Form>

          {/* Login redirect */}
          <div className="mt-3 d-flex justify-content-between align-items-center">
          <span className="text-muted" style={{ fontSize: '0.9rem' }}>
            Already a User?
          </span>
            <Button variant="dark" size="sm" style={{ minWidth: '150px' }}>
              Login
            </Button>
          </div>
        </Card>
      </Container>
  );
};

export default Register;
