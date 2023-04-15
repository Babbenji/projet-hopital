#!/bin/bash

# créer le dossier local config-repo
mkdir config-repo
cd config-repo/ || exit

# initialiser le repo git local
git init
git add .

# lier le repo local au repo distant
git remote add origin https://github.com/rachy-da/config-repo.git

# vérifier que le remote est bien ajouté
git remote -v

# récupérer le repo distant
git pull origin main
