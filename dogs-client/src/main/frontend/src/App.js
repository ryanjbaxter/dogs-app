import React from 'react';
import { Routes, Route, NavLink } from 'react-router-dom';
import Home from './components/Home';
import DogList from './components/DogList';
import DogForm from './components/DogForm';
import './App.css';

function App() {
  return (
    <div className="app">
      <header className="header">
        <div className="container">
          <h1>Dogs Management</h1>
          <nav>
            <NavLink to="/" end>Home</NavLink>
            <NavLink to="/dogs">Dogs</NavLink>
            <NavLink to="/dogs/new">Add Dog</NavLink>
          </nav>
        </div>
      </header>

      <main className="main">
        <div className="container">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/dogs" element={<DogList />} />
            <Route path="/dogs/new" element={<DogForm />} />
            <Route path="/dogs/:id/edit" element={<DogForm />} />
          </Routes>
        </div>
      </main>

      <footer className="footer">
        <div className="container">
          <p>&copy; {new Date().getFullYear()} Spring Framework</p>
        </div>
      </footer>
    </div>
  );
}

export default App;
