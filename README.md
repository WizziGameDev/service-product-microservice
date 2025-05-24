
# 🚀 Service Product Microservice

## ⚠️ Prasyarat
Sebelum menjalankan aplikasi, pastikan Anda sudah membuat network Docker yang digunakan oleh service-service di dalam `docker-compose.yml`.  
Jika belum, jalankan perintah berikut:

```bash
sudo docker network create app-network-microservice
```

## ▶️ Menjalankan Aplikasi
Jalankan perintah berikut untuk menjalankan semua service secara otomatis:

```bash
docker-compose up -d
```

Service yang akan berjalan otomatis:
- 🐬 MySQL (Database)
- 🖥️ PhpMyAdmin (Admin database via web)
- ⚡ Redis (Cache dan storage)
- 📦 Service Product Microservice (API produk)

## 📖 Cara Mengakses Swagger UI (API Documentation)
Setelah aplikasi berjalan, Anda dapat mengakses dokumentasi REST API menggunakan Swagger UI dengan membuka URL:

```
http://localhost:9003/swagger-ui/index.html
```

Di halaman ini Anda dapat melihat daftar endpoint API, melakukan testing request langsung dari browser, serta mempelajari model request dan response.

---

## 🛠️ Troubleshooting
Jika aplikasi gagal dijalankan atau tidak bisa terhubung ke database/service lainnya, coba hal berikut:

- ✅ Pastikan Docker sudah berjalan dan network `app-network-microservice` sudah dibuat  
- 🔍 Periksa log container dengan perintah `docker-compose logs`  
- 🔄 Restart container dengan `docker-compose restart`  
- 🚪 Pastikan port yang digunakan (misal 9003 untuk service produk) tidak bentrok dengan aplikasi lain di komputer Anda
- Jika product-service gagal karena sql belum siap lakukan perintah `docker-compose up -d` setelah mysql status healthy

---

## 📞 Kontak
Jika ada masalah atau pertanyaan, silakan hubungi tim pengembang di:

- 📧 Email: rickyadamsaputra11@gmail.com
