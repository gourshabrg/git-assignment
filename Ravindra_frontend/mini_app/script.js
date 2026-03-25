// js

// default product data (used first time)
let defaultProducts = [
  { id: 1, name: "Laptop", price: 50000, stock: 5, category: "electronics" },
  { id: 2, name: "Shirt", price: 1200, stock: 10, category: "clothing" },
  { id: 3, name: "Book", price: 500, stock: 2, category: "books" },
  { id: 4, name: "Headphones", price: 2000, stock: 0, category: "electronics" },
  { id: 5, name: "Watch", price: 3000, stock: 3, category: "accessories" },
];

// load from localStorage or default
let products = JSON.parse(localStorage.getItem("products")) || defaultProducts;

// save to localStorage
function saveData() {
  localStorage.setItem("products", JSON.stringify(products));
}

// pagination
let currentPage = 1;
let itemsPerPage = 4; // number of item display

// edit tracking
let editId = null;

// render products its takes the array of product object
function renderProducts(data) {
  let container = document.getElementById("products"); // get the html container
  container.innerHTML = "";
  // pagination main logic
  let start = (currentPage - 1) * itemsPerPage;
  let paginated = data.slice(start, start + itemsPerPage);

  if (paginated.length === 0) {
    container.innerHTML = "<p>No products found</p>";
    return;
  }

  // for every product object we can create the html
  paginated.forEach((p) => {
    let div = document.createElement("div"); // creating the div
    div.className = "card"; // give the call name of div

    // inside this div add all the details of product
    div.innerHTML = ` 
      <h3>${p.name}</h3>
      <p>Category: ${p.category}</p>
      <p>Price: ₹${p.price}</p>
      <p>Stock: ${p.stock}</p>
      <button onclick="editProduct(${p.id})">Edit</button>
      <button onclick="deleteProduct(${p.id})">Delete</button>
    `;
    // adding the product into the div
    container.appendChild(div);
  });

  // this show the page no and its product on that page or change the inner html according the page no. logic
  document.getElementById("pageInfo").innerText =
    `Page ${currentPage} of ${Math.ceil(data.length / itemsPerPage)}`;
}
