document.getElementById("sellStockForm").addEventListener("submit", (event) => {
  event.preventDefault();

  const tickerSymbol = document.getElementById("sellTickerSymbol").value;
  const quantity = parseFloat(document.getElementById("sellQuantity").value);

  const stockDTO = { tickerSymbol, companyName: "", price: 0, quantity };

  fetch("/stocks/" + tickerSymbol + "/sell", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(stockDTO),
  }).then((response) => {
    if (response.ok) {
      $("#sellStockModal").modal("hide");
      loadStocks();
    } else {
      alert("Failed to sell stock");
    }
  });
});

async function sellStock() {
  const tickerSymbol = document.querySelector(
    "#sellStockModal #tickerSymbol"
  ).value;
  const sellPrice = parseFloat(
    document.querySelector("#sellStockModal #sellPrice").value
  );
  const quantity = parseFloat(
    document.querySelector("#sellStockModal #quantity").value
  );

  const stockDTO = { tickerSymbol, price: sellPrice, quantity };

  const response = await fetch(
    `http://localhost:8080/stocks/${tickerSymbol}/sell`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(stockDTO),
    }
  );

  if (response.ok) {
    $("#sellStockModal").modal("hide");
    displayStocks();
  } else {
    console.error("Failed to sell stock");
  }
}
