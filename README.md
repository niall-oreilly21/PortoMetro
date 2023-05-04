<a name="readme-top"></a>

<div align="center">
  <img src="/src/main/resource/img/icons/logo.png" width="80" height="80">
</div>

<h1 align="center">Porto Metro Application</h1>
  <p align="center">
    The following application is a Java application which is based on the Porto Metro System.
  <br/><br/>
    <a href="https://github.com/niall-oreilly21/PortoMetro"><strong>Explore the project »</strong></a>
    <br />
  </p>
 
![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)
  
  <!-- TABLE OF CONTENTS -->
<h2>📖&ensp;Table of Contents</h2>
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">:train: About the Project</a>
      <ul>
        <li><a href="#main-features">:sparkles: Main Features</a></li>
        <l1><a href="#built-with">:hammer: Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">:tada: Getting Started</a>
      <ul>
        <li><a href="#dependencies">:wrench: Dependencies</a></li>
        <li><a href="#installation">:inbox_tray: Installation</a></li>
      </ul>
    </li>
    <li><a href="#ui">:performing_arts: User Interface</a>
      <ul>
        <li><a href="#screenshots">:camera: Screenshots</a></li>
      </ul>
    </li>
    <li><a href="#contributors">:busts_in_silhouette: Contributors</a></li>
    <li><a href="#acknowledgments">:memo: Acknowledgments</a></li>
  </ol>
</details>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)

<h2 id="about-the-project">🚋&ensp;About the Project</h2>
<p>This application is a Java desktop application based on the Porto Metro System. It is built focusing on object-oriented principles and has a graphical user interface (GUI). Our aim is to develop a user-friendly interface and a personalised user experience while navigating through the Porto Metro Application.</p>
<br>

<div align="center">
  <img alt="Home page" width="700" src="https://i.postimg.cc/VsTgf9t1/Screenshot-2023-05-04-at-00-19-31.png">
</div>
<br>

<h3 id="main-features">:sparkles: Main Features</h3>
<ul>
  <li>Order a Metro card</li>
  <li>Look up Metro schedules</li>
  <li>Plan a journey and save favourite routes</li>
  <li>Look up stations information</li>
  <li>Edit user profile</li>
  <li>View user's card details</li>
</ul>

<p>There is also an administrator page that can only be accessed by the administrator of the page to show all trains, users, and cards.</p>
<br>
<h3 id="built-with">:hammer: Built With</h3>

<a href="https://docs.oracle.com/en/java/"><img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"></a>
<a href="https://openjfx.io/"><img src="https://img.shields.io/badge/-JavaFX-yellow?style=for-the-badge&color=yellow"/></a>
<a href="https://maven.apache.org/guides/"><img src="https://shields.io/badge/Apache%20Maven-C71A36?logo=Apache%20Maven&style=for-the-badge&logoColor=white"></a>
<a href="https://www.mysql.com/"><img src="https://shields.io/badge/MySQL-4479A1?logo=mysql&style=for-the-badge&logoColor=white"></a>
<a href="https://developer.mozilla.org/en-US/docs/Web/CSS"><img src="https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white"></a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)

<h2 id="getting-started">:tada:&ensp;Getting Started</h2>
<p>This project is a Java project built with Maven. To run locally, you will need a Java IDE (e.g. IntelliJ, Eclipse) and a running MySQL server.</p>
<br>
<h3 id="dependencies">:wrench: Dependencies</h3>
<ul>
  <li>MySQL connector Java</li>
  <li>JUnit</li>
  <li>JavaFX (controls, FXML)</li>
  <li>MyBatis</li>
  <li>Apache POI</li>
  <li>Apache PDFBox</li>
  <li>bcrypt</li>
</ul>
<br>
<h3 id="installation">:inbox_tray: Installation</h3>
<ol>
  <li>Clone the repository to your preferred Java IDE using the following link: <a href="https://github.com/niall-oreilly21/PortoMetro.git">https://github.com/niall-oreilly21/PortoMetro.git</a><br>
  </li>
  <li>Build the project using Maven and ensure that you have all the required dependencies</li>
  <li>Run MySQL in your local machine</li>
  <li>Create the database and insert data by running the application in: <a href="https://github.com/niall-oreilly21/PortoMetro/blob/master/src/main/java/com/metroporto/createdatabase/InsertPortoMetroSystemDatabase.java">/src/main/java/com/metroporto/createdatabase/InsertPortoMetroSystemDatabase.java</a></li>
  <li>The application is now ready to go and can be accessed by running the application in: <a href="https://github.com/niall-oreilly21/PortoMetro/blob/master/src/main/java/com/gui/App.java">/src/main/java/com/gui/App.java</a></li>
</ol>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)

<h2 id="ui">:performing_arts:&ensp;User Interface</h2>

<p>Shown in the picture below is the flow of the application's user interface:</p>
<img width="700" src="https://i.postimg.cc/jSspVWFz/Flowchart.jpg">

<br>
<h3 id="screenshots">:camera: Screenshots</h3>

| Screenshot | Page Title |
| --- | --- |
| <img src="https://i.postimg.cc/02GnqcT4/Screenshot-2023-05-04-at-02-24-28.png" alt="Sign up" width="500"/> | Sign Up  |
| <img src="https://i.postimg.cc/VkYRsX1k/Screenshot-2023-05-04-at-02-24-06.png" alt="Sign in" width="500"/> | Sign In |
| <img src="https://i.postimg.cc/hvNtyLrb/Screenshot-2023-05-04-at-03-13-18.png" alt="Passenger card" width="500"/> | Passenger Card |
| <img src="https://i.postimg.cc/43GmR1TW/Screenshot-2023-05-04-at-03-11-45.png" alt="Student university" width="500"/> | Student University |
| <img src="https://i.postimg.cc/rmnmWXRH/Screenshot-2023-05-04-at-03-12-11.png" alt="Card zone" width="500"/> | Card Zone |
| <img src="https://i.postimg.cc/4yY3QLzx/Screenshot-2023-05-04-at-03-12-34.png" alt="Card invoice" width="500"/> | Card Invoice |
| <img src="https://i.postimg.cc/VsTgf9t1/Screenshot-2023-05-04-at-00-19-31.png" alt="Home" width="500"/> | Home |
| <img src="https://i.postimg.cc/pVFks2qR/Screenshot-2023-05-04-at-01-57-42.png" alt="Schedule" width="500"/> | Schedule |
| <img src="https://i.postimg.cc/0jDfjfbb/Screenshot-2023-05-04-at-01-58-12.png" alt="Journey planner" width="500"/> | Journey Planner |
| <img src="https://i.postimg.cc/28NGJPKv/Screenshot-2023-05-04-at-01-58-29.png" alt="Stations" width="500"/> | Stations |
| <img src="https://i.postimg.cc/wvS29PxC/Screenshot-2023-05-04-at-01-59-08.png" alt="Edit profile" width="500"/> | Edit Profile |
| <img src="https://i.postimg.cc/tT1DQybd/Screenshot-2023-05-04-at-01-59-24.png" alt="Card details" width="500"/> | Card Details |
| <img src="https://i.postimg.cc/QxkMkT75/Screenshot-2023-05-04-at-03-17-34.png" alt="Administrator" width="500"/> | Administrator |

<p align="right">(<a href="#readme-top">back to top</a>)</p>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)

<h2 id="contributors">:busts_in_silhouette:&ensp;Contributors</h2>

<p>This project was made to complete a module during our Erasmus+ Mobility Program in <a href="https://ispgaya.pt/en">Instituto Superior Politécnico Gaya (ISPGAYA)</a>.</p>

:woman:&ensp;<b>Luana Kimley</b> <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GitHub: <a href="https://github.com/luanakimley">@luanakimley</a> <br>

:boy:&ensp;<b>Niall O'Reilly</b> <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GitHub: <a href="https://github.com/niall-oreilly21">@niall-oreilly21</a> <br>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)

<h3 id="acknowledgments">:memo:&ensp;Acknowledgments</h3>
<ul>
  <li><a href="https://gitmoji.dev/">Gitmoji</a></li>
  <li><a href="https://chat.openai.com/">ChatGPT</a></li>
  <li><a href="https://www.flaticon.com/">Flat Icon</a></li>
  <li><a href="https://en.metrodoporto.pt/metrodoporto/uploads/document/file/635/horarios_abril_2023.pdf">Porto Metro timetable information</a></li>
</ul>

<p align="right">(<a href="#readme-top">back to top</a>)</p>
