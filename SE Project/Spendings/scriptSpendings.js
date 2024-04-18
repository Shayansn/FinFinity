function submitSpendings(event) {
    // Prevent the default form submission behavior
    event.preventDefault();

    // Get the values from the form inputs
    const amount = document.getElementById('amount').value;
    const category = document.getElementById('category').value;

    console.log('Attempting to send data:', { amount, category });

    // Perform the fetch() call to send data to the server
    fetch('/api/spendings', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        amount: amount,
        category: category
      }),
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log('Success:', data);
      // Optionally, redirect the user or clear the form here
      // window.location.href = 'successPage.html'; // Example redirect
      // document.getElementById('spending-form').reset(); // Example form reset
    })
    .catch((error) => {
      console.error('Error:', error);
    });
  }

  // Add event listener for the form submission
  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('spending-form');
    if (form) {
      form.addEventListener('submit', submitSpendings);
    }
  });
