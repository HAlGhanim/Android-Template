# Android Template

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
├── data/
│ ├── dtos/
│ │ └── User.kt # Data class representing a user
│ ├── requests/
│ │ └── RegisterRequest.kt # Request payload for user registration
│ └── responses/
│ └── TokenResponse.kt # Response payload for JWT token
│
├── navigation/
│ ├── AppNavigation.kt # Main NavHost and navigation graph
│ └── Screen.kt # Sealed class for all navigation routes
│
├── network/
│ ├── AuthApiService.kt # Retrofit interface for auth-related API calls
│ ├── RetrofitHelper.kt # Singleton for Retrofit instance setup
│ └── TokenInterceptor.kt # Interceptor to add JWT token to requests
│
├── ui/
│ ├── composables/
│ │ └── LoadingIndicator.kt # Reusable loading indicator composable
│ ├── screens/
│ │ ├── HomeScreen.kt # Home screen shown after login
│ │ └── LoginScreen.kt # Login form screen
│ └── theme/
│ ├── Color.kt # Color definitions
│ ├── Theme.kt # AppTheme wrapper
│ └── Type.kt # Typography settings
│
├── utils/
│ ├── AppInitializer.kt # Provides token manager and services manually
│ └── TokenManager.kt # Manages saving/retrieving JWT tokens
│
├── viewmodels/
│ ├── AuthViewModel.kt # ViewModel handling login and session state
│ └── BaseViewModel.kt # Base class for shared loading/error state
│
└── MainActivity.kt # Entry point for Compose app
```
---

## 🚀 Getting Started
### 1. Fork this repository

Go to the GitHub repository and click Fork.

### 2. Choose this template when creating your repo then Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/YourAppName.git
cd YourAppName
```
### 3. Rename your project

Use the provided script to rename the project
```bash
chmod +x rename-template.sh
./rename-template.sh yourprojectname com.yourcompany.yourprojectname
```
Example:
```bash
./rename-template.sh capstone com.yourcompany.capstone
```

This will automatically replace:
```bash
androidtemplate             → yourprojectname
AndroidTemplate             → YourProjectName
com.example.androidtemplate → com.yourcompany.yourapp
```
It will work even if it displays red words or unresolved references.

After running the script, make sure to sync Gradle and then invalidate cache for it to fully apply.

### 4a. Remove admin role from User DTO and Login screen if no roles are implemented. (optional)

### 4b. Run the app
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

        val token = jwtService.generateToken(request.email, user.role.name) // remove role if not needed in your controller and jwtservice
        return ResponseEntity.ok(AuthResponse(token = token, user = user))
    } else {
        throw UsernameNotFoundException("Invalid credentials")
    }
}

// DTOs

data class AuthRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val user: User)
```

## 💠 Add Your Own Features
To add a new screen:
Create ScreenNameScreen.kt under ui/screens

- Add it to Screen.kt class

- Add a composable() entry in AppNavigation.kt

To add a new API:
- Create YourApiService.kt in network/

- Add it in AppInitializer.kt

- Inject it into your ViewModel

## 🤝 Contributing
Contributions are welcome! Feel free to open issues or submit PRs.

## 🧠 Maintained by
Humoud – @HAlGhanim
