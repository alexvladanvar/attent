import axios from 'axios'

export const signUp = data => axios.post('http://localhost:8081/register', data)
