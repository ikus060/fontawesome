[![Build](http://git.patrikdufresne.com/pdsl/fontawesome/badges/master/build.svg)](http://git.patrikdufresne.com/pdsl/fontawesome)
[![Quality](http://sonar.patrikdufresne.com/api/project_badges/measure?project=com.patrikdufresne%3Acom.patrikdufresne.fontawesome&metric=alert_status)](http://sonar.patrikdufresne.com/dashboard?id=com.patrikdufresne%3Acom.patrikdufresne.fontawesome)

# fontawesome
Font Awesome for SWT. Allow use of fontawesome icons in SWT application any where font are allowed.

## Usage
To make use of this library, you must add the following to your maven project.

    <repositories>
        <repository>
            <id>patrikdufresne</id>
            <url>http://nexus.patrikdufresne.com/content/groups/public/</url>
        </repository>
    </repositories>

    ...

    <dependency>
        <groupId>com.patrikdufresne</groupId>
        <artifactId>com.patrikdufresne.fontawesome</artifactId>
        <version>4.3.0-3</version>
    </dependency>
    
SWT and JFace is required to be in your classpath for this library to be working.
    
In your java code you can use fontawesome icons like this:

    Button button = new Button(parent, SWT.FLAT);
    button.setFont(FontAwesome.getFont());
    button.setText(FontAwesome.times);

