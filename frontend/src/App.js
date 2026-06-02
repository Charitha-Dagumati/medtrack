import React, { useEffect, useState } from "react";
import axios from "axios";
import Login from "./pages/Login";

const API_URL = "http://localhost:8080/medicine";

function App() {
  const [token, setToken] = useState(
    localStorage.getItem("token")
  );

  const [medicines, setMedicines] = useState([]);
  const [search, setSearch] = useState("");

  const [formData, setFormData] = useState({
    name: "",
    category: "",
    quantity: "",
    price: "",
    expiryDate: ""
  });

  const [editingId, setEditingId] = useState(null);

  

  // ✅ Axios config with JWT
  const authConfig = {
    headers: {
      Authorization: `Bearer ${token}`
    }
  };

  useEffect(() => {
    if (token) {
      fetchMedicines();
    }
  }, [token]);

  const fetchMedicines = () => {
    axios.get(API_URL, authConfig)
      .then((res) => setMedicines(res.data))
      .catch((err) => console.log(err));
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const addOrUpdateMedicine = () => {
    if (editingId) {
      axios.put(`${API_URL}/${editingId}`, formData, authConfig)
        .then(() => {
          fetchMedicines();
          resetForm();
        })
        .catch((err) => console.log(err));
    } else {
      axios.post(API_URL, formData, authConfig)
        .then(() => {
          fetchMedicines();
          resetForm();
        })
        .catch((err) => console.log(err));
    }
  };

  const deleteMedicine = (id) => {
    if (window.confirm("Delete this medicine?")) {
      axios.delete(`${API_URL}/${id}`, authConfig)
        .then(() => fetchMedicines())
        .catch((err) => console.log(err));
    }
  };

  const editMedicine = (medicine) => {
    setEditingId(medicine.id);
    setFormData({
      name: medicine.name,
      category: medicine.category,
      quantity: medicine.quantity,
      price: medicine.price,
      expiryDate: medicine.expiryDate
    });
  };

  const resetForm = () => {
    setEditingId(null);
    setFormData({
      name: "",
      category: "",
      quantity: "",
      price: "",
      expiryDate: ""
    });
  };

  const isExpiringSoon = (expiryDate) => {
    const today = new Date();
    const expiry = new Date(expiryDate);
    const diffDays = (expiry - today) / (1000 * 60 * 60 * 24);
    return diffDays <= 30;
  };

  // 🔐 LOGIN SCREEN
  if (!token) {
    return <Login setToken={setToken} />;
  }

  return (
    <div className="container mt-4">

      {/* HEADER */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1>Medicine Tracker Dashboard</h1>

        <button
          className="btn btn-danger"
          onClick={() => {
            localStorage.removeItem("token");
            setToken(null);
          }}
        >
          Logout
        </button>
      </div>

      {/* FORM */}
      <h2>{editingId ? "Edit Medicine" : "Add Medicine"}</h2>

      <input
        name="name"
        placeholder="Name"
        className="form-control mb-2"
        value={formData.name}
        onChange={handleChange}
      />

      <input
        name="category"
        placeholder="Category"
        className="form-control mb-2"
        value={formData.category}
        onChange={handleChange}
      />

      <input
        name="quantity"
        type="number"
        placeholder="Quantity"
        className="form-control mb-2"
        value={formData.quantity}
        onChange={handleChange}
      />

      <input
        name="price"
        type="number"
        placeholder="Price"
        className="form-control mb-2"
        value={formData.price}
        onChange={handleChange}
      />

      <input
        name="expiryDate"
        type="date"
        className="form-control mb-2"
        value={formData.expiryDate}
        onChange={handleChange}
      />

      <button
        className="btn btn-primary"
        onClick={addOrUpdateMedicine}
      >
        {editingId ? "Update Medicine" : "Add Medicine"}
      </button>

      {editingId && (
        <button
          className="btn btn-secondary ms-2"
          onClick={resetForm}
        >
          Cancel
        </button>
      )}

      {/* STATS */}
      <div className="row mt-4 mb-4">
        <div className="col-md-3">
          <div className="card text-center p-3">
            <h5>Total</h5>
            <h3>{medicines.length}</h3>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card text-center p-3">
            <h5>Low Stock</h5>
            <h3>{medicines.filter(m => m.quantity < 10).length}</h3>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card text-center p-3">
            <h5>Expiring Soon</h5>
            <h3>{medicines.filter(m => isExpiringSoon(m.expiryDate)).length}</h3>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card text-center p-3">
            <h5>Value</h5>
            <h3>
              ₹{medicines.reduce(
                (t, m) => t + m.price * m.quantity,
                0
              )}
            </h3>
          </div>
        </div>
      </div>

      {/* TABLE */}
      <input
        className="form-control mb-3"
        placeholder="Search Medicine"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Category</th>
            <th>Qty</th>
            <th>Price</th>
            <th>Expiry</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {medicines
            .filter(m =>
              m.name.toLowerCase().includes(search.toLowerCase())
            )
            .map((m) => {
              let status = "OK";
              if (m.quantity < 10) status = "Low Stock";
              if (isExpiringSoon(m.expiryDate)) status = "Expiring Soon";

              return (
                <tr key={m.id}>
                  <td>{m.id}</td>
                  <td>{m.name}</td>
                  <td>{m.category}</td>
                  <td>{m.quantity}</td>
                  <td>₹{m.price}</td>
                  <td>{m.expiryDate}</td>
                  <td>{status}</td>
                  <td>
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => editMedicine(m)}
                    >
                      Edit
                    </button>

                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => deleteMedicine(m.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              );
            })}
        </tbody>
      </table>
    </div>
  );
}

export default App;