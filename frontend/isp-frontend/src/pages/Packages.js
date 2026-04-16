import { useEffect, useState } from "react";
import API from "../services/api";
import Navbar from "../components/Navbar";

function Packages() {
  const [packages, setPackages] = useState([]);

  const [form, setForm] = useState({
    name: "",
    speed: "",
    price: ""
  });

  useEffect(() => {
    fetchPackages();
  }, []);

  const fetchPackages = () => {
    API.get("/packages")
      .then(res => setPackages(res.data.data))
      .catch(err => console.error(err));
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    API.post("/packages", {
      ...form,
      speed: Number(form.speed),
      price: Number(form.price)
    })
      .then(() => {
        alert("Package created!");
        fetchPackages();
      })
      .catch(err => {
        alert(err.response?.data?.message || "Error");
      });
  };

    const deletePackage = (id) => {
    API.delete(`/packages/${id}`)
      .then(() => {
        alert("Deleted!");
        fetchPackages();
      })
      .catch(err => console.error(err));
  };

  return (
    <div>
      <Navbar />
      <h2>Packages</h2>

      {/* FORM */}
      <form onSubmit={handleSubmit}>
        <input name="name" placeholder="Name" onChange={handleChange} />
        <br />
        <input name="speed" placeholder="Speed (Mbps)" onChange={handleChange} />
        <br />
        <input name="price" placeholder="Price" onChange={handleChange} />
        <br />
        <button type="submit">Add Package</button>
      </form>

      <br />

      {/* TABLE */}
      <table border="1">
        <thead>
          <tr>
            <th>Name</th>
            <th>Speed</th>
            <th>Price</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {packages.map((p) => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>{p.speed} Mbps</td>
              <td>{p.price} ৳</td>
              <td>
                <button onClick={() => deletePackage(p.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Packages;