import { getDoctors, filterDoctors, saveDoctor } from "./doctorService.js";
import { createDoctorCard } from "./doctorCard.js";
import { openModal, closeModal } from "./modal.js";

const content = document.getElementById("content");
const searchBar = document.getElementById("searchBar");
const timeFilter = document.getElementById("timeFilter");
const specialtyFilter = document.getElementById("specialtyFilter");
const addDoctorButton = document.getElementById("addDoctorButton");


/* Open Add Doctor Modal */
if (addDoctorButton) {
    addDoctorButton.addEventListener("click", () => {
        openModal("addDoctor");
    });
}


/* Load Doctors when page loads */
document.addEventListener("DOMContentLoaded", () => {
    loadDoctorCards();
});


/* Fetch and display all doctors */
async function loadDoctorCards() {

    try {
        const doctors = await getDoctors();
        renderDoctorCards(doctors);
    } catch (error) {
        console.error("Error loading doctors:", error);
    }
}


/* Attach filters */
if (searchBar) searchBar.addEventListener("input", filterDoctorsOnChange);
if (timeFilter) timeFilter.addEventListener("change", filterDoctorsOnChange);
if (specialtyFilter) specialtyFilter.addEventListener("change", filterDoctorsOnChange);


/* Filter doctors */
async function filterDoctorsOnChange() {

    const name = searchBar.value || null;
    const time = timeFilter.value || null;
    const specialty = specialtyFilter.value || null;

    try {

        const doctors = await filterDoctors(name, time, specialty);

        if (doctors && doctors.length > 0) {
            renderDoctorCards(doctors);
        } else {
            content.innerHTML = "<p>No doctors found with the given filters.</p>";
        }

    } catch (error) {
        alert("Error filtering doctors");
        console.error(error);
    }
}


/* Render doctor cards */
function renderDoctorCards(doctors) {

    content.innerHTML = "";

    doctors.forEach(doctor => {

        const card = createDoctorCard(doctor);

        content.appendChild(card);
    });
}


/* Add new doctor */
async function adminAddDoctor() {

    const name = document.getElementById("doctorName").value;
    const email = document.getElementById("doctorEmail").value;
    const phone = document.getElementById("doctorPhone").value;
    const password = document.getElementById("doctorPassword").value;
    const specialty = document.getElementById("doctorSpecialty").value;

    const availableTimes = Array.from(
        document.querySelectorAll("input[name='availableTimes']:checked")
    ).map(el => el.value);

    const token = localStorage.getItem("token");

    if (!token) {
        alert("Authentication token missing. Please login again.");
        return;
    }

    const doctor = {
        name,
        email,
        phone,
        password,
        specialty,
        availableTimes
    };

    try {

        await saveDoctor(doctor, token);

        alert("Doctor added successfully!");

        closeModal();

        window.location.reload();

    } catch (error) {

        alert("Error adding doctor");

        console.error(error);
    }
}