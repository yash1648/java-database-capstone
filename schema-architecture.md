# Architecture summary
This Spring Boot application uses both MVC and REST controllers. Thymeleaf templates are used for the Admin and Doctor dashboards, while REST APIs serve all other modules. The application interacts with two databasesΓÇöMySQL (for patient, doctor, appointment, and admin data) and MongoDB (for prescriptions). All controllers route requests through a common service layer, which in turn delegates to the appropriate repositories. MySQL uses JPA entities while MongoDB uses document models.

# Numbered flow of data and control
[1] The user interacts with the web interface rendered using Thymeleaf templates.

[2] The browser sends an HTTP request to the Spring Boot MVC controller.

[3] The controller receives the request and forwards it to the service layer.

[4] The service layer executes the required business logic.

[5] The service interacts with the repository layer to retrieve or store data.

[6] The repository communicates with the database using Spring Data JPA.

[7] The processed result is returned back through the service and controller, and the Thymeleaf template renders the response to the user.


