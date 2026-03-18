function renderFooter() {
    const footerDiv = document.getElementById("footer");
    if (!footerDiv) return;

    footerDiv.innerHTML = `
        <footer class="footer">
            <div class="footer-content">
                <p>&copy; 2026 Hospital CMS. All rights reserved.</p>
                <div class="footer-links">
                    <a href="#">Privacy Policy</a>
                    <a href="#">Terms of Service</a>
                    <a href="#">Contact Us</a>
                </div>
            </div>
        </footer>
    `;
}

document.addEventListener("DOMContentLoaded", renderFooter);
