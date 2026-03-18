// modals.js
export function openModal(type, data = null) {
    const modal = document.getElementById("modal");
    const modalBody = document.getElementById("modal-body");
    if (!modal || !modalBody) return;

    let content = "";

    if (type === "adminLogin") {
        content = `
            <h2>Admin Login</h2>
            <input type="text" id="adminUsername" placeholder="Username" class="input-field">
            <input type="password" id="adminPassword" placeholder="Password" class="input-field">
            <button onclick="adminLoginHandler()" class="adminBtn">Login</button>
        `;
    } else if (type === "doctorLogin") {
        content = `
            <h2>Doctor Login</h2>
            <input type="email" id="doctorEmail" placeholder="Email" class="input-field">
            <input type="password" id="doctorPassword" placeholder="Password" class="input-field">
            <button onclick="doctorLoginHandler()" class="adminBtn">Login</button>
        `;
    } else if (type === "patientLogin") {
        content = `
            <h2>Patient Login</h2>
            <input type="email" id="patientEmail" placeholder="Email" class="input-field">
            <input type="password" id="patientPassword" placeholder="Password" class="input-field">
            <button onclick="patientLoginHandler()" class="adminBtn">Login</button>
        `;
    } else if (type === "patientSignup") {
        content = `
            <h2>Patient Sign Up</h2>
            <input type="text" id="name" placeholder="Full Name" class="input-field">
            <input type="email" id="email" placeholder="Email" class="input-field">
            <input type="password" id="password" placeholder="Password" class="input-field">
            <input type="text" id="phone" placeholder="Phone Number" class="input-field">
            <input type="text" id="address" placeholder="Address" class="input-field">
            <button onclick="signupPatient()" class="adminBtn">Sign Up</button>
        `;
    } else if (type === "addDoctor") {
        content = `
            <h2>Add Doctor</h2>
            <input type="text" id="doctorName" placeholder="Name" class="input-field">
            <input type="text" id="doctorSpecialty" placeholder="Specialty" class="input-field">
            <input type="email" id="doctorEmail" placeholder="Email" class="input-field">
            <input type="password" id="doctorPassword" placeholder="Password" class="input-field">
            <input type="text" id="doctorPhone" placeholder="Phone" class="input-field">
            <div class="available-times">
                <label><input type="checkbox" name="availableTimes" value="09:00"> 09:00</label>
                <label><input type="checkbox" name="availableTimes" value="10:00"> 10:00</label>
                <label><input type="checkbox" name="availableTimes" value="11:00"> 11:00</label>
                <label><input type="checkbox" name="availableTimes" value="14:00"> 14:00</label>
                <label><input type="checkbox" name="availableTimes" value="15:00"> 15:00</label>
            </div>
            <button onclick="adminAddDoctor()" class="adminBtn">Add Doctor</button>
        `;
    }

    modalBody.innerHTML = content;
    modal.style.display = "block";
}

export function closeModal() {
    const modal = document.getElementById("modal");
    if (modal) {
        modal.style.display = "none";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modal");
    const closeBtn = document.querySelector(".close");
    if (closeBtn) {
        closeBtn.onclick = closeModal;
    }
    window.onclick = (event) => {
        if (event.target == modal) {
            closeModal();
        }
    };
});
