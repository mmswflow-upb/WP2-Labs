const prodDiv = document.getElementById("products");
const addProductButton = document.getElementById("addProductButton");
const deleteProductButton = document.getElementById("deleteProductButton");
const modifyProductButton = document.getElementById("modifyProductButton");
const deleteIdSelect = document.getElementById("deleteId");
const modifyIdSelect = document.getElementById("modifyId");
const selectedProductDetails = document.getElementById("selectedProductDetails");
const productDetailsContent = document.getElementById("productDetailsContent");

// Global variable to store all products
let allProducts = [];

refreshProducts();

function refreshProducts(){
    fetch('/api/v1/products')
        .then(response => response.json())
        .then(data => {
            allProducts = data;
            showProducts(data);
            populateProductIdSelects(data);
        });
}

function populateProductIdSelects(products) {
    // Clear existing options except the first one
    deleteIdSelect.innerHTML = '<option value="">Select Product ID</option>';
    modifyIdSelect.innerHTML = '<option value="">Select Product ID</option>';
    
    // Add options for each product
    products.forEach(product => {
        const deleteOption = document.createElement('option');
        deleteOption.value = product.id;
        deleteOption.textContent = `ID: ${product.id} - ${product.name}`;
        deleteIdSelect.appendChild(deleteOption);
        
        const modifyOption = document.createElement('option');
        modifyOption.value = product.id;
        modifyOption.textContent = `ID: ${product.id} - ${product.name}`;
        modifyIdSelect.appendChild(modifyOption);
    });
}

function showProducts(products){
    // Also, useful to see difference between imperative programming in Vanilla JS vs declarative in REACT
    prodDiv.innerHTML="";
    for(let i = 0; i< products.length; i++ ){
        let addedProd = document.createElement("div");
        addedProd.innerHTML = `${products[i].id} ${products[i].name} ${products[i].price}`;
        prodDiv.appendChild(addedProd);
    }
}

// Event listener for modifyId select change
modifyIdSelect.addEventListener('change', function() {
    const selectedId = this.value;
    
    if (selectedId) {
        // Find the selected product
        const selectedProduct = allProducts.find(product => product.id == selectedId);
        
        if (selectedProduct) {
            // Display product details
            productDetailsContent.innerHTML = `
                <p><strong>Name:</strong> ${selectedProduct.name}</p>
                <p><strong>Type:</strong> ${selectedProduct.type}</p>
                <p><strong>Description:</strong> ${selectedProduct.description}</p>
                <p><strong>Price:</strong> ${selectedProduct.price}</p>
            `;
            selectedProductDetails.style.display = 'block';
            
            // Pre-fill the form fields with current values
            document.getElementById("modifyName").value = selectedProduct.name;
            document.getElementById("modifyType").value = selectedProduct.type;
            document.getElementById("modifyDescription").value = selectedProduct.description;
            document.getElementById("modifyPrice").value = selectedProduct.price;
        }
    } else {
        // Hide details if no product is selected
        selectedProductDetails.style.display = 'none';
        
        // Clear form fields
        document.getElementById("modifyName").value = '';
        document.getElementById("modifyDescription").value = '';
        document.getElementById("modifyPrice").value = '';
    }
});

addProductButton.onclick = () => {
    const newProduct = {
        name: document.getElementById("addName").value,
        type: document.getElementById("addType").value,
        description: document.getElementById("addDescription").value,
        price: parseFloat(document.getElementById("addPrice").value)
    };
    
    fetch('/api/v1/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newProduct),
    })
    .then(response => {
        if (response.ok) {
            // Clear form fields
            document.getElementById("addName").value = '';
            document.getElementById("addDescription").value = '';
            document.getElementById("addPrice").value = '';
            
            alert("Product added successfully!");
            
            // Refresh the product list
            refreshProducts();
        } else {
            console.error('Error adding product:', response.statusText);
            alert("Error adding product: " + response.statusText);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Error adding product: " + error.message);
    });
};

deleteProductButton.onclick = () => {
    const productId = deleteIdSelect.value;
    
    if (!productId) {
        alert("Please select a product ID to delete");
        return;
    }
    
    fetch(`/api/v1/products/${productId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            // Show success message
            alert("Product deleted successfully!");
            
            // Reset the select
            deleteIdSelect.value = '';
            
            // Refresh the product list
            refreshProducts();
        } else {
            console.error('Error deleting product:', response.statusText);
            alert("Error deleting product: " + response.statusText);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Error deleting product: " + error.message);
    });
};

modifyProductButton.onclick = () => {
    const productId = modifyIdSelect.value;
    
    if (!productId) {
        alert("Please select a product ID to modify");
        return;
    }
    
    const updatedProduct = {
        name: document.getElementById("modifyName").value,
        type: document.getElementById("modifyType").value,
        description: document.getElementById("modifyDescription").value,
        price: parseFloat(document.getElementById("modifyPrice").value)
    };
    
    fetch(`/api/v1/products/${productId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedProduct),
    })
    .then(response => {
        if (response.ok) {
            // Show success message
            alert("Product updated successfully!");
            
            // Reset form and hide details
            modifyIdSelect.value = '';
            document.getElementById("modifyName").value = '';
            document.getElementById("modifyDescription").value = '';
            document.getElementById("modifyPrice").value = '';
            selectedProductDetails.style.display = 'none';
            
            // Refresh the product list
            refreshProducts();
        } else if (response.status === 404) {
            alert("Product not found");
        } else {
            console.error('Error updating product:', response.statusText);
            alert("Error updating product: " + response.statusText);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Error updating product: " + error.message);
    });
};

