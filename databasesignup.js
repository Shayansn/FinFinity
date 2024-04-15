const express = require('express');
const mysql = require('mysql2/promise');
const bodyParser = require('body-parser');

const app = express();

// Parse URL-encoded bodies (as sent by HTML forms)
app.use(bodyParser.urlencoded({ extended: true }));

// Parse JSON bodies (as sent by API clients)
app.use(bodyParser.json());

const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'finfinity',
    database: 'FinFinity'
});

app.post('/signup', async (req, res) => {
    const { firstName, lastName, email, password } = req.body;
    
    try {
        // Get a connection from the pool
        const connection = await pool.getConnection();
        
        // Insert data into the database
        await connection.query('INSERT INTO Users (FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?)', [firstName, lastName, email, password]);
        
        // Release the connection
        connection.release();
        
        // Respond with success message
        res.send('User registered successfully!');
    } catch (error) {
        console.error('Error registering user:', error);
        res.status(500).send('Error registering user');
    }
});



// Start the server
const port = 4001;
app.listen(port, () => {
    console.log(`Server is listening on port ${port}`);
});
