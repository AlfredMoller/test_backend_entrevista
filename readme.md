# Gestión de Pagos de Servicios

Este repositorio contiene un conjunto de endpoints desarrollados con Java Server Faces (JSF), funcionando en un servidor Glassfish 4.1 o 5. El objetivo de este conjunto de endpoints es proporcionar una solución para la gestión de pagos de servicios. La base de datos se encuentra en el mismo proyecto en un archivo llamado "pagos_servicios_db"

## Funcionalidades

El sistema ofrece las siguientes funcionalidades:

### 1. Consultar Deudas

Obtén información detallada sobre las deudas pendientes en base a los servicios prestados. Este endpoint permite consultar las deudas asociadas a un usuario o a un servicio específico.

### 2. Procesar Pagos

Permite realizar el procesamiento de pagos de manera segura y eficiente. Este endpoint facilita la actualización del estado de las deudas a "Cancelado" una vez que se haya completado el pago.

### 3. Consultar Pagos por Fecha

Consulta los pagos realizados dentro de un rango de fechas específico. Este endpoint te permitirá filtrar los pagos según las fechas de inicio y fin proporcionadas.

### 4. Consultar Pagos por Servicio

Accede a la información de los pagos basados en los servicios prestados. Puedes obtener los pagos realizados en función del nombre del servicio.

## Documentación de la API

Para obtener detalles más específicos sobre cada endpoint, consulta la documentación completa en el siguiente enlace:

[Documentación de la API](https://documenter.getpostman.com/view/10049027/2sA3s9BnRd)

## Requisitos

- Java Server Faces (JSF)
- Glassfish 4.1 o 5

## Instalación y Ejecución

1. Clona el repositorio en tu máquina local:
    ```bash
    git clone https://github.com/tu_usuario/tu_repositorio.git
    ```

2. Despliega el proyecto en un servidor Glassfish.

3. Configura las propiedades necesarias en el archivo `web.xml` y asegúrate de que el servidor Glassfish esté corriendo.

4. Accede a los endpoints a través de tu navegador o cliente REST preferido.


## Funcionalidades

### 1. Registro de Usuario

Para comenzar a utilizar la API, primero debes registrarte. Utiliza el siguiente endpoint:

- **Endpoint**: `http://localhost:8080/pago_servicios/v1/usuario/registrar`
- **Método**: POST

### 2. Crear Deudas

Una vez registrado, debes crear las respectivas deudas en la tabla `deudas_servicios` y agregar un saldo en la tabla `cuenta`. El saldo se tomará del último saldo registrado hasta la fecha. Si no sabes qué servicio agregar, consulta la tabla `servicios`.

### 3. Inicio de Sesión

Para iniciar sesión, utiliza el siguiente endpoint:

- **Endpoint**: `http://localhost:8080/pago_servicios/v1/usuario/login`
- **Método**: POST

### 4. Consultar Deudas

Para consultar las deudas, puedes hacerlo de dos maneras:

#### Si estás logueado

Utiliza la siguiente estructura JSON:

```json
{
    "nombre_servicio": "Personal",
    "page": 1,
    "size": 10
}
```

##### Si no estás logueado
Utiliza esta estructura JSON con nis_ocedula:
```json
{
    "nis_cedula": "123456789",
    "nombre_servicio": "Personal",
    "page": 1,
    "size": 10
}
```

- **Endpoint**: `http://localhost:8080/pago_servicios/v1/servicios/consultarDeudas`
- **Método**: POST

### 5.  Procesar Pago
Para procesar el pago de una deuda, utiliza el campo id_deuda. Este endpoint solo está disponible para usuarios logueados.
```json
{
    "id_deuda": 1
}

```

- **Endpoint**: `http://localhost:8080/pago_servicios/v1/pagos/procesarPago`
- **Método**: POST


### 6.  Consultar Pagos por Fecha
#### Si estás logueado

```json
{
    "fecha_inicio": "2024-08-01",
    "fecha_fin": "2024-08-31",
    "page": 1,
    "size": 10
}

```
##### Si no estás logueado
Utiliza esta estructura JSON con nis_ocedula:

```json
{
    "fecha_inicio": "2024-08-01",
    "fecha_fin": "2024-08-31",
    "nis_cedula": "123456789",
    "page": 1,
    "size": 10
}
```
- **Endpoint**: ` http://localhost:8080/pago_servicios/v1/pagos/listarPagosPorFecha`
- **Método**: POST

### 7.  Consultar Pagos por Fecha
#### Si estás logueado

```json
{
    "nombre_servicio": "Personal",
    "page": 1,
    "size": 10
}

```

##### Si no estás logueado
Utiliza esta estructura JSON con nis_ocedula:

```json
{
    "nis_cedula": "123456789",
    "nombre_servicio": "Personal",
    "page": 1,
    "size": 10
}

```

---

Para más información, consulta la [documentación](https://documenter.getpostman.com/view/10049027/2sA3s9BnRd).
