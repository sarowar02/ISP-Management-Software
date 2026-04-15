import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import "./App.css";

import Users from "./pages/Users";
import Packages from "./pages/Packages";
import Bills from "./pages/BIlls";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <BrowserRouter>
      <div className="container">

        {/* Sidebar */}
        <div className="sidebar">
          <h3>ISP Panel</h3>
          <Link to="/users">Users</Link>
          <Link to="/packages">Packages</Link>
          <Link to="/bills">Bills</Link>
          <Link to="/dashboard">Dashboard</Link>
        </div>

        {/* Content */}
        <div className="content">
          <Routes>
            <Route path="/users" element={<Users />} />
            <Route path="/packages" element={<Packages />} />
            <Route path="/bills" element={<Bills />} />
            <Route path="/dashboard" element={<Dashboard />} />
          </Routes>
        </div>

      </div>
    </BrowserRouter>
  );
}

export default App;