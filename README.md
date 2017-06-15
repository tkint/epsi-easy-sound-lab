# EasySoundLab
Editeur de fichiers son en ligne dans le cadre du projet Java du second semestre à l'EPSI

# Serveurs utilisés
GlassFish Server 4.1

MySQL (WAMP fait l'affaire)

# JNDI
Un JNDI est un objet de paramètres disponible sous GlassFish Server. Il permet, par exemple, de configurer le chemin de fichiers d'images, les données d'un driver SQL, etc.

Ils sont accessibles depuis la console d'administration du serveur GlassFish.

IMPORTANT: Après une modification de JNDI, il faut redémarrer le serveur et il est conseillé de Clean&Build l'application.

# JDBC-Config
Nom: JDBC-Config

Type: model.ConnexionConfig

Factory: model.ConnexionConfigFactory

Description: Configure le driver MySQL.

Propriétés:
 - url
 - port
 - login
 - password
 - database

# DATABASE-Config
Nom: DATABASE-Config

Type: model.DatabaseConfig

Factory: model.DatabaseConfigFactory

Description: Défini le nom des tables de la base de données à utiliser pour les différents objets.

Propriétés:
 - user
 - folder
 - playlist
 - musicfile
 - mail
