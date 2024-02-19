<!-- Readme Template: See: https://github.com/othneildrew/Best-README-Template -->
<a name="readme-top"></a>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
*** for badge : https://ileriayo.github.io/markdown-badges/ or https://home.aveek.io/GitHub-Profile-Badges/
-->

[![Sonatype Repository][Sonatype Repository]][Sonatype Repository-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/zakaoai/cold-library-backend/">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Cold Library Backend</h3>

  <p align="center">
    Ce back permet de communiquer avec une base de données, des apis Jikan / MyAnimeList / Nyaa et Deluge Torrent afin de fournir les apis nécessaire à la gestion d'une base d'anime à télécharger. L'outil est en lien avec une connexion via Auth0 pour sécuriser les apis
    <br />
    <a href="/docs"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://www.bonjour404.fr/">View Demo</a>
    ·
    <a href="https://github.com/zakaoai/cold-library-backend//issues">Report Bug</a>
    ·
    <a href="https://github.com/zakaoai/cold-library-backend//issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Sommaire</summary>
  <ol>
    <li>
      <a href="#a-propos-du-projet">A propos du projet</a>
      <ul>
        <li><a href="#construit-avec">Construit avec</a></li>
      </ul>
    </li>
    <li>
      <a href="#pour-bien-démarrer">Pour bien démarrer</a>
      <ul>
        <li><a href="#prérequis">Prérequis</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#utilisation">Utilisation</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contribution">Contribution</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## A propos du projet

[![Product Name Screen Shot][product-screenshot]](https://echo-dev.altima-assurances.fr/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Construit avec

#### Back
* [![Kotlin][Kotlin]][Kotlin-url]
* [![Spring][Spring]][Spring-url]
* [![Spring Security][Spring Security]][Spring Security-url]
* [![Liquibase][Liquibase]][Liquibase-url]
#### Front
* [![React][React.js]][React-url]
* [![Vite][Vite.js]][Vite-url]
* [![TypeScript][TypeScript]][TypeScript-url]
* [![MUI][MUI]][MUI-url]
* [![React Query][React Query]][React Query-url]
* [![React Router][React Router]][React Router-url]
* [![Eslint][Eslint]][Eslint-url]
* [![Testing Library][Testing Library]][Testing Library-url]
* [![Vitest][Vitest]][Vitest-url]
* [![Mock Service Worker][Mock Service Worker]][Mock Service Worker-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Pour bien démarrer

### Prérequis

* Java 17 - Télécharger JDK 17 Témurin via IntelliJ IDEA ( Project Structures > Project > SDK > Add SDK > Download SDK > Version 17 > Vendor Eclipse Temurin (AdoptOpenJDK HotSpot) )
* Git - [Télécharger et installer Git](https://git-scm.com/downloads). Les machines OSX et Linux l'ont déjà d'installé.

### Installation

1. Cloner le repo
   ```sh
   git clone https://github.com/zakaoai/cold-library-backend/.git
   ```
2. Ouvrer le projet avec IntelliJ et laisser le importer les dépendances

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Utilisation

### Fonctionnalités

L'application possède plusieurs fonctionnalités :

### Démarrer l'application en local
Pour lancer l'application, assurez-vous que le port 9000 renseigné dans le fichier [application.yml](src/main/ressources/application.yml) sur la config server:port ne correspond pas à une autre application lancer sur votre machine.

#### Lancer l'application
Depuis la racine du projet lancer la commande gradle suivante
   ```sh
   ./gradlew bootRun
   ```
ou Depuis IntelliJ, creer une nouvelle configuration projet Gradle avec les paramètres suivant :
* Run : bootRun

### Authentification

L'application est protéger par une authentification gérée par auth0.
Seuls les utilisateurs autorisés peuvent accéder aux endpoints protégé.

### Rôles

La catégorisation des utilisateurs avec les rôles se fait dans Auth0, et en fonction de cette dernière le périmètre d'action dans l'outil est défini.

_Pour plus d'informations sur son usage, merci de vous référer à la [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Roadmap

- [ ] 

Voir [open issues](https://github.com/zakaoai/cold-library-backend//issues) pour une liste complete des feature ( et des issues connues)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Contribution

Si vous avez une suggestion pour améliorer les choses, veuillez créer une branche sur le dépôt et créer une pull request. Vous pouvez aussi simplement ouvrir un ticket avec la balise "enhancement".
N'oubliez pas de donner une étoile au projet ! Merci encore!

1. Creer votre Feature Branch (`git checkout -b feature/AmazingFeature`)
2. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
3. Push votre Branch (`git push origin feature/AmazingFeature`)
4. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Contact

Zakaoai

Lien projet: [https://github.com/zakaoai/cold-library-backend/](https://github.com/zakaoai/cold-library-backend/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/altima-assurances/altima-echo.svg?style=for-the-badge
[contributors-url]: https://github.com/zakaoai/cold-library-backend//graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/altima-assurances/altima-echo.svg?style=for-the-badge
[forks-url]: https://github.com/zakaoai/cold-library-backend//network/members
[stars-shield]: https://img.shields.io/github/stars/altima-assurances/altima-echo.svg?style=for-the-badge
[stars-url]: https://github.com/zakaoai/cold-library-backend//stargazers
[issues-shield]: https://img.shields.io/github/issues/altima-assurances/altima-echo.svg?style=for-the-badge
[issues-url]: https://github.com/zakaoai/cold-library-backend//issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[product-screenshot]: images/screenshot.png
[Spring]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[Vite.js]: https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white
[Vite-url]: https://vitejs.dev/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Kotlin]: https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white
[Kotlin-url]: https://kotlinlang.org/
[TypeScript]: https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white
[TypeScript-url]: https://www.typescriptlang.org/
[MUI]: https://img.shields.io/badge/MUI-007FFF.svg?style=for-the-badge&logo=MUI&logoColor=white
[MUI-url]: https://mui.com/
[React Router]:https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white
[React Router-url]: https://reactrouter.com/en/main
[React Query]: https://img.shields.io/badge/-React%20Query-FF4154?style=for-the-badge&logo=react%20query&logoColor=white
[React Query-url]: https://tanstack.com/query/v3/
[Sonatype Repository]: https://img.shields.io/badge/Sonatype_Repository-228B22.svg?style=for-the-badge&logo=Sonatype&logoColor=white
[Sonatype Repository-url]: localhost
[Spring Security]: https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring-Security&logoColor=white
[Spring Security-url]: https://spring.io/projects/spring-security
[Testing Library]: https://img.shields.io/badge/Testing%20Library-E33332.svg?style=for-the-badge&logo=Testing-Library&logoColor=white
[Testing Library-url]: https://testing-library.com/
[Eslint]: https://img.shields.io/badge/ESLint-4B32C3.svg?style=for-the-badge&logo=ESLint&logoColor=white
[Eslint-url]: https://eslint.org/
[Vitest]: https://img.shields.io/badge/Vitest-6E9F18.svg?style=for-the-badge&logo=Vitest&logoColor=white
[Vitest-url]: https://vitest.dev/
[Liquibase]: https://img.shields.io/badge/Liquibase-2962FF.svg?style=for-the-badge&logo=Liquibase&logoColor=white
[Liquibase-url]: https://www.liquibase.org/
[Mock Service Worker]: https://img.shields.io/badge/Mock%20Service%20Worker-FF6A33.svg?style=for-the-badge&logo=Mock-Service-Worker&logoColor=white
[Mock Service Worker-url]: https://v1.mswjs.io/
