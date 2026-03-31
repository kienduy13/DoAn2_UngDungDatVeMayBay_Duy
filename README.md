```✈️ Giới thiệu Ứng Dụng Di Động Đặt Vé Máy Bay & Tư Vấn Lịch Trình Du Lịch Tự Động bằng AI

Ứng dụng đặt vé máy bay trên nền tảng di động được xây dựng ở mức độ cơ bản, dễ tiếp cận, giúp sinh viên có thể đọc hiểu code nhanh chóng, triển khai và sử dụng để làm báo cáo bài tập lớn, đồ án hoặc thuyết trình nhóm. Mã nguồn được viết rõ ràng, dễ tùy chỉnh và phù hợp để mở rộng thêm các tính năng nâng cao trong tương lai.

Ứng dụng tập trung vào việc hỗ trợ người dùng tìm kiếm chuyến bay, đặt vé và gợi ý lịch trình du lịch tự động bằng trí tuệ nhân tạo, mang lại trải nghiệm tiện lợi và hiện đại.

🧰 Công nghệ sử dụng
📱 Mobile: Android (Java)
🌐 Backend API: ASP.NET Web API (RESTful)
🗄️ Cơ sở dữ liệu: SQL Server
🤖 AI: Gemini AI (gợi ý lịch trình du lịch tự động)
🎓 Đề tài đồ án – Ứng dụng AI trong đặt vé máy bay

Đây là hệ thống hỗ trợ đặt vé máy bay và gợi ý lịch trình du lịch thông minh trên thiết bị di động. Ứng dụng kết hợp giữa công nghệ AI và cơ sở dữ liệu SQL Server nhằm nâng cao trải nghiệm người dùng thông qua việc cá nhân hóa chuyến đi dựa trên sở thích, thời gian lưu trú và điều kiện thực tế.

📌 Tính năng nổi bật
🔒 Phân quyền hệ thống

Ứng dụng hiện tại được phát triển với một phân quyền chính là khách hàng.
Các chức năng quản trị như quản lý chuyến bay, nhập dữ liệu vé chưa được triển khai giao diện Admin, dữ liệu được cập nhật trực tiếp từ cơ sở dữ liệu SQL Server.

🔐 Xác thực và quản lý tài khoản
Đăng ký tài khoản người dùng
Đăng nhập hệ thống
Quên mật khẩu bằng OTP gửi qua Email
Cập nhật thông tin cá nhân
Xem hồ sơ người dùng
✈️ Đặt vé máy bay
Tìm kiếm chuyến bay theo điểm đi và điểm đến
Chọn chuyến bay và hạng ghế mong muốn
Thanh toán nội bộ (giả lập)
Gửi email xác nhận đặt vé thành công
Hiển thị thông báo nhanh khi đặt vé thành công
📅 Quản lý lịch sử đặt vé
Xem danh sách vé đã đặt
Hiển thị chi tiết thông tin chuyến bay
Lưu trữ dữ liệu đặt vé trên SQL Server
Dữ liệu chuyến bay được nhập trực tiếp từ cơ sở dữ liệu
🤖 Tính năng AI (Gemini AI)
Gợi ý lịch trình du lịch tự động
Đề xuất địa điểm tham quan theo sở thích người dùng
Phân tích thời gian lưu trú để xây dựng lịch trình hợp lý
Hỗ trợ lên kế hoạch chuyến đi sau khi đặt vé máy bay
🎯 Mục tiêu ứng dụng

Ứng dụng hướng đến việc xây dựng một hệ thống đặt vé máy bay trên thiết bị di động đơn giản, dễ sử dụng và tích hợp AI nhằm:

Tăng trải nghiệm người dùng ✨
Hỗ trợ lên lịch trình tự động 🤖
Dễ dàng mở rộng tính năng trong tương lai 🚀
Phù hợp làm đồ án hoặc bài tập lớn cho sinh viên 🎓

CẤU TRÚC THƯ MỤC 
FlightBookingApp/
│
├── backend/                     # ASP.NET Web API
│   ├── Controllers/             # API Controllers
│   ├── Models/                  # Entity Models
│   ├── DTOs/                    # Data Transfer Objects
│   ├── Services/                # Business Logic
│   ├── Repositories/            # Data Access Layer
│   ├── Data/                    # DbContext & cấu hình SQL Server
│   ├── Helpers/                 # JWT, Email, OTP...
│   ├── appsettings.json         # Config hệ thống
│   └── Program.cs               # Entry point
│
├── android/                     # Android App
│   ├── app/src/main/java/com/flightbooking/
│   │   ├── activities/
│   │   │   ├── LoginActivity.java
│   │   │   ├── RegisterActivity.java
│   │   │   ├── HomeActivity.java
│   │   │   ├── FlightListActivity.java
│   │   │   ├── BookingActivity.java
│   │   │   └── AIPlannerActivity.java
│   │   │
│   │   ├── adapters/
│   │   ├── models/
│   │   ├── api/
│   │   ├── utils/
│   │   └── services/
│   │
│   ├── res/
│   │   ├── layout/
│   │   ├── drawable/
│   │   └── values/
│   │
│   └── AndroidManifest.xml
│
├── database/
│   ├── FlightBooking.sql
│   └── sample_data.sql
│
├── docs/
│   ├── ERD.png
│   ├── UseCase.png
│   └── API_Document.txt
│
└── README.md

LINK DEMO https://drive.google.com/file/d/1KZ2I-OFNNRNi2TGQG0T9JFaAjOh-pQNf/view?usp=drive_link

Mật khẩu mysql management sever 
tên đăng nhap 
sa
mật khẩu
12341234

tên đăng nhap Android 
Quachuy
mật khẩu 
Huy123456


Tác giả / Nhóm Đồ án
Phát triển bởi Huỳnh Kiến Duy (225345) phục vụ cho đồ án 2 

Dạ còn phần backend em có up riêng ra file backend bên trong tài khoản github của em nữa ạ
```
