async function fetchStocks() {
  const response = await fetch("http://localhost:8080/stocks");
  const stocks = await response.json();
  return stocks;
}

async function displayStocks() {
  const stocks = await fetchStocks();
  const tableBody = document.querySelector("#stocks-table tbody");
  tableBody.innerHTML = ""; // Clear the table

  if (stocks.length === 0) {
    tableBody.innerHTML =
      '<tr><td colspan="5">No stocks available. Please add stocks.</td></tr>';
  } else {
    stocks.forEach((stock) => {
      const row = `
          <tr>
              <td data-column="tickerSymbol">${stock.tickerSymbol}</td>
              <td data-column="companyName">${stock.companyName}</td>
              <td data-column="buyPrice">${stock.price.toFixed(2)}</td>
              <td data-column="quantity">${stock.quantity.toFixed(2)}</td>
              <td data-column="totalValue">${stock.totalValue.toFixed(2)}</td>
          </tr>`;
      tableBody.innerHTML += row;
    });
  }
}

// Call displayStocks to initially load the data
displayStocks();

// Polling example: Refresh data every 10 seconds
setInterval(displayStocks, 10000);
