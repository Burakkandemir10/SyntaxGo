# 🚀 SyntaxGo: A Distraction-Free Offline Code Reader

Reading plain text or source code files (.java, .cs, .py, etc.) on modern mobile devices is often a cumbersome experience, plagued by ad-heavy applications. SyntaxGo is a minimalist Android application designed to streamline the code-reading and exam-preparation workflow for software developers and computer science students by **eliminating all distracting elements**.

## 🎯 Why Was It Developed?
This project was born out of the necessity to read source code files transferred from computers to mobile devices in an offline environment, while maintaining professional IDE-grade (e.g., VS Code) syntax highlighting and formatting.

## ⚙️ Key Features
* **Modern File Access:** Secure and lightweight file selection using the Storage Access Framework (SAF), fully compliant with Android 10+ guidelines.
* **100% Offline Capability:** Integrated with `highlight.js` as a local asset, allowing the app to function perfectly underground, in subways, or anywhere without data consumption.
* **Advanced Syntax Highlighting:** Automatic detection and colorization of 190+ programming languages leveraging dynamic HTML/CSS manipulation inside a native WebView.
* **Minimalist UI:** A clean, focused user interface stripped of redundant elements to ensure maximum code readability.

## 🛠️ Tech Stack
* **Language:** Java (Android Native)
* **APIs & Frameworks:** Android SDK, Storage Access Framework (ContentResolver), WebKit (WebView)
* **Markup & Styling:** HTML, CSS, [Highlight.js](https://highlightjs.org/)

## 📸 Screenshots
<img width="393" height="883" alt="Ekran görüntüsü 2026-06-15 025742" src="https://github.com/user-attachments/assets/308e88d4-020c-4cfd-8fd7-884fec932a94" />
<img width="393" height="882" alt="Ekran görüntüsü 2026-06-15 025649" src="https://github.com/user-attachments/assets/53829b68-d842-4b73-8207-c66307b3f8e4" />
<img width="394" height="881" alt="Ekran görüntüsü 2026-06-15 025717" src="https://github.com/user-attachments/assets/05d62f87-0609-4294-81e2-6acbc17ad74d" />

## 🚀 Installation & Setup
1. Clone this repository to your local machine.
2. Open the project in **Android Studio**.
3. Once the Gradle synchronization is complete, run the application on a physical device or an emulator.
