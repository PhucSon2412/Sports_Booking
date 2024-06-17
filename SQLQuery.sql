Create database Sports_Booking


-- Tạo table members --
Create table members(
            id UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
			email VARCHAR(255) NOT NULL,
			member_since DATETIME2(6) NOT NULL,
            password VARCHAR(255) NOT NULL,                   
            payment_due FLOAT NOT NULL,
			penalty_exemption INT NOT NULL,
			rank VARCHAR(255),
			role VARCHAR(255) NOT NULL,
			total_paid FLOAT NOT NULL,
			username NVARCHAR(255) NOT NULL
)

-- Chèn dữ liệu mẫu vào bảng members --
INSERT INTO members (email, member_since, password, payment_due, penalty_exemption, rank, role, total_paid, username)
VALUES 
('john.doe@example.com', GETDATE(), 'password123', 0, 0, 'Gold', 'USER', 1000, 'john_doe'),
('jane.smith@example.com', GETDATE(), 'securePass456', 0, 0, 'Silver', 'USER', 500, 'jane_smith'),
('alice.jones@example.com', GETDATE(), 'alicePass789', 0, 0, 'Bronze', 'USER', 300, 'alice_jones'),
('bob.brown@example.com', GETDATE(), 'bobSecure000', 0, 0, 'Gold', 'USER', 1500, 'bob_brown'),
('charlie.white@example.com', GETDATE(), 'charliePass111', 0, 0, NULL, 'USER', 0, 'charlie_white'),
('admin@example.com', GETDATE(), 'adminPass123', 0, 0, NULL, 'ADMIN', 0, 'admin_user');


-- Tạo bảng rooms --
CREATE TABLE rooms (
			id VARCHAR(255) PRIMARY KEY NOT NULL,
			price FLOAT NOT NULL,
			room_type VARCHAR(255) NOT NULL		
)
-- Chèn dữ liệu mẫu vào bảng rooms --
INSERT INTO rooms (id, price, room_type)
VALUES
('R001', 100, 'Standard'),
('R002', 150, 'Deluxe'),
('R003', 200, 'Suite'),
('R004', 80, 'Economy'),
('R005', 120, 'Standard'),
('R006', 180, 'Deluxe'),
('R007', 220, 'Suite'),
('R008', 90, 'Economy'),
('R009', 140, 'Standard');

-- Tạo bảng bookings --
CREATE TABLE bookings (
                     id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
					 booked_date DATE NOT NULL,
					 booked_time TIME(7) NOT NULL,
					 datetime_of_booking DATE NOT NULL,
					 expired_time TIME(7) NOT NULL,
					 payment_due FLOAT NOT NULL,
					 payment_status VARCHAR(255) NOT NULL,
					 member_id UNIQUEIDENTIFIER NOT NULL FOREIGN KEY REFERENCES members(id),
                     room_id VARCHAR(255) NOT NULL FOREIGN KEY REFERENCES rooms(id)
)
-- Chèn dữ liệu mẫu vào bảng bookings --
INSERT INTO bookings (booked_date, booked_time, datetime_of_booking, expired_time, payment_due, payment_status, member_id, room_id)
VALUES
('2024-06-01', '14:00:00', '2024-05-20', '16:00:00', 100, 'Paid', (SELECT id FROM members WHERE email = 'john.doe@example.com'), 'R001'),
('2024-06-02', '09:00:00', '2024-05-22', '11:00:00', 150, 'Unpaid', (SELECT id FROM members WHERE email = 'jane.smith@example.com'), 'R002'),
('2024-06-03', '18:00:00', '2024-05-23', '20:00:00', 200, 'Paid', (SELECT id FROM members WHERE email = 'alice.jones@example.com'), 'R003'),
('2024-06-04', '08:00:00', '2024-05-24', '10:00:00', 80, 'Paid', (SELECT id FROM members WHERE email = 'bob.brown@example.com'), 'R004'),
('2024-06-05', '13:00:00', '2024-05-25', '15:00:00', 120, 'Unpaid', (SELECT id FROM members WHERE email = 'charlie.white@example.com'), 'R005'),
('2024-06-06', '17:00:00', '2024-05-26', '19:00:00', 180, 'Paid', (SELECT id FROM members WHERE email = 'john.doe@example.com'), 'R006'),
('2024-06-07', '12:00:00', '2024-05-27', '14:00:00', 220, 'Unpaid', (SELECT id FROM members WHERE email = 'jane.smith@example.com'), 'R007'),
('2024-06-08', '10:00:00', '2024-05-28', '12:00:00', 90, 'Paid', (SELECT id FROM members WHERE email = 'alice.jones@example.com'), 'R008'),
('2024-06-09', '15:00:00', '2024-05-29', '17:00:00', 140, 'Paid', (SELECT id FROM members WHERE email = 'bob.brown@example.com'), 'R009');



--CHECKED Tạo lệnh select member từ bảng members (đầu vào : id của member)(đầu ra : tất cả thông tin của member)
SELECT * FROM members WHERE id = @Id;

--CHECKED Tạo lệnh select tìm các booking của khách (đầu vào : id của member)
--(đầu ra :roomtype, bookedDate, bookedTime, expiredTime, paymentStatus của các booking trong bảng bookings có memberId bằng id của member đầu vào)
SELECT room_type, booked_date, booked_time, expired_time, payment_status
FROM bookings
JOIN rooms ON bookings.room_id = rooms.id
WHERE bookings.member_id = '818cbbf7-584a-48ae-8d2f-c1ad86c695c1';

--CHECKED Tạo lệnh select tìm các booking chưa trả của khách (đầu vào : id của member)
--(đầu ra :roomType, bookedDate, bookedTime, expiredTime, paymentDue của các booking trong bảng bookings có memberId bằng id của member đầu vào và trạng thái thanh toán là "Unpaid")
SELECT room_type, booked_date, booked_time, expired_time, payment_due
FROM bookings
JOIN rooms ON bookings.room_id = rooms.id
WHERE bookings.member_id = '045b7c2a-99fe-4a2e-a71f-e084e6abb5cf' AND payment_status = 'Unpaid';

--CHECKED Tạo procedure cập nhật username của khách (đầu vào : username, id của khách)
CREATE PROCEDURE UpdateMemberUsername
    @Id UNIQUEIDENTIFIER,
    @Username NVARCHAR(255)
AS
BEGIN
    UPDATE members SET username = @Username WHERE id = @Id;
END;

--CHECKED Tạo procedure cập nhật email của khách (đầu vào : email, id của khách)
CREATE PROCEDURE UpdateMemberEmail
    @Id UNIQUEIDENTIFIER,
    @Email VARCHAR(255)
AS
BEGIN
    UPDATE members SET email = @Email WHERE id = @Id;
END;

-- Tạo procedure cập nhật password của khách (đầu vào : password, id của khách)
CREATE PROCEDURE UpdateMemberPassword
    @Id UNIQUEIDENTIFIER,
    @Password VARCHAR(255)
AS
BEGIN
    UPDATE members SET password = @Password WHERE id = @Id;
END;

-- Tạo procedure tìm phòng cho khách (đầu vào : loại phòng, ngày thuê, giờ thuê, giờ trả)
--(Tìm roomId có loại phòng giống đầu vào, tìm trong bookings xem vào ngày thuê phòng đó còn trống từ giờ thuê đến giờ trả không, nếu không thì phải tìm phòng khác cùng loại)

--CHECKED Tạo procedure hủy booking của khách (đầu vào : bookingId)
-- (Chỉnh payment_status của booking thành "Cancelled)
CREATE PROCEDURE CancelBooking
    @BookingId INT
AS
BEGIN
    UPDATE bookings SET payment_status = 'Cancelled' WHERE id = @BookingId;
END;

--CHECKED Member thực hiện hủy booking --
EXEC CancelBooking 
	@BookingId = '1';

--CHECKED Tạo lệnh select tất cả member trong bảng members
SELECT * FROM members;

--CHECKED Tạo lệnh xóa khách (đầu vào : id)(đồng thời xóa hết booking của khách này)
CREATE PROCEDURE DeleteMember
    @Id UNIQUEIDENTIFIER
AS
BEGIN
    DELETE FROM bookings WHERE member_id = @Id;
    DELETE FROM members WHERE id = @Id;
END;

--CHECKED Xóa khách
EXEC DeleteMember 
	@Id = '2c8d68f1-6ed9-4a72-baaf-6fb5f8886488';

--CHECKED Tạo lệnh select hiện tất cả trong rooms
SELECT * FROM rooms;

--CHECKED Tạo lệnh select tìm phòng (đầu vào : id)
SELECT * FROM rooms WHERE id = '';

--CHECKED Tạo lệnh select tìm phòng (đầu vào : roomType)
SELECT * FROM rooms WHERE room_type = '';


--CHECKED Tạo lệnh select hiện tất cả trong bookings
SELECT * FROM bookings;

-- Tạo lệnh select bookings (đầu vào : roomid, memberid, bookeddate, daytimeofbooking, paymentstatus)
--(đầu ra : tìm phòng với đầu vào là 5 hoặc chỉ 4 trong 5 hoặc chỉ 3 trong 5 hoặc chỉ 2 trong 5 hoặc chỉ 1 trong 5 đầu vào trên)

--CHECKED Tạo procedure cập nhật trạng thái thanh toán của booking (đầu vào : id, payment_status)
-- (Chỉnh trạng thái thánh toán payment_status của booking thành payment_status ở đầu vào)
CREATE PROCEDURE UpdateBookingPaymentStatus
    @Id INT,
    @PaymentStatus VARCHAR(255)
AS
BEGIN
    UPDATE bookings SET payment_status = @PaymentStatus WHERE id = @Id;
END;

--CHECKED Cập nhật trạng thái thanh toán
EXEC UpdateBookingPaymentStatus 
	@Id = '5',
	@PaymentStatus = 'Paid';




-- Tạo trigger tự động kiểm tra penalty_exemption của member khi bảng chuyển từ trạng thái unpaid sang cancelled









-- Tạo procedure register (đầu vào : username, email, password)(trước khi tạo cần kiểm tra xem email này đã có trong members chưa)
-- (đầu ra : member mới với id tự sinh, member_since lấy ngày hiện tại, payment_due = 0, role = "USER", rank = null, totalpaid = 0)
CREATE PROCEDURE register
    @username NVARCHAR(255),
    @password VARCHAR(255),
    @Email VARCHAR(255)
AS
BEGIN
    -- Kiểm tra xem email đã tồn tại hay chưa
    IF EXISTS (SELECT 1 FROM members WHERE email = @Email)
    BEGIN
        -- Nếu email đã tồn tại, trả về thông báo lỗi
        THROW 50000, 'Email đã tồn tại.', 1;
    END
    ELSE
    BEGIN
        -- Nếu email chưa tồn tại, thêm thành viên mới
        DECLARE @id UNIQUEIDENTIFIER = NEWID();
        DECLARE @member_since DATETIME2(6) = GETDATE();
        DECLARE @payment_due FLOAT = 0;
        DECLARE @penalty_exemption INT = 0;
        DECLARE @role VARCHAR(255) = 'USER';
        DECLARE @rank VARCHAR(255) = NULL;
        DECLARE @total_paid FLOAT = 0;

        INSERT INTO members (id, username, email, password, member_since, payment_due, penalty_exemption, role, rank, total_paid)
        VALUES (@id, @username, @Email, @password, @member_since, @payment_due, @penalty_exemption, @role, @rank, @total_paid);
        
        -- Trả về thông tin của thành viên mới
        SELECT * FROM members WHERE id = @id;
    END
END;



EXEC register 
    @username = 'PhucSon',
    @password = 'son123',
	@email = 'son@gmail.com';

--CHECKED Tạo lệnh select member từ bảng members (đầu vào : email của member)(đầu ra : tất cả thông tin của member) (sửa thành function)
SELECT * FROM members WHERE email = @Email;


-- Procedure create booking

-- Function cập nhật paymentDue của member


