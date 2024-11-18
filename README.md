# SGTD
Link al video: https://drive.google.com/file/d/1hfCdyA4vN6Mw-urbIe9jxytBo1d-xuhk/view?usp=drivesdk

 Sistema de Gestion de Tratamientos Dentales
Introducción
El proyecto desarrollado es un sistema informático para la gestión integral de una clínica odontológica. Este sistema permite la administración eficiente de pacientes, tratamientos y usuarios, integrando funcionalidades de registro, actualización y consultas mediante una interfaz gráfica en Java Swing, con persistencia en una base de datos MySQL.

El objetivo del proyecto es facilitar la operativa diaria de la clínica, mejorando la organización y proporcionando una herramienta escalable que permita una fácil expansión en futuras versiones.

Modelo de Negocio y Justificación del Proyecto:
La clínica odontológica requiere un sistema que centralice la información de pacientes y tratamientos, garantizando accesibilidad y seguridad en el manejo de los datos. 

El proyecto informático propuesto permite:

Almacenar de forma centralizada la información de pacientes y tratamientos.
Reducir errores humanos mediante la automatización de procesos.
Facilitar el acceso a la información para diferentes tipos de usuarios (Administrador, Recepcionista, Odontólogo).
Ofrecer un historial completo de tratamientos para cada paciente.
El sistema se desarrolló utilizando el patrón de diseño MVC, asegurando una clara separación de responsabilidades y una arquitectura escalable.

El proyecto sigue el patrón de diseño MVC (Modelo-Vista-Controlador):

Modelo: Contiene la lógica de negocio y maneja la persistencia de datos con MySQL.
Vista: Formada por interfaces gráficas en Java Swing, que interactúan con el usuario.
Controlador: Gestiona la lógica de la aplicación, validando datos y facilitando la comunicación entre la vista y el modelo.
Ventajas del Patrón MVC en el Proyecto
Separación de Responsabilidades: Mejora la mantenibilidad al separar la lógica de negocio, la presentación y la interacción con el usuario.
Escalabilidad: Permite agregar nuevas funcionalidades y módulos sin afectar la estructura del sistema.
Reutilización de Código: Las clases del modelo y las interfaces pueden ser reutilizadas fácilmente en diferentes módulos.

Persistencia de Datos con MySQL:
El sistema utiliza una base de datos MySQL para gestionar la información de usuarios, pacientes y tratamientos. La base de datos ha sido normalizada para evitar redundancia de datos y mejorar la integridad.

Estructura de la Base de Datos
La base de datos está compuesta por las siguientes tablas principales:

usuarios: Contiene información de los usuarios, incluyendo su rol (Administrador, Recepcionista, Odontólogo).
pacientes: Almacena datos de los pacientes, como nombre, email, teléfono y dirección.
tratamientos: Registra los tratamientos realizados a los pacientes, con detalles sobre el tipo de tratamiento y su estado.


Análisis del Proceso Unificado de Desarrollo (PUD)
Fase de Elaboración
Durante esta fase se definieron los requisitos del sistema y se diseñaron los diagramas de casos de uso, identificando las funcionalidades principales: registro de pacientes, gestión de usuarios y administración de tratamientos.

Fase de Construcción
En esta fase se implementaron los módulos del sistema utilizando Java y MySQL. 
Fase de Transición
Se realizaron pruebas de usuario para garantizar la usabilidad y corregir posibles errores antes de la entrega final del prototipo.

Manejo de Excepciones
El sistema maneja adecuadamente las excepciones para asegurar su robustez frente a errores. Se emplean bloques try-catch para manejar problemas de conexión y validación de datos.


Conclusión
SGTD implementa un sistema robusto y escalable para la gestión odontológica. Utilizando el patrón MVC, persistencia en MySQL y manejo adecuado de excepciones, el sistema garantiza una operación eficiente y segura. Los módulos implementados permiten una gestión completa de usuarios, pacientes y tratamientos, facilitando las operaciones diarias de la clínica odontológica.



