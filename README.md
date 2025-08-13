# Learn Data Structures - Android App

**Learn Data Structures** is an educational Android application that presents data structure concepts interactively and in a simple way. The app is developed using **Java** and follows the **MVVM** architecture.

---

## 🔹 User Flow & UX

### 1. Splash Screen (IntroActivity)
After launching the app, the splash screen displays the app logo and name.  
*Figure 1*

### 2. Main Screen (MainActivity)
The main screen includes a bottom navigation bar with two tabs, each displaying a separate fragment:
- **Lessons Tab (LessonsFragment)**
- **Settings Tab (ProfileFragment)**

### 3. Lessons Tab
- Shows a complete list of lessons with categories at the top. Users can select a lesson. *Figure 2*
- Selecting a lesson reveals the following options: *Figure 3*

#### Text Lessons
- Multi-page textual content for each lesson. *Figure 4*
- After reading, the lesson is marked as "Read" with a checkmark.

#### Multiple-Choice Quiz
- Displays a set of multiple-choice questions. *Figure 5*
- User's score is saved after completion and the quiz status changes to "Completed."

#### Coding Quiz
- Users submit their answers in Python code and send it to an API for execution. *Figure 6*
- Features include:
  - Hint for solving exercises
  - Editor theme selection (Light / Dark)
  - Displaying results after submission

#### Score Display
- Scores are displayed next to each quiz, and the status is marked with a checkmark.

### 4. Settings Tab
Includes app settings: *Figure 7*
- Change app language (Persian / English) *Figure 8*
- Change app theme (Light / Dark) *Figure 9*
- Sound management (On / Off)
- Study reminder:
  - Enable/disable daily reminders *Figure 10*
  - Select notification time
- Send feedback via email
- Share the app with others
- Gamification: list of user achievements *Figure 11*

---

## 🔹 Development Environment

- **IDE:** Android Studio Hedgehog | 2023.1.1 Patch 2  
- **Gradle version:** 8.0  
- **Android Gradle Plugin version:** 8.2.2  
- **Target SDK:** Android 14 (API 34)  
- **Min SDK:** Android 7.0 (API 24)  

Android Studio provides emulator, UI design tools, debugging, and Gradle management.

---

## 🔹 Programming Language

- **Java**: Officially supported for Android development

---

## 🔹 Libraries Used

| Library | Version | Purpose |
|---------|--------|---------|
| androidx.recyclerview:recyclerview | 1.3.2 | Scrollable lists in UI |
| androidx.room:room-runtime & room-compiler | 2.6.1 | Local SQLite database with ORM |
| com.google.code.gson:gson | 2.8.8 | JSON parsing & generation |
| com.squareup.okhttp3:okhttp | 4.12.0 | HTTP requests (e.g., send code to API) |
| androidx.transition:transition | 1.4.1 | UI transition animations |
| com.airbnb.android:lottie | 6.0.0 | High-quality JSON-based animations |
| com.google.android.gms:play-services-ads | 22.6.0 | AdMob ads integration |
| junit:junit | 4.13.2 | Unit testing |
| androidx.test.ext:junit & espresso-core | 1.2.1 / 3.6.1 | UI testing |

---

## 🔹 Software Architecture

- **MVVM (Model-View-ViewModel)** for separation of concerns and easier maintenance:
  - **Model:** Data classes and JSON file management
  - **ViewModel:** Processes data and connects Model to View
  - **View:** XML layouts, Activities, and Fragments
  - **Data:** Local data management (SharedPreferences, user settings)
  - **Utils:** Helper functions and common utilities

---

## 🔹 Installation & Running the App

### Accessing the Source Code
[GitHub Repository](https://github.com/homa-mh/Learning-android-app)

```bash
git clone https://github.com/homa-mh/Learning-android-app
