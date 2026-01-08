
# 🏪 Franchise API

API REST **reactiva** para la gestión de **franquicias, sucursales y productos**, desarrollada con **Java 21 + Spring Boot WebFlux + MongoDB**.

El proyecto incluye:

- Backend desacoplado y no bloqueante
- Persistencia en MongoDB
- Contenerización con Docker
- Automatización de despliegue en AWS usando **Terraform + Ansible**
- Arquitectura limpia y manejo profesional de errores

---

## 🌍 API pública (ambiente de prueba)

La API está desplegada en una instancia EC2 de AWS y puede probarse desde cualquier cliente REST (Postman / Insomnia):

```

http://44.204.72.134:8080/api/franchise

```

⚠️ **Nota**  
El acceso está limitado exclusivamente a llamadas HTTP.  
No existe acceso directo al servidor ni a la base de datos.

---

## 📦 Funcionalidades

La API permite:

- Crear franquicias
- Listar todas las franquicias
- Agregar sucursales a una franquicia
- Agregar productos a una sucursal
- Evitar entidades duplicadas (franquicias, sucursales, productos)
- Eliminar productos
- Actualizar stock de productos
- Actualizar nombres (franquicia, sucursal, producto)
- Consultar el producto con mayor stock por sucursal en una franquicia
- Manejo centralizado y profesional de errores

---

## 🛠 Tecnologías utilizadas

| Tecnología          | Uso                             |
| ------------------- | ------------------------------- |
| Java 21             | Lenguaje principal              |
| Spring Boot WebFlux | Backend reactivo                |
| MongoDB             | Base de datos NoSQL             |
| Spring Data MongoDB | Persistencia                    |
| Docker              | Contenerización                 |
| Docker Compose v2   | Orquestación                    |
| Terraform           | Infraestructura como código     |
| Ansible             | Automatización de configuración |
| Maven               | Gestión de dependencias         |
| JUnit / Mockito     | Pruebas unitarias               |

---

## 🔌 Endpoints principales

**Base URL**

```

/api/franchise

````

### ➕ Crear franquicia

```http
POST /api/franchise
````

```json
{
  "name": "Mi Franquicia"
}
```

---

### ➕ Agregar sucursal

```http
POST /api/franchise/{id}/branch
```

```json
{
  "branchName": "Sucursal Medellín"
}
```

---

### ➕ Agregar producto

```http
POST /api/franchise/{id}/branch/{branchName}/product
```

```json
{
  "productName": "Pizza",
  "stock": 40
}
```

---

### 🧼 Eliminar producto

```http
DELETE /api/franchise/{id}/branch/{branchName}/product/{productName}
```

---

### ✏️ Actualizar stock

```http
PUT /api/franchise/{id}/branch/{branchName}/product/{productName}/stock/{newStock}
```

---

### 🔍 Producto con mayor stock

```http
GET /api/franchise/{id}/highest-stock-products
```

---

### ✏️ Actualizar nombre de franquicia

```http
PUT /api/franchise/{id}/name
```

```json
"Nuevo Nombre"
```

---

### ✏️ Actualizar nombre de sucursal

```http
PUT /api/franchise/{id}/branch/name?oldName=Antigua&newName=Nueva
```

---

### ✏️ Actualizar nombre de producto

```http
PUT /api/franchise/{id}/branch/{branchName}/product/name?oldName=Antiguo&newName=Nuevo
```

---

### 📋 Obtener todas las franquicias

```http
GET /api/franchise
```

---

## 🧪 Pruebas de la API (Insomnia)

Para facilitar la validación, el repositorio incluye un **archivo de exportación de Insomnia** con todos los endpoints ya configurados.

### 📁 Archivo incluido

* Export realizado en **Insomnia v5**
* Contiene:

    * Todos los endpoints
    * Variables de entorno
    * Base URL configurable (local / nube)

### 📥 Cómo importar en Insomnia

1. Abrir Insomnia
2. Ir a **Application → Preferences → Data → Import Data**
3. Seleccionar el archivo incluido en el repositorio
4. Elegir el entorno deseado (local o nube)
5. Ejecutar los endpoints directamente

---

## 💻 Ejecución local

### Requisitos

* Java 21
* Maven
* Docker
* Docker Compose (v2)

---

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/Johnki1/franchise-api.git
cd franchise-api
```

---

### 2️⃣ Ejecutar con Docker Compose

```bash
docker compose up -d --build
```

Esto levantará:

* API → `http://localhost:8080`
* MongoDB → `localhost:27017`

---

### 3️⃣ Probar en local

```
http://localhost:8080/api/franchise
```

Los endpoints son los mismos del ambiente público.

---

## 🧪 Pruebas unitarias

```bash
mvn test
```

Las pruebas se encuentran en:

```
src/test/java/com/example/franchise
```

---

## 📁 Estructura del proyecto

```bash
franchise-api/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
├── franchise-IaC/
│   ├── terraform/
│   └── ansible/
├── src/
│   ├── main/java/com/example/franchise/
│   │   ├── controller
│   │   ├── domain
│   │   │   ├── dto
│   │   │   └── model
│   │   ├── repository
│   │   ├── service
│   │   └── exception
│   └── test/java/com/example/franchise/
```

---

## 🧠 Consideraciones técnicas

* Arquitectura reactiva (no bloqueante)
* Manejo centralizado de excepciones
* Validaciones de negocio en el servicio
* Índices en MongoDB para evitar duplicados
* Infraestructura reproducible mediante IaC

---

## 👨‍💻 Autor

**Jhon Kider Alzate**
🔗 GitHub: [https://github.com/Johnki1](https://github.com/Johnki1)
---


