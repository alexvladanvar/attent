import axios from 'axios'

export const signUp = data => axios.get('http://localhost:8081/register', data)
export const logIn = data => axios.post('http://localhost:8081/login', data)
