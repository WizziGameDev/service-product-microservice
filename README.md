
# 🚀 Service Product Microservice

## ⚠️ Prerequisites
Before running the application, make sure you have created the Docker network used by the services in the `docker-compose.yml`.  
If not, run the following command:

```bash
sudo docker network create app-network-microservice
```

## ▶️ Running the Application
Run the following command to start all services automatically:

```bash
docker-compose up -d
```

The following services will run automatically:
- 🐬 MySQL (Database)
- 🖥️ PhpMyAdmin (Database web admin)
- ⚡ Redis (Cache and storage)
- 📦 Product Service Microservice (Product API)

## 📖 How to Access Swagger UI (API Documentation)
Once the application is running, you can access the REST API documentation using Swagger UI by opening the URL:

```
http://localhost:9003/swagger-ui/index.html
```

On this page, you can see the list of API endpoints, test requests directly from the browser, and explore request and response models.

---

## 🛠️ Troubleshooting
If the application fails to run or cannot connect to the database or other services, try the following:

- ✅ Make sure Docker is running and the network `app-network-microservice` has been created  
- 🔍 Check container logs using the command `docker-compose logs`  
- 🔄 Restart containers using `docker-compose restart`  
- 🚪 Ensure the port used (e.g., 9003 for the product service) is not conflicting with other applications on your computer  
- 🔄 If the product service fails because MySQL is not ready, run `docker-compose up -d` again after MySQL status is healthy

---

## 📞 Contact
If you have any issues or questions, please contact the development team at:

- 📧 Email: rickyadamsaputra11@gmail.com
