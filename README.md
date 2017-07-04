# EasySoundLab
Editeur de fichiers son en ligne dans le cadre du projet Java du second semestre à l'EPSI

# Serveurs utilisés
GlassFish Server 4.1

MySQL (WAMP fait l'affaire)

# JNDI
Un JNDI est un objet de paramètres disponible sous GlassFish Server. Il permet, par exemple, de configurer le chemin de fichiers d'images, les données d'un driver SQL, etc.

Ils sont accessibles depuis la console d'administration du serveur GlassFish.

IMPORTANT: Après une modification de JNDI, il faut redémarrer le serveur et il est conseillé de Clean&Build l'application.

### JDBC-Config
Nom: JDBC-Config

Type: model.config.ConnexionConfig

Factory: model.config.ConnexionConfigFactory

Description: Configure le driver MySQL.

Propriétés:
 - url : localhost
 - port : 3306
 - login : easysoundlab
 - password : easy
 - database : easysoundlab

### DATABASE-Config
Nom: DATABASE-Config

Type: model.config.DatabaseConfig

Factory: model.config.DatabaseConfigFactory

Description: Défini le nom des tables de la base de données à utiliser pour les différents objets. Le nom des propriétés correspond au nom qu'ont les entités dans l'annotation @ESLEntity.

Propriétés:
 - user : user
 - folder : folder
 - playlist : playlist
 - musicfile : musicfile
 - mail : mail
 - follow : follow
 - playlistmusicfile : playlist_musicfile

### MAIN-Config
Nom: MAIN-Config

Type: model.config.MainConfig

Factory: model.config.MainConfigFactory

Description: Défini le disque dans lequel seront stockés les fichiers. Il doit être le même que celui de l'installation de Glassfish.

Propriétés:
 - disk : c:

# Hébergement des fichiers son
Les fichiers sont accessibles depuis une servlet à l'adresse "musicfiles".

- Get: récupère le fichier au chemin demandé
- Post: upload un fichier

Le servlet va automatiquement créé le répertoire de fichiers sur l'ordinateur local, sur le même disque sur lequel GlassFish tourne. Par défaut, c'est dans le C et le servlet crééra le dossier musicfiles sous "C:/easysoundlab/musicfiles".

Les fichiers sont mappés dans la base de données, dans la table musicfile. Elle contient le titre du morceau, sa durée et deux versions de son chemin absolu (pour la suppression du fichier).

# Annotations
Afin d'optimiser la couche d'accès aux données, des annotations ont été développées, elles portent le préfixe ESL.

### ESLEntity
- name : nom de l'entité dans le JNDI DATABASE-Config

Se place sur une classe et permet aux DAO de récupérer la référence de table à prendre dans la base de données depuis le JNDI DATABASE-Config. Toute classe devant être répliquée dans la base doit avoir cette annotation.

### ESLId
Se place sur un attribut d'une classe et permet aux DAO de définir l'attribut annoté comme clef primaire dans la table de la classe.

### ESLField
- name : nom du champ dans la table de la classe

Se place sur un attribut d'une classe et permet aux DAO de définir l'attribut annoté comme champ de la table de la classe.

### ESLReference
- entity : la classe de référence

Se place sur un attribut d'une classe et permet aux DAO de définir l'attribut annoté comme clef étrangère. Le paramètre entity doit pointer sur une classe. Les DAO récupère la table liée à la classe étrangère pour faire des requêtes imbriquées.
