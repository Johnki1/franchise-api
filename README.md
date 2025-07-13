# 🏪 Franchise API

API REST reactiva para la gestión de franquicias, sucursales y productos.  
Desarrollada en **Java + Spring Boot WebFlux + MongoDB**, con despliegue automatizado mediante contenedores Docker y herramientas de infraestructura como código (Terraform + Ansible).

---

## 🌍 Enlace público para probar la API

> Puedes probar la API desde cualquier cliente REST como **Insomnia** o **Postman**:

```

[http://44.204.72.134:8080/api/franchise]

```

⚠️ **Nota:** Solo se puede interactuar mediante llamadas HTTP.  
**No tienes acceso al servidor EC2 ni a la base de datos MongoDB directamente.**

---

## 📦 Funcionalidades principales

Esta API permite:

- Crear franquicias.
- Agregar sucursales a una franquicia.
- Agregar productos a una sucursal.
- Eliminar productos.
- Actualizar stock.
- Consultar productos con mayor stock por franquicia.
- Actualizar nombre de franquicia, sucursal o producto.
- Consultar todas las franquicias existentes.

---

## 🛠 Tecnologías utilizadas

| Tecnología     | Descripción                                  |
|----------------|----------------------------------------------|
| Java 21        | Lenguaje principal                           |
| Spring Boot WebFlux | Framework reactivo (no bloqueante)     |
| MongoDB        | Base de datos NoSQL                          |
| Docker         | Contenedores para backend y base de datos    |
| Docker Compose | Orquestación                           |
| Terraform      | Infraestructura como código (IaC)            |
| Ansible        | Automatización del despliegue (IaC)          |
| Maven          | Gestión de dependencias y empaquetado        |
| JUnit + Mockito| Pruebas unitarias                            |
| Insomnia/Postman| Cliente REST para pruebas manuales          |

---

## 🧪 Cómo probar los endpoints

Puedes usar [Insomnia](https://insomnia.rest/) o [Postman](https://www.postman.com/) con esta **URL base**:

```

[http://44.204.72.134:8080/api/franchise]

```

### ✅ Endpoints disponibles

#### ➕ Crear una franquicia
```

POST /api/franchise
Body:
{
"name": "Mi Franquicia"
}

```

#### ➕ Agregar sucursal
```

POST /api/franchise/{id}/branch
Body:
{
"branchName": "Sucursal Medellín"
}

```

#### ➕ Agregar producto a sucursal
```

POST /api/franchise/{id}/branch/{branchName}/product
Body:
{
"productName": "Pizza",
"stock": 40
}

```

#### 🧼 Eliminar un producto
```

DELETE /api/franchise/{id}/branch/{branchName}/product/{productName}

```

#### ✏️ Actualizar stock de producto
```

PUT /api/franchise/{id}/branch/{branchName}/product/{productName}/stock/{newStock}

```

#### 🔍 Obtener productos con mayor stock
```

GET /api/franchise/{id}/highest-stock-products

```

#### ✏️ Cambiar nombre de franquicia
```

PUT /api/franchise/{id}/name
Body:
"Nuevo Nombre"

```

#### ✏️ Cambiar nombre de sucursal
```

PUT /api/franchise/{id}/branch/name?oldName=Antigua\&newName=Nueva"

```

#### ✏️ Cambiar nombre de producto
```

PUT /api/franchise/{id}/branch/{branchName}/product/name?oldName=Antigua\&newName=Nueva

```

#### 📋 Obtener todas las franquicias
```

GET /api/franchise

````

---

## 💻 Cómo ejecutar localmente

### 1. Clonar el proyecto
### Requisito para clonar el repositorio
*git

```bash
git init
git clone https://github.com/Johnki1/franchise-api.git
cd franchise-api
````

### 2. Requisitos

* Java 21
* Maven
* Docker y Docker Compose

### 3. Ejecutar con Docker Compose

```bash
docker-compose up --build
```

Esto ejecutará:

* Backend en `localhost:8080`
* MongoDB en `localhost:27017`

### 4. Probar en local

Base URL:

```
http://localhost:8080/api/franchise
```

Prueba los mismos endpoints anteriores usando tu cliente REST.

---

## 📁 Estructura del proyecto

```bash
franchise-api/
├── Dockerfile                   # Dockerización del backend
├── docker-compose.yml          # Orquesta app + MongoDB
├── franchise-IaC/              # Infraestructura como Código
│   ├── ansible/                # Automatización con Ansible
│   │   ├── hosts.ini
│   │   └── setup.yml
│   └── terraform/              # Provisionamiento en AWS
│       ├── main.tf
│       ├── modules/
│       │   ├── EC2/
│       │   └── Networking/
├── src/
│   ├── main/java/com/example/franchise/
│   │   ├── controller/         # Controladores REST
│   │   ├── model/              # Modelos: Franchise, Branch, Product
│   │   ├── repository/         # Repositorio MongoDB
│   │   └── service/            # Lógica de negocio
│   └── test/java/com/example/franchise/
│       └── FranchiseServiceTest.java # pruebas
├── pom.xml                     # Dependencias del proyecto
└── README.md                   # Este archivo
```

---

## ✅ Pruebas Unitarias

```bash
mvn test
```

Las pruebas están ubicadas en:

```
src/test/java/com/example/franchise/FranchiseServiceTest.java
```

Cubren los casos más relevantes del servicio.

---

## 🧠 Consideraciones

* No es necesario conectarse por SSH ni acceder a la base de datos directamente.
* Toda interacción debe hacerse por HTTP (GET, POST, PUT, DELETE).
* Si deseas extender el sistema o contribuir, puedes trabajar localmente.

---

## 👨‍💻 Autor

**Jhon Kider Alzate**
🔗 [GitHub - @Johnki1](https://github.com/Johnki1)

---
