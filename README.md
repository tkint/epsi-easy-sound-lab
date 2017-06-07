# EasySoundLab
Editeur de fichiers son en ligne dans le cadre du projet Java du second semestre à l'EPSI

# Avant de lancer le programme
Sur Netbeans, configurer un serveur GlassFish 4.1 et non 4.1.1.

Le définir comme serveur de run sur le projet

Démarrer le serveur

Aller dans la console d'admin

Aller dans JNDI -> Custom Resources

Ajouter une nouvelle ressource nommée "JDBC-Config", de type "model.ConnexionConfig" avec la factory "model.ConnexionConfigFactory"

Ajouter les propriétés:
 - url
 - port
 - login
 - password
 - database
