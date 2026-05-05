# 1. Imagen ligera de Python
FROM python:3.11-slim

# 2. Directorio de trabajo
WORKDIR /app

# 3. Instalamos dependencias
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 4. Copiamos el código
COPY . .

# 5. Puerto de Flask
EXPOSE 5000

# 6. Ejecución
CMD ["python", "app.py"]