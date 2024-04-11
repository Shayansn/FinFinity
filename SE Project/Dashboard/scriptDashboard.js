
document.addEventListener('DOMContentLoaded', () => {
    const hexagons = document.querySelectorAll('.hexagon');



    // Ensure this code is below the previous block of code to overwrite the event listener for the Spending hexagon
    const spendingHexagon = document.getElementById('spending-hexagon');
    if (spendingHexagon) {
        spendingHexagon.addEventListener('click', () => {
            window.location.href = '../Spendings/indexSpendings.html';
        });
    }

    const chatbotHexagon = document.getElementById('chatbot-hexagon');
    if(chatbotHexagon)
    {
        chatbotHexagon.addEventListener('click', () =>
        {
            window.location.href = '../Chatbot/indexChatbot.html';
        });
    }

    const logoutHexagon = document.getElementById('logout-hexagon');
    if(logoutHexagon)
    {
        logoutHexagon.addEventListener('click', () =>
        {
            window.location.href = '../Homepage/home.html';
        });
    }
});

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

