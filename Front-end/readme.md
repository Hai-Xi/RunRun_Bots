# RunRun Bots - Frontend Design

## 1. Project Overview

This project aims to provide a seamless **robot & drone delivery service** for users in San Francisco via a **web application**. Users can place delivery orders, select optimized delivery methods, and track their shipments.

## 2. Target Platform

**Platform:** Web Application

## 3. User Flow & Proposed Pages

### 3.1 Authentication & User Profile

* **Login / Sign-up Page**  
  * Users log in via email & password  
  * Sign-up process for new users  
  * Engineering Decision – session-based or token-based?  
* **User Profile & Settings Page**  
  * Manage personal details, delivery addresses, payment methods

### 3.2 Delivery Booking & Recommendations

* **Order Placement Page**  
  * Enter package details (size, weight, type)  
  * Input pickup & delivery locations  
  * Choose urgency (standard/express)  
* **Delivery Options Recommendation Page**  
  * Suggested routes with estimated delivery times  
  * Availability of robots/drones  
  * Cost estimates for each option  
  * Delivery method selection (robot vs. drone)

### 3.3 Order Confirmation & Tracking

* **Order Confirmation Page**  
  * Displays final pricing and estimated time of arrival  
  * Confirm and process payment  
* **Order Tracking Page**  
  * Live map to track the real-time location of the assigned robot/drone  
  * Status updates (e.g., "Out for delivery," "Arrived at destination")  
* **Order History Page**  
  * View past deliveries with timestamps and details

## 4. User Story

### A Day in the Life of a User

Jane, a busy professional working in downtown San Francisco, needs to send an important document to her colleague across town. She opens the **Robot & Drone Delivery Web App** on her laptop and quickly logs in using her Google account.

1. **Placing an Order:** Jane navigates to the order placement page, enters the details of her package, and specifies her delivery requirements. She inputs the pickup and delivery addresses and selects "express delivery."  
2. **Receiving Delivery Recommendations:** The app instantly presents Jane with a list of optimized delivery options. One option is via a **ground robot**, which takes 45 minutes, while the other is via a **drone**, which takes only 20 minutes but costs slightly more. She chooses the drone delivery for speed.  
3. **Confirming & Paying:** Jane reviews the final cost and delivery time estimate, confirms her order, and securely pays using her saved payment method.  
4. **Tracking in Real-Time:** After a few minutes, Jane gets a notification that her drone is en route. She opens the tracking page and watches a **real-time map** of her package moving across the city, powered by **Google Maps API**.  
5. **Delivery Completion:** 20 minutes later, Jane receives another notification: "Your package has arrived!" She checks her doorstep and finds the document securely delivered. The app prompts her to **rate her experience** and logs the order in her **delivery history** for future reference.

This seamless process ensures Jane can quickly and efficiently send packages without hassle, enhancing her daily productivity.

## 5. API Integration Requirements

| Feature | API Endpoint | Method | Description |
| ----- | ----- | ----- | ----- |
| User Authentication | `/api/auth/login` | POST | Authenticate user login |
| User Registration | `/api/auth/signup` | POST | Register a new user |
| Fetch User Profile | `/api/user/profile` | GET | Retrieve user profile details |
| Update User Profile | `/api/user/profile/update` | PUT | Modify user details |
| Create Order | `/api/orders/create` | POST | Place a new delivery order |
| Get Delivery Options | `/api/orders/recommendations` | GET | Fetch optimized delivery options |
| Confirm Order & Payment | `/api/orders/confirm` | POST | Confirm order and process payment |
| Order Tracking | `/api/orders/track/:id` | GET | Get real-time tracking info |
| Order History | `/api/orders/history` | GET | Fetch past delivery orders |
| Google Maps API (Frontend) | `Google Maps SDK` | Client-side | Display real-time tracking interface |
| Google Maps API (Backend) | `Google Directions API` | Server-side | Compute optimal delivery routes |

### *Frontend vs Backend Google Maps API Usage

* **Frontend:** Google Maps SDK will be used to display the **real-time tracking map** within the user interface, allowing users to visually see the robot/drone moving towards its destination. [https://developers.google.com/maps/documentation/javascript/overview](https://developers.google.com/maps/documentation/javascript/overview)

* **Backend:** Google Directions API will be utilized to **compute optimized routes**, taking into account traffic conditions, drone no-fly zones, and robot-friendly paths. The backend will then return this data to the frontend for display.

## 6. Frontend Architecture

* **Frontend Framework:** React.js   
* **State Management:** Redux / React Context?  
* **Styling:** TailwindCSS?  
* **Real-time Tracking:** WebSockets / Firebase

## 7. Team Responsibilities & Work Split

| Task | Developer | Notes |
| :---- | :---- | :---- |
| UI/UX Design | UX Designer | Wireframes & prototypes |
| Authentication Flow | Frontend Dev A | Login, signup, session management |
| Order Placement & Recommendations | Frontend Dev B | Form handling, API calls for order details |
| Order Confirmation & Payment | Frontend Dev C | Payment integration, confirmation page |
| Order Tracking (Live Map) | Frontend Dev D | Real-time tracking UI, WebSockets integration, Google Maps SDK |
| Responsive Design & Testing | Frontend Dev E | Ensure mobile compatibility |
