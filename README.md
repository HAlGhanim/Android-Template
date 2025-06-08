# AndroidTemplate

A modern, scalable Android project template built with **Jetpack Compose**, **ViewModel**, **Retrofit**, and **SharedPreferences** authentication.

This template is designed to be the starting point for full-scale apps with clean architecture, reusable components, and dependency separation.

---

## ✨ Features

- ✅ Jetpack Compose UI
- ✅ Shared ViewModel state (loading, error)
- ✅ Token-based authentication
- ✅ Persistent login via `SharedPreferences`
- ✅ Retrofit with automatic token injection
- ✅ Modular navigation system
- ✅ Manual dependency injection (no Hilt)
- ✅ Ready to scale with more APIs and ViewModels

---

## 📦 Tech Stack

- **Jetpack Compose** – UI Toolkit
- **ViewModel & State** – State management
- **Retrofit** – API client
- **SharedPreferences** – Token persistence
- **Navigation Compose** – App routing
- **Material 3** – Modern design

---

## 📁 Project Structure
```
androidtemplate/
│
├── data/ # DTOs, requests, and responses
├── network/ # Retrofit, interceptors, api services
├── navigation/ # Screens and NavGraph
├── ui/ # Composables and screens
├── utils/ # TokenManager, AppInitializer
├── viewmodels/ # ViewModel + BaseViewModel
└── MainActivity.kt # App entry point
```
---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/AndroidTemplate.git
cd AndroidTemplate
```
2. Rename your project
Search and replace all instances of:

androidtemplate → yourprojectname
com.example.androidtemplate → com.yourcompany.yourapp

3. Run the app
Open in Android Studio

Select your emulator or device

Press ▶️ "Run"

## 🔐 Authentication Setup

This project includes:

- `TokenManager` for saving/retrieving JWTs
- `AuthViewModel` that handles login/logout
- Automatic token injection via `TokenInterceptor`

On successful login:

- Token is saved via `SharedPreferences`
- User is auto-logged in on next app launch

> ⚠️ **Backend Requirements**: Your backend must include the following endpoints for this template to work:

```kotlin
// GET /users/me
@GetMapping("/users/me")
@PreAuthorize("isAuthenticated()")
fun getCurrentUser(): ResponseEntity<User> {
    val email = SecurityContextHolder.getContext().authentication.name
    val user = userRepository.findByEmail(email)
        ?: throw UsernameNotFoundException("User not found with email: $email")
    return ResponseEntity.ok(user)
}

// POST /login
@PostMapping("/login")
fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
    val authToken = UsernamePasswordAuthenticationToken(request.email, request.password)
    val authentication = authenticationManager.authenticate(authToken)

    if (authentication.isAuthenticated) {
        val user = userRepository.findByEmail(request.email)
            ?: throw UsernameNotFoundException("User not found")

        val token = jwtService.generateToken(request.email, user.role.name)
        return ResponseEntity.ok(AuthResponse(token = token, user = user))
    } else {
        throw UsernameNotFoundException("Invalid credentials")
    }
}

// DTOs

data class AuthRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val user: User)
```

💠 Add Your Own Features
To add a new screen:
Create ScreenNameScreen.kt under ui/screens

Add it to Screen.kt class

Add a composable() entry in AppNavigation.kt

To add a new API:
Create YourApiService.kt in network/

Add it in AppInitializer.kt

Inject it into your ViewModel

🤝 Contributing
Contributions are welcome! Feel free to open issues or submit PRs.

🧠 Maintained by
Humoud – @HAlGhanim
