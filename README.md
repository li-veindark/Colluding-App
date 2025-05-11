# 🔐 DRDO Internship Project – Colluding Android Apps (Calculator & Flashlight)

> **Developed as part of a research internship under DRDO**, this project demonstrates the covert behavior of colluding Android apps to highlight data leakage threats and enhance national mobile security awareness.
---

## 🛡️ Project Context

This Android project was built under the supervision of the **Defence Research and Development Organisation (DRDO)** to simulate how two seemingly benign mobile applications can **collaborate to leak sensitive user data**, posing serious threats to **national and device-level security**.

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

- **Simulate malicious app behavior** for cybersecurity research
- **Educate** about covert inter-app communication
- **Enhance awareness** of Android permission misuse and app collusion
- Contribute towards **national mobile application threat analysis**

  
---

## 🛠️ Tech Stack

- Java (Android SDK)
- Android Studio
- Gradle Build System
- HashMap-based data exchange

---

## 📂 Folder Structure

project-root/
![image](https://github.com/user-attachments/assets/3c17793b-4f47-496d-a6d5-b0acaa5149bb)

---

## 🚀 How to Run

1. Clone the repository:
git clone https://github.com/li-veindark/Colluding-App/tree/master

2. Open the project in Android Studio.
3. Build and run both apps on an emulator or device with SMS and internet permissions enabled.

---

⚠️ Disclaimer
This project is strictly for **research and educational use** under the DRDO internship program. It is **not intended for public deployment** or malicious use in any form. All activities were conducted in a **controlled environment** for national security evaluation.

---
📌 Key Learnings
Android permission handling
Inter-app communication and data leakage vectors
Security implications of app collusion


