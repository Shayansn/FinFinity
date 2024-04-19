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

const convertDate = (inputFormat) => {
  function pad(s) { return (s < 10) ? '0' + s : s; }
  var d = new Date(inputFormat);
  return [d.getFullYear(), pad(d.getMonth()+1), pad(d.getDate())].join('-');
}




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




app.delete('/financial-goals/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const connection = await pool.getConnection();
        const [result] = await connection.query('DELETE FROM FinancialGoals WHERE id = ?', [id]);
        connection.release();
        if (result.affectedRows === 0) {
            res.status(404).send('Goal not found');
        } else {
            res.status(200).send('Goal deleted successfully');
        }
    } catch (error) {
        connection?.release();
        console.error('Error:', error);
        res.status(500).send('Failed to delete goal: ' + error.message);
    }
});

app.put('/financial-goals/:id', async (req, res) => {
    const { id } = req.params;
    const { title, description, targetAmount, deadline, priority } = req.body;
    const formattedDeadline = convertDate(deadline);
    try {
        const connection = await pool.getConnection();
        const [results] = await connection.query(
            'UPDATE FinancialGoals SET title = ?, description = ?, targetAmount = ?, deadline = ?, priority = ? WHERE id = ?',
            [title, description, targetAmount, formattedDeadline, priority, id]
        );
        connection.release();

        if (results.affectedRows === 0) {
            return res.status(404).send('Goal not found');
        }
        res.status(200).send('Goal updated successfully');
    } catch (error) {
        console.error('Error updating goal:', error);
        res.status(500).send('Failed to update goal: ' + error.message);
    }
});






app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});

