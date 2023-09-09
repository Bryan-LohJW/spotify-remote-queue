# Step 1: Build the application
FROM node:16 AS builder

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm ci

COPY . .

ENTRYPOINT ["npm", "run", "dev"]

# Step 2: Set up the production environment
# FROM nginx:stable-alpine
# COPY --from=builder /app/build /usr/share/nginx/html
# COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

# EXPOSE 8081
# CMD ["nginx", "-g", "daemon off;"]