# TO DO

| Area         | HTTP   | Path                                         | Description                                       |
| ------------ | ------ | -------------------------------------------- | ------------------------------------------------- |
| Auth         | POST   | /api/auth/register                           | Register new user, return JWT or success.         |
| Auth         | POST   | /api/auth/login                              | Login with email/username + password, return JWT. |
| Auth         | POST   | /api/auth/refresh                            | Refresh access token using refresh token.         |
| Auth         | POST   | /api/auth/logout                             | Invalidate tokens (optional, future).             |
| User         | GET    | /api/users/me                                | Get current user profile (from JWT).              |
| User         | PUT    | /api/users/me                                | Update own profile (name, phone, etc.).           |
| User         | GET    | /api/users/{id}                              | Get user details (admin only).                    |
| User         | GET    | /api/users                                   | List users with pagination (admin only).          |
| User         | PATCH  | /api/users/{id}/roles                        | Assign roles/permissions (admin only).            |
| Subscription | POST   | /api/users/me/subscriptions                  | Subscribe to product (or category) notifications. |
| Subscription | GET    | /api/users/me/subscriptions                  | List current subscriptions.                       |
| Subscription | DELETE | /api/users/me/subscriptions/{subscriptionId} | Unsubscribe.                                      |