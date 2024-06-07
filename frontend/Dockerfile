# Użyj oficjalnego obrazu Node.js jako podstawy
FROM node:latest as builder

# Ustaw katalog roboczy w kontenerze
WORKDIR /app

# Skopiuj pliki aplikacji do kontenera
COPY . .

# Zainstaluj zależności aplikacji
RUN npm install

# Zbuduj aplikację Angular
RUN npm run build

# Użyj oficjalnego obrazu NGINX jako podstawy dla obrazu docelowego
FROM nginx:latest

# Skopiuj pliki zbudowanej aplikacji do katalogu domyślnego NGINX-a
COPY --from=builder /app/dist/* /usr/share/nginx/html/

# zmiana konfiguracji nginx
COPY ./default.conf /etc/nginx/conf.d/default.conf

# Egzekwuj nasłuchiwanie na porcie 80
EXPOSE 80
