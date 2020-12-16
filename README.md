# practicaPMDM
Proyecto de Móviles. Pablo y Jonatan

## DESCRIPCIÓN

**practicaPMDM** se trata de una aplicación desarrollada en android studio que tiene un menú lateral desplegable desde el cual tendremos las opciones de posicionar al usuario 
sobre un mapa de Street Maps, tiene la opción de buscar las piscinas y los polideportivos que estén a un radio de 8km sobre la posición del usuario, la posición se estará 
actualizando contínuamente con el paso del tiempo o cuando el usuario varíe su posición. Tendremos otra opción de ir a páginas web específicas que se ejecutarán y visualizarán
dentro de nuestra aplicación. Por último tendremos la opción de cada piscina o polideportivo visualizados agregarlos a favoritos(que se mantendrán aunque se cierre la aplicación),
quitarlo de favoritos,o acceder a su posición en el mapa.

## REQUISITOS

+ Los datos los obtendremos haciendo peticiones al portal de datos abiertos de Madrid url: (https://datos.madrid.es). 
+ Utilizaremos gradel como gestor de dependencias

## PASOS PARA CLONAR EL PROYECTO

1. Descargar la consola de git desde la siguiente url: (https://git-scm.com/downloads), y la abrimos.

2. Escribir en la consola:
 
git clone https://github.com/PabloViniegra/practicaPMDM.git

Con eso clonará el repositorio en una carpeta local


## EJECUCIÓN Y DEPENDENCIAS

1. Para ejecutarlo abrimos android studio, configuramos el emulador en la pestaña AVD Manager con una API de android igual o superior a 27 o bien podemos utilizar el movil 
como emulador. Éste youtuber os enseña a hacerlo en caso de duda url: (https://www.youtube.com/watch?v=vf3C8h0CbyU&ab_channel=CristianHenao). 

2. Dependencias:

    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.navigation:navigation-fragment:2.1.0'
    implementation 'androidx.navigation:navigation-ui:2.1.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.1.0'
    implementation 'com.android.support:support-annotations:28.0.0'
    implementation 'androidx.work:work-runtime:2.2.0'
    implementation group: 'com.squareup.retrofit2', name: 'retrofit', version: '2.9.0'
    implementation group: 'com.squareup.retrofit2', name: 'converter-gson', version: '2.9.0'
    implementation 'com.android.support:design:29.0.0'
    annotationProcessor 'org.projectlombok:lombok:1.18.16'
    implementation 'com.android.support:support-v4:27.0.2'
    implementation 'org.osmdroid:osmdroid-android:6.1.8' 
    implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
    implementation group: 'org.projectlombok', name: 'lombok', version: '1.18.16'
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.2.0'
    implementation 'com.google.code.gson:gson:2.8.6'

## APUNTES FINALES

Es un sencillo programa donde se tratarán aspectos básicos como la creación de activities, movernos entre ellas, la utilización de intents, la utilización de una pantalla de 
carga al iniciar la aplicación, acceder a una api, obtener un json, parsearlo y obtener sus datos. Mandar datos entre actívities. Plasmar los datos en un View Adapter, cargar
un mapa en nuestra aplicación, entre otras muchas cosas.
