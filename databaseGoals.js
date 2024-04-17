import express from 'express';
import mysql from 'mysql2/promise';
import cors from 'cors';

const app = express();
const port = 4012;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());

const pool = mysql.createPool({
    host: 'localhost',
    user: 'testaccount',
    password: 'SE3354',
    database: 'testDB'
});

app.post('/submit-goal', async (req, res) => {
    try {
        const { title, description, targetAmount, deadline, priority } = req.body;

        console.log('Title:', title);
        console.log('Description:', description);
        console.log('Target Amount:', targetAmount);
        console.log('Deadline:', deadline);
        console.log('Priority:', priority);

        if (!title || !description || !targetAmount || !deadline || !priority) {
            throw new Error('Missing required fields');
        }

        const connection = await pool.getConnection();
        await connection.query('INSERT INTO FinancialGoals (title, description, targetAmount, deadline, priority) VALUES (?, ?, ?, ?, ?)', [title, description, targetAmount, deadline, priority]);
        connection.release();
        res.status(200).send('Financial goal submitted successfully');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to submit financial goal: ' + error.message);
    }
});

app.delete('/delete-goal', async (req, res) => {
    try {
        const { id } = req.body;

        console.log('Goal ID:', id);

        if (!id) {
            throw new Error('Missing required fields');
        }

        const connection = await pool.getConnection();
        await connection.query('DELETE FROM FinancialGoals WHERE id = ?', [id]);
        connection.release();
        res.status(200).send('Financial goal deleted successfully');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to delete financial goal: ' + error.message);
    }
});

app.put('/update-goal', async (req, res) => {
    try {
        const { id, title, description, targetAmount, deadline, priority } = req.body;

        console.log('Goal ID:', id);
        console.log('Title:', title);
        console.log('Description:', description);
        console.log('Target Amount:', targetAmount);
        console.log('Deadline:', deadline);
        console.log('Priority:', priority);

        if (!id || !title || !description || !targetAmount || !deadline || !priority) {
            throw new Error('Missing required fields');
        }

        const connection = await pool.getConnection();
        await connection.query('UPDATE FinancialGoals SET title = ?, description = ?, targetAmount = ?, deadline = ?, priority = ? WHERE id = ?', [title, description, targetAmount, deadline, priority, id]);
        connection.release();
        res.status(200).send('Financial goal updated successfully');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to update financial goal: ' + error.message);
    }
});

app.get('/financial-goals', async (req, res) => {
    try {
        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM FinancialGoals');
        connection.release();
        res.status(200).json(rows);
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to fetch financial goals: ' + error.message);
    }
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
