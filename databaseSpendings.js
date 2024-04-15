import express from 'express';
import mysql from 'mysql2/promise';
import cors from 'cors';

const app = express();
const port = 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());

const pool = mysql.createPool({
    host: 'localhost',
    user: 'testaccount',
    password: 'SE3354',
    database: 'testDB'
});

app.post('/submit-spending', async (req, res) => {
    try {
        const { amount, category } = req.body;
        const connection = await pool.getConnection();
        await connection.query('INSERT INTO NewSpendings (amount, category) VALUES (?, ?)', [amount,category]);
        connection.release();
        res.status(200).send('Spending submitted successfully');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to submit spending');
    }
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
