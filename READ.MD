#  Restful Booker - API Testing Portfolio

Portfolio pengujian API menggunakan Postman pada aplikasi [Restful Booker](https://restful-booker.herokuapp.com).

##  Tentang Project

Project ini merupakan implementasi API testing secara menyeluruh pada Restful Booker,
sebuah aplikasi booking hotel yang menyediakan REST API publik untuk keperluan latihan testing.

##  Tools & Tech

- **Postman** - API testing & automation
- **JavaScript** - Test scripts & assertions
- **Newman** *(coming soon)* - CLI runner untuk CI/CD

##  Struktur Project

restful-booker-portfolio/
├── postman/
│   ├── RestfulBooker.collection.json
│   └── environments/
│       └── RestfulBooker.environment.json
└── README.md

##  Endpoint yang Diuji

| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| POST | `/auth` | Generate token autentikasi |
| GET | `/booking` | Ambil semua data booking |
| GET | `/booking/:id` | Ambil booking berdasarkan ID |
| POST | `/booking` | Buat booking baru |
| PUT | `/booking/:id` | Update data booking |
| DELETE | `/booking/:id` | Hapus booking |

##  Test Coverage

- Status code validation
- Response body structure
- Data type assertion
- Authentication flow
- Environment variables & chaining antar request
- Post-delete verification (404 check)

##  Cara Menjalankan

1. Install [Postman](https://www.postman.com/downloads/)
2. Import file collection: `postman/RestfulBooker.collection.json`
3. Import environment: `postman/environments/RestfulBooker.environment.json`
4. Pilih environment **RestfulBooker**
5. Jalankan collection secara berurutan atau gunakan **Collection Runner**

##  Catatan

- Jalankan request sesuai urutan: `CreateToken` > `GetAllBookings` > `GetBookingById` > `CreateBooking` > `UpdateBooking` > `DeleteBooking`
- Token dan booking ID tersimpan otomatis via environment variables
- Server Restful Booker bersifat publik dan di-reset berkala