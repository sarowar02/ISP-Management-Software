import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import "./App.css";

import ProtectedRoute from "./components/ProtectedRoute";
import Login from "./pages/Login";

import Users from "./pages/Users";
import Packages from "./pages/Packages";
import Bills from "./pages/BIlls";
import Dashboard from "./pages/Dashboard";
import Landing from "./pages/Landing";

function App() {

        
        const token = localStorage.getItem("token");
  return (
    <div className="landing">
      <BrowserRouter>
        <div className="container">



          {/* Sidebar */}
            {token && (
              <div className="sidebar">
                  <Link to="/">Home</Link>
                  <Link to="/dashboard">Dashboard</Link>
                  <Link to="/users">Users</Link>
                  <Link to="/packages">Packages</Link>
                  <Link to="/bills">Bills</Link>
                  
              </div>
            )}

          {/* Content */}

          <div className="content" style={{marginLeft : token ? "221px" : "0px"}}>
            <Routes>
              <Route path="/" element={<Landing />} />
              <Route path="/login" element={<Login />} />

              {/* PROTECTED */}
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
              } />
              
              <Route path="/users" element={
              <ProtectedRoute>
                <Users />
              </ProtectedRoute>
              } />
              
              <Route path="/packages" element={
              <ProtectedRoute>
                <Packages />
              </ProtectedRoute>
              } />
              
              <Route path="/bills" element={
              <ProtectedRoute>
                <Bills />
              </ProtectedRoute>
              } />

              

            </Routes>
          </div>

        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;