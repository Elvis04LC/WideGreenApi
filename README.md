﻿# WideGreenApi

**WideGreenApi** es una API RESTful desarrollada en **Java 17 + Spring Boot** que impulsa la plataforma **WideGreen**, un ecosistema digital de participación ciudadana ambiental. El sistema permite la gestión de usuarios, eventos, publicaciones, calendarios de actividades, comentarios, organizadores y estadísticas, promoviendo la sostenibilidad urbana mediante herramientas colaborativas.

---

## 🚀 Tecnologías principales

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- ModelMapper
- Lombok
- Swagger OpenAPI
- Maven

---

## 🔐 Autenticación y Seguridad

- Sistema de login/registro con autenticación JWT.
- Roles: `USER`, `ADMIN`.
- Accesos protegidos por `SecurityConfig.java`.
- CORS habilitado para `http://localhost:4200`.

---

## 📌 Funcionalidades principales
### 👤 Gestión de usuarios
- Registro y autenticación con JWT.
- Visualización de perfil.
- Edición de información personal.

### 📰 Publicaciones y comentarios
- Crear, listar, filtrar publicaciones.
- Asociar imágenes o enlaces.
- Añadir, listar y eliminar comentarios (solo el autor puede eliminarlos).

### 📅 Calendarios y actividades
- Cada usuario posee su calendario.
- Registro y visualización de actividades diarias.
- Sincronización con eventos inscritos.

### 📍 Eventos ambientales
- Registro, edición y eliminación de eventos.
- Inscripción y cancelación por parte del usuario.
- Gestión de organizadores.

### 📊 Estadísticas
- Cantidad de inscritos por evento.
- Actividades registradas por usuario.
- Reportes para panel administrador.

---
## 🌐 Endpoints REST destacados

| Método | Ruta | Descripción | Seguridad |
|--------|------|-------------|-----------|
| `POST` | `/api/auth/register` | Registro de usuario | Público |
| `POST` | `/api/auth/login` | Login y emisión JWT | Público |
| `GET`  | `/api/usuarios/{id}` | Obtener perfil de usuario | JWT |
| `GET`  | `/api/eventos` | Listar eventos | JWT |
| `POST` | `/api/eventos` | Crear evento (admin) | ADMIN |
| `POST` | `/api/inscripciones/registrar` | Inscribirse a evento | JWT |
| `GET`  | `/api/actividades/usuario/{id}` | Ver actividades | JWT |
| `GET`  | `/api/publicaciones` | Ver publicaciones | JWT |

## 🛠️ Configuración y ejecución

### 🧱 Requisitos previos
- Java 17
- Maven 3.8+
- PostgreSQL 13+

  
### 🧪 Pruebas
- Todas las funcionalidades han sido probadas vía Swagger UI y Postman.
- JWT se incluye en los headers con Authorization: Bearer {token}.
- Se usan DTOs para evitar exposición directa de entidades.

## 👨‍💻 Autores
- Elvis Leonardo Larico Chavez (Lider de Proyecto)
- Gabriel infante
- Lucia Ly
- Maykol García
### Arquitectura de Aplicaciones Web · Universidad Peruana de Ciencias Aplicadas
