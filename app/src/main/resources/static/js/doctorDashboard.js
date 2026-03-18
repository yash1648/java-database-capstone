import { getAllAppointments } from "./appointmentService.js";
import { createPatientRow } from "./patientRow.js";

const tableBody = document.getElementById("patientTableBody");
const searchBar = document.getElementById("searchBar");
const todayButton = document.getElementById("todayButton");
const datePicker = document.getElementById("datePicker");

let selectedDate = new Date().toISOString().split("T")[0];
let patientName = null;

const token = localStorage.getItem("token");


/* Search patient by name */
if (searchBar) {

    searchBar.addEventListener("input", () => {

        const value = searchBar.value.trim();

        patientName = value ? value : null;

        loadAppointments();
    });
}


/* Show today's appointments */
if (todayButton) {

    todayButton.addEventListener("click", () => {

        selectedDate = new Date().toISOString().split("T")[0];

        datePicker.value = selectedDate;

        loadAppointments();
    });
}


/* Filter by date */
if (datePicker) {

    datePicker.addEventListener("change", () => {

        selectedDate = datePicker.value;

        loadAppointments();
    });
}


/* Fetch and render appointments */
async function loadAppointments() {

    try {

        const appointments = await getAllAppointments(
            selectedDate,
            patientName,
            token
        );

        tableBody.innerHTML = "";

        if (!appointments || appointments.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="5">No Appointments found for today.</td>
                </tr>
            `;

            return;
        }

        appointments.forEach(app => {

            const patient = {
                id: app.patient.id,
                name: app.patient.name,
                phone: app.patient.phone,
                email: app.patient.email
            };

            const row = createPatientRow(patient, app);

            tableBody.appendChild(row);
        });

    } catch (error) {

        console.error(error);

        tableBody.innerHTML = `
            <tr>
                <td colspan="5">Error loading appointments. Try again later.</td>
            </tr>
        `;
    }
}


/* Page initialization */
document.addEventListener("DOMContentLoaded", () => {

    if (typeof renderContent === "function") {
        renderContent();
    }

    datePicker.value = selectedDate;

    loadAppointments();
});