import { openModal } from "../components/modals.js";
import { API_BASE_URL } from "../config/config.js";

const ADMIN_API = `${API_BASE_URL}/admin/login`;
const DOCTOR_API = `${API_BASE_URL}/doctor/login`; // Assuming doctor login exists

window.onload = () => {
    const adminLoginBtn = document.getElementById("adminLogin");
    const doctorLoginBtn = document.getElementById("doctorLogin");

    if (adminLoginBtn) {
        adminLoginBtn.addEventListener("click", () => openModal("adminLogin"));
    }
    if (doctorLoginBtn) {
        doctorLoginBtn.addEventListener("click", () => openModal("doctorLogin"));
    }
};

window.adminLoginHandler = async () => {
    const username = document.getElementById("adminUsername").value;
    const password = document.getElementById("adminPassword").value;

    try {
        const response = await fetch(ADMIN_API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.token);
            selectRole("admin");
        } else {
            alert("Invalid admin credentials");
        }
    } catch (error) {
        console.error("Admin login error:", error);
        alert("An error occurred during admin login");
    }
};

window.doctorLoginHandler = async () => {
    const email = document.getElementById("doctorEmail").value;
    const password = document.getElementById("doctorPassword").value;

    try {
        const response = await fetch(DOCTOR_API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.token);
            selectRole("doctor");
        } else {
            alert("Invalid doctor credentials");
        }
    } catch (error) {
        console.error("Doctor login error:", error);
        alert("An error occurred during doctor login");
    }
};
