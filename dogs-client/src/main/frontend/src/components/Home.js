import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
  return (
    <div className="home">
      <div className="spring-card hero">
        <h1>Welcome to the Dogs Management Application</h1>
        <p>A Spring Boot application with React.js frontend</p>
        <Link to="/dogs" className="spring-button">View Dogs</Link>
      </div>

      <div className="features">
        <div className="spring-card feature">
          <h3>View Dogs</h3>
          <p>Browse through our collection of dogs with detailed information.</p>
        </div>

        <div className="spring-card feature">
          <h3>Add New Dogs</h3>
          <p>Register new dogs with their breed, name, age, and other details.</p>
        </div>

        <div className="spring-card feature">
          <h3>Edit Dog Information</h3>
          <p>Update existing dog information as needed.</p>
        </div>

        <div className="spring-card feature">
          <h3>Remove Dogs</h3>
          <p>Remove dogs from the system when necessary.</p>
        </div>
      </div>
    </div>
  );
}

export default Home;
