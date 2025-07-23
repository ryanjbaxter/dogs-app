import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './DogList.css';

function DogList() {
  const [dogs, setDogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [dogToDelete, setDogToDelete] = useState(null);

  useEffect(() => {
    fetchDogs();
  }, []);

  const fetchDogs = async () => {
    try {
      setLoading(true);
      const response = await axios.get('/api/dogs');
      setDogs(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch dogs: ' + (err.response?.data?.message || err.message));
      console.error('Error fetching dogs:', err);
    } finally {
      setLoading(false);
    }
  };

  const confirmDelete = (dog) => {
    setDogToDelete(dog);
    setShowDeleteModal(true);
  };

  const handleDelete = async () => {
    if (!dogToDelete) return;

    try {
      setLoading(true);
      await axios.delete(`/api/dogs/${dogToDelete.id}`);
      setShowDeleteModal(false);
      setDogToDelete(null);
      fetchDogs(); // Refresh the list
      setError(null);
    } catch (err) {
      setError('Failed to delete dog: ' + (err.response?.data?.message || err.message));
      console.error('Error deleting dog:', err);
    } finally {
      setLoading(false);
    }
  };

  const cancelDelete = () => {
    setShowDeleteModal(false);
    setDogToDelete(null);
  };

  return (
    <div className="dog-list">
      <div className="list-header">
        <h2>Dogs List</h2>
        <Link to="/dogs/new" className="spring-button">Add New Dog</Link>
      </div>

      {loading && <div className="loading">Loading dogs...</div>}

      {error && <div className="error-message">{error}</div>}

      {!loading && dogs.length === 0 && (
        <div className="no-dogs">
          <p>No dogs found. Add a new dog to get started.</p>
          <Link to="/dogs/new" className="spring-button">Add New Dog</Link>
        </div>
      )}

      {!loading && dogs.length > 0 && (
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Breed</th>
              <th>Age</th>
              <th>Weight</th>
              <th>Medical Conditions</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {dogs.map(dog => (
              <tr key={dog.id}>
                <td>{dog.id}</td>
                <td>{dog.name}</td>
                <td>{dog.breed}</td>
                <td>{dog.age}</td>
                <td>{dog.weight} lbs</td>
                <td>{dog.existingMedicalConditions ? 'Yes' : 'No'}</td>
                <td className="actions">
                  <Link to={`/dogs/${dog.id}/edit`} className="edit-button">Edit</Link>
                  <button onClick={() => confirmDelete(dog)} className="delete-button">Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* Delete Confirmation Modal */}
      {showDeleteModal && (
        <div className="modal-backdrop">
          <div className="modal">
            <h3>Confirm Delete</h3>
            <p>Are you sure you want to delete {dogToDelete?.name}?</p>
            <div className="modal-actions">
              <button onClick={handleDelete} className="delete-button">Delete</button>
              <button onClick={cancelDelete} className="cancel-button">Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default DogList;
