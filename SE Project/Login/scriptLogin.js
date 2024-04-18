// Function to expand the badge on mouse enter
function expandBadge() {
    const badge = document.querySelector('.recaptcha-badge');
    const link = badge.querySelector('.recaptcha-link');
    badge.style.width = '150px'; // Expanded width
    link.style.display = 'block'; // Show the link
}

// Function to collapse the badge on mouse leave
function collapseBadge() {
    const badge = document.querySelector('.recaptcha-badge');
    const link = badge.querySelector('.recaptcha-link');
    badge.style.width = '40px'; // Collapsed width
    link.style.display = 'none'; // Hide the link
}

// Event listeners for the badge hover effect
document.addEventListener('DOMContentLoaded', function() {
    const badge = document.querySelector('.recaptcha-badge');
    badge.addEventListener('mouseenter', expandBadge);
    badge.addEventListener('mouseleave', collapseBadge);
});

function simulateSocialLogin(platform) {
    
    
    // Example URLs that point to the OAuth login pages for each service.
    // These URLs would be replaced with your OAuth endpoints in a real application.
    const authUrls = {
        'Twitter': 'https://twitter.com/i/oauth2/authorize',
        'Facebook': 'https://www.facebook.com/dialog/oauth',
        'Google': 'https://accounts.google.com/o/oauth2/auth'
    };
    
    // Redirecting to the authorization URL
    if (authUrls[platform]) {
        window.location.href = authUrls[platform]; // This would redirect the user to the auth URL.
    } else {
        console.error('Unsupported platform');
    }
}

function handleLogin(event) {
    event.preventDefault(); // Prevent the default form submit action
    
    // Simulate login validation (Replace this with actual validation logic)
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    if (username === 'admin' && password === 'admin') { // Replace with actual validation
        // Redirect to the dashboard page if login is successful
        window.location.href = '../Dashboard/indexDashboard.html'; // The actual URL of your dashboard page
    } else {
        // Show an error message or handle login failure
        alert('Login failed! Please check your username and password.');
    }
}
