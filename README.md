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

androidtemplate/
│
├── data/ # DTOs, requests, and responses
├── network/ # Retrofit, interceptors, api services
├── navigation/ # Screens and NavGraph
├── ui/ # Composables and screens
├── utils/ # TokenManager, AppInitializer
├── viewmodels/ # ViewModel + BaseViewModel
└── MainActivity.kt # App entry point

yaml
Copy
Edit

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

🔐 Authentication Setup
This project includes:

TokenManager for saving/retrieving JWTs

AuthViewModel that handles login/logout

Automatic token injection via TokenInterceptor

On successful login:

Token is saved via SharedPreferences

User is auto-logged in on next app launch

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
