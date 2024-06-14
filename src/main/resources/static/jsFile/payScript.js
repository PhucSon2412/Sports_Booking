document.addEventListener('DOMContentLoaded', function() {
    // Giả lập dữ liệu từ cơ sở dữ liệu
    const invoiceData = {
        customerName: "Nguyen Van A",
        items: [
            { description: "Dịch vụ A", amount: 500000 },
            { description: "Dịch vụ B", amount: 300000 },
            { description: "Dịch vụ C", amount: 200000 }
        ]
    };

    // Hàm để định dạng tiền tệ
    function formatCurrency(amount) {
        return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    }

    // Lấy phần tử HTML để hiển thị hóa đơn
    const invoiceElement = document.getElementById('invoice');

    // Tạo nội dung cho hóa đơn
    let invoiceContent = `
        <div class="invoice-item">
            <span class="label">Họ và tên khách hàng:</span>
            <span>${invoiceData.customerName}</span>
        </div>
        <div class="invoice-item">
            <span class="label">Dịch vụ đã sử dụng:</span>
        </div>
    `;

    // Thêm các dịch vụ đã sử dụng vào nội dung hóa đơn
    invoiceData.items.forEach(item => {
        invoiceContent += `
            <div class="invoice-item">
                <span>${item.description}</span>
                <span>${formatCurrency(item.amount)}</span>
            </div>
        `;
    });

    // Tính tổng số tiền
    const totalAmount = invoiceData.items.reduce((total, item) => total + item.amount, 0);

    // Thêm tổng số tiền vào nội dung hóa đơn
    invoiceContent += `
        <div class="invoice-item total">
            <span class="label">Tổng số tiền:</span>
            <span>${formatCurrency(totalAmount)}</span>
        </div>
    `;

    // Hiển thị nội dung hóa đơn
    invoiceElement.innerHTML = invoiceContent;
});
