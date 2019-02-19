import axios from 'axios'

export const signUp = data => axios.get('http://localhost:8081/register', data)
export const logIn = data =>
  axios.get('https://jsonplaceholder.typicode.com/users/1', data)
