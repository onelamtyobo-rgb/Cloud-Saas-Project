let companyId = null, assessmentId = null;

async function createCompany() {
    const data = {
        name: document.getElementById('companyName').value,
        industry: document.getElementById('industry').value,
        employeeCount: parseInt(document.getElementById('employeeCount').value),
        currentInfrastructure: document.getElementById('serverSetup').value
    };
    try {
        const res = await fetch('/api/companies', { method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data) });
        const result = await res.json();
        companyId = result.id;
        document.getElementById('companyResult').classList.remove('hidden');
        document.getElementById('companyResult').innerHTML = `<strong>✅ Company Created! ID: ${companyId}</strong>`;
        document.getElementById('assessmentSection').classList.remove('hidden');
    } catch (e) { alert('Error: ' + e.message); }
}

async function createAssessment() {
    if (!companyId) return alert('Create company first!');
    const type = document.getElementById('assessmentType').value;
    try {
        const res = await fetch(`/api/assessments/company/${companyId}?type=${type}`, { method: 'POST' });
        const result = await res.json();
        assessmentId = result.id;
        document.getElementById('assessmentResult').classList.remove('hidden');
        document.getElementById('assessmentResult').innerHTML = `<strong>✅ Assessment Created! ID: ${assessmentId}</strong>`;
        document.getElementById('completeSection').classList.remove('hidden');
    } catch (e) { alert('Error: ' + e.message); }
}

async function completeAssessment() {
    const budget = document.getElementById('budget').value;
    const employeeCount = parseInt(document.getElementById('employeeCount').value);
    const isSmallBiz = employeeCount <= 10;

    // Get checked resources
    const resources = [];
    document.querySelectorAll('.checkbox-group input:checked').forEach(cb => resources.push(cb.value));

    let recs = [];

    // BUDGET MATCHED RECOMMENDATIONS
    if (budget === '<50k') {
        recs.push(`<div class="recommendation-item"><h4>🚀 Low-Budget Cloud Strategy (Under R50k)</h4>
            <ul class="action-list">
                <li>Use AWS Free Tier / Azure Free Account for first 12 months</li>
                <li>Use Google Workspace Starter (R60/user/month) for email/docs</li>
                <li>Use Bitwarden (Free) for password management</li>
                <li>DIY Migration: Use AWS Migration Evaluator (Free tool)</li>
            </ul>
            <strong>Est. Cost:</strong> R0 - R2,000/month</div>`);
    } else if (budget === '50k-150k') {
        recs.push(`<div class="recommendation-item"><h4>📈 Mid-Budget Cloud Strategy (R50k - R150k)</h4>
            <ul class="action-list">
                <li>Subscribe to Microsoft 365 Business Basic (R100/user/month)</li>
                <li>Use AWS Lightsail or Azure App Service for predictable pricing</li>
                <li>Hire a freelance cloud architect for initial setup (R15k once-off)</li>
                <li>Implement automated daily backups (R500/month)</li>
            </ul>
            <strong>Est. Cost:</strong> R5,000 - R12,000/month</div>`);
    } else if (budget === '150k-500k') {
        recs.push(`<div class="recommendation-item"><h4>🏢 SME Cloud Strategy (R150k - R500k)</h4>
            <ul class="action-list">
                <li>Deploy Microsoft 365 E3 or Google Workspace Business Plus</li>
                <li>Use AWS EC2 Reserved Instances or Azure VMs for steady workloads</li>
                <li>Implement Veeam or Datto for enterprise-grade backups</li>
                <li>Engage a Managed Service Provider (MSP) for 24/7 monitoring</li>
            </ul>
            <strong>Est. Cost:</strong> R15,000 - R40,000/month</div>`);
    } else {
        recs.push(`<div class="recommendation-item"><h4>🏢 Enterprise Cloud Strategy (R500k+)</h4>
            <ul class="action-list">
                <li>Engage AWS/Azure Professional Services for full migration</li>
                <li>Implement Enterprise Support (24/7 dedicated Technical Account Manager)</li>
                <li>Deploy Advanced Threat Protection & SIEM (e.g., CrowdStrike, SentinelOne)</li>
                <li>Multi-region Active-Active Disaster Recovery setup</li>
                <li>Custom compliance auditing (SOC2, ISO 27001 automation)</li>
            </ul>
            <strong>Est. Cost:</strong> R50,000+/month</div>`);
    }

    const html = `
        <div id="reportContent">
            <h2 style="text-align:center;">☁️ Cloud Readiness Report</h2>
            <p><strong>Company:</strong> ${document.getElementById('companyName').value} | <strong>Employees:</strong> ${employeeCount}</p>
            <p><strong>Resources:</strong> ${resources.length > 0 ? resources.join(', ') : 'None selected'}</p>
            <p><strong>Budget:</strong> ${budget} | <strong>Timeline:</strong> ${document.getElementById('timeline').value}</p>

            <h3>📋 Cloud Migration Recommendations</h3>
            ${recs.join('')}
        </div>
        <button class="download-btn" onclick="downloadPDF()">📥 Download Cloud PDF Report</button>
    `;

    document.getElementById('finalResult').classList.remove('hidden');
    document.getElementById('finalResult').innerHTML = html;
    document.getElementById('finalResult').scrollIntoView({ behavior: 'smooth' });
}

function downloadPDF() {
    const element = document.getElementById('reportContent');

    // Try the fancy PDF library first
    if (typeof html2pdf !== 'undefined') {
        try {
            const opt = {
                margin: 10,
                filename: 'Cloud-Readiness-Report.pdf',
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