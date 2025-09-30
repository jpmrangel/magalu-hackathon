import axios from 'axios';

export const api = axios.create({
    //baseURL: 'http://localhost:8080', 
    baseURL: 'http://201.23.71.90:8080',
});