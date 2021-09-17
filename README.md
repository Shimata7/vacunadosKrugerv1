# Vacunados Kruger

Vacunados Kruger es un servicio RestApi que permite a la empresa conocer el estado de vacunación de sus empleados. Este Proyecto tiene como perfil BACKEND, por lo que se utilizaron tecnologías a corde.
Está construido en en java utilizando Springboot 2.5.4 y como base de datos Postgressql 13.0, para la gestión y producción se utilizó Maven. 

## Instalación

Para instalar la aplicación seguir los siguientes pasos:<br>
    <br>- Instalar Postgressql 13.0, crear una base de datos llamada "vacunación" y realizar un restore del siguiente archivo: [vacunacion.sql](https://drive.google.com/file/d/1Tr5tg1x5HjgUfDzq4ENpAJFtzHYMWNht/view?usp=sharing)<br>
    - Realizar la clonación del proyecto en el IDE preferido. Y en el archivo src/main/resources/application.properties cambiar la información de la base de datos para que coincida con su base de datos.<br>
    <img src="https://i.ibb.co/ZX8Vx35/aplication-Configuration.jpg" alt="aplication-Configuration" border="0"><br>
    - Correr la aplicación.
    


## Ejecución

Una vez que la aplicación se encuentre corriendo se puede utilizar en primera instancia el servicio login para obtener el token necesario para el uso de los demás servicios,
en la base de datos se encuentra precargado con el archivo de restores el siguiente usuario tipo administrador:<br>
 -Usuario= du1713766688<br>
 -Clave= 123<br>
 ### Login
 <br>
<img src="https://i.ibb.co/k9J8rvZ/login.jpg" alt="login" border="0">
 <br>
 se debe enviar el body de la siguiente manera:<br>
 
```json
{
  "usuario": "du1713766688",
  "clave": "123"
}
```
La respuesta contendrá el token junto con el rol e id del usuario.<br><br>
A continuación, se describe la forma de utilizar cada uno de los endpoints:<br>
### Obtener usuarios
<br>
<img src="https://i.ibb.co/p4240PZ/get-Usuarios.jpg" alt="get-Usuarios" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". <br>
<h3> Registrar usuario </h3>
 <br>
 <img src="https://i.ibb.co/N2MrHbv/registar-Usuario.jpg" alt="registar-Usuario" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". Además, los siguientes datos del usuario a generar:
 
```json
{
        "cedula": 1713766654,
        "nombres": "James",
        "apellidos": "Hetfield",
        "correo": "jam@hetfield.com"
}
```
Todos los campos son validados antes de hacer la inserción, si los datos son correctos se crea un nombre de usuario) y contraseña automáticamente para el usuario. El nombre de usuario es la primera letra del nombre más la primera 
letra del apellido más el número de cédula, la clave es el número de cédula.<br><br>

### Obtener usuarios según estado de vacunación
<br>
<img src="https://i.ibb.co/NnntdmK/usuario-Por-Estado-Vacuna.jpg" alt="usuario-Por-Estado-Vacuna" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". Y en la url el estado de vacuna que se busca true o false. La respuesta es un listado de usuarios.<br>

### Obtener usuarios según tipo de vacuna
<br>
<img src="https://i.ibb.co/r54s7cy/usuario-Por-Tipo-Vacuna.jpg" alt="usuario-Por-Tipo-Vacuna" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". Y en la url el id de vacuna que se busca. La respuesta es un listado de usuarios.<br>

### Obtener usuarios según rango de fecha de vacunación
<br>
<img src="https://i.ibb.co/b1txwZk/usuarios-Por-Rango-Fechas.jpg" alt="usuarios-Por-Rango-Fechas" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". Y en la url la fencha de inicio del rango y luego la fecha final. Ejemplo:/api/usuarios_rango_fecha/2021-09-01/2021-09-30. La respuesta es un listado de usuarios.<br>
 
 ### Obtener usuario por su id
<br>
<img src="https://i.ibb.co/jyt3T05/usuario-Por-Id.jpg" alt="usuario-Por-Id" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". Y en la url el id del usuario. La respuesta es un usuario.<br>
 
  ### Modificar usuario
<br>
<img src="https://i.ibb.co/MNZhXpK/modificar-Usuario.jpg" alt="modificar-Usuario" border="0">
 <br>
 Se debe enviar el token en el header "Authorization", el id del usuario en la url y toda la información del usuario de la siguietne manera:<br>
 
 ```json
{
  "apellidos": "string",
  "cedula": 0,
  "celular": 0,
  "clave": "string",
  "correo": "string",
  "direccion": "string",
  "dosis": 0,
  "estado": true,
  "estado_vacuna": true,
  "fecha_nacimiento": "2021-09-17T04:04:47.843Z",
  "fecha_vacuna": "2021-09-17T04:04:47.843Z",
  "id": 0,
  "nombres": "string",
  "rol": {
    "id": 0,
    "rol": "string"
  },
  "tipo_vacuna": {
    "id": 0,
    "tipo_vacuna": "string"
  },
  "usuario": "string"
}
```
La respuesta es un true si todo salió bien, si existen campos no válidos la respuesta es false.

   ### Eliminar usuario
<br>
<img src="https://i.ibb.co/h7qdfbr/eliminar-Usuario.jpg" alt="eliminar-Usuario" border="0">
 <br>
 Se debe enviar el token en el header "Authorization". Y en la url el id del usuario. La respuesta es un true si todo salió bien, si existe algún error la respuesta es false.<br>
 
 ### Registrar datos de vacunación por el empleado
<br>
<img src="https://i.ibb.co/D9JTYDr/info-Vacunacion-Por-Empleado.jpg" alt="info-Vacunacion-Por-Empleado" border="0">
 <br>
 Se debe enviar el token en el header "Authorization", el id del usuario en la url y toda la información del usuario de la siguietne manera:<br>
 
 ```json
{
  "id":6,
        "fecha_nacimiento": "1987-07-26T05:00:00.000+00:00",
        "direccion": "Itchimbia hg",
        "celular": "099658846",
        "estado_vacuna": true,
         "tipo_vacuna_id": 3,
        "fecha_vacuna": "2021-01-03T05:00:00.000+00:00",
        "dosis": 1
}
```
La respuesta es un true si todo salió bien, si existen campos no válidos la respuesta es false.<br>
## Modelo de datos
<img src="https://i.ibb.co/vLLj9W4/modelo-de-datos.jpg" alt="modelo-de-datos" border="0">

[vacunacion.sql](https://drive.google.com/file/d/1Tr5tg1x5HjgUfDzq4ENpAJFtzHYMWNht/view?usp=sharing)

## Construcción

Para construir la solución se utilizó en primera instancia el Spring Initializr, luego se creó el package models con las clases Rol, Tipo_vacuna y Usuario, se crearon sus atributos, sus getter y setters, junto con las notaciones de springboot para la persistencia de datos con hibernate. Se crearon los controladores de los modelos más el AthorizationController encargado del login. Se generaron los DAO respectivoss con la interface e implementación. Se crearon en las implementaciones las solicitudes a las bases de datos y funciones de validación de la información recibida. Se agregó un package Utils en el que se ubicó la clase JWUtil para la generación del token. Se escribieron las propiedades en el application.properties y durante el proceso se instalaron las dependencias necesarias que se pueden evidenciar en el pom.xml.

## Cualquier duda o comentario 
Mauricio López - lopezsmauricio@gmail.com
