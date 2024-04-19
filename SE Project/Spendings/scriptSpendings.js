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

app.get('/spendings', async (req, res) => {
    try {
        const connection = await pool.getConnection();
        const [spendings] = await connection.query('SELECT * FROM NewSpendings');
        connection.release();
        res.json(spendings);
    } catch (error) {
        console.error('Error fetching spendings:', error);
        res.status(500).send('Error fetching spendings');
    }
});

app.delete('/delete-spending/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const connection = await pool.getConnection();
        const [result] = await connection.query('DELETE FROM NewSpendings WHERE id = ?', [id]);
        connection.release();
        if (result.affectedRows === 0) {
            res.status(404).send('No such spending found');
        } else {
            res.status(200).send('Spending deleted successfully');
        }
    } catch (error) {
        console.error('Error deleting spending:', error);
        res.status(500).send('Server error');
    }
});


app.put('/update-spending/:id', async (req, res) => {
    const { id } = req.params;
    const { amount, category, spendingDate } = req.body;

    if (!amount || !category || !spendingDate) {
        return res.status(400).send('All fields are required.');
    }

    try {
        const connection = await pool.getConnection();
        const [results] = await connection.query(
            'UPDATE NewSpendings SET amount = ?, category = ?, spendingDate = ? WHERE id = ?',
            [amount, category, new Date(spendingDate), id]
        );
        connection.release();

        if (results.affectedRows === 0) {
            return res.status(404).send('Spending not found');
        }

        res.send('Spending updated successfully');
    } catch (error) {
        console.error('Error updating spending:', error);
        res.status(500).send('Failed to update spending: ' + error.message);
    }
});

app.post('/submit-spending', async (req, res) => {
    try {
        const { amount, category, spendingDate } = req.body;
        console.log('Amount:', amount);
        console.log('Category:', category);
        console.log('Spending Date:', spendingDate);

        if (!amount || !category || !spendingDate) {
            throw new Error('Missing required fields');
        }

        const connection = await pool.getConnection();
        await connection.query('INSERT INTO NewSpendings (amount, category, spendingDate) VALUES (?, ?, ?)', [amount, category, spendingDate]);
        connection.release();
        res.status(200).send('Spending submitted successfully');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to submit spending: ' + error.message);
    }
});



  // Add event listener for the form submission
  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('spending-form');
    if (form) {
      form.addEventListener('submit', submitSpendings);
    }
  });
