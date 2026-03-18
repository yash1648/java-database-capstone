import { API_BASE_URL } from "../config/config.js";

const DOCTOR_API = `${API_BASE_URL}/doctor`;

export async function getDoctors() {
    try {
        const response = await fetch(DOCTOR_API);
        const data = await response.json();
        return data.doctors || [];
    } catch (error) {
        console.error("Error fetching doctors:", error);
        return [];
    }
}

export async function deleteDoctor(id, token) {
    try {
        const response = await fetch(`${DOCTOR_API}/${id}`, {
            method: "DELETE"
        });
        const data = await response.json();
        return { success: response.ok, message: data.message };
    } catch (error) {
        console.error("Error deleting doctor:", error);
        return { success: false, message: "Error deleting doctor" };
    }
}

export async function saveDoctor(doctor, token) {
    try {
        const response = await fetch(`${DOCTOR_API}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(doctor)
        });
        const data = await response.json();
        return { success: response.ok, message: data.message };
    } catch (error) {
        console.error("Error saving doctor:", error);
        return { success: false, message: "Error saving doctor" };
    }
}

export async function filterDoctors(name, time, specialty) {
    try {
        let url = `${DOCTOR_API}?`;
        if (name) url += `name=${name}&`;
        if (time) url += `time=${time}&`;
        if (specialty) url += `specialty=${specialty}&`;
        
        const response = await fetch(url);
        if (response.ok) {
            const data = await response.json();
            return data.doctors || [];
        } else {
            console.error("Error filtering doctors");
            return [];
        }
    } catch (error) {
        alert("Error filtering doctors");
        console.error(error);
        return [];
    }
}
