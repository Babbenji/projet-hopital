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


#warning: dépôt git embarqué ajouté : config-repo
#astuce: You've added another git repository inside your current repository.
#astuce: Clones of the outer repository will not contain the contents of
#astuce: the embedded repository and will not know how to obtain it.
#astuce: If you meant to add a submodule, use:
#astuce:
#astuce:         git submodule add <url> config-repo
#astuce:
#astuce: If you added this path by mistake, you can remove it from the
#astuce: index with:
#astuce:
#astuce:         git rm --cached config-repo
#astuce:
#astuce: See "git help submodule" for more information.

