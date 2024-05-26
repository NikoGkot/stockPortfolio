function getJwtToken() {
  // console.log("Retrieving JWT Token from localStorage"); // Debug statement
  const token = localStorage.getItem("accessToken");
  // console.log("Retrieved JWT Token:", token); // Log the token
  return token;
}

async function fetchStocks() {
  const token = getJwtToken();
  if (!token) {
    console.error("No JWT Token found"); // Log an error if the token is null
    throw new Error("No JWT Token found");
  }

  const response = await fetch("http://localhost:8080/api/stocks", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    console.error("Error Response:", response); // Log the error response
    throw new Error("Failed to fetch stocks");
  }

  const stocks = await response.json();
  return stocks;
}

async function loadModal(url, modalId) {
  const response = await fetch(url);
  const html = await response.text();
  document.getElementById("modalPlaceholder").innerHTML = html;
  $(modalId).modal("show");

  // Add event listeners for modal buttons after loading modal content
  if (modalId === "#addStockModal") {
    document
      .getElementById("addStockButtonInModal")
      .addEventListener("click", addStock);
  } else if (modalId === "#buyStockModal") {
    document
      .getElementById("buyStockButtonInModal")
      .addEventListener("click", buyStock);
  } else if (modalId === "#sellStockModal") {
    document
      .getElementById("sellStockButtonInModal")
      .addEventListener("click", sellStock);
  }

  // For buy and sell modals, populate ticker dropdowns
  if (modalId === "#buyStockModal" || modalId === "#sellStockModal") {
    await populateTickerDropdowns(modalId);
  }
}

async function addStock() {
  const tickerSymbol = document.getElementById("tickerSymbol").value;
  const companyName = document.getElementById("companyName").value;

  const stockDTO = {
    tickerSymbol: tickerSymbol,
    companyName: companyName,
    price: 0.0,
    quantity: 0.0,
  };

  const token = getJwtToken();

  try {
    const response = await fetch("http://localhost:8080/api/stocks", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(stockDTO),
    });

    if (response.ok) {
      alert("Stock added successfully!");
      $("#addStockModal").modal("hide");
      displayStocks(); // Refresh the stocks table
    } else {
      alert("Failed to add stock. Please try again.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred. Please try again.");
  }
}

async function buyStock() {
  const tickerSymbol = document.getElementById("tickerSymbol").value;
  const price = parseFloat(document.getElementById("price").value);
  const quantity = parseFloat(document.getElementById("quantity").value);

  const stockDTO = {
    tickerSymbol: tickerSymbol,
    price: price,
    quantity: quantity,
  };

  const token = getJwtToken();

  try {
    const response = await fetch(
      `http://localhost:8080/stocks/${tickerSymbol}/buy`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(stockDTO),
      }
    );

    if (response.ok) {
      alert("Stock bought successfully!");
      $("#buyStockModal").modal("hide");
      displayStocks(); // Refresh the stocks table
    } else {
      alert("Failed to buy stock. Please try again.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred. Please try again.");
  }
}

async function sellStock() {
  const tickerSymbol = document.getElementById("tickerSymbol").value;
  const price = parseFloat(document.getElementById("price").value);
  const quantity = parseFloat(document.getElementById("quantity").value);

  const stockDTO = {
    tickerSymbol: tickerSymbol,
    price: price,
    quantity: quantity,
  };

  const token = getJwtToken();

  try {
    const response = await fetch(
      `http://localhost:8080/stocks/${tickerSymbol}/sell`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(stockDTO),
      }
    );

    if (response.ok) {
      alert("Stock sold successfully!");
      $("#sellStockModal").modal("hide");
      displayStocks(); // Refresh the stocks table
    } else {
      alert("Failed to sell stock. Please try again.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred. Please try again.");
  }
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

async function populateTickerDropdowns(modalId) {
  const stocks = await fetchStocks();
  const tickerOptions = stocks
    .map(
      (stock) =>
        `<option value="${stock.tickerSymbol}">${stock.tickerSymbol}</option>`
    )
    .join("");

  const tickerDropdown = document.querySelector(`${modalId} #tickerSymbol`);
  tickerDropdown.innerHTML = tickerOptions;
}

document.addEventListener("DOMContentLoaded", displayStocks);

// Polling example: Refresh data every 10 seconds
setInterval(displayStocks, 10000);

document.getElementById("addStockButton").addEventListener("click", () => {
  loadModal("addStock.html", "#addStockModal");
});

document.getElementById("buyStockButton").addEventListener("click", () => {
  loadModal("buyStock.html", "#buyStockModal");
});

document.getElementById("sellStockButton").addEventListener("click", () => {
  loadModal("sellStock.html", "#sellStockModal");
});
