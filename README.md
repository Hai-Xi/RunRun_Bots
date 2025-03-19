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


## **API Integration Requirements**

| **Feature**                         | **API Endpoint**            | **Method**  | **Description**                                    | **Json scheme**                                              |
| ----------------------------------- | --------------------------- | ----------- | -------------------------------------------------- | ------------------------------------------------------------ |
| User Authentication                 | /api/auth/login             | POST        | Authenticate user login                            | {email, passcode}                                            |
| User Registration                   | /api/auth/signup            | POST        | Register a new user                                | {name, email, passcode}                                      |
| List Recent Order                   | /api/orders/list            | GET         | Get recent historic order id                       | {{order_id},{order_time},{status}}      
| Get Order                           | /api/orders/get             | GET         | Get information of a confirmed order from order id | {departure_address, destination_address, dispatch_address,route_id,bot_option, payment_amount,status, estimated_time} |                                          |
| Transport Order Info for Estimation (REMOVE) | /api/orders/estimate        | POST        | Transport Order Info for Estimation                | {departure_address, destination_address, bot_option}         |
| Get Delivery Options (REMOVE)               | /api/orders/recommendations | GET         | Fetch optimized delivery options                   | {dispatch_address,route_id,bot_option, payment_amount, estimated_time} |
| Confirm Order                        | /api/orders/confirm         | POST         | Confirm Order & Payment and Generate New Order ID                        | {departure_address, destination_address, dispatch_address,route_id,bot_option, order_time, payment_amount,status, estimated_time, payment_amount, payment_type}                                  |
| Update Status                        | /api/orders/status_update         | POST         | Update Order Status based on frontend real-time tracking                        | {order_id, current_time, status}                                  |

