document.getElementById('addStockForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const tickerSymbol = document.getElementById('tickerSymbol').value;
    const companyName = document.getElementById('companyName').value;

    const stockDTO = {
        tickerSymbol: tickerSymbol,
        companyName: companyName,
        price: 0.0,
        quantity: 0.0
    };

    try {
        const response = await fetch('http://localhost:8080/stocks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stockDTO)
        });

        if (response.ok) {
            alert('Stock added successfully!');
            window.location.href = 'index.html'; // Redirect to the main page
        } else {
            alert('Failed to add stock. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred. Please try again.');
    }
});
