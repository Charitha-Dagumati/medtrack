import React, { useEffect, useState } from "react";
import API from "../services/api";

function Dashboard() {
  const [medicines, setMedicines] = useState([]);

  useEffect(() => {
    loadMedicines();
  }, []);

  const loadMedicines = async () => {
    try {
      const res = await API.get("/medicine");
      setMedicines(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  const isExpiringSoon = (expiryDate) => {
    const today = new Date();
    const expiry = new Date(expiryDate);

    const diffDays =
      (expiry - today) / (1000 * 60 * 60 * 24);

    return diffDays <= 30;
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Medicine Dashboard</h2>
      <div style={{
  display: "flex",
  gap: "20px",
  marginBottom: "20px"
}}>

  <div style={{
    border: "1px solid gray",
    padding: "15px",
    width: "200px"
  }}>
    <h3>Total Medicines</h3>
    <h2>{medicines.length}</h2>
  </div>

  <div style={{
    border: "1px solid gray",
    padding: "15px",
    width: "200px"
  }}>
    <h3>Low Stock</h3>
    <h2>
      {medicines.filter(m => m.quantity < 10).length}
    </h2>
  </div>

  <div style={{
    border: "1px solid gray",
    padding: "15px",
    width: "200px"
  }}>
    <h3>Expiring Soon</h3>
    <h2>
      {
        medicines.filter(m =>
          isExpiringSoon(m.expiryDate)
        ).length
      }
    </h2>
  </div>

</div>

  <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Expiry Date</th>
          </tr>
        </thead>

        <tbody>
          {medicines.map((m) => (
            <tr key={m.id}>
              <td>{m.name}</td>
              <td>{m.category}</td>
              <td>{m.quantity}</td>
              <td>{m.price}</td>
              <td>{m.expiryDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Dashboard;