const express = require('express');
const mysql = require('mysql2/promise');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();

// Correct CORS configuration to allow the frontend server
const corsOptions = {
    origin: 'http://localhost:56835',  // This should match the URL of your frontend
    optionsSuccessStatus: 200
};
app.use(cors(corsOptions));

app.use(bodyParser.urlencoded({ extended: true }));
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
        const connection = await pool.getConnection();
        await connection.query('INSERT INTO Users (FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?)', [firstName, lastName, email, password]);
        connection.release();
        res.send('User registered successfully!');
    } catch (error) {
        console.error('Error registering user:', error);
        res.status(500).send('Error registering user');
    }
});

app.post('/reset-password', async (req, res) => {
    const { email, newPassword } = req.body;
    try {
        const connection = await pool.getConnection();
        const [users] = await connection.query('SELECT * FROM Users WHERE Email = ?', [email]);
        if (users.length === 0) {
            connection.release();
            return res.status(404).send('Email not found');
        }
        await connection.query('UPDATE Users SET Password = ? WHERE Email = ?', [newPassword, email]);
        connection.release();
        res.send('Password updated successfully!');
    } catch (error) {
        console.error('Error updating password:', error);
        res.status(500).send('Error updating password');
    }
});



// Start the server
const port = 4006;
app.listen(port, () => {
    console.log(`Server is listening on port ${port}`);
});
