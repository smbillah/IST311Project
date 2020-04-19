# To get the updated Eclipselink JPA, go to 
https://www.eclipse.org/eclipselink/downloads/index.php#27


To get the missing .jar for JAVAFX if you are using autocomplete control, download the jar 
https://nexus.gluonhq.com/nexus/content/repositories/releases/com/gluonhq/charm-glisten/4.4.1/charm-glisten-4.4.1.jar, add it as a library in NetBeans 11.2 (e.g, GluonLib)

VM Options:
--add-modules javafx.controls,javafx.fxml

Connection String:
jdbc:mysql://localhost:3306/ist311_db?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC

