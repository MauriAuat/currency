#Currency Converter
Esta aplicación de conversión de divisas permite a los usuarios convertir montos entre diferentes monedas utilizando la API de ExchangeRate-API.

#Descripción
La aplicación utiliza la biblioteca HttpClient de Java para enviar solicitudes HTTP a la API de ExchangeRate-API y obtener las tasas de cambio actuales. Los usuarios pueden seleccionar la moneda de origen, la moneda de destino y el monto a convertir. La aplicación calcula y muestra el monto convertido.

#Requisitos
Java 8 o superior
Biblioteca com.fasterxml.jackson.core:jackson-databind para manejar JSON
Instalación

1. Clona este repositorio
   git clone https://github.com/MauriAuat/currency-converter.git
2. Asegúrate de tener la biblioteca jackson-databind en tu classpath.

Uso
1. Compila y ejecuta la aplicación:

2. Selecciona la moneda de origen y la moneda de destino desde los menús desplegables.
![image](https://github.com/user-attachments/assets/7ccc21c2-bf5e-4fc2-a087-dce2f3f7312c)

3. Introduce el monto a convertir.

4. Haz clic en el botón "Convertir" para ver el resultado.
