document.getElementById("buyStockForm").addEventListener("submit", (event) => {
  event.preventDefault();

  const tickerSymbol = document.getElementById("buyTickerSymbol").value;
  const quantity = parseFloat(document.getElementById("buyQuantity").value);
  const price = parseFloat(document.getElementById("buyPrice").value);

  const stockDTO = { tickerSymbol, companyName: "", price, quantity };

  fetch("/stocks/" + tickerSymbol + "/buy", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(stockDTO),
  }).then((response) => {
    if (response.ok) {
      $("#buyStockModal").modal("hide");
      loadStocks();
    } else {
      alert("Failed to buy stock");
    }
  });
});

async function buyStock() {
  const tickerSymbol = document.querySelector(
    "#buyStockModal #tickerSymbol"
  ).value;
  const buyPrice = parseFloat(
    document.querySelector("#buyStockModal #buyPrice").value
  );
  const quantity = parseFloat(
    document.querySelector("#buyStockModal #quantity").value
  );

  const stockDTO = { tickerSymbol, price: buyPrice, quantity };

  const response = await fetch(
    `http://localhost:8080/stocks/${tickerSymbol}/buy`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(stockDTO),
    }
  );

  if (response.ok) {
    $("#buyStockModal").modal("hide");
    displayStocks();
  } else {
    console.error("Failed to buy stock");
  }
}
