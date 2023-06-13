***Pong FLANEJ***

- Presentation

Cette recréation de pong permet au joueur de jouer à une reconstiution du jeu 
original, et de profiter d'autres modes qui changent subtilement l'expérience
classique, mais donnant un jeu plus dynamique

- Lancement
 
Comme nous n'avons pas réussi à utiliser gradle pour exporter un exécutable,
le jeu se lance toujours avec ./gradlew run depuis le répertoire d'installation

- Contrôles
 
Le joueur de gauche peut faire monter et descendre sa raquette avec ctrl et alt
respectivement. Le joueur de droite fait de même avec les flèches haut et bas


- Modes de jeu

1. pong

Le mode de jeu classique, fidèle au jeu original. Deux joueurs s'affrontent en
s'échangeant une balle, et marque un point si son advresaire ne parvient pas
à la récupérer. La partie se termine lorsqu'un des joueurs a dépassé 20 points

2. 2 balles

La même chose, mais cette fois avec deux balles au lieu d'une. Un nouveau tour
se joue à la première balle manquée

3. Acceleration

Dans ce mode de jeu, la balle accélère à chaque rebond sur une raquette. Pour
compenser, les raquettes aussi deviennent plus rapides

4. Raquette changeante

Ici, à chaque fois qu'un joueur manque la balle, sa raquette rétrécit un peu.
Le jeu se termine lorqu'une des raquettes devient trop petite pour être utilisée

(Modification post-délai) A cause d'une corruption des données, j'ai dû retaper
l'entièrerté du readme et ai oublié d'instérer le lien vers notre vidéo : 
https://www.youtube.com/watch?v=NKb1p0L76Ys
Le lien peut aussi être trouvé dans le commit d57aadf5, 
qui était avant la date limite.