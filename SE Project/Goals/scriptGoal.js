/*function submitGoal(event)
{
  event.preventDefault();

  const category = document.getElementById('category').value;
  const amount = document.getElementById('amount').value;
  const date = document.getElementById('date').value;

  console.log('Attempting to send data:', { category, amount, date });

  fetch('/api/goal', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      category: category,
      amount: amount,
      date: date
    }),
  })
  .then(response => {
    if(!response.ok) {
      throw new Error('No Network Response');
    }
    return response.json();
  })
  .then(data => {
    console.log('Success:', data);
  })
  .catch((error) => {
    console.error('Error', error)
  });
}

document.addEventListener('DOMContentLSoaded', function() {
  const form = document.getElementById('goal');
  if(form) {
    form.addEventListener('submit', submitGoal)
  }
});
*/