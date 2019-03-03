import axios from 'axios'

export const signUp = data =>
  axios.get('https://jsonplaceholder.typicode.com/users/1', data)
export const logIn = data =>
  axios.get('https://jsonplaceholder.typicode.com/users/1', data)
