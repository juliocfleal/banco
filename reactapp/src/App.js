import './App.css';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Person from './components/Person';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import Navbarsup from './components/Navbarsup';
import Account from './components/Account';
import Transition from './components/Transition';


function App() {
  return (

    <div className="App">
      <Router>
            <div>
            <Navbarsup/>
          <Routes>
          <Route exact path="/transition" element={<Transition/>}></Route>
          <Route exact path="/account" element={<Account/>}></Route>
            <Route exact path="/" element={<Person/>}></Route>

          </Routes>
            </div>
      </Router>
    </div>
  );
}

export default App;
