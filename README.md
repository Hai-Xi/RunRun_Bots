# RunRun Bots
RunRun Bots is an intelligent dispatch and delivery management system that leverages autonomous ground robots and drones to optimize small and medium-sized package deliveries.


## **API Integration Requirements**

|      | **Feature**                | **API Endpoint**            | **Method**  | **Description**                      |
| ---- | -------------------------- | --------------------------- | ----------- | ------------------------------------ |
| ✅    | User Authentication        | /api/auth/login             | POST        | Authenticate user login              |
| 🟩    | User Registration          | /api/auth/signup            | POST        | Register a new user                  |
| 🟩    | Fetch User Profile         | /api/user/profile           | GET         | Retrieve user profile details        |
| 🟩    | Update User Profile        | /api/user/profile/update    | PUT         | Modify user details                  |
| 🟦    | Create Order               | /api/orders/create          | POST        | Place a new delivery order           |
| 🟦    | Get Delivery Options       | /api/orders/recommendations | GET         | Fetch optimized delivery options     |
| 🟨    | Confirm Order & Payment    | /api/orders/confirm         | POST        | Confirm order and process payment    |
| 🟧    | Order Tracking             | /api/orders/track/:id       | GET         | Get real-time tracking info          |
| 🟧    | Order History              | /api/orders/history         | GET         | Fetch past delivery orders           |
|      | Google Maps API (Frontend) | Google Maps SDK             | Client-side | Display real-time tracking interface |
|      | Google Maps API (Backend)  | Google Directions API       | Server-side | Compute optimal delivery routes      |
