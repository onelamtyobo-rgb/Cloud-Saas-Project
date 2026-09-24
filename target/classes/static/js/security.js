// Mirrors RiskAssessor.java questions
const secQuestions = [
    { q: "Do you use Multi-Factor Authentication (MFA) on all business accounts?", weakness: "No MFA implemented" },
    { q: "Are your critical files backed up regularly to an offsite/cloud location?", weakness: "No reliable backups" },
    { q: "Do you use a Password Manager to generate and store unique passwords?", weakness: "Weak/reused passwords" },
    { q: "Is your business Wi-Fi network separated from the guest network?", weakness: "Unsecured network" },
    { q: "Do you have basic antivirus/anti-malware software installed on all devices?", weakness: "No endpoint protection" },
    { q: "Have you trained your employees on how to spot phishing emails?", weakness: "No security training" }
];

// Mirrors RiskScore.java enum
function getRiskLevel(score) {
    if (score >= 0 && score <= 30) return { label: "Low Risk", desc: "Your business has a solid foundation.", color: "#10b981" };
    if (score >= 31 && score <= 60) return { label: "Medium Risk", desc: "There are some gaps that need attention.", color: "#eab308" };
    if (score >= 61 && score <= 80) return { label: "High Risk", desc: "Your business is vulnerable to common attacks.", color: "#f97316" };
    return { label: "Critical Risk", desc: "Immediate action is required to protect your business.", color: "#ef4444" };
}

// Inject questions on load
document.addEventListener('DOMContentLoaded', function() {
    const container = document.getElementById('securityQuestions');
    secQuestions.forEach((item, i) => {
        container.innerHTML += `
            <div class="radio-item" style="grid-column: span 2;">
                <label style="margin:0; flex:1; cursor:pointer;">${item.q}</label>
                <input type="radio" name="sec_q${i}" value="yes" checked style="margin-left:10px;"> Yes
                <input type="radio" name="sec_q${i}" value="no" style="margin-left:10px;"> No
            </div>
        `;
    });
});

function generateSecurityReport() {
    const bizName = document.getElementById('bizName').value;
    const industry = document.getElementById('bizIndustry').value;

    let riskScore = 0;
    let weaknesses = [];

    // Calculate Score (Mirrors RiskAssessor.java)
    secQuestions.forEach((item, i) => {
        const ans = document.querySelector(`input[name="sec_q${i}"]:checked`).value;
        if (ans === 'no') {
            riskScore += 20;
            weaknesses.push(item.weakness);
        }
    });
    if (riskScore > 100) riskScore = 100;

    const riskLevel = getRiskLevel(riskScore);

    // Generate Policy (Mirrors PolicyGenerator.java)
    let policyHTML = `<div id="securityReportContent" style="background: white; padding: 20px; border-radius: 8px;">`;
    policyHTML += `<h2 style="text-align:center; border-bottom: 2px solid #333; padding-bottom: 10px;">CYBERSECURITY POLICY & RECOMMENDATIONS REPORT</h2>`;
    policyHTML += `<p><strong>Prepared for:</strong> ${bizName} | <strong>Industry:</strong> ${industry}</p>`;
    policyHTML += `<p><strong>Overall Risk Level:</strong> <span class="risk-badge" style="background:${riskLevel.color}">${riskLevel.label}</span></p>`;
    policyHTML += `<p><strong>Risk Score:</strong> ${riskScore}/100</p>`;

    policyHTML += `<h3>EXECUTIVE SUMMARY</h3><p>${riskLevel.desc}</p>`;

    policyHTML += `<h3>IDENTIFIED WEAKNESSES & ACTIONABLE RECOMMENDATIONS</h3>`;
    if (weaknesses.length === 0) {
        policyHTML += `<div class="recommendation-item" style="border-left-color: #10b981;"><h4>✅ Excellent!</h4><p>No critical weaknesses were identified. Continue maintaining your current security practices.</p></div>`;
    } else {
        weaknesses.forEach(w => {
            policyHTML += generateSpecificRecommendation(w);
        });
    }

    policyHTML += `<h3>PRIORITY ACTION PLAN (First 30 Days)</h3>
        <ul class="action-list">
            <li><strong>Week 1:</strong> Enable MFA on all accounts (email, banking, social media)</li>
            <li><strong>Week 2:</strong> Set up automatic cloud backups (Google Drive/OneDrive)</li>
            <li><strong>Week 3:</strong> Install a password manager (Bitwarden/1Password)</li>
            <li><strong>Week 4:</strong> Update router firmware and change default passwords</li>
        </ul>`;
    policyHTML += `</div><button class="download-btn" onclick="downloadSecurityPDF()">📥 Download Security Report</button>`;

    document.getElementById('securityResult').classList.remove('hidden');
    document.getElementById('securityResult').innerHTML = policyHTML;
    document.getElementById('securityResult').scrollIntoView({ behavior: 'smooth' });
}

// Mirrors the switch statement in PolicyGenerator.java
function generateSpecificRecommendation(weakness) {
    let rec = `<div class="recommendation-item" style="border-left-color: #ef4444;">`;
    switch (weakness) {
        case "No MFA implemented":
            rec += `<h4>[!] Issue: No Multi-Factor Authentication (MFA)</h4>
                <p><strong>RISK:</strong> Hackers can easily access your accounts with just a password.</p>
                <p><strong>✅ SOLUTION:</strong> Enable MFA/2FA on ALL business accounts</p>
                <ul class="action-list"><li>Go to email settings → Security → 2-Step Verification</li><li>Download Google/Microsoft Authenticator app</li><li>Enable MFA on banking and cloud storage</li></ul>
                <p><strong>🛠️ TOOLS:</strong> Google Authenticator (Free) | <strong>💰 COST:</strong> Free</p>`;
            break;
        case "No reliable backups":
            rec += `<h4>[!] Issue: No Reliable Backups</h4>
                <p><strong>RISK:</strong> Ransomware or hardware failure could delete all business data.</p>
                <p><strong>✅ SOLUTION:</strong> Implement the 3-2-1 Backup Rule</p>
                <ul class="action-list"><li>Use Google Drive/OneDrive for automatic syncing</li><li>Buy an external USB hard drive for weekly manual backups</li><li>Enable 'Version History' in cloud storage</li></ul>
                <p><strong>🛠️ TOOLS:</strong> Backblaze ($7/month) | <strong> COST:</strong> Free to $10/month</p>`;
            break;
        case "Weak/reused passwords":
            rec += `<h4>[!] Issue: Weak or Reused Passwords</h4>
                <p><strong>RISK:</strong> If one account is breached, ALL accounts are vulnerable.</p>
                <p><strong>✅ SOLUTION:</strong> Use a Password Manager</p>
                <ul class="action-list"><li>Download Bitwarden (free) or 1Password</li><li>Create a strong master password (12+ characters)</li><li>Use the generator for unique passwords</li></ul>
                <p><strong>🛠️ TOOLS:</strong> Bitwarden (Free) | <strong>💰 COST:</strong> Free to $5/month</p>`;
            break;
        case "Unsecured network":
            rec += `<h4>[!] Issue: Unsecured Wi-Fi Network</h4>
                <p><strong>RISK:</strong> Hackers can intercept data or access devices on the network.</p>
                <p><strong>✅ SOLUTION:</strong> Secure Your Business Router</p>
                <ul class="action-list"><li>Change default router admin password</li><li>Update router firmware to latest version</li><li>Enable WPA3 or WPA2 encryption (NOT WEP)</li><li>Create a separate 'Guest' network</li></ul>
                <p><strong>🛠️ TOOLS:</strong> Router admin panel (192.168.1.1) | <strong>💰 COST:</strong> Free</p>`;
            break;
        case "No endpoint protection":
            rec += `<h4>[!] Issue: No Antivirus/Malware Protection</h4>
                <p><strong>RISK:</strong> Viruses and malware can steal data or lock files.</p>
                <p><strong>✅ SOLUTION:</strong> Install Antivirus Software</p>
                <ul class="action-list"><li>Windows: Enable Windows Defender (built-in)</li><li>Mac: Install Malwarebytes or use XProtect</li><li>Enable automatic weekly scans</li></ul>
                <p><strong>🛠️ TOOLS:</strong> Windows Defender (Free) | <strong>💰 COST:</strong> Free</p>`;
            break;
        case "No security training":
            rec += `<h4>[!] Issue: No Employee Security Awareness</h4>
                <p><strong>RISK:</strong> Employees may click phishing links or fall for scams.</p>
                <p><strong>✅ SOLUTION:</strong> Basic Security Training</p>
                <ul class="action-list"><li>Watch Google's 'Phishing Quiz' together (15 mins)</li><li>Teach the '3 Rules': Don't click suspicious links, verify requests, report strange emails</li><li>Create a 1-page 'Security Cheat Sheet' for the office</li></ul>
                <p><strong>🛠️ RESOURCES:</strong> Google Phishing Quiz (Free) | <strong>💰 COST:</strong> Free</p>`;
            break;
    }
    rec += `</div>`;
    return rec;
}

function downloadSecurityPDF() {
    const element = document.getElementById('securityReportContent');
    const bizName = document.getElementById('bizName').value.replace(/[^a-zA-Z0-9]/g, "_");

    // Try the fancy PDF library first
    if (typeof html2pdf !== 'undefined') {
        try {
            const opt = {
                margin: 10,
                filename: `${bizName}_Security_Report.pdf`,
                html2canvas: { scale: 2 },
                jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
            };
            html2pdf().set(opt).from(element).save();
            return; // Success, stop here
        } catch (e) {
            console.error("PDF Library failed, using print fallback:", e);
        }
    }

    // Fallback: Use browser print dialog
    alert("Opening print dialog. Please select 'Save as PDF' as the destination.");
    window.print();
}