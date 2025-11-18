# NAYA E-COMMERCE - GUÍA COMPLETA DE INSTALACIÓN Y USO

## 📋 REQUISITOS PREVIOS

1. **Java JDK 17 o superior**
2. **MySQL** (o XAMPP que incluye MySQL)
3. **IDE**: IntelliJ IDEA, Eclipse, o VS Code con extensiones Java
4. **Maven** (viene incluido en IntelliJ y Eclipse)

---

## 🚀 PASO 1: CONFIGURAR LA BASE DE DATOS

### Opción A - Usando MySQL Workbench:
```sql
CREATE DATABASE naya_ecommerce;
```

### Opción B - Usando XAMPP:
1. Inicia XAMPP
2. Inicia Apache y MySQL
3. Abre phpMyAdmin (http://localhost/phpmyadmin)
4. Crea una base de datos llamada `naya_ecommerce`

---

## 📦 PASO 2: IMPORTAR EL PROYECTO

### Opción A - IntelliJ IDEA (Recomendado):
1. Abre IntelliJ IDEA
2. `File → Open → Selecciona la carpeta naya-ecommerce`
3. IntelliJ detectará automáticamente que es un proyecto Maven
4. Espera a que descargue todas las dependencias (puede tardar unos minutos)

### Opción B - Eclipse:
1. Abre Eclipse
2. `File → Import → Existing Maven Projects`
3. Selecciona la carpeta `naya-ecommerce`
4. Click en `Finish`

### Opción C - VS Code:
1. Instala extensiones: "Java Extension Pack" y "Spring Boot Extension Pack"
2. Abre la carpeta `naya-ecommerce`
3. VS Code detectará el proyecto automáticamente

---

## ⚙️ PASO 3: CONFIGURAR application.properties

Abre el archivo:
```
src/main/resources/application.properties
```

Verifica/modifica estas líneas según tu configuración de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/naya_ecommerce?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
```

**IMPORTANTE:**
- Si usas XAMPP, normalmente el password es vacío: `spring.datasource.password=`
- Si instalaste MySQL por separado, usa tu password de MySQL

---

## 🎯 PASO 4: EJECUTAR LA APLICACIÓN

### Desde IntelliJ/Eclipse:
1. Busca el archivo `NayaEcommerceApplication.java`
2. Click derecho → `Run`

### Desde Terminal/CMD:
```bash
cd naya-ecommerce
mvn spring-boot:run
```

**La aplicación se ejecutará en:** http://localhost:8080

---

## 📊 PASO 5: CARGAR PRODUCTOS INICIALES

### Opción A - Usando MySQL Workbench o phpMyAdmin:

Ejecuta estos SQL para insertar tus productos:

```sql
-- Productos Capilares
INSERT INTO productos (nombre, descripcion, precio, categoria, stock, tamanio, activo) VALUES
('Shampoo Extracto de Cayena', 'Favorece el crecimiento del cabello previniendo la caída del folículo piloso', 25000, 'CAPILAR', 100, '550ml', true),
('Acondicionador Extracto de Cayena', 'Aporta suavidad, brillo y da vida a la hebra capilar', 25000, 'CAPILAR', 100, '550ml', true),
('Shampoo Extracto de Romero', 'Contribuye a disminuir la fragilidad del cabello y fortalece la fibra capilar', 25000, 'CAPILAR', 100, '550ml', true),
('Acondicionador Extracto de Romero', 'Acondiciona y da sedosidad al cabello', 25000, 'CAPILAR', 100, '550ml', true),
('Ampolleta Nutrición', 'Recupera y transforma tu cabello con nutrición intensiva', 15000, 'CAPILAR', 50, '30ml', true),
('Ampolleta Crecimiento', 'Estimula el crecimiento capilar de manera natural y efectiva', 15000, 'CAPILAR', 50, '30ml', true),
('Tónico Capilar Intensivo', 'Tratamiento premium de crecimiento a base de aceites naturales', 35000, 'CAPILAR', 30, 'Presentación especial', true);

-- Productos Corporales
INSERT INTO productos (nombre, descripcion, precio, categoria, stock, tamanio, activo) VALUES
('Mantequilla Coco Festival', 'Hidratación profunda con vitamina E. Textura sedosa', 30000, 'CORPORAL', 100, '350g', true),
('Mantequilla Cherry', 'Dulce y nutritiva, deja tu piel suave e hidratada', 28000, 'CORPORAL', 100, 'Presentación estándar', true),
('Mantequilla Uva', 'Enriquecida con antioxidantes naturales', 28000, 'CORPORAL', 100, 'Presentación estándar', true),
('Mantequilla Kiwi', 'Vitaminas y frescura natural', 28000, 'CORPORAL', 100, 'Presentación estándar', true);
```

### Opción B - Crear un controlador de inicialización (Lo haré ahora mismo)

---

## 🛍️ FUNCIONALIDADES DEL E-COMMERCE

### PARA CLIENTES:
1. ✅ Ver catálogo de productos
2. ✅ Filtrar por categoría (Capilar/Corporal)
3. ✅ Agregar productos al carrito
4. ✅ Modificar cantidades en el carrito
5. ✅ Proceso de checkout con formulario
6. ✅ Selección de método de pago
7. ✅ Confirmación de pedido
8. ✅ Mensaje automático a WhatsApp con detalles del pedido

### FLUJO DE COMPRA:
1. Cliente navega productos
2. Agrega productos al carrito
3. Va a checkout
4. Completa sus datos (nombre, teléfono, dirección)
5. Elige método de pago
6. Confirma pedido
7. Recibe página de confirmación
8. Click en botón de WhatsApp para contactar con detalles del pedido

---

## 📱 CONFIGURACIÓN DE WHATSAPP

El número de WhatsApp está en:
```
src/main/resources/application.properties
```

Línea:
```properties
naya.whatsapp.number=573042051221
```

Cuando un cliente completa un pedido, se genera automáticamente un mensaje de WhatsApp con:
- Datos del cliente
- Productos pedidos
- Total
- Método de pago

---

## 🔧 ESTRUCTURA DEL PROYECTO

```
naya-ecommerce/
├── src/
│   ├── main/
│   │   ├── java/com/naya/ecommerce/
│   │   │   ├── model/              # Entidades (Producto, Pedido, Cliente)
│   │   │   ├── repository/         # Acceso a base de datos
│   │   │   ├── service/            # Lógica de negocio
│   │   │   ├── controller/         # Controladores web
│   │   │   └── NayaEcommerceApplication.java
│   │   └── resources/
│   │       ├── templates/          # Vistas HTML (Thymeleaf)
│   │       ├── static/             # CSS, JS, imágenes
│   │       └── application.properties
├── pom.xml                         # Dependencias Maven
└── README.md
```

---

## 🎨 PERSONALIZACIÓN

### Cambiar precios:
Edita directamente en la base de datos o crea productos desde código

### Agregar más productos:
Usa los INSERT SQL o crea un panel de administración

### Cambiar colores:
Edita las variables CSS en los archivos HTML:
```css
--primary-black: #000000;
--rose-gold: #C9A687;
--rose-gold-light: #D4B59E;
--cream: #F5F1ED;
```

---

## ❓ SOLUCIÓN DE PROBLEMAS

### Error: "Cannot connect to database"
- Verifica que MySQL esté corriendo
- Revisa usuario y contraseña en application.properties
- Verifica que la base de datos exista

### Error: "Port 8080 already in use"
- Otra aplicación está usando el puerto 8080
- Cambia el puerto en application.properties: `server.port=8081`

### No aparecen productos:
- Ejecuta los INSERT SQL para cargar productos
- Verifica que la tabla productos tenga datos: `SELECT * FROM productos;`

---

## 📞 SOPORTE

Para cualquier duda o problema, contacta al desarrollador o revisa la documentación de Spring Boot:
https://spring.io/guides

---

## 🚀 PRÓXIMOS PASOS (MEJORAS FUTURAS)

- Panel de administración para gestionar productos y pedidos
- Sistema de autenticación para clientes
- Integración real con pasarela de pagos
- Envío de emails de confirmación
- Sistema de tracking de pedidos
- Generación de reportes de ventas

---

¡Tu e-commerce de Naya está listo para funcionar! 🌿✨
