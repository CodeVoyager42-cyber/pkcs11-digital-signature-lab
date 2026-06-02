# PKCS#11 Digital Signature Lab (Java + SoftHSM)

## 📌 Overview

This project demonstrates how Java integrates with the **PKCS#11 standard** to perform digital signatures using a cryptographic token.

Instead of using real hardware (HSM or USB token), we use **SoftHSM**, a software-based Hardware Security Module that simulates real secure cryptographic devices.

This project helps understand how real-world systems (banks, e-signature platforms, government systems) perform secure cryptographic operations.

---

## 🧠 Architecture

Java Application → SunPKCS11 Provider → PKCS#11 Library (libsofthsm2.so) → SoftHSM Token → Private Key (never leave the token) → Digital Signature

---

## 🔐 Key Concepts

- **PKCS#11** → Standard API for cryptographic devices  
- **SoftHSM** → Software implementation of an HSM  
- **SunPKCS11** → Java provider that connects Java to PKCS#11 devices  
- **Token** → Virtual secure storage for cryptographic keys  
- **Private Key** → Never leaves the token  

---

## ⚙️ Requirements

### Linux / WSL (Recommended)

- Java 11+
- SoftHSM2
- Git (optional)

### Install dependencies (Ubuntu / WSL)

```bash
sudo apt update
sudo apt install softhsm2 openjdk-17-jdk# PKCS#11 Digital Signature Lab (Java + SoftHSM)

## 📌 Overview

This project demonstrates how Java integrates with the **PKCS#11 standard** to perform digital signatures using a cryptographic token.

Instead of using real hardware (HSM or USB token), we use **SoftHSM**, a software-based Hardware Security Module that simulates real secure cryptographic devices.

This project helps understand how real-world systems (banks, e-signature platforms, government systems) perform secure cryptographic operations.

---

## 🧠 Architecture

Java Application → SunPKCS11 Provider → PKCS#11 Library (libsofthsm2.so) → SoftHSM Token → Private Key → Digital Signature

---

## 🔐 Key Concepts

- **PKCS#11** → Standard API for cryptographic devices  
- **SoftHSM** → Software implementation of an HSM  
- **SunPKCS11** → Java provider that connects Java to PKCS#11 devices  
- **Token** → Virtual secure storage for cryptographic keys  
- **Private Key** → Never leaves the token  

---

## ⚙️ Requirements

### Linux / WSL (Recommended)

- Java 11+
- SoftHSM2
- Git (optional)

### Install dependencies (Ubuntu / WSL)

```bash
sudo apt update

## SoftHSM Setup
softhsm2-util --init-token --slot 0 --label "test-token"
You will be asked to set:
SO PIN
User PIN

## Verify token
softhsm2-util --show-slots

## ⚙️ Configuration File
nano pkcs11.cfg 
name = SoftHSM
library = /usr/lib/x86_64-linux-gnu/softhsm/libsofthsm2.so
slotListIndex = 0

## How to Run

javac SignDirect.java
java SignDirect

## Output
Provider loaded: SunPKCS11-SoftHSM
Login successful: "long Base64 string is the digital signature".

## 🔄 What Happens Internally
Java loads SunPKCS11 provider
Provider reads pkcs11.cfg
Loads libsofthsm2.so (PKCS#11 engine)
Connects to SoftHSM token
PIN authentication is performed
RSA key is generated or retrieved
Data is signed inside the token
Signature is returned to Java

📁 Project Structure
.
├── SignDirect.java
├── SignRealKey.java
├── SignTest.java
├── TestPKCS11.java
├── TestProvider.java
├── ListProviders.java
├── UseRealHSMKey.java
├── pkcs11.cfg

