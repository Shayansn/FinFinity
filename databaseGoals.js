import express from 'express';
import mysql from 'mysql2/promise';
import cors from 'cors';
import bodyParser from 'body-parser';

const app = express();
const port = 5501;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true })); // Parse URL-encoded bodies
app.use(bodyParser.json());

const corsOptions = {
    origin: 'http://127.0.0.1:5501',  // Update the origin to match your frontend URL
    optionsSuccessStatus: 200
};
app.use(cors(corsOptions));

const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'Antidote*10',
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
        console.log('Goal Deleted');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to delete financial goal: ' + error.message);
    }
});

app.post('/update-goal', async (req, res) => {
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
        
        // Delete the existing goal with the specified ID
        await connection.query('DELETE FROM FinancialGoals WHERE id = ?', [id]);

        // Add a new goal with the updated information
        await connection.query('INSERT INTO FinancialGoals (title, description, targetAmount, deadline, priority) VALUES (?, ?, ?, ?, ?)', [title, description, targetAmount, deadline, priority]);
        
        connection.release();
        
        res.status(200).send('Financial goal updated successfully');
    } catch (error) {
        console.error('Error:', error);
        res.status(500).send('Failed to update financial goal: ' + error.message);
    }
});


app.get('/update-goal/:id', async (req, res) => {
    try {
        const { id } = req.params;

        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM FinancialGoals WHERE id = ?', [id]);
        connection.release();

        if (rows.length === 0) {
            return res.status(404).send('Goal not found');
        }

        const goal = rows[0];
        goal.deadline = goal.deadline.toISOString().split('T')[0];

        // Send the goal data as JSON response
        res.status(200).json(rows[0]);
    } catch (error) {
        console.error('Error fetching goal:', error);
        res.status(500).send('Failed to fetch goal: ' + error.message);
    }
});

app.get('/financial-goals', async (req, res) => {
    try {
        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM FinancialGoals');
        connection.release();
        res.status(200).json(rows);
    } catch (error) {
        console.error('Error fetching financial goals:', error);
        res.status(500).send('Failed to fetch financial goals: ' + error.message);
    }

    
      
});

app.get('/financial-goals/:id', async (req, res) => {
    const goalId = req.params.id;
    console.log('Goal ID:', goalId); // Add this line to log the goal ID
    try {
        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM FinancialGoals WHERE id = ?', [goalId]);
        connection.release();
        res.status(200).json(rows[0]);
    } catch (error) {
        console.error('Error fetching financial goal:', error);
        res.status(500).send('Failed to fetch financial goal: ' + error.message);
    }
});


app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});

