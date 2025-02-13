import React, { useEffect, useState } from 'react';
import { PhotoIcon, ShoppingCartIcon } from '@heroicons/react/16/solid'
import './App.css';

interface Product {
  Name: string;
  Title: string;
  Price: number;
  URL: string;
}

const App: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [isFileSelected, setIsFileSelected] = useState(false);

  useEffect(() => { // fetch products list from the backend API
    fetch('http://localhost:8080/products/list')
      .then((response) => response.json())
      .then((data) => setProducts(data))
      .catch((error) => console.error('Error fetching data:', error));
  }, []);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => { // update file selection state
    setIsFileSelected(!!(event.target.files && event.target.files.length > 0));
  };

  const handleUpload = (event: React.FormEvent<HTMLFormElement>) => { // upload file to the backend API
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    fetch('http://localhost:8080/uploadFile', {
      method: 'POST',
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => setProducts(data))
      .catch((error) => console.error('Error uploading file:', error));
  };
  
  return (
    <div className="App">
      <div className="file-upload-section">
      <h1 className="text-3xl font-bold text-center mt-8 w-full">Upload Shopping List</h1>
        <div className="file-upload-container">
          <div className="flex flex-col gap-4">
            {/* <input id="file-upload" type="file" />
            <button type="submit">Upload File</button> */}
            <form onSubmit={handleUpload}>
                <input name="file" type="file" id="fileInput" onChange={handleFileChange} multiple />
                <button type="submit" id="uploadButton" disabled={!isFileSelected}>Upload</button>
            </form>
          </div>
        </div>
      </div>

      <h1 className="text-3xl font-bold text-center mt-8">Product List</h1>
      <div className="product-list">
        {products.map((product) => (
          <div key={product.Name} className="product-card">
            <ShoppingCartIcon className="shopping-cart-icon" />
            <h2 className="product-title">{product.Title}</h2>
            <p className="product-name">{product.Name}</p>
            <p className="product-price">Price: ${product.Price.toFixed(2)}</p>
            <a
              href={product.URL}
              target="_blank"
              rel="noopener noreferrer"
              className="product-link"
            >
              View Product
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};

export default App;
