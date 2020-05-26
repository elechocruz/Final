# Final
Proyecto Final - 16100176

EVALUACION 3

**Implementar el almacenamiento en la memoria interna :**
El Almacenamiento interno se utiliza en MainActivity.kt en la funcion btnLog() para crear un archivo que guarda el ultimo usuario
con el que se ingreso a la aplicacion, evitando asi que se tenga que volver a escribir en un futuro.
(Para continuar en la aplicacion, puede registrar un usuario solo poniendo un username y un password y dando en el boton de registrar, 
posteriormente intentar loguear con el nuevo usuario, si no funciona intentar con **usuario:** usuario **contrasena:** qwe123).

**Implementar el almacenamiento en la memoria externa :**
Al loguear con un usuario lo llevara a Perfil.kt donde se implementa el almacenamiento externo, en la funcion createImageFile() llamada al
presionar en el ImageView, esto tomara una foto haciendo uso de la aplicacion de camara, la mostrara en el ImageView y la guardara en el
almacenamiento externo, esta Activity esta destinada a ser el perfil del usuario, no funciona aun esta en proceso, para continuar es necesario 
darle al boton de BUTTON, aunque no guarda los datos por el momento.


**Implementar el almacenamiento de datos utilizando la biblioteca de ROOM :**
La base de datos en ROOM se puede encontrar el modelo en el paquete modelo y los archivos de la base de datos en el paquete room, la 
base de datos esta destinada a crear una lista persona de peliculas que ya viste, por lo que solo guarda localmente las peliculas que seleccionas como vistas
esto se hace desde DetallesPelicula.kt en la funcion btnMarcarVisto, en el boton con una palomita de la esquina, el cual guardara desde una 
AsynTask la informacion de la pelicula, para ser mostrada posteriormente como una lista personal de peliculas (Aun no implementado, solo guardar por el momento).
Para llegar a esta Activity es necesario seleccionar cualquier pelicula del RecyclerView donde se carga la lista de peliculas.


**Implementar el consumo de un proveedor de contenido existente :**
Por ultimo el Content Provider utilizado fue el de contactos, para mandar un SMS recomendando una pelicula, esto se implementa de igual manera en
DetallesPelicula.kt para llenar un Spinner con la informacion de los contactos, seleccionar uno y posteriormente mandar el SMS.

