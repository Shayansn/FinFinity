document.addEventListener('DOMContentLoaded', () => {
    const hexagons = document.querySelectorAll('.hexagon');



    // Ensure this code is below the previous block of code to overwrite the event listener for the Spending hexagon
    const spendingHexagon = document.getElementById('spending-hexagon');
    if (spendingHexagon) {
        spendingHexagon.addEventListener('click', () => {
            window.location.href = 'Spendings/indexSpendings.html'; // Make sure the path is correct
        });
    }
});
