import express from 'express';
import mysql from 'mysql2/promise';
import cors from 'cors';
import bodyParser from 'body-parser';

const app = express();
const port = 5502;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true })); // Parse URL-encoded bodies
app.use(bodyParser.json());

const corsOptions = {
    origin: 'http://127.0.0.1:5502',  // Update the origin to match your frontend URL
    optionsSuccessStatus: 200
};
app.use(cors(corsOptions));

const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'Antidote*10',
    database: 'FinFinity'
});

const convertDate = (inputFormat) => {
    function pad(s) { return (s < 10) ? '0' + s : s; }
    var d = new Date(inputFormat);
    return [d.getFullYear(), pad(d.getMonth()+1), pad(d.getDate())].join('-');
}

// Endpoint to fetch all budget categories
app.get('/budget-categories', async (req, res) => {
    try {
        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM Budget');
        connection.release();
        res.status(200).json(rows);
    } catch (error) {
        console.error('Error fetching budget categories:', error);
        res.status(500).send('Failed to fetch budget categories: ' + error.message);
    }
});

// Endpoint to delete a budget category
app.delete('/budget/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const connection = await pool.getConnection();
        const [result] = await connection.query('DELETE FROM Budget WHERE id = ?', [id]);
        connection.release();
        if (result.affectedRows === 0) {
            res.status(404).send('Category not found');
        } else {
            res.status(200).send('Category deleted successfully');
        }
    } catch (error) {
        connection?.release();
        console.error('Error:', error);
        res.status(500).send('Failed to delete category: ' + error.message);
    }
});

// Endpoint to update the spent and remaining amounts in the Budget table based on spending data
app.put('/update-budget', async (req, res) => {
    try {
        // Retrieve spending data from the NewSpendings table
        const connection = await pool.getConnection();
        const [spendingRows] = await connection.query('SELECT category, SUM(amount) AS totalSpent FROM NewSpendings GROUP BY category');
        
        // Update the Budget table for each spending category
        for (const spendingRow of spendingRows) {
            const { category, totalSpent } = spendingRow;
            const [budgetRow] = await connection.query('SELECT budget_amount, spent_amount FROM Budget WHERE category = ?', [category]);
            
            // Calculate remaining amount
            const { budget_amount, spent_amount } = budgetRow[0];
            const remaining_amount = budget_amount - totalSpent;
            
            // Update spent_amount and remaining_amount in the Budget table
            await connection.query('UPDATE Budget SET spent_amount = ?, remaining_amount = ? WHERE category = ?', [totalSpent, remaining_amount, category]);
        }
        
        connection.release();

        res.status(200).send('Budget updated successfully');
    } catch (error) {
        console.error('Error updating budget:', error);
        res.status(500).send('Failed to update budget: ' + error.message);
    }
});

// Endpoint to add a new budget category
app.post('/budget', async (req, res) => {
    const { category, budgetAmount } = req.body;
    try {
        const connection = await pool.getConnection();
        await connection.query('INSERT INTO Budget (category, budget_amount, spent_amount, remaining_amount) VALUES (?, ?, 0, ?)', [category, budgetAmount, budgetAmount]);
        connection.release();
        res.status(201).send('Budget category added successfully');
    } catch (error) {
        console.error('Error adding budget category:', error);
        res.status(500).send('Failed to add budget category: ' + error.message);
    }
});

// Endpoint to update a budget category
app.put('/budget/:id', async (req, res) => {
    const { id } = req.params;
    const { name, budgetAmount } = req.body;
    try {
        const connection = await pool.getConnection();
        const [budgetRow] = await connection.query('SELECT category, budget_amount FROM Budget WHERE id = ?', [id]);
        const oldCategory = budgetRow[0].category;
        const oldBudgetAmount = budgetRow[0].budget_amount;
        await connection.query('UPDATE Budget SET category = ?, budget_amount = ? WHERE id = ?', [name, budgetAmount, id]);
        
        // Retrieve spending data from the NewSpendings table
        const [spendingRows] = await connection.query('SELECT category, SUM(amount) AS totalSpent FROM NewSpendings WHERE category = ? GROUP BY category', [oldCategory]);
        
        // Update the spent and remaining amounts in the Budget table for the old category
        if (spendingRows.length > 0) {
            const totalSpent = spendingRows[0].totalSpent;
            const remaining_amount = budgetAmount - totalSpent;
            await connection.query('UPDATE Budget SET spent_amount = ?, remaining_amount = ? WHERE category = ?', [totalSpent, remaining_amount, oldCategory]);
        } else {
            await connection.query('UPDATE Budget SET spent_amount = ?, remaining_amount = ? WHERE category = ?', [0, budgetAmount, oldCategory]);
        }
        
        // Update the spending amounts for the new category
        await connection.query('UPDATE NewSpendings SET category = ? WHERE category = ?', [name, oldCategory]);
        
        connection.release();
        res.status(200).send('Budget category updated successfully');
    } catch (error) {
        console.error('Error updating budget category:', error);
        res.status(500).send('Failed to update budget category: ' + error.message);
    }
});

// Start the server
app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
