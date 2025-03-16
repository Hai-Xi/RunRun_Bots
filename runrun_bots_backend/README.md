
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

### ***Frontend vs Backend Google Maps API Usage**

- **Frontend:** Google Maps SDK will be used to display the **real-time tracking map** within the user interface, allowing users to visually see the robot/drone moving towards its destination. [https://developers.google.com/maps/documentation/javascript/overview](https://developers.google.com/maps/documentation/javascript/overview)

- **Backend:** Google Directions API will be utilized to **compute optimized routes**, taking into account traffic conditions, drone no-fly zones, and robot-friendly paths. The backend will then return this data to the frontend for display.