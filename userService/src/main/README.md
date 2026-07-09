# ✅ Completed Modules

- **JWT Authentication**
- **Refresh Token**
- **Logout**
- **Email Verification**
- **Forgot/Reset Password**
- **RabbitMQ Integration**
- **Notification Events**
- **Global Exception Handling**
- **Validation**
- **Soft Delete**
- **Auditing**

# TO DO

| Area         | HTTP   | Path                                         | Description                                       | Status       |
| ------------ | ------ | -------------------------------------------- | ------------------------------------------------- |--------------|
| Auth         | POST   | /api/auth/register                           | Register new user, return JWT or success.         | Done         |
| Auth         | POST   | /api/auth/login                              | Login with email/username + password, return JWT. | Done         |
| Auth         | POST   | /api/auth/refresh                            | Refresh access token using refresh token.         | Done         |
| Auth         | POST   | /api/auth/logout                             | Invalidate tokens (optional, future).             | Done         |
| User         | GET    | /api/users/me                                | Get current user profile (from JWT).              | In progress  |
| User         | PUT    | /api/users/me                                | Update own profile (name, phone, etc.).           | In progress  |
| User         | GET    | /api/users/{id}                              | Get user details (admin only).                    | In progress  |
| User         | GET    | /api/users                                   | List users with pagination (admin only).          | In progress  |
| User         | PATCH  | /api/users/{id}/roles                        | Assign roles/permissions (admin only).            | In progress  |
| Subscription | POST   | /api/users/me/subscriptions                  | Subscribe to product (or category) notifications. | In progress  |
| Subscription | GET    | /api/users/me/subscriptions                  | List current subscriptions.                       | In progress  |
| Subscription | DELETE | /api/users/me/subscriptions/{subscriptionId} | Unsubscribe.                                      | In progress  |