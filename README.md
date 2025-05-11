# 📱 Colluding Android Apps – Calculator & Flashlight

A research-focused Android project demonstrating how seemingly benign apps can collaborate to exfiltrate user data. Developed during internship to showcase malicious app behavior using inter-app communication.

---

## 🔍 Overview

This project consists of **two Android applications**:
1. **Calculator App** – Functions like a normal calculator but with access to **SMS messages**.
2. **Flashlight App** – Turns on the flashlight and has **internet access** for data transmission.

While each app appears innocent, they **collude** in the background:
- **Calculator** reads SMS data and passes it to **Flashlight** using a `HashMap`.
- **Flashlight** then transmits this data to a remote server using its internet access.

This structure simulates how malicious apps bypass permission restrictions through cooperation.

---

## 🧠 Purpose

- Highlight the risks of data leakage via **colluding apps**
- Demonstrate how **permissions split across apps** can be abused
- Explore **inter-process communication (IPC)** and covert data sharing techniques in Android

---

## 🛠️ Tech Stack

- Java (Android SDK)
- Android Studio
- Gradle Build System

---

## 📂 Folder Structure

project-root/
├── Calculator-app/ # Contains Calculator App code
├── app/ # May include shared resources or Flashlight App
├── gradle/ # Gradle wrapper settings
├── .idea/ # IDE configurations
├── build.gradle # Gradle build file
└── README.md

---

## 🚀 How to Run

1. Clone the repository:
git clone https://github.com/yourusername/colluding-apps-demo.git

2. Open the project in Android Studio.
3. Build and run both apps on an emulator or device with SMS and internet permissions enabled.


⚠️ Disclaimer
This project is strictly for Internship purposes under DRDO. It demonstrates how malicious behavior can be hidden in plain sight and why careful permission auditing is necessary when installing apps.

📌 Key Learnings
Android permission handling
Inter-app communication and data leakage vectors
Security implications of app collusion


