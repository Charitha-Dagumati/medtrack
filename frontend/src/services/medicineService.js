import axios from "axios";

const API_URL = "http://localhost:8080/api/medicine";

export const getMedicines = () => {
  return axios.get(API_URL);
};

export const addMedicine = (medicine) => {
  return axios.post(API_URL, medicine);
};

export const updateMedicine = (id, medicine) => {
  return axios.put(`${API_URL}/${id}`, medicine);
};

export const deleteMedicine = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};