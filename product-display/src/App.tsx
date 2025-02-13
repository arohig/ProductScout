import React, { useEffect, useState } from 'react';
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
  const [view, setView] = useState<'upload' | 'product'>('upload');
  const [item, setItem] = useState('');

  useEffect(() => { // fetch products list from the backend API every time the product view is selected
    if (view === 'product') {
      fetch('http://localhost:8080/products/list')
        .then((response) => response.json())
        .then((data) => setProducts(data))
        .catch((error) => console.error('Error fetching data:', error));
    }
  }, [view]);
  

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

  const handleSubmit = (event: React.MouseEvent<HTMLButtonElement>) => { // submit product name to the backend API
    event.preventDefault();
    fetch('http://localhost:8080/enterItem', {
      method: 'POST',
      headers: {
        'Content-Type': 'text/plain',
      },
      body: item,
    })
      .then((response) => response.json())
      .then((data) => {
        setProducts(data);
      })
      .catch((error) => console.error('Error adding product:', error));
  };

  const toggleView = () => {
    setView(view === 'upload' ? 'product' : 'upload');
  };
  
  return (
    <div className="App">
        <div className="view-toggle">
            <button onClick={toggleView}>
            {view === 'upload' ? 'Go to Product List' : 'Go to Upload Page'}
            </button>
        </div>

    {view === 'upload' ? (
      <div className="upload-sections">
        <div className="file-upload-section">
        <h1 className="text-3xl font-bold text-center mt-8 w-full">Upload Shopping List</h1>
            <div className="file-upload-container">
            <div className="flex flex-col gap-4">
                <form onSubmit={handleUpload}>
                    <input name="file" type="file" id="fileInput" onChange={handleFileChange} multiple />
                    <button type="submit" id="uploadButton" disabled={!isFileSelected}>Upload</button>
                </form>
            </div>
            </div>
        </div>

        <div className="product-enter-section">
            <h1 className="text-3xl font-bold text-center mt-8 w-full">Type a Product Name</h1>
            <div className="product-enter-container">
                <input type="text" id="itemName" name="name" value={item} onChange={(e) => setItem(e.target.value)}/>
                <button onClick={handleSubmit} disabled={!item.trim()}>Submit</button>
            </div>
        </div>
      </div>
    ) : (
      <div className="product-list">
        <div className='list-title'>
            <h1 className="text-3xl font-bold text-center mt-8">Product List</h1>
        </div>
        <div className='products-info'>
        {products.map((product) => (
          <div key={product.Name} className="product-card">
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
      )}
    </div>
)};

export default App;
