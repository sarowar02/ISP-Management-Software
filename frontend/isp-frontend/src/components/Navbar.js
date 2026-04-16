

function Navbar(){

    const token = localStorage.getItem("token");

      const logout = () => {
        localStorage.removeItem("token");
        window.location.href = "/";
        };

     return(
        <div>
            {/* NAVBAR */}
            <div className="navbar">
                <h3>ISP Service</h3>
                <div>
                <a href="#packages">Packages</a>
                <a href="#bill">Check Bill</a>
                { !token ? (<a href="/login">Login</a>) :
                    <button onClick={logout} style={{marginLeft:"15px"}}>Logout</button>

                }
                </div>
            </div>
        </div>
     );
}

export default Navbar;