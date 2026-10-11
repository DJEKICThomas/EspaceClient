# Conversation TFE — Technologies Espace Client

*Conversation avec Claude Code — Projet : Espace Client Depairon — Date : 2026-04-20*

---

## 👤 Utilisateur

> Lis mon code, liste toutes les technologies utilisées et donne moi des liens pour aller voir la documentation. Le but de ma demande c'est pour écrire 15-20 pages pour les technologies utilisées dans mon projet pour mon TFE (avec liste des sources etc).
>
> *(Précision : ne fais aucune modification)*

---

## 🤖 Claude

# Technologies utilisées — Espace Client Depairon

## Architecture générale
Projet full-stack multi-modules Maven + npm :
- **Backend** : Java / Spring Boot (API REST + WebSocket)
- **Frontend** : React SPA (avec DevExtreme)
- **Ancien frontend** : module JSP (DevExtreme) en cours de migration
- **Base de données** : SQL Server (test) / IBM DB2 AS/400 (prod)

---

## 1. Langages

| Techno | Version | Documentation |
|---|---|---|
| Java | 17 | https://docs.oracle.com/en/java/javase/17/ |
| JavaScript (ES6+) | — | https://developer.mozilla.org/fr/docs/Web/JavaScript |
| JSX | — | https://react.dev/learn/writing-markup-with-jsx |
| HTML5 / CSS3 | — | https://developer.mozilla.org/fr/docs/Web/HTML |
| SQL (T-SQL / DB2 SQL) | — | https://learn.microsoft.com/sql/t-sql/language-reference |

---

## 2. Backend — Java / Spring

| Techno | Version | Rôle | Documentation |
|---|---|---|---|
| Spring Boot | 2.5.0 | Framework applicatif | https://docs.spring.io/spring-boot/docs/2.5.0/reference/html/ |
| Spring Web (MVC) | — | REST controllers | https://docs.spring.io/spring-framework/reference/web/webmvc.html |
| Spring Security | — | Authentification / autorisation | https://docs.spring.io/spring-security/reference/ |
| Spring Data JPA | — | ORM / repositories | https://spring.io/projects/spring-data-jpa |
| Spring Data JDBC | — | Accès JDBC | https://spring.io/projects/spring-data-jdbc |
| Hibernate | via JPA | Implémentation JPA | https://hibernate.org/orm/documentation/ |
| HikariCP | — | Pool de connexions JDBC | https://github.com/brettwooldridge/HikariCP |
| Spring Boot Mail | — | Envoi d'emails (SMTP) | https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.email |
| Spring WebSocket + STOMP | — | Notifications temps réel | https://docs.spring.io/spring-framework/reference/web/websocket.html |
| Maven | 3.x | Build / gestion dépendances | https://maven.apache.org/guides/ |
| Apache Tomcat | 9 | Serveur d'application (WAR) | https://tomcat.apache.org/tomcat-9.0-doc/ |

### Librairies Java

| Techno | Version | Rôle | Documentation |
|---|---|---|---|
| JJWT | 0.9.1 | Génération / validation JWT | https://github.com/jwtk/jjwt |
| dev.samstevens.totp | 1.7.1 | 2FA TOTP | https://github.com/samdjstevens/java-totp |
| Lombok | 1.18.24 | Génération de boilerplate | https://projectlombok.org/features/ |
| ModelMapper | 2.4.4 | Mapping Entity ↔ DTO | http://modelmapper.org/getting-started/ |
| Apache Commons Lang3 | 3.12.0 | Utilitaires | https://commons.apache.org/proper/commons-lang/ |
| Apache Commons Collections4 | 4.4 | Collections | https://commons.apache.org/proper/commons-collections/ |
| IBM DB2 JDBC (jcc) | runtime | Driver AS/400 | https://www.ibm.com/docs/en/db2/11.5?topic=drivers-jdbc-driver |
| JT400 | 10.6 | Toolbox IBM i (AS/400) | https://jt400.sourceforge.net/ |
| jTDS | 1.3.1 | Driver SQL Server | https://jtds.sourceforge.net/ |
| Email Template Builder (rocketbase) | 2.4.0 | Templates HTML email | https://github.com/rocketbase-io/email-template-builder |
| BCrypt (Spring Security) | — | Hash de mots de passe | https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html |

---

## 3. Frontend — React

| Techno | Version | Rôle | Documentation |
|---|---|---|---|
| React | 19.2 | Bibliothèque UI | https://react.dev/ |
| React DOM | 19.2 | Rendu DOM | https://react.dev/reference/react-dom |
| React Router DOM | 6.30 | Routage SPA | https://reactrouter.com/ |
| Create React App | 4.0.3 | Tooling (Webpack, Babel) | https://create-react-app.dev/docs/getting-started |
| DevExtreme | 22.2 | Composants UI avancés | https://js.devexpress.com/React/Documentation/Guide/ |
| DevExtreme React | 22.2 | Wrappers React | https://js.devexpress.com/React/Documentation/Guide/React_Components/ |
| Bootstrap | 5.3 | Framework CSS | https://getbootstrap.com/docs/5.3/ |
| React Bootstrap Icons | 1.11 | Pack d'icônes | https://github.com/ismamz/react-bootstrap-icons |
| i18next | 23.10 | Internationalisation | https://www.i18next.com/ |
| react-i18next | 13.5 | Binding React i18next | https://react.i18next.com/ |
| i18next-browser-languagedetector | 7.0 | Détection langue | https://github.com/i18next/i18next-browser-languageDetector |
| ExcelJS | 4.4 | Export Excel | https://github.com/exceljs/exceljs |
| FileSaver.js | 2.0 | Téléchargement fichiers | https://github.com/eligrey/FileSaver.js |
| Web Vitals | 2.1 | Métriques de perf | https://web.dev/vitals/ |

### Tests

| Techno | Documentation |
|---|---|
| Jest | https://jestjs.io/docs/getting-started |
| React Testing Library | https://testing-library.com/docs/react-testing-library/intro/ |
| @testing-library/jest-dom | https://github.com/testing-library/jest-dom |
| @testing-library/user-event | https://testing-library.com/docs/user-event/intro |

---

## 4. Sécurité

| Techno | Rôle | Documentation |
|---|---|---|
| JWT | Authentification stateless | https://datatracker.ietf.org/doc/html/rfc7519 |
| BCrypt | Hachage mots de passe | https://en.wikipedia.org/wiki/Bcrypt |
| TOTP (RFC 6238) | 2FA | https://datatracker.ietf.org/doc/html/rfc6238 |
| CORS | Cross-Origin | https://developer.mozilla.org/fr/docs/Web/HTTP/CORS |
| HTTPS / TLS | Chiffrement transport | https://datatracker.ietf.org/doc/html/rfc8446 |

---

## 5. Bases de données & stockage

| Techno | Documentation |
|---|---|
| Microsoft SQL Server | https://learn.microsoft.com/sql/sql-server/ |
| IBM DB2 for i (AS/400) | https://www.ibm.com/docs/en/i/7.5?topic=overviews-db2-i |
| JDBC | https://docs.oracle.com/javase/tutorial/jdbc/ |
| JPA (Jakarta Persistence) | https://jakarta.ee/specifications/persistence/ |

---

## 6. Communication client/serveur

| Techno | Documentation |
|---|---|
| REST (HTTP/JSON) | https://restfulapi.net/ |
| Fetch API | https://developer.mozilla.org/fr/docs/Web/API/Fetch_API |
| WebSocket (RFC 6455) | https://datatracker.ietf.org/doc/html/rfc6455 |
| STOMP | https://stomp.github.io/ |
| SockJS | https://github.com/sockjs/sockjs-client |
| SMTP | https://datatracker.ietf.org/doc/html/rfc5321 |

---

## 7. Outils / Environnement

| Techno | Documentation |
|---|---|
| IntelliJ IDEA | https://www.jetbrains.com/help/idea/ |
| Git | https://git-scm.com/doc |
| npm | https://docs.npmjs.com/ |
| Maven | https://maven.apache.org/guides/ |
| Webpack (via CRA) | https://webpack.js.org/concepts/ |
| Babel (via CRA) | https://babeljs.io/docs/ |

---
---

## 👤 Utilisateur

> Et je peux me documenter où pour écrire mon TFE sur les technos ?

---

## 🤖 Claude

# Sources pour rédiger le TFE

## 1. Sources officielles (à citer en priorité)

Les docs officielles sont LA référence pour un TFE technique.
- Spring : https://spring.io/projects + https://docs.spring.io/
- React : https://react.dev/learn
- DevExtreme : https://js.devexpress.com/Documentation/
- RFC (standards) : https://www.rfc-editor.org/

## 2. Livres de référence (très valorisés en TFE)

| Sujet | Livre |
|---|---|
| Java | *Effective Java* — Joshua Bloch (3e éd., Addison-Wesley) |
| Spring | *Spring in Action* — Craig Walls (Manning) |
| Spring Security | *Spring Security in Action* — Laurentiu Spilca (Manning) |
| React | *Learning React* — Alex Banks & Eve Porcello (O'Reilly) |
| React | *The Road to React* — Robin Wieruch |
| Architecture | *Clean Architecture* — Robert C. Martin |
| Architecture | *Patterns of Enterprise Application Architecture* — Martin Fowler |
| REST | *RESTful Web APIs* — Richardson & Amundsen (O'Reilly) |
| Sécurité web | *The Web Application Hacker's Handbook* — Stuttard & Pinto |
| JPA / Hibernate | *Java Persistence with Hibernate* — Bauer, King, Gregory (Manning) |

## 3. Articles académiques et scientifiques

- **Google Scholar** : https://scholar.google.com
- **IEEE Xplore** : https://ieeexplore.ieee.org
- **ACM Digital Library** : https://dl.acm.org
- **ScienceDirect** : https://www.sciencedirect.com
- **ResearchGate** : https://www.researchgate.net

→ Souvent accessibles gratuitement via le portail de votre école.

## 4. Sites techniques fiables

| Site | Usage |
|---|---|
| Baeldung | https://www.baeldung.com — tutoriels Spring / Java |
| MDN Web Docs | https://developer.mozilla.org — référence web |
| OWASP | https://owasp.org — sécurité web |
| Martin Fowler (blog) | https://martinfowler.com — architecture |
| Microsoft Learn | https://learn.microsoft.com — SQL Server |
| IBM Docs | https://www.ibm.com/docs — DB2, AS/400 |
| web.dev (Google) | https://web.dev — performance, SPA |

**À éviter comme source principale** : Stack Overflow, Medium, blogs persos, Wikipedia → OK pour comprendre, PAS pour citer.

## 5. Standards et spécifications

- **RFC 7519** (JWT) : https://datatracker.ietf.org/doc/html/rfc7519
- **RFC 6238** (TOTP) : https://datatracker.ietf.org/doc/html/rfc6238
- **RFC 6455** (WebSocket) : https://datatracker.ietf.org/doc/html/rfc6455
- **RFC 7617** (Basic Auth) : https://datatracker.ietf.org/doc/html/rfc7617
- **RFC 8446** (TLS 1.3) : https://datatracker.ietf.org/doc/html/rfc8446
- **ECMAScript** : https://tc39.es/ecma262/
- **HTML Living Standard (WHATWG)** : https://html.spec.whatwg.org/
- **Jakarta Persistence** (JPA) : https://jakarta.ee/specifications/persistence/

## 6. Organismes et fondations

| Organisme | URL |
|---|---|
| Apache Software Foundation | https://www.apache.org |
| Eclipse Foundation (Jakarta EE) | https://www.eclipse.org |
| OpenJS Foundation | https://openjsf.org |
| Oracle (Java) | https://www.oracle.com/java/ |
| Meta (React) | https://opensource.fb.com |
| IETF (RFC) | https://www.ietf.org |
| W3C | https://www.w3.org |

## 7. Méthodologie de citation

Styles possibles : **APA**, **IEEE**, ou **ISO 690** (vérifiez le guide de votre école).

Outils bibliographiques :
- **Zotero** (gratuit, recommandé) : https://www.zotero.org
- **Mendeley** : https://www.mendeley.com
- **Google Scholar** → bouton "Citer"

## 8. Conseils pour un TFE crédible

1. **2-3 sources par technologie** minimum : doc officielle + 1 livre + 1 article/RFC.
2. **Citer le numéro de version** utilisée (Spring Boot 2.5.0, React 19.2…).
3. **Date de consultation** des URL (obligatoire en ISO 690 et APA).
4. **Bibliographie triée** par ordre alphabétique de l'auteur.
5. **Évitez Wikipedia** en source directe → consultez ses sources en bas de page.

## 9. Ressources spécifiques aux écoles belges

- Portail ressources numériques HE (accès O'Reilly Learning, Cairn.info)
- Limo : https://limo.libis.be — ULiège : https://lib.uliege.be
- Archives de TFE : Orbi (ULiège), DIAL (UCLouvain)

---
---

## 👤 Utilisateur

> Pour les technos ?

---

## 🤖 Claude

# Plan TFE détaillé — Technologies (15-20 pages)

## Chapitre 1 — Architecture générale (1-2 p.)

**Contenu** : schéma 3-tiers, séparation frontend/backend, API REST, SPA.

**Sources** :
- Fowler, M. — *Patterns of Enterprise Application Architecture* (Addison-Wesley, 2002)
- Richardson, C. — *Microservices Patterns* (Manning, 2018)
- Martin, R.C. — *Clean Architecture* (Prentice Hall, 2017)
- Fielding, R. — thèse **REST** (2000) : https://ics.uci.edu/~fielding/pubs/dissertation/top.htm

## Chapitre 2 — Langage Java 17 (1 p.)

- Oracle — Java SE 17 Documentation : https://docs.oracle.com/en/java/javase/17/
- **JSR 392** — Java SE 17 specification
- Bloch, J. — *Effective Java* (3e éd., Addison-Wesley, 2018)
- Oracle — *The Java Language Specification, Java SE 17 Edition*

## Chapitre 3 — Spring Boot & écosystème Spring (3-4 p.)

### 3.1 Spring Boot 2.5
- Docs : https://docs.spring.io/spring-boot/docs/2.5.x/reference/html/
- Walls, C. — *Spring in Action* (6e éd., Manning, 2022)
- Baeldung : https://www.baeldung.com/spring-boot

### 3.2 Spring MVC (REST Controllers)
- Docs : https://docs.spring.io/spring-framework/reference/web/webmvc.html
- Richardson, L. & Amundsen, M. — *RESTful Web APIs* (O'Reilly, 2013)

### 3.3 Spring Data JPA + Hibernate
- Spring Data JPA : https://docs.spring.io/spring-data/jpa/reference/
- Bauer, C., King, G., Gregory, G. — *Java Persistence with Hibernate* (2e éd., Manning, 2015)
- **JSR 338** — Jakarta Persistence API

### 3.4 Spring WebSocket + STOMP
- Docs : https://docs.spring.io/spring-framework/reference/web/websocket.html
- **STOMP 1.2** spec : https://stomp.github.io/stomp-specification-1.2.html
- **RFC 6455** (WebSocket) : https://datatracker.ietf.org/doc/html/rfc6455

### 3.5 Spring Mail
- Docs : https://docs.spring.io/spring-framework/reference/integration/email.html
- **RFC 5321** (SMTP) : https://datatracker.ietf.org/doc/html/rfc5321

## Chapitre 4 — Sécurité (3 p.) — gros chapitre, **très valorisé**

### 4.1 Spring Security
- Docs : https://docs.spring.io/spring-security/reference/
- Spilca, L. — *Spring Security in Action* (Manning, 2020)

### 4.2 JWT
- **RFC 7519** : https://datatracker.ietf.org/doc/html/rfc7519
- **RFC 7515** (JSON Web Signature) : https://datatracker.ietf.org/doc/html/rfc7515
- jwt.io : https://jwt.io/introduction
- Librairie JJWT : https://github.com/jwtk/jjwt

### 4.3 BCrypt
- Provos, N. & Mazières, D. — *A Future-Adaptable Password Scheme* (USENIX, 1999) : https://www.usenix.org/legacy/event/usenix99/provos/provos.pdf
- OWASP Password Storage Cheat Sheet : https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html

### 4.4 2FA / TOTP
- **RFC 6238** (TOTP) : https://datatracker.ietf.org/doc/html/rfc6238
- **RFC 4226** (HOTP) : https://datatracker.ietf.org/doc/html/rfc4226
- Librairie samstevens/totp : https://github.com/samdjstevens/java-totp
- OWASP Authentication Cheat Sheet : https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html

### 4.5 CORS / HTTPS
- **CORS** (Fetch spec) : https://fetch.spec.whatwg.org/#http-cors-protocol
- MDN CORS : https://developer.mozilla.org/fr/docs/Web/HTTP/CORS
- **RFC 8446** (TLS 1.3) : https://datatracker.ietf.org/doc/html/rfc8446

### 4.6 OWASP Top 10
- https://owasp.org/www-project-top-ten/

## Chapitre 5 — Frontend React (3-4 p.)

### 5.1 React 19
- Docs : https://react.dev/learn
- Banks, A. & Porcello, E. — *Learning React* (2e éd., O'Reilly, 2020)
- Wieruch, R. — *The Road to React* : https://www.roadtoreact.com/

### 5.2 Virtual DOM & JSX
- React — *Rendering Elements* : https://react.dev/learn/rendering-lists
- Abramov, D. — *React as a UI Runtime* : https://overreacted.io/react-as-a-ui-runtime/

### 5.3 React Router
- Docs : https://reactrouter.com/en/main

### 5.4 Context API & State management
- https://react.dev/learn/passing-data-deeply-with-context

### 5.5 Hooks
- https://react.dev/reference/react/hooks
- Abramov, D. — *A Complete Guide to useEffect* : https://overreacted.io/a-complete-guide-to-useeffect/

### 5.6 Create React App / Webpack / Babel
- CRA : https://create-react-app.dev/docs/getting-started
- Webpack : https://webpack.js.org/concepts/
- Babel : https://babeljs.io/docs/

## Chapitre 6 — DevExtreme (1-2 p.)

- Docs React : https://js.devexpress.com/React/Documentation/Guide/
- DevExtreme DataGrid : https://js.devexpress.com/React/Documentation/Guide/UI_Components/DataGrid/
- DevExpress (éditeur) : https://www.devexpress.com/

## Chapitre 7 — UI / UX (1-2 p.)

### 7.1 Bootstrap 5
- Docs : https://getbootstrap.com/docs/5.3/
- Spurlock, J. — *Bootstrap* (O'Reilly, 2013)

### 7.2 Internationalisation (i18next)
- i18next : https://www.i18next.com/
- react-i18next : https://react.i18next.com/
- **BCP 47** (codes langues) : https://www.rfc-editor.org/info/bcp47

### 7.3 Accessibilité / Dark Mode
- WCAG 2.1 : https://www.w3.org/TR/WCAG21/
- MDN — prefers-color-scheme : https://developer.mozilla.org/fr/docs/Web/CSS/@media/prefers-color-scheme

## Chapitre 8 — Persistance & bases de données (2 p.)

### 8.1 Microsoft SQL Server
- Docs : https://learn.microsoft.com/sql/sql-server/
- Driver jTDS : https://jtds.sourceforge.net/

### 8.2 IBM DB2 / AS-400 (iSeries)
- IBM Docs : https://www.ibm.com/docs/en/i/7.5?topic=overviews-db2-i
- JT400 Toolbox : https://jt400.sourceforge.net/
- IBM Redbooks : https://www.redbooks.ibm.com/

### 8.3 JDBC
- Docs Oracle : https://docs.oracle.com/javase/tutorial/jdbc/
- **JSR 221** : JDBC 4.0 specification

### 8.4 HikariCP
- GitHub : https://github.com/brettwooldridge/HikariCP

## Chapitre 9 — Communication temps réel (1 p.)

- **RFC 6455** (WebSocket) : https://datatracker.ietf.org/doc/html/rfc6455
- STOMP 1.2 : https://stomp.github.io/
- SockJS : https://github.com/sockjs/sockjs-client
- Fette, I. & Melnikov, A. — *The WebSocket Protocol* (IETF, 2011)

## Chapitre 10 — Outils & méthodologie (1-2 p.)

| Outil | Sources |
|---|---|
| Maven | https://maven.apache.org/guides/ — Porter, B. et al. — *Maven: The Definitive Guide* (O'Reilly) |
| npm | https://docs.npmjs.com/ |
| Git | Chacon, S. & Straub, B. — *Pro Git* (Apress, 2014) : https://git-scm.com/book/fr/v2 |
| IntelliJ IDEA | https://www.jetbrains.com/help/idea/ |
| Tomcat 9 | https://tomcat.apache.org/tomcat-9.0-doc/ |

---

## Gabarit de rédaction pour chaque technologie

1. **Présentation** — qu'est-ce que c'est, qui l'a créé, quand
2. **Fonctionnement technique** — concepts clés, schéma éventuel
3. **Justification** — *pourquoi CE choix dans votre projet* ← c'est ça que le jury veut lire
4. **Utilisation concrète** — dans quel module, exemple de code
5. **Alternatives envisagées** — pourquoi pas Angular / Vue / Quarkus / etc.

Les points **3 (justification)** et **5 (alternatives)** distinguent un bon TFE d'un simple rapport descriptif.

---
---

## 👤 Utilisateur

> Donc en gros là, je respecte ton architecture par technologie, j'utilise les sources que tu m'as citées pour chaque chapitre et j'aurai au minimum 15 pages pour les technologies ?

---

## 🤖 Claude

# Estimation du volume & conseils finaux

## Estimation page par page (~350 mots/page)

| Chapitre | Pages estimées | Commentaire |
|---|---|---|
| 1. Architecture générale | 1-2 | ✅ schéma + explication 3-tiers |
| 2. Java 17 | 1 | ✅ langage + JVM + nouveautés |
| 3. Spring Boot & écosystème | 3-4 | ✅ 5 sous-sections |
| 4. Sécurité | 3 | ✅ chapitre dense, RFC à citer |
| 5. React | 3-4 | ✅ VDOM, JSX, hooks, routing |
| 6. DevExtreme | 1-2 | ⚠️ risque d'être court |
| 7. UI/UX | 1-2 | ✅ |
| 8. Persistance | 2 | ✅ |
| 9. Temps réel | 1 | ⚠️ limite |
| 10. Outils | 1-2 | ✅ |
| **TOTAL** | **17-23 pages** | ✅ **objectif largement atteint** |

## Les 4 pièges à éviter

### 1. Ne pas paraphraser la doc officielle
Le jury déteste lire des paraphrases recopiées. Reformulez, citez, et **ajoutez votre analyse**.

### 2. Le point qui fait basculer un TFE descriptif vers un bon TFE
Pour chaque techno, **ajoutez systématiquement** :
- **Pourquoi ce choix** dans VOTRE projet (contrainte client ? existant ? compétences équipe ?)
- **Alternative non retenue** (Angular vs React, Quarkus vs Spring Boot, PostgreSQL vs SQL Server…)
- **Limites rencontrées** en développement

### 3. Les schémas/captures gagnent des pages
- Schéma architecture 3-tiers (1/2 page)
- Diagramme de séquence JWT authentication (1/2 page)
- Capture du dashboard DevExtreme (1/2 page)
- Schéma cycle de vie composant React (1/2 page)
- Diagramme entité-relation DB (1 page)

→ **4-5 schémas = 2-3 pages "gratuites"** + ça fait pro.

### 4. Les extraits de code bien choisis
Un extrait **court et commenté** de :
- configuration `SecurityConfig.java`
- un controller REST
- un composant React avec hooks
- config WebSocket

→ 2-3 extraits de 10 lignes = ~1 page de plus.

## Recette concrète pour garantir 15+ pages

Pour chaque techno, 5 paragraphes :

```
1. Présentation (5-6 lignes)
2. Fonctionnement technique (8-10 lignes)
3. Justification du choix (6-8 lignes) ← ESSENTIEL
4. Utilisation dans le projet + extrait code (8-10 lignes)
5. Alternative envisagée + limites (5-6 lignes) ← ESSENTIEL
```

= ~40 lignes ≈ **3/4 de page par techno**.

~30 technos listées → mathématiquement **~22 pages** sans forcer.

## Checklist finale

- [ ] Table des matières
- [ ] Introduction (1-2 p.) — contexte projet, entreprise (Depairon), objectifs
- [ ] 10 chapitres techniques (~17-20 p.)
- [ ] Conclusion (1 p.) — retour d'expérience
- [ ] Bibliographie triée (livres / articles / webographie avec date consultation)
- [ ] Annexes (schéma BD complet, extraits de code longs)
- [ ] Chaque affirmation technique = note de bas de page ou référence

## Réponse courte

**Oui.** En suivant l'architecture proposée + les sources + le gabarit de 5 paragraphes par techno + schémas + extraits de code, vous aurez **18-22 pages** sans difficulté — et un TFE **solide**, pas juste "rempli".

Le seul vrai risque : se contenter de décrire sans justifier. **La justification des choix techniques, c'est 50 % de votre note.**

---

*Fin de la conversation — exporté depuis Claude Code pour Thomas Djekic.*
