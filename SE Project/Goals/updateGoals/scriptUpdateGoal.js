function changeColor(amount)
{
    var starting = document.getElementById('amount').value;
    var goalAmount = document.getElementById('amount').value;

    if(amount <= (goalAmount * 0.33))
    {
        return '#00FF00';
    }

    return '#000000'
}