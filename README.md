# 🌍 Plateforme de Crowdfunding avec Gestion de Communautés

Bienvenue dans notre application web de **Crowdfunding** qui permet aux utilisateurs de créer, rejoindre et gérer des **communautés**, et de soutenir des **campagnes de crowdfunding** en ligne.

---

## 🚀 Fonctionnalités principales

### 👥 Gestion des utilisateurs
- Inscription et connexion sécurisée
- Profils utilisateurs (porteurs de projet ou contributeurs)

### 🫂 Communautés / Groupes
- Création de communautés par les utilisateurs
- Invitation, adhésion et gestion des membres
- Page dédiée à chaque communauté (description, membres, campagnes associées)
- (Bonus) Espace de discussion / fil d’actualité

### 💡 Campagnes de Crowdfunding
- Création et édition de campagnes (titre, description, objectif, durée)
- Association des campagnes à une ou plusieurs communautés
- Archivage automatique des campagnes à la fin de leur durée

### 💳 Système de Contributions
- Paiements simulés via Stripe/PayPal
- Suivi en temps réel des contributions

### 📊 Tableaux de bord
- Visualisation des campagnes par utilisateur et par communauté
- Historique des contributions personnelles

### 🔔 Notifications 
- Alertes pour nouveaux projets, invitations et nouvelles contributions

---

## 🛠️ Technologies utilisées

### 🖥️ Frontend – React
- Application SPA (Single Page Application) développée avec **React**
- Utilisation de **React Router** pour la navigation
- Appels aux APIs REST du backend via **Axios**
- Gestion des états via **useState/useEffect** ou **Redux** (selon besoin)

### 🧠 Backend – Spring Boot
- API REST sécurisée développée avec **Spring Boot**
- Utilisation de **Spring Security** pour l’authentification JWT
- Couche service + couche contrôleur pour une logique métier claire
- Intégration avec une base de données MySQL via **Spring Data JPA**

### 🗄️ Base de données – MySQL
- Base relationnelle utilisée pour stocker :
  - Utilisateurs
  - Campagnes
  - Groupes/Communautés
  - Contributions
- Mapping des entités avec **JPA/Hibernate**

---

## 🛠️ Technologies utilisées

| Couche       | Technologie                         |
|--------------|-------------------------------------|
| Frontend     | React, JavaScript, Axios |
| Backend      | Spring Boot, Spring Security, JPA   |
| Base de données | MySQL                          |
| Authentification | JWT (JSON Web Token)           |
| Paiement (simulé) | Stripe / PayPal (mock ou sandbox) |

---
