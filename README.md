# 💰 Gestor de Gastos - API REST

## 📖 Descripción

Gestor de Gastos es una API REST desarrollada con Spring Boot que
permite administrar gastos personales.

La aplicación permite: - Crear gastos - Editarlos - Eliminarlos -
Filtrarlos por categoría - Filtrarlos por método de pago - Filtrarlos
por rango de fechas - Generar reportes financieros - Obtener resumen del
mes actual

Proyecto desarrollado como práctica profesional backend utilizando
buenas prácticas y arquitectura en capas.

------------------------------------------------------------------------

# 🛠️ Tecnologías Utilizadas

-   Java 21
-   Spring Boot 3
-   Spring Web
-   Spring Data JPA
-   Hibernate
-   MySQL
-   Lombok
-   Jakarta Validation
-   Swagger / OpenAPI (springdoc)
-   JUnit & Mockito

------------------------------------------------------------------------

# 🏗️ Arquitectura del Proyecto

controller → Manejo de endpoints REST\
service → Lógica de negocio\
repository → Acceso a base de datos\
entity → Modelo de datos

Inyección de dependencias por constructor y separación clara de
responsabilidades.

------------------------------------------------------------------------

# 📦 Modelo de Datos

## Expense

  Campo           Tipo                Descripción
  --------------- ------------------- ------------------------
  id              Long                Identificador único
  description     String              Descripción del gasto
  amount          BigDecimal          Monto del gasto
  category        CategoryEnum        Categoría
  paymentMethod   PaymentMethodEnum   Método de pago
  date            LocalDate           Fecha del gasto
  createdAt       LocalDateTime       Fecha de creación
  updatedAt       LocalDateTime       Fecha de actualización

------------------------------------------------------------------------

# 🏷️ Enums

## CategoryEnum

FOOD\
TRANSPORT\
ENTERTAINMENT\
HEALTH\
OTHER

## PaymentMethodEnum

CASH\
CREDIT_CARD\
DEBIT_CARD\
TRANSFER

------------------------------------------------------------------------

# 🚀 Cómo Ejecutar el Proyecto

## 1️⃣ Clonar el repositorio

git clone https://github.com/tu-usuario/gestor-de-gastos.git\
cd gestor-de-gastos

## 2️⃣ Crear base de datos

CREATE DATABASE gestor_de_gastos;

## 3️⃣ Configurar application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/gestor_de_gastos\
spring.datasource.username=TU_USUARIO\
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update\
spring.jpa.show-sql=true\
spring.jpa.properties.hibernate.format_sql=true

## 4️⃣ Ejecutar

mvn spring-boot:run

Aplicación disponible en:\
http://localhost:8080

------------------------------------------------------------------------

# 📚 Documentación Swagger

http://localhost:8080/swagger-ui.html\
http://localhost:8080/swagger-ui/index.html

------------------------------------------------------------------------

# 📌 Endpoints

Base URL: http://localhost:8080/api/expenses

## Crear gasto

POST /api/expenses

Body ejemplo:

{ "description": "Cena", "amount": 150.00, "category": "FOOD",
"paymentMethod": "CREDIT_CARD", "date": "2026-02-15" }

## Obtener todos

GET /api/expenses

## Obtener por ID

GET /api/expenses/{id}

## Actualizar

PUT /api/expenses/{id}

## Eliminar

DELETE /api/expenses/{id}

------------------------------------------------------------------------

# 🔎 Filtros

## Por categoría

GET /api/expenses/category/{category}

Ejemplo: GET /api/expenses/category/FOOD

## Por método de pago

GET /api/expenses/payment-method/{paymentMethod}

## Entre fechas

GET /api/expenses/between?startDate=2026-02-01&endDate=2026-02-28

Formato obligatorio: YYYY-MM-DD

------------------------------------------------------------------------

# 📊 Reportes

## Reporte por categoría

GET /api/expenses/reports/by-category

Ejemplo respuesta:

\[ { "category": "FOOD", "amount": 450.00, "expenses": 3 }\]

## Reporte por período

GET /api/expenses/reports/period?startDate=2026-02-01&endDate=2026-02-28

## Reporte mes actual

GET /api/expenses/reports/current-month

------------------------------------------------------------------------

# 🧪 Testing

Tests unitarios implementados con: - JUnit - Mockito

Cobertura sobre: - Service layer - Lógica de reportes - Casos exitosos y
excepciones

------------------------------------------------------------------------

# 📈 Mejoras Futuras

-   Implementar DTOs
-   Manejo global de excepciones
-   Spring Security
-   Paginación
-   Docker
-   Deploy en la nube
-   Frontend (React/Angular)

------------------------------------------------------------------------

# 👩‍💻 Autor

Lara\
Backend Developer en formación 🚀

------------------------------------------------------------------------

# ⭐ Objetivo

Proyecto desarrollado para reforzar: - Desarrollo de APIs REST -
Arquitectura backend - Persistencia con JPA - Cálculos financieros con
BigDecimal - Testing unitario
