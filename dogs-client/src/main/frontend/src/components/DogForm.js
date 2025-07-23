import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import './DogForm.css';

function DogForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditMode = !!id;

  const [formData, setFormData] = useState({
    id: null,
    name: '',
    breed: '',
    age: 0,
    weight: 0,
    existingMedicalConditions: false
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (isEditMode) {
      fetchDog();
    }
  }, [id]);

  const fetchDog = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`/api/dogs/${id}`);
      setFormData(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch dog: ' + (err.response?.data?.message || err.message));
      console.error('Error fetching dog:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleNumberChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value === '' ? '' : Number(value)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);

      // Ensure numeric fields are numbers, not strings
      const dogData = {
        ...formData,
        age: Number(formData.age),
        weight: Number(formData.weight)
      };

      if (isEditMode) {
        await axios.put('/api/dogs', dogData);
      } else {
        await axios.post('/api/dogs', dogData);
      }

      navigate('/dogs');
    } catch (err) {
      setError('Failed to save dog: ' + (err.response?.data?.message || err.message));
      console.error('Error saving dog:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="dog-form-container">
      <h2>{isEditMode ? 'Edit Dog' : 'Add New Dog'}</h2>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit} className="dog-form">
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="breed">Breed</label>
          <input
            type="text"
            id="breed"
            name="breed"
            value={formData.breed}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="age">Age</label>
          <input
            type="number"
            id="age"
            name="age"
            value={formData.age}
            onChange={handleNumberChange}
            className="form-control"
            min="0"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="weight">Weight (lbs)</label>
          <input
            type="number"
            id="weight"
            name="weight"
            value={formData.weight}
            onChange={handleNumberChange}
            className="form-control"
            min="0"
            step="0.1"
            required
          />
        </div>

        <div className="form-group checkbox-group">
          <input
            type="checkbox"
            id="existingMedicalConditions"
            name="existingMedicalConditions"
            checked={formData.existingMedicalConditions}
            onChange={handleChange}
          />
          <label htmlFor="existingMedicalConditions">Has Existing Medical Conditions</label>
        </div>

        <div className="form-actions">
          <button type="submit" className="spring-button" disabled={loading}>
            {loading ? 'Saving...' : 'Save Dog'}
          </button>
          <Link to="/dogs" className="cancel-button">Cancel</Link>
        </div>
      </form>
    </div>
  );
}

export default DogForm;
