// Get modal elements
var changeInfoModal = document.getElementById("change-info-modal");
var viewBillsModal = document.getElementById("view-bills-modal");
var logoutModal = document.getElementById("logout-modal");
var viewBookingsModal = document.getElementById("view-bookings-modal");

// Get button elements
var changeInfoBtn = document.getElementById("change-info-btn");
var viewBillsBtn = document.getElementById("view-bills-btn");
var logoutBtn = document.getElementById("logout-btn");
var viewBookingsBtn = document.getElementById("view-bookings-btn");

// Get close elements
var closeChangeInfo = document.getElementById("close-change-info");
var closeViewBills = document.getElementById("close-view-bills");
var closeLogout = document.getElementById("close-logout");
var closeViewBookings = document.getElementById("close-view-bookings");

// Open modals
changeInfoBtn.onclick = function() {
    changeInfoModal.style.display = "block";
}

viewBillsBtn.onclick = function() {
    viewBillsModal.style.display = "block";
}

logoutBtn.onclick = function() {
    logoutModal.style.display = "block";
}

viewBookingsBtn.onclick = function() {
    viewBookingsModal.style.display = "block";
}
// Close modals
closeChangeInfo.onclick = function() {
    changeInfoModal.style.display = "none";
}

closeViewBills.onclick = function() {
    viewBillsModal.style.display = "none";
}

closeLogout.onclick = function() {
    logoutModal.style.display = "none";
}

closeViewBookings.onclick = function() {
    viewBookingsModal.style.display = "none";
}
// Confirm logout
confirmLogoutBtn.onclick = function() {
    alert("Logged out successfully!");
    logoutModal.style.display = "none";
}

// Cancel logout
cancelLogoutBtn.onclick = function() {
    logoutModal.style.display = "none";
}
// Close modals when clicking outside of the modal
window.onclick = function(event) {
    if (event.target == changeInfoModal) {
        changeInfoModal.style.display = "none";
    }
    if (event.target == viewBillsModal) {
        viewBillsModal.style.display = "none";
    }
    if (event.target == logoutModal) {
        logoutModal.style.display = "none";
    }
    if (event.target == viewBookingsModal) {
        viewBookingsModal.style.display = "none";
    }
}
