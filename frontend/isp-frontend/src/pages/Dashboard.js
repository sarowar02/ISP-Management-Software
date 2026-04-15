import { useEffect, useState } from "react";
import API from "../services/api";

function Dashboard() {
  const [stats, setStats] = useState({});

  useEffect(() => {
    API.get("/dashboard/stats")
      .then(res => setStats(res.data.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div>
      <h2>Dashboard</h2>

      <div style={{ display: "flex", gap: "20px" }}>
        
        <div className="card">
          <h3>Total Users</h3>
          <p>{stats.users}</p>
        </div>

        <div className="card">
          <h3>Total Packages</h3>
          <p>{stats.packages}</p>
        </div>

        <div className="card">
          <h3>Total Bills</h3>
          <p>{stats.bills}</p>
        </div>

        <div className="card">
          <h3>Unpaid Bills</h3>
          <p>{stats.unpaid}</p>
        </div>

        <div className="card">
          <h3>Total Revenue</h3>
          <p>{stats.revenue}</p>
        </div>

        <div className="card">
          <h3>Total Due</h3>
          <p>{stats.due}</p>
        </div>



      </div>
    </div>
  );
}

export default Dashboard;