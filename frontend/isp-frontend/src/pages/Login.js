import { useState } from "react";
import API from "../services/api";
import Navbar from "../components/Navbar";

function Login() {
  const [form, setForm] = useState({ username: "", password: "" });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const login = () => {
    API.post("/login", form)
      .then(res => {
        localStorage.setItem("token", res.data.data);
        window.location.href = "/dashboard";
      })
      .catch(() => alert("Invalid credentials"));
  };

  return (

    <div>
      <Navbar />
      <div style={{ padding: "0 30px", textAlign: "center" }}>
      <h2>Admin Login</h2>

      <input name="username" placeholder="Username" onChange={handleChange} />
      <br />
      <input name="password" placeholder="Password" type="password" onChange={handleChange} />
      <br />
      <button type="submit" onClick={login}>Login</button>
    </div>

    </div>
    
  );
}

export default Login;