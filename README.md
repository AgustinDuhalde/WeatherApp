# WeatherApp
Examen técnico

<div style="text-align: justify"> 

Esta es una aplicación de un examen técnico para **Android** hecha en **Kotlin**. La idea es visualizar el pronóstico actual y de los proximos 5 dias de la ciudad actual y de otras ciudades 5 ciudades seleccionables. Se utilizó el servicio API de **Open Weather Map**. Los endpoints usados fueron:

* Current weather data: https://openweathermap.org/current

* 5 day weather forecast: https://openweathermap.org/forecast5

Para la arquitectura de la app decidí utilizar **MVVM** (Model-View-ViewModel) como así también los **AAC** (Android Architecture Components), en particular **ViewModel** y **LiveData**. Al ser una aplicación sencilla, la **Activity** se encargó del control de la vista, que a su vez utilizó la librería **databinding** para la muestra de datos en la vista y evitar código “boilerplate”. Para la UI se utilizaron dos librerías de Android support que fueron **RecyclerView** y **CardView**, para el simple hecho de mostrar uso de componentes. También se utilizó **Picasso** para la carga de imágenes remotas en la UI (iconos del clima).

Se utilizó el patrón **Repository** para la parte de comunicación con la API. De esta manera se abstrae del ViewModel el conocimiento de donde pedir los datos. En el Repository se puede decidir si traer los datos de la fuente remota o sino de una local (por ejemplo podría usarse **Room**, el cual fue una tarea pendiente). Para la comunicación HTTP se utilizó la librería **Retrofit 2** en conjunto con **Gson** (para el mapeo de JSON) y **RxJava**.

Como recién mencionamos, utilicé para la comunicación entre el ViewModel y el Repository a **RxJava** y **RxAndroid**. Esto permitió el sencillo uso de manejo de threads, responses y errores, a través de **Observables**.

Para aplicar “Dependency Injection” usé la librería **Dagger**. Así se abstrae la responsabilidad de crear los objetos que se necesitan dentro de una clase y se facilita la etapa de testing.

Para obtener la ubicación actual usé el servicio de **Google Service**, el **Location Provider**. De esta manera se obtiene la última ubicación conocida luego de haber chequeado previamente los permisos necesarios.

Finalmente, las 5 ciudades seleccionables se crearon en un **Enum** y fueron elegidas desde la API utilizada, podría implementarse de otra manera. La **API Key** y las **URLs** fueron provistas desde un objeto estático a los lugares necesarios, esto fue más que nada por simplicidad, lo correcto sería tener distintos **flavors** o ambientes en donde cada uno tenga su archivo de configuración. Pero no fue necesario en este caso.

Contiene **tests** del ViewModel corroborando el correcto funcionamiento entre la lógica y estado, con el manejo de los eventos.

</div>
