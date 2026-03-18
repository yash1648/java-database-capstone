function renderHeader() {
    const headerDiv = document.getElementById("header");
    if (!headerDiv) return;

    const role = localStorage.getItem("userRole");
    const token = localStorage.getItem("token");

    let headerContent = `
        <header class="header">
            <div class="logo-section" onclick="window.location.href='/'" style="cursor: pointer;">
                <img src="/assets/images/logo/logo.png" alt="Hospital CRM Logo" class="logo-img">
                <span class="logo-title">Hospital CMS</span>
            </div>
            <nav class="nav-section">
    `;

    if (window.location.pathname === "/" || window.location.pathname.endsWith("/index.html")) {
        localStorage.removeItem("userRole");
        headerContent += `</nav></header>`;
        headerDiv.innerHTML = headerContent;
        return;
    }

    if (role === "admin") {
        headerContent += `
            <button id="addDoctorButton" class="adminBtn">Add Doctor</button>
            <button class="adminBtn" onclick="logout()">Logout</button>
        `;
    } else if (role === "doctor") {
        headerContent += `
            <button class="adminBtn" onclick="window.location.href='/doctorDashboard/${token}'">Home</button>
            <button class="adminBtn" onclick="logout()">Logout</button>
        `;
    } else if (role === "patient") {
        headerContent += `
            <button id="patientLogin" class="adminBtn">Login</button>
            <button id="patientSignup" class="adminBtn">Sign Up</button>
        `;
    } else if (role === "loggedPatient") {
        headerContent += `
            <button class="adminBtn" onclick="window.location.href='/pages/loggedPatientDashboard.html'">Home</button>
            <button class="adminBtn" onclick="window.location.href='/pages/patientAppointments.html'">Appointments</button>
            <button class="adminBtn" onclick="logout()">Logout</button>
        `;
    }

    headerContent += `
            </nav>
        </header>
    `;
    headerDiv.innerHTML = headerContent;
}

window.logout = () => {
    localStorage.clear();
    window.location.href = "/";
};

document.addEventListener("DOMContentLoaded", renderHeader);
   
