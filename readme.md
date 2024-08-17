# Gestión de Pagos de Servicios

Este repositorio contiene un conjunto de endpoints desarrollados con Java Server Faces (JSF), funcionando en un servidor Glassfish 4.1 o 5. El objetivo de este conjunto de endpoints es proporcionar una solución para la gestión de pagos de servicios.

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

---

Para más información, consulta la [documentación](https://documenter.getpostman.com/view/10049027/2sA3s9BnRd).
