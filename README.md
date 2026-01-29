# Java RMI String Inversion Service

> **Course:** Systèmes d’information distribués (SID)  
> **University:** University of Batna 2  
> **Student:** [Your Name]  
> **Date of Completion:** November 10, 2025  
> **Technology:** Java RMI (Remote Method Invocation), Swing GUI

## 📌 Project Overview
This project demonstrates the implementation of a distributed system using **Java RMI**. It creates a Client-Server architecture where the client sends a string to a remote server, and the server processes the request (inverts the string) and returns the result.

The project meets the requirements of **TP2**, showcasing:
* **Remote Interface definition** (`ServiceInversion`).
* **RMI Registry** management (programmatic creation via `LocateRegistry`).
* **UnicastRemoteObject** implementation.
* **Graphical User Interface (GUI)** for both Client and Server using Java Swing.

## 📸 Screenshots
The system consists of two separate GUIs communicating over the network:
![Uploading Screenshot 2026-01-29 225605.png…]()



## 📂 Project Structure
The repository is divided into two modules:
* **TP2_RMI_server**: Contains the Remote Interface, Server Implementation, and Server GUI.
* **TP2_RMI_client**: Contains the Client GUI and the shared Interface.

## 🚀 How to Run
### Prerequisites
* Java Development Kit (JDK) 11.
* Eclipse IDE (or any Java IDE).

### Steps
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YourUsername/TP2-RMI-Inversion.git](https://github.com/YourUsername/TP2-RMI-Inversion.git)
    ```

2.  **Start the Server:**
    * Navigate to `TP2_RMI_server/src/rmi/ServerGUI.java`.
    * Run the file. Click **"Démarrer le Serveur"**.
    * *The RMI Registry will start on port 1099.*

3.  **Start the Client:**
    * Navigate to `TP2_RMI_client/src/rmi/ClientGUI.java`.
    * Run the file.
    * Enter a text and click **"Inverser"**.

## 🛠️ Concepts Applied
* **Middleware:** Java RMI.
* **Stub/Skeleton Architecture.**
* **Multithreading:** Handling server GUI and RMI registry concurrently.
* **Socket Programming:** Abstracted via RMI.

---
*Completed as part of the Master 1 ISIDS curriculum.*
